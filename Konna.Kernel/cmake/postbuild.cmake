add_custom_command(
    TARGET Konna.Kernel.shared
    POST_BUILD
    COMMAND ${CMAKE_COMMAND} -E copy_if_different
        $<TARGET_FILE:Konna.Kernel.shared>
        "${CMAKE_SOURCE_DIR}/../Konna.Kernel.shared.dll"
    COMMAND ${CMAKE_COMMAND} -E copy_if_different
        $<TARGET_FILE:Konna.Kernel.shared>
        "${CMAKE_SOURCE_DIR}/../Konna.Core/bin/Debug/net10.0/Konna.Kernel.shared.dll"
    COMMENT "Copying build files to Konna.Core"
)