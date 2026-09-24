Set-Variable -Name "DOTNET_INSTALL_SCRIPT_URL" -Value "https://builds.dotnet.microsoft.com/dotnet/scripts/v1/dotnet-install.ps1"
Set-Variable -Name "DOTNET_VERSION" -Value "10.0.401"
Set-Variable -Name "INSTALL_DIR" -Value "./install"

Write-Output "Installing Konna project for development"

Write-Output "-- Installing .NET $DOTNET_VERSION"

New-Item -ItemType Directory -Force -Path $INSTALL_DIR | Out-Null
Invoke-WebRequest $DOTNET_INSTALL_SCRIPT_URL -OutFile $INSTALL_DIR\dotnet-install.ps1

.\install\dotnet-install.ps1 -Channel LTS -InstallDir $INSTALL_DIR\dotnet -NoPath -Version $DOTNET_VERSION

Write-Output "-- Installation is succesfull"
