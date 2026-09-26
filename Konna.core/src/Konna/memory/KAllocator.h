/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#ifndef KONNA_CORE_KALLOCATOR_H
#define KONNA_CORE_KALLOCATOR_H

#include <rpmalloc.h>

#include <Konna/KonnaExport.h>
#include <Konna/type/PrimitiveTypes.h>

namespace Konna::Core::Memory {

    /**
     * Standard memory allocator that does not have any specific functions. Just allocates memory
     * segments, objects and frees them.
     *
     * @version 0.7.0
     * @author Darth Akira Nihil
     */
    class KONNA_CORE_API KAllocator {

    public:

        /**
         * Allocates a continuous, non-initialized memory segment
         * @param size Size of allocated segment in bytes
         * @return Allocated memory segment
         */
        static void* alloc(const ksize& size) noexcept {
            return rpmalloc(size);
        }

        /**
         * Frees previously allocated memory segment. Does not have any checks for invalid
         * or null address.
         * @param ptr Pointer of memory segment to delete
         */
        static void free(void* ptr) noexcept {
            rpfree(ptr);
        }

        /**
         * Allocates an object. Same as @link KAllocator::alloc(const ksize&) \endlink, but with automatic cast.
         * Does not support allocating on a pre-allocated segment.
         *
         * @tparam T Type of allocated object
         * @return Raw pointer to allocated object
         */
        template <typename T>
        static T* allocObject() noexcept {
            return static_cast<T*>(rpmalloc(sizeof(T)));
        }

    };

} // Konna::Core::Memory

#endif //KONNA_CORE_KALLOCATOR_H
