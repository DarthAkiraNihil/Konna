set_target_properties(Konna.core.static PROPERTIES POSITION_INDEPENDENT_CODE ON)

target_include_directories(Konna.core.static SYSTEM PRIVATE "${DOTNET_NETHOST_INCLUDE}")
target_include_directories(Konna.core.static SYSTEM PRIVATE "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc")
target_include_directories(Konna.core.static PUBLIC "${CMAKE_SOURCE_DIR}/src")

target_compile_definitions(Konna.core.static PRIVATE KONNA_CORE_EXPORTS)
target_compile_options(Konna.core.static PRIVATE -O2)

target_link_libraries(Konna.core.static PRIVATE "${NETHOST_LIB}")


set_target_properties(Konna.core.shared PROPERTIES POSITION_INDEPENDENT_CODE ON)

target_include_directories(Konna.core.shared SYSTEM PRIVATE "${DOTNET_NETHOST_INCLUDE}")
target_include_directories(Konna.core.shared SYSTEM PRIVATE "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc")
target_include_directories(Konna.core.shared PUBLIC "${CMAKE_SOURCE_DIR}/src")

target_compile_definitions(Konna.core.shared PRIVATE KONNA_CORE_EXPORTS)
target_compile_options(Konna.core.shared PRIVATE -O2)

target_link_libraries(Konna.core.shared PRIVATE "${NETHOST_LIB}")

message(STATUS "NETHOST configuration finished. Version: ${DOTNET_VERSION}")

add_executable(
    Tests
    test/main.cpp
    test/Konna/memory/KAllocator_tests.cpp
    ${SOURCE_FILES}
)

#add_compile_definitions(RPMALLOC_FIRST_CLASS_THREAD=1)


target_include_directories(Tests PRIVATE "${DOTNET_NETHOST_INCLUDE}")
target_include_directories(Tests PRIVATE "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc")
target_include_directories(Tests PRIVATE "${CMAKE_SOURCE_DIR}/src")

target_compile_definitions(Tests PRIVATE KONNA_CORE_EXPORTS)
target_link_libraries(Tests PRIVATE -Wl,-Bdynamic Konna.core.static -Wl,-Bstatic Catch2::Catch2WithMain)

#if(WIN32)
#    # Ensure we use standard dynamic runtime libraries
#    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreadedDLL")
#endif()

target_compile_options(Tests PRIVATE -fprofile-instr-generate -fcoverage-mapping -O0 -g -static)
target_link_options(Tests PRIVATE -fprofile-instr-generate -fcoverage-mapping -static)