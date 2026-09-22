cmake_minimum_required(VERSION 3.20)
project(Konna.core LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_compile_options(-DCMAKE_C_COMPILER=clang -DCMAKE_CXX_COMPILER=clang++)

# Создаем динамическую библиотеку (DLL/Shared Object)
add_library(Konna.core SHARED src/main.cpp
        src/type/PrimitiveTypes.h
        src/Konna.h)

# --- АВТОМАТИЗАЦИЯ ДЛЯ C# ---
# После успешной сборки LLVM автоматически скопирует DLL в output-папку C#
add_custom_command(TARGET Konna.core POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E copy_if_different
        $<TARGET_FILE:Konna.core>
        "${CMAKE_CURRENT_SOURCE_DIR}/../Tete/bin/Debug/net8.0/" # Путь к билду C#
)
