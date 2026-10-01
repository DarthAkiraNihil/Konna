cmake_minimum_required(VERSION 3.20)
project(Konna.core LANGUAGES C CXX)

# This file is ugly, but I won't prettify it soon

add_compile_options(
    -DCMAKE_TOOLCHAIN_FILE=./cmake/toolchain.cmake
)

Include(FetchContent)

FetchContent_Declare(
    Catch2
    GIT_REPOSITORY https://github.com/catchorg/Catch2.git
    GIT_TAG        v3.16.0
)

FetchContent_MakeAvailable(Catch2)

#add_executable(tests test.cpp)
#target_link_libraries(tests PRIVATE Catch2::Catch2WithMain)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

file(
    GLOB_RECURSE
    SOURCE_FILES
    CONFIGURE_DEPENDS
    "src/*.cpp"
    "src/*.h"
)

file(
    GLOB_RECURSE
    RPMALLOC_FILES
    CONFIGURE_DEPENDS
    "extern/rpmalloc/rpmalloc/*.h"
    "extern/rpmalloc/rpmalloc/*.c"
)


set(
    SOURCES
    ${SOURCE_FILES}
    ${RPMALLOC_FILES}
)

set(DOTNET_INSTALL_DIR "${CMAKE_SOURCE_DIR}/../setup/dotnet")
set(DOTNET_VERSION "10.0.401")
set(DOTNET_VERSION_PARENT "10.0.12")

add_library(
    Konna.core.static
    STATIC
    ${SOURCES}
)

add_library(
    Konna.core.shared
    SHARED
    ${SOURCES}
)

# add_custom_command(
#     TARGET Konna.core POST_BUILD
#     COMMAND ${CMAKE_COMMAND} -E copy_if_different
#     $<TARGET_FILE:Konna.core>
#     "${CMAKE_CURRENT_SOURCE_DIR}/../Tete/bin/Debug/net9.0/"
# )

if(WIN32)
    include("cmake/win.cmake")
elseif(APPLE)

    message(STATUS "Platform: MacOS")
    include("cmake/unix.cmake")
    set(DOTNET_PLATFORM_SUFFIX "osx-arm64")

else()

    message(STATUS "Platform: Linux")
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
