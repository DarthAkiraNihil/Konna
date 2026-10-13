//
// Created by EgrZver on 12.10.2026.
//

#ifndef KONNA_KERNEL_KRINGBUFFER_H
#define KONNA_KERNEL_KRINGBUFFER_H
#include <Konna/type/KonnaPrimitiveTypes.h>

namespace Konna::Kernel::Memory {

    class KRingBuffer final {

        public:
            explicit KRingBuffer(const ksize& size) noexcept;
            void* alloc(const ksize& size, const ksize& alignment) noexcept;
            void free(void* ptr, const ksize& alignment) noexcept;
        private:
            void* memoryStart;
            ksize memorySize;

            void* first;
            void* last;


    };

}

#endif //KONNA_KERNEL_KRINGBUFFER_H
