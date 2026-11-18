//
// Created by EgrZver on 13.10.2026.
//

#ifndef KONNA_KERNEL_KMEMORYSEGMENT_H
#define KONNA_KERNEL_KMEMORYSEGMENT_H
#include <Konna/type/KonnaPrimitiveTypes.h>

namespace Konna::Kernel::Memory {

    /**
     * Simple wrapper over a raw memory segment that stores end address in addition with
     * start address. However, use @link KMemorySegment::end \endlink member since it points
     * to the next address after last address reserved for this segment;
     *
     * @version 0.7.0
     * @author Darth Akira Nihil
     */
    struct KMemorySegment {

        /**
         * Start of this memory segment.
         */
        void* start;
        /**
         * Address of memory located right after the last address really reserved for this segment.
         */
        void* end;

        /**
         * @return Size of this segment in bytes
         */
        [[nodiscard]] ksize size() const {
            return reinterpret_cast<ksize>(this->end) - reinterpret_cast<ksize>(this->start);
        }

        /**
         * Casts this segment to a void pointer. It returns @link KMemorySegment::end \endlink.
         */
        explicit operator void*() const {
            return this->start;
        }

    };

}


#endif //KONNA_KERNEL_KMEMORYSEGMENT_H
