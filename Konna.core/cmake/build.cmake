foreach (prime_target IN LISTS Konna.core.static Konna.core.shared)

    set_target_properties(prime_target PROPERTIES POSITION_INDEPENDENT_CODE ON)

    target_include_directories(prime_target PRIVATE "${DOTNET_NETHOST_INCLUDE}")
    target_include_directories(prime_target PRIVATE "${CMAKE_SOURCE_DIR}/extern/rpmalloc/rpmalloc")
    target_include_directories(prime_target PRIVATE "${CMAKE_SOURCE_DIR}/src")

    target_compile_definitions(prime_target PRIVATE KONNA_CORE_EXPORTS)
    target_compile_options(prime_target PRIVATE -02 -stdlib=libc++)

    target_link_libraries(prime_target PRIVATE "${NETHOST_LIB}")

endforeach ()

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
target_link_libraries(Tests PRIVATE -Wl,-Bdynamic Konna.core.static -Wl,-Bstatic Catch2::Catch2WithMain)

#if(WIN32)
#    # Ensure we use standard dynamic runtime libraries
#    set(CMAKE_MSVC_RUNTIME_LIBRARY "MultiThreadedDLL")
#endif()

target_compile_options(Tests PRIVATE -fprofile-instr-generate -fcoverage-mapping -O0 -g -static -stdlib=libc++)
target_link_options(Tests PRIVATE -fprofile-instr-generate -fcoverage-mapping -static -stdlib=libc++)