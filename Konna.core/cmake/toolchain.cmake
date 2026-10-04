set(LLVM_PATH "${CMAKE_SOURCE_DIR}/../setup/llvm") # Change to your absolute path

if (WIN32)
    set(CMAKE_C_COMPILER "${LLVM_PATH}/bin/clang.exe")
    set(CMAKE_CXX_COMPILER "${LLVM_PATH}/bin/clang++.exe")
else()
    set(CMAKE_C_COMPILER "${LLVM_PATH}/bin/clang")
    set(CMAKE_CXX_COMPILER "${LLVM_PATH}/bin/clang++")
endif()

# Force libc++ headers and usage
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -stdlib=libc++ -I${LLVM_PATH}/include/c++/v1" CACHE STRING "" FORCE)

# Force libc++ linking and embed the runtime path (rpath)
set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -stdlib=libc++ -L${LLVM_PATH}/lib -Wl,-rpath,${LLVM_PATH}/lib" CACHE STRING "" FORCE)
set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -stdlib=libc++ -L${LLVM_PATH}/lib -Wl,-rpath,${LLVM_PATH}/lib" CACHE STRING "" FORCE)
