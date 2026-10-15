message(STATUS "Platform: Windows")

if(NOT EXISTS "${DOTNET_INSTALL_DIR}/dotnet.exe")
    message(FATAL_ERROR "Could not find project-wise installed .NET. Please check if you have run the install script")
    return()
endif()

set_target_properties(Konna.Kernel.static PROPERTIES PREFIX "")
set_target_properties(Konna.Kernel.shared PROPERTIES PREFIX "")

set(DOTNET_PLATFORM_SUFFIX "win-x64")
set(DOTNET_HOST_LIB_NAME "nethost.lib")
