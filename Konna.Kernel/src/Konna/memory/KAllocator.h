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

#ifndef KONNA_KERNEL_KALLOCATOR_H
#define KONNA_KERNEL_KALLOCATOR_H

#include <rpmalloc.h>

#include <Konna/KonnaExport.h>
#include <Konna/type/KonnaPrimitiveTypes.h>

#include "KMemorySegment.h"
#include "KonnaMemoryUtils.h"

namespace Konna::Kernel::Memory {

    /**
     * Default alignment used by @link KAllocator::allocateAligned \endlink
     * and @link KAllocator::allocateAlignedFast \endlink.
     */
    inline constexpr ksize KONNA_DEFAULT_ALIGN = 64;

    /**
     * Standard memory allocator that does not have any specific functions. Just allocates memory
     * segments, objects and frees them.
     *
     * @version 0.7.0
     * @author Darth Akira Nihil
     */
    class KAllocator {

        public:

            /**
             * Allocates a continuous, non-initialized memory segment object
             * @param size Size of allocated segment in bytes
             * @return Allocated memory segment object
             */
            static KMemorySegment allocate(const ksize& size) noexcept {
                void* ptr = KAllocator::allocateFast(size);
                return {
                    .start = ptr,
                    .end = MemUtils::ptradd(ptr, size)
                };
            }

            /**
             * Allocates a continuous, non-initialized memory segment object using
             * default alignment, specified by @link KONNA_DEFAULT_ALIGN \endlink
             * @param size Size of allocated segment in bytes
             * @return Allocated memory segment object
             */
            static KMemorySegment allocateAligned(const ksize& size) noexcept {
                void *ptr = KAllocator::allocateAlignedFast(size);
                return {
                    .start = ptr,
                    .end = MemUtils::ptradd(ptr, size)
                };
            }

            /**
             * Allocates a continuous, non-initialized memory segment object.
             * Only use if you don't need memory segment wrapper because of reasons.
             * @param size Size of allocated segment in bytes
             * @return Raw address of allocated memory segment
             */
            static void* allocateFast(const ksize& size) noexcept {
                return rpmalloc(size);
            }

            /**
             * Allocates a continuous, non-initialized memory segment object using
             * default alignment, specified by @link KONNA_DEFAULT_ALIGN \endlink.
             * Only use if you don't need memory segment wrapper because of reasons.
             * @param size Size of allocated segment in bytes
             * @return Raw address of allocated memory segment
             */
            static void* allocateAlignedFast(const ksize& size) noexcept {
                if (!rpmalloc_is_thread_initialized()) {
                    rpmalloc_thread_initialize();
                }
                return rpaligned_alloc(KONNA_DEFAULT_ALIGN, size);
            }

            /**
             * Frees previously allocated memory segment. Does not have any checks for invalid
             * or null address.
             * @param segment Memory segment to delete
             */
            static void free(const KMemorySegment& segment) noexcept {
                rpfree(static_cast<void*>(segment));
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
             * Allocates an object. Same as @link KAllocator::allocate(const ksize&) \endlink, but with automatic cast.
             * Does not support allocating on a pre-allocated segment.
             *
             * @tparam T Type of allocated object
             * @return Raw pointer to allocated object
             */
            template<typename T>
            static T* allocObject() noexcept {
                return static_cast<T*>(rpmalloc(sizeof(T)));
            }
    };

    extern "C" {

        KONNA_KERNEL_API KMemorySegment KonnaKernel_Memory_allocateAligned(ksize size) noexcept;
        KONNA_KERNEL_API void KonnaKernel_Memory_free(const KMemorySegment& segment) noexcept;

    }

} // namespace Konna::Kernel::Memory

#endif // KONNA_KERNEL_KALLOCATOR_H
