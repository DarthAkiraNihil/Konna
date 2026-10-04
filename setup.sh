#!/usr/bin/env bash

SETUP_DIR=setup

DOTNET_SCRIPT_URL=https://builds.dotnet.microsoft.com/dotnet/scripts/v1/dotnet-install.sh
DOTNET_VERSION=10.0.401

LLVM_SCRIPT_URL=https://apt.llvm.org/llvm.sh
LLVM_VERSION=20

echo "[[ Setting Konna project for development up ]]"

sudo apt-get update && sudo apt-get upgrade -y
mkdir $SETUP_DIR && cd $SETUP_DIR

echo "-- Installing .NET..."
wget $DOTNET_SCRIPT_URL
chmod +x ./dotnet-install.sh && ./dotnet-install.sh --channel LTS --version $DOTNET_VERSION --no-path --install-dir ./dotnet

echo "-- Instaliing LLVM..."
wget $LLVM_SCRIPT_URL
chmod +x llvm.sh && sudo ./llvm.sh $LLVM_VERSION all

rm -rf ./llvm.sh ./dotnet-install.sh

cd ..

echo "-- Setting up is done!"