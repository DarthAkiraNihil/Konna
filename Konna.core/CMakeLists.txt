cmake_minimum_required(VERSION 3.20)
project(Konna.core LANGUAGES C CXX)

# This file is ugly, but I won't prettify it soon

include("./CMakeDeps.cmake")

add_compile_options(
    -DCMAKE_TOOLCHAIN_FILE=./cmake/toolchain.cmake
)



#add_executable(tests test.cpp)
#target_link_libraries(tests PRIVATE Catch2::Catch2WithMain)

set(KONNA_ROOT_DIR "${CMAKE_SOURCE_DIR}/..")
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

file(
    GLOB_RECURSE
    SOURCE_FILES CONFIGURE_DEPENDS
    "src/*.cpp" "src/*.h"
)

file(
    GLOB_RECURSE
    RPMALLOC_FILES CONFIGURE_DEPENDS
    "extern/rpmalloc/rpmalloc/*.h" "extern/rpmalloc/rpmalloc/*.c"
)


set(
    SOURCES
    ${SOURCE_FILES} ${RPMALLOC_FILES}
)

# .NET Variables

set(DOTNET_INSTALL_DIR "${CMAKE_SOURCE_DIR}/../setup/dotnet")
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
    # --- WINDOWS (llvm-mingw) ---
    set(CMAKE_SYSTEM_NAME Windows)

    # 1. Вместо сырого clang/ld.lld указываем на целевые обертки из llvm-mingw.
    # Они автоматически знают пути к <iostream>, wchar, mbstate_t и UCRT.
    set(CMAKE_C_COMPILER   "${LLVM_ROOT}/bin/x86_64-w64-mingw32-clang${EXE_SUFFIX}")
    set(CMAKE_CXX_COMPILER "${LLVM_ROOT}/bin/x86_64-w64-mingw32-clang++${EXE_SUFFIX}")
    set(CMAKE_LINKER       "${LLVM_ROOT}/bin/x86_64-w64-mingw32-ld${EXE_SUFFIX}")

    # 2. Убираем ВСЕ ручные -isystem, --sysroot и -fuse-ld.
    # Обертки от llvm-mingw сделают это за нас идеально и без ошибок компиляции libc++.
    set(CMAKE_C_FLAGS               "${CMAKE_C_FLAGS}")
    set(CMAKE_CXX_FLAGS             "${CMAKE_CXX_FLAGS}")

    # Позволяем Windows-бинарникам искать dll в своей папке
    set(CMAKE_EXE_LINKER_FLAGS      "${CMAKE_EXE_LINKER_FLAGS} -Wl,-rpath,'$ORIGIN'")
    set(CMAKE_SHARED_LINKER_FLAGS   "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-rpath,'$ORIGIN'")
#    set(CMAKE_SYSTEM_NAME Windows)
#
#    # Задаем целевую архитектуру, которую ожидает llvm-mingw
#    set(WIN_TARGET "x86_64-w64-mingw32")
#    set(CMAKE_C_COMPILER_TARGET   ${WIN_TARGET})
#    set(CMAKE_CXX_COMPILER_TARGET ${WIN_TARGET})
#
#    # В llvm-mingw в папке bin уже лежат готовые обертки x86_64-w64-mingw32-ld
#    # Но мы можем явно форсировать использование ld.lld
#    set(CMAKE_LINKER "${LLVM_ROOT}/bin/ld.lld${EXE_SUFFIX}")
#
#    # Указываем компилятору использовать lld и сообщаем, где лежат системные библиотеки MinGW
#    set(SYSROOT_FLAGS "--sysroot=${LLVM_ROOT}/${WIN_TARGET} -fuse-ld=lld -isystem ${LLVM_ROOT}/include -isystem ${LLVM_ROOT}/include/c++/v1")
#    set(CMAKE_C_FLAGS               "${CMAKE_C_FLAGS} ${SYSROOT_FLAGS}")
#    set(CMAKE_CXX_FLAGS             "${CMAKE_CXX_FLAGS} ${SYSROOT_FLAGS}")
#    set(CMAKE_EXE_LINKER_FLAGS      "${CMAKE_EXE_LINKER_FLAGS} ${SYSROOT_FLAGS}")
#    set(CMAKE_SHARED_LINKER_FLAGS   "${CMAKE_SHARED_LINKER_FLAGS} ${SYSROOT_FLAGS}")

elseif(CMAKE_HOST_SYSTEM_NAME STREQUAL "Darwin")
    # --- macOS (Mac) ---
    set(CMAKE_SYSTEM_NAME Darwin)
    set(CMAKE_LINKER "${LLVM_ROOT}/bin/ld.lld")
    
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -stdlib=libc++")
    set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -stdlib=libc++ -Wl,-rpath,'@loader_path/../setup/llvm/lib'")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -stdlib=libc++ -Wl,-rpath,'@loader_path/../setup/llvm/lib'")

else()
    # --- LINUX / FREEBSD ---
    set(CMAKE_SYSTEM_NAME Linux)

    # 1. Указываем прямые бинарники из официального архива GitHub
    set(CMAKE_C_COMPILER   "${LLVM_ROOT}/bin/clang")
    set(CMAKE_CXX_COMPILER "${LLVM_ROOT}/bin/clang++")
    set(CMAKE_LINKER       "${LLVM_ROOT}/bin/ld.lld")

    # 2. Формируем пути к внутреннейlibc++ самого LLVM
    set(LLVM_INC_DIR "${LLVM_ROOT}/include/c++/v1")
    set(LLVM_LIB_DIR "${LLVM_ROOT}/lib")

    # 3. Флаги компиляции:
    # -nostdinc++ отключает поиск системной библиотеки GCC (убирает конфликты ABI)
    # -isystem насильно подключает инклюды libc++ из папки вашего LLVM
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -nostdinc++ -isystem \"${LLVM_INC_DIR}\" -stdlib=libc++")

    # 4. Флаги линковки:
    # -fuse-ld=lld включает быстрый линкер из архива
    # -L указывает линкеру искать libc++.a / .so внутри папки вашего LLVM
    # -Wl,-rpath запекает этот путь в готовый бинарник, чтобы он работал на любой машине
    # -lm -pthread подключают математику и потоки (обязательно для Linux)
    set(LINKER_FLAGS "-fuse-ld=lld -L\"${LLVM_LIB_DIR}\" -Wl,-rpath,'$ORIGIN/../third_party/llvm23/lib' -lm -pthread")

    set(CMAKE_EXE_LINKER_FLAGS    "${CMAKE_EXE_LINKER_FLAGS} ${LINKER_FLAGS}")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} ${LINKER_FLAGS}")

    # Изолируем CMake от системных путей хоста
    set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
    set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
    set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)
endif()

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
