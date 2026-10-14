//
// Created by EgrZver on 14.10.2026.
//

#ifndef KONNA_KERNEL_KONNAMEMORYUTILS_H
#define KONNA_KERNEL_KONNAMEMORYUTILS_H
#include <Konna/type/KonnaPrimitiveTypes.h>

namespace Konna::Kernel::Memory::MemUtils {

    inline void* align(const void* ptr, const ksize alignment) noexcept {
        return (void*) ((reinterpret_cast<ksize>(ptr) + (alignment - 1)) & (~(alignment - 1))); // NOLINT
    }

    inline void* ptradd(const void* ptr, const ksize offset) noexcept {
        return (void*) ((reinterpret_cast<ksize>(ptr) + offset)); // NOLINT
    }

}



#endif //KONNA_KERNEL_KONNAMEMORYUTILS_H
