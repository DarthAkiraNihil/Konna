from collections.abc import Callable
import xml.etree.ElementTree as ET
from pathlib import Path
import subprocess
import argparse
import shutil
import json
import sys

MIN_COVERAGE = 90.0

def chaied_command(shells: list[str], cwd: str) -> int:
    try:
        for shell in shells:
            pipeword = shell[0]
            if pipeword.startswith('!pipe'):
                with open(pipeword.split(":")[1], 'w+', encoding='utf8') as f:
                    subprocess.run(shell[1:], check=True, cwd=cwd, stdout=f)
            else:
                subprocess.run(shell, check=True, cwd=cwd)
        return 0
    except subprocess.CalledProcessError as e:
        return 1
    
class Env:
    CMakePreset = 'cmake_preset'
    KernelBuildDir = 'kernel_build_dir'
    KernelFullBuildDir = 'kernel_full_build_dir'
    TestExecName = 'test_exec_name'
    TestExec = 'test_exec'
    TestExecCwd = 'test_exec_cwd'
    LLVMProfdataExec = 'llvm_profdata_exec'
    LLVMCovExec = 'llvm_cov_exec'
    
def make_envs(
    cmake_preset: str,
    kernel_build_dir: str,
    test_exec_name: str,
    test_exec: str,
    test_exec_cwd: str,
    llvm_profdata_exec: str,
    llvm_cov_exec: str
) -> dict:
    return {
        Env.CMakePreset: cmake_preset,
        Env.KernelBuildDir: kernel_build_dir,
        Env.KernelFullBuildDir: f'./Konna.Kernel/{kernel_build_dir}',
        Env.TestExecName: test_exec_name,
        Env.TestExec: test_exec,
        Env.TestExecCwd: test_exec_cwd,
        Env.LLVMProfdataExec: llvm_profdata_exec,
        Env.LLVMCovExec: llvm_cov_exec
    }
    
envs = {
    'win': make_envs(
        cmake_preset='konna-kernel-release-win',
        kernel_build_dir='build/win/release',
        test_exec_name='./Tests.exe',
        test_exec='./Konna.Kernel/build/win/release/Tests.exe',
        test_exec_cwd='./Konna.Kernel/build/win/release/',
        llvm_profdata_exec='./setup/llvm/bin/llvm-profdata.exe',
        llvm_cov_exec='./setup/llvm/bin/llvm-cov.exe'
    ),
    'linux': make_envs(
        cmake_preset='konna-kernel-release-linux',
        kernel_build_dir='build/linux/release',
        test_exec_name='./Tests',
        test_exec='./Tests',
        test_exec_cwd='./Konna.Kernel/build/linux/release/',
        llvm_profdata_exec='llvm-profdata-20',
        llvm_cov_exec='llvm-cov-20'
    )
}

def superchained_command(chains: list[int]) -> int:
    return 0 if all(map(lambda x: x == 0, chains)) else 1

def process_coverage_data(env: str) -> int:
    c = dict()
    total, covered = 0, 0
    with open(f"./Konna.Kernel/build/{env}/release/coverage.json", "r", encoding="utf8") as f:
        c = json.load(f)
        total += c['data'][0]['totals']['lines']['count']
        covered += c['data'][0]['totals']['lines']['covered']
        
    
    target = Path("./coverage")
    cov_dir = next((p for p in target.iterdir() if p.is_dir()), None)
    if not cov_dir:
        print("CSharp coverage dir does not exist")
        return 1
    
    csharp_cov = ET.parse(f"coverage/{cov_dir.name}/coverage.cobertura.xml")
    root = csharp_cov.getroot()
    
    total += int(root.attrib['lines-valid'])
    covered += int(root.attrib['lines-covered'])
    
    coverage = (covered / total) * 100.0
    if total < MIN_COVERAGE:
        print(f"Coverage requirement is not passed. Expected at least {MIN_COVERAGE}, got {coverage}")
        return 1
    
    print(f"Coverage: {coverage}")
    print("OK")        
    return 0

def create_runner(env: str) -> Callable[[bool, bool], int]:
    runner_env = envs[env]
    
    def runner(make_report: bool, clean: bool) -> int:
        stat = superchained_command(
            [chaied_command(
                [
                    ["cmake", "--preset", runner_env[Env.CMakePreset]],
                    ["cmake", "--build", runner_env[Env.KernelBuildDir], "--clean-first", "--target", "Tests"] \
                        if clean else ["cmake", "--build", runner_env[Env.KernelBuildDir] , "--target", "Tests"],
                    ["cmake", "--build", runner_env[Env.KernelBuildDir], "--target", "Konna.Kernel.shared"]
                ],
                cwd="./Konna.Kernel"
            ),
            chaied_command(
                [[runner_env[Env.TestExec]]], cwd=runner_env[Env.TestExecCwd]
            )]
        )
        
        if stat != 0:
            return stat
        
        cpp_coverage_chain = [
            [runner_env[Env.LLVMProfdataExec], 'merge', '-sparse', 'default.profraw', '-o', 'coverage.profdata'],
            [f'!pipe:{runner_env[Env.KernelFullBuildDir]}/coverage.json', runner_env[Env.LLVMCovExec], 'export', runner_env[Env.TestExecName], f'-instr-profile=./coverage.profdata', '-summary-only', '--ignore-filename-regex=(extern|test|catch2|build|main)'],
        ]
        
        if make_report:
            
            cpp_coverage_chain += [
                [runner_env[Env.LLVMCovExec], 'show', runner_env[Env.TestExecName], f'-instr-profile=./coverage.profdata', '-show-line-counts-or-regions', '-output-dir=./../../../../coverage_report_kernel', '-format=html', '-ignore-filename-regex=(catch2|extern|build|test|main)']         
            ]
            
        stat = chaied_command(
            cpp_coverage_chain,
            cwd=runner_env[Env.KernelFullBuildDir]
        )
        
        if stat != 0:
            return stat
        
        csharp_test_chain = [[
            'dotnet', 'test', '--collect:XPlat Code Coverage', '--results-directory', '.\\coverage'
        ]]
        
        if make_report:
            csharp_test_chain += [
                ["reportgenerator", "-reports:\"./coverage/**/coverage.cobertura.xml\"", "-targetdir:\"coverage_report_csharp\"", "-reporttypes:Html"]
            ]
            
        stat = chaied_command(
            csharp_test_chain,
            cwd="."
        )
        
        if stat != 0:
            return stat
        
        return process_coverage_data(env)
    
    return runner

if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        prog='konna-test-runner',
        description='Konna Test Runner',
    )
    parser.add_argument(
        '-e', '--env',
        required=True, choices=['win', 'linux']
    )
    parser.add_argument(
        '-r', '--report',
        default=False, action='store_true'
    )
    parser.add_argument(
        '-c', '--clean',
        default=False, action='store_true'
    )

    args = parser.parse_args()

    if Path('./coverage').exists():
        shutil.rmtree('./coverage')
    if Path('./coverage_report_kernel').exists():
        shutil.rmtree('./coverage_report_kernel')
    if Path('./coverage_report_csharp').exists():
        shutil.rmtree('./coverage_report_csharp')
        
    sys.exit(
        create_runner(args.env)(args.report, args.clean)
    )
