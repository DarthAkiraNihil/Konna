cmake_minimum_required(VERSION 3.20)
project(Konna.core LANGUAGES C CXX)

# This file is ugly, but I won't prettify it soon

add_compile_options(
    -DCMAKE_C_COMPILER=clang
    -DCMAKE_CXX_COMPILER=clang++
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



add_compile_options(
    -DCMAKE_C_COMPILER=clang
    -DCMAKE_CXX_COMPILER=clang++
)

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

add_library(
    Konna.core SHARED
    ${SOURCE_FILES}
    ${RPMALLOC_FILES}
)

# add_custom_command(
#     TARGET Konna.core POST_BUILD
#     COMMAND ${CMAKE_COMMAND} -E copy_if_different
#     $<TARGET_FILE:Konna.core>
#     "${CMAKE_CURRENT_SOURCE_DIR}/../Tete/bin/Debug/net9.0/"
# )

set(DOTNET_INSTALL_DIR "${CMAKE_SOURCE_DIR}/../setup/dotnet")
set(DOTNET_VERSION "10.0.401")
set(DOTNET_VERSION_PARENT "10.0.12")

if(WIN32)

    message(STATUS "Platform: Windows")

    if(NOT EXISTS "${DOTNET_INSTALL_DIR}/dotnet.exe")
        message(FATAL_ERROR "Could not find project-wise installed .NET. Please check if you have run the install script")
        return()
    endif()

    set(DOTNET_PLATFORM_SUFFIX "win-x64")
    set(DOTNET_HOST_LIB_NAME "nethost.lib")

elseif(APPLE)

    message(STATUS "Platform: MacOS")

    if(NOT EXISTS "${DOTNET_INSTALL_DIR}/dotnet")
        message(FATAL_ERROR "Could not find project-wise installed .NET. Please check if you have run the install script")
        return()
    endif()

    set(DOTNET_PLATFORM_SUFFIX "osx-arm64")
    set(DOTNET_HOST_LIB_NAME "libnethost.a")

else()

    message(STATUS "Platform: Linux")

    if(NOT EXISTS "${DOTNET_INSTALL_DIR}/dotnet")
        message(FATAL_ERROR "Could not find project-wise installed .NET. Please check if you have run the install script")
        return()
    endif()

    set(DOTNET_PLATFORM_SUFFIX "linux-x64")
    set(DOTNET_HOST_LIB_NAME "libnethost.a")

endif()

set(DOTNET_NETHOST_PATH "${DOTNET_INSTALL_DIR}/packs/Microsoft.NETCore.App.Host.${DOTNET_PLATFORM_SUFFIX}")
set(DOTNET_NETHOST_INCLUDE "${DOTNET_NETHOST_PATH}/${DOTNET_VERSION_PARENT}/runtimes/${DOTNET_PLATFORM_SUFFIX}/native")
set(DOTNET_NETHOST_LIB "${DOTNET_NETHOST_INCLIDE}/${DOTNET_HOST_LIB_NAME}")

if (NOT EXISTS "${DOTNET_NETHOST_PATH}")
    message(FATAL_ERROR "Could not find NETHOST at ${DOTNET_NETHOST_PATH}")
    return()
endif()

set_target_properties(Konna.core PROPERTIES POSITION_INDEPENDENT_CODE ON)

target_include_directories(Konna.core PRIVATE "${DOTNET_NETHOST_INCLUDE}")
target_include_directories(Konna.core PRIVATE "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc")
target_include_directories(Konna.core PRIVATE "${CMAKE_SOURCE_DIR}/src")

target_compile_definitions(Konna.core PRIVATE KONNA_CORE_EXPORTS)

target_link_libraries(Konna.core PRIVATE "${NETHOST_LIB}")

message(STATUS "NETHOST configuration finished. Version: ${DOTNET_VERSION}")

add_executable(
    Tests
    test/main.cpp
        test/Konna/memory/KAllocator_tests.cpp
    ${SOURCE_FILES}
    ${RPMALLOC_FILES}
)

#add_compile_definitions(RPMALLOC_FIRST_CLASS_THREAD=1)


target_include_directories(Tests PRIVATE "${DOTNET_NETHOST_INCLUDE}")
target_include_directories(Tests PRIVATE "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc")
target_include_directories(Tests PRIVATE "${CMAKE_SOURCE_DIR}/src")

target_compile_definitions(Tests PRIVATE KONNA_CORE_EXPORTS)
target_link_libraries(Tests PRIVATE Catch2::Catch2WithMain)
target_link_libraries(Tests PRIVATE -Wl,-Bstatic Konna.core)

#if(WIN32)
#    # Ensure we use standard dynamic runtime libraries
#    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreadedDLL")
#endif()

target_compile_options(Tests PRIVATE -fprofile-instr-generate -fcoverage-mapping -O0 -g)
target_link_options(Tests PRIVATE -fprofile-instr-generate -fcoverage-mapping)
