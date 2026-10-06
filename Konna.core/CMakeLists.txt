cmake_minimum_required(VERSION 3.20)
project(Konna.core LANGUAGES C CXX)

# This file is ugly, but I won't prettify it soon

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

include("./CMakeDeps.cmake")

cmake_path(GET CMAKE_CURRENT_SOURCE_DIR PARENT_PATH KONNA_ROOT_DIR)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

file(
    GLOB_RECURSE
    SOURCE_FILES CONFIGURE_DEPENDS
    "${CMAKE_SOURCE_DIR}/src/*.cpp" "${CMAKE_SOURCE_DIR}/src/*.h"
)

file(
    GLOB_RECURSE
    RPMALLOC_FILES CONFIGURE_DEPENDS
    "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc/*.h" "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc/*.c"
)


set(
    SOURCES
    ${SOURCE_FILES} ${RPMALLOC_FILES}
        src/Konna/system/KLogger.cpp
        src/Konna/system/KLogger.h
        src/Konna/system/KLogHandler.h
        src/Konna/system/KLogLevel.h
        src/Konna/system/KLogFormatter.h
)

# .NET Variables

set(DOTNET_INSTALL_DIR "${KONNA_ROOT_DIR}/setup/dotnet")
set(DOTNET_VERSION "10.0.401")
set(DOTNET_VERSION_PARENT "10.0.12")

add_library(
    Konna.core.static STATIC
    ${SOURCES}
)

add_library(
    Konna.core.shared SHARED
    ${SOURCES}
)

# add_custom_command(
#     TARGET Konna.core POST_BUILD
#     COMMAND ${CMAKE_COMMAND} -E copy_if_different
#     $<TARGET_FILE:Konna.core>
#     "${CMAKE_CURRENT_SOURCE_DIR}/../Tete/bin/Debug/net9.0/"
# )

if(WIN32)
    set(EXE_SUFFIX ".exe")
else()
    set(EXE_SUFFIX "")
endif()

if(CMAKE_HOST_SYSTEM_NAME STREQUAL "Windows")
    set(IS_WIN TRUE)
    set(EXE_SUFFIX ".exe")
else()
    set(EXE_SUFFIX "")
endif()

# =============================================================================
# 2. БАЗОВЫЕ ПУТИ К ВАШЕМУ LLVM (в third_party/llvm23)
# =============================================================================
set(LLVM_ROOT "${KONNA_ROOT_DIR}/setup/llvm")

# Передаем пути к компиляторам
set(CMAKE_C_COMPILER   "${LLVM_ROOT}/bin/clang${EXE_SUFFIX}")
set(CMAKE_CXX_COMPILER "${LLVM_ROOT}/bin/clang++${EXE_SUFFIX}")

# =============================================================================
# 3. НАСТРОЙКИ СИСТЕМЫ И ЛИНКЕРА
# =============================================================================

if(IS_WIN)

    set(CMAKE_SYSTEM_NAME Windows)

    set(CMAKE_C_COMPILER   "${LLVM_ROOT}/bin/x86_64-w64-mingw32-clang.exe")
    set(CMAKE_CXX_COMPILER "${LLVM_ROOT}/bin/x86_64-w64-mingw32-clang++.exe")
    set(CMAKE_LINKER       "${LLVM_ROOT}/bin/x86_64-w64-mingw32-ld.lld.exe")

    set(CMAKE_EXE_LINKER_FLAGS      "${CMAKE_EXE_LINKER_FLAGS} -Wl,-rpath,'$ORIGIN'")
    set(CMAKE_SHARED_LINKER_FLAGS   "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-rpath,'$ORIGIN'")

    include("cmake/win.cmake")

elseif(APPLE)
    message(STATUS "Platform: MacOS")

    set(CMAKE_SYSTEM_NAME Darwin)

    set(CMAKE_C_COMPILER   "clang")
    set(CMAKE_CXX_COMPILER "clang++")
    set(CMAKE_LINKER       "ld.lld")

    include("cmake/unix.cmake")
    set(DOTNET_PLATFORM_SUFFIX "osx-arm64")

else()
    message(STATUS "Platform: Linux")

    set(CMAKE_SYSTEM_NAME Linux)

    set(CMAKE_C_COMPILER   "clang-20")
    set(CMAKE_CXX_COMPILER "clang++-20")
    set(CMAKE_LINKER       "ld.lld-20")

    include("cmake/unix.cmake")
    set(DOTNET_PLATFORM_SUFFIX "linux-x64")

endif()

set(DOTNET_NETHOST_PATH "${DOTNET_INSTALL_DIR}/packs/Microsoft.NETCore.App.Host.${DOTNET_PLATFORM_SUFFIX}")
set(DOTNET_NETHOST_INCLUDE "${DOTNET_NETHOST_PATH}/${DOTNET_VERSION_PARENT}/runtimes/${DOTNET_PLATFORM_SUFFIX}/native")
set(DOTNET_NETHOST_LIB "${DOTNET_NETHOST_INCLIDE}/${DOTNET_HOST_LIB_NAME}")

if (NOT EXISTS "${DOTNET_NETHOST_PATH}")
    message(FATAL_ERROR "Could not find NETHOST at ${DOTNET_NETHOST_PATH}")
    return()
endif()

include("cmake/build.cmake")
