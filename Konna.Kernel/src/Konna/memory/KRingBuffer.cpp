//
// Created by EgrZver on 12.10.2026.
//

#include "KRingBuffer.h"

#include "KAllocator.h"

namespace Konna::Kernel::Memory {

    KRingBuffer::KRingBuffer(const ksize& size) noexcept {

        this->memorySize = size;
        this->memoryStart = KAllocator::alloc(size);

        this->first = this->memoryStart;
        this->last = nullptr;

    }

    void* KRingBuffer::alloc(const ksize& size, const ksize& alignment) noexcept {

        if (this->last && this->first > this->last) {
            return nullptr;
        }

        if (!this->last) {
            this->last = this->memoryStart;
        }

        void* ptr = this->first;

        auto raw = reinterpret_cast<ksize>(ptr) + size;
        if (raw > this->memorySize) {
            raw = 0;
        }

        raw += (alignment - raw % alignment);
        this->first = reinterpret_cast<void*>(raw); // NOLINT

        return ptr;
    }

    void KRingBuffer::free(void* ptr, const ksize& alignment) noexcept {
    }

}
