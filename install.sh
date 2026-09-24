#!/usr/bin/env bash

#may not work, need to check

DOTNET_INSTALL_SCRIPT_URL="https://builds.dotnet.microsoft.com/dotnet/scripts/v1/dotnet-install.sh"
DOTNET_VERSION="10.0.401"
INSTALL_DIR="./install"

echo "Installing Konna project for development"

echo "-- Installing .NET $DOTNET_VERSION"

mkdir -p $INSTALL_DIR
curl -o $INSTALL_DIR/dotnet-install.sh $DOTNET_INSTALL_SCRIPT_URL

.\install\dotnet-install.sh --channel LTS --install-dir $INSTALL_DIR/dotnet --no-path -version $DOTNET_VERSION
