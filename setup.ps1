$SETUP_DIR = "setup"

$DOTNET_SCRIPT_URL = "https://builds.dotnet.microsoft.com/dotnet/scripts/v1/dotnet-install.ps1"
$DOTNET_VERSION = "10.0.401"

$LLVM_PACKAGE_URL = "https://github.com/mstorsjo/llvm-mingw/releases/download/20250709/llvm-mingw-20250709-ucrt-x86_64.zip"
$LLVM_PACKAGE_ROOT_DIR = "llvm-mingw-20250709-ucrt-x86_64"

Write-Host "Setting Konna project for development up "

Write-Host "-- Installing .NET..."

New-Item -ItemType Directory -Force -Path $SETUP_DIR | Out-Null
Set-Location "./$SETUP_DIR"

Invoke-WebRequest $DOTNET_SCRIPT_URL -OutFile dotnet-install.ps1

.\dotnet-install.ps1 -Channel LTS -InstallDir .\dotnet -NoPath -Version $DOTNET_VERSION

Write-Host "-- Installing LLVM"

Invoke-WebRequest $LLVM_PACKAGE_URL -OutFile llvm.zip
Expand-Archive llvm.zip -DestinationPath . -Force

Rename-Item -Path ".\$LLVM_PACKAGE_ROOT_DIR" -NewName "llvm"

Remove-Item llvm.zip
Remove-Item dotnet-install.ps1

Set-Location "./.."

Write-Host "-- Set up is done!"
