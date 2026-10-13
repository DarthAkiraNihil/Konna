//
// Created by EgrZver on 13.10.2026.
//

#ifndef KONNA_KERNEL_KMEMORYSEGMENT_H
#define KONNA_KERNEL_KMEMORYSEGMENT_H
#include <Konna/type/KonnaPrimitiveTypes.h>

namespace Konna::Kernel::Memory {

    struct KMemorySegment {

        void* start;
        void* end;

        [[nodiscard]] ksize size() const {
            return reinterpret_cast<ksize>(end) - reinterpret_cast<ksize>(start);
        }

    };

}


#endif //KONNA_KERNEL_KMEMORYSEGMENT_H
