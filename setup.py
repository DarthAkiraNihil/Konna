import platform
import requests
import subprocess
import zipfile
import tarfile
import os

DOTNET_INSTALL_SCRIPT_WIN = "https://builds.dotnet.microsoft.com/dotnet/scripts/v1/dotnet-install.ps1"
DOTNET_INSTALL_SCRIPT_UNIX = "https://builds.dotnet.microsoft.com/dotnet/scripts/v1/dotnet-install.sh"
DOTNET_INSTALL_SCRIPTS = {
    "windows": DOTNET_INSTALL_SCRIPT_WIN,
    "darwin": DOTNET_INSTALL_SCRIPT_UNIX,
    "linux": DOTNET_INSTALL_SCRIPT_UNIX,
}

DOTNET_VERSION = "10.0.401"

LLVM_PACKAGE_WIN = "https://github.com/mstorsjo/llvm-mingw/releases/download/20260922/llvm-mingw-20260922-ucrt-x86_64.zip"
LLVM_PACKAGE_MAC_OS = "https://github.com/llvm/llvm-project/releases/download/llvmorg-23.1.2/LLVM-23.1.2-macOS-ARM64.tar.xz"
LLVM_PACKAGE_LINUX_X64 = "https://github.com/llvm/llvm-project/releases/download/llvmorg-23.1.2/LLVM-23.1.2-Linux-X64.tar.xz"
LLVM_PACKAGES = {
    "windows": LLVM_PACKAGE_WIN,
    "darwin": LLVM_PACKAGE_MAC_OS,
    "linux": LLVM_PACKAGE_LINUX_X64,
}

LLVM_VERSION = "23.1.2"

CWD = os.getcwd()
SETUP_DIR = f"{CWD}/setup"

def message(s: str = None):
    print("--" if not s else f"-- {s}")
    
def download_file(url, fname):
    with requests.get(url, stream=True) as r:
        
        try:
            r.raise_for_status()
        except Exception as e:
            return e
        
        with open(f'{SETUP_DIR}/{fname}', 'wb+') as f:
            for chunk in r.iter_content(chunk_size=8192): 
                f.write(chunk)
                
    return None


message("Setting Konna project up for development")

os.makedirs("./setup", exist_ok=True)

project_os = platform.system().lower()
project_arch = platform.machine().lower()
project_machine = (project_os, project_arch)

message(f"Project is setting up at {project_machine}")

message()

message("Toolchain info:")
message(f".NET version: {DOTNET_VERSION}")
message(f"LLVM version: {LLVM_VERSION}")

message()

message("Fetching .NET install script")

dotnet_script_url = DOTNET_INSTALL_SCRIPTS[project_machine[0]]
dotnet_script_name = "dotnet-install.ps1" if project_os == "windows" else "dotnet-install.sh"
download_file(dotnet_script_url, dotnet_script_name)

message("Installing .NET")

if project_os == "windows":
    subprocess.run(
        [
            "powershell.exe",
            "-ExecutionPolicy",
            "Bypass",
            "-File",
            f"{SETUP_DIR}/{dotnet_script_name}",
            "-Channel", "LTS",
            "-InstallDir", f"{SETUP_DIR}/dotnet",
            "-NoPath",
            "-Version", f"{DOTNET_VERSION}",
        ],
        shell=True
    ) 
else:
    subprocess.run(
        [
            f"{SETUP_DIR}/{dotnet_script_name}",
            "--channel", "LTS",
            "--install-dir",f"{SETUP_DIR}/dotnet",
            "--no-path",
            "--version", f"{DOTNET_VERSION}"
        ],
        shell=True
    )
    
message(f"Installed .NET {DOTNET_VERSION}")

message()

message("Installing LLVM")
llvm_package_url = LLVM_PACKAGES[project_os]
llvm_package_fname = "llvm.zip" if project_os == "windows" else "llvm.tar.xz"
download_file(llvm_package_url, llvm_package_fname)

llvm_package_path = f"{SETUP_DIR}/{llvm_package_fname}"
llvm_extraction_path = f"{SETUP_DIR}/llvm"

if project_os == "windows":
    
    with zipfile.ZipFile(llvm_package_path, 'r') as pack:
        for member in pack.infolist():
            parts = member.filename.split("/", 1)
            if len(parts) <= 1 or not parts[1]:
                continue

            member.filename = parts[1]
            pack.extract(member, path=llvm_extraction_path)
        
else:
    
    with tarfile.open(llvm_package_path, "r:xz") as pack:
        members = []
        for member in pack.getmembers():
            parts = member.name.split("/", 1)
            if len(parts) <= 1 or not parts[1]:
                continue

            member.name = parts[1]
            members.append(member)

        pack.extractall(path=llvm_extraction_path, members=members)
        
message(f"Installed LLVM {LLVM_VERSION}")

message("Cleaning installation files")

os.remove(f"{SETUP_DIR}/{dotnet_script_name}")
os.remove(f"{SETUP_DIR}/{llvm_package_fname}")

message("Setup completed successfully!")
