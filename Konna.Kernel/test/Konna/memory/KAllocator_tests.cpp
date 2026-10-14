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

#include <Konna/memory/KAllocator.h>
#include <catch2/catch_all.hpp>

namespace Konna::Kernel::Memory {

    TEST_CASE("Allocator allocates successfully", "[memory]") {

        void* m = KAllocator::allocateFast(16);
        REQUIRE(m != nullptr);

        KMemorySegment seg = KAllocator::allocate(16);
        REQUIRE(static_cast<void*>(seg) != nullptr);
        REQUIRE(seg.start != nullptr);
        REQUIRE(seg.end != nullptr);
        REQUIRE(seg.size() == 16);

        KAllocator::free(m);
        KAllocator::free(seg);

    }

    TEST_CASE("Allocator allocates with default alignment successfully", "[memory]") {

        void* m = KAllocator::allocateAlignedFast(16);
        REQUIRE(m != nullptr);

        KMemorySegment seg = KAllocator::allocateAligned(16);
        REQUIRE(static_cast<void*>(seg) != nullptr);
        REQUIRE(seg.start != nullptr);
        REQUIRE(seg.end != nullptr);
        REQUIRE(seg.size() == 16);

        KMemorySegment cseg = KonnaKernel_Memory_allocateAligned(16);
        REQUIRE(static_cast<void*>(cseg) != nullptr);
        REQUIRE(cseg.start != nullptr);
        REQUIRE(cseg.end != nullptr);
        REQUIRE(cseg.size() == 16);

        KAllocator::free(m);
        KAllocator::free(seg);
        KonnaKernel_Memory_free(cseg);
    }

    TEST_CASE("Allocator allocates object successfully", "[memory]") {

        ki32* a = KAllocator::allocObject<ki32>();

        REQUIRE(a != nullptr);
        *a = 1;
        REQUIRE(*a == 1);

        struct Point {
            int x;
            int y;
        };

        auto* p = KAllocator::allocObject<Point>();
        REQUIRE(p != nullptr);
        p->x = 1;
        p->y = 2;
        REQUIRE(p->x == 1);
        REQUIRE(p->y == 2);

        KAllocator::free(a);
        KAllocator::free(p);

    }

}