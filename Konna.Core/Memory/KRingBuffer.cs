using System.Runtime.CompilerServices;

namespace Konna.Core.Memory;

public sealed class KRingBuffer {

    private readonly KMemorySegment _segment;

    private nuint _head;
    private nuint _tail;
    private bool _full;

    public KRingBuffer(KMemorySegment segment) {
        this._segment = segment;
        
        this._head = (nuint) segment.start;
        this._tail = 0;
        this._full = false;
    }

    // okay... this should work
    public KMemorySegment allocate(nuint size) {

        if (this._full) {
            return KMemorySegment.NULL;
        }

        unsafe {
            KMemorySegment result;
            
            nuint resultSize = size + (nuint) sizeof(nuint);
            bool enoughPlaceWithoutJump = (this._segment.endUnsigned - this._head) >= resultSize;
            if (enoughPlaceWithoutJump) {
                nint startAddress = (nint) (this._head + (nuint) sizeof(nuint));
                Unsafe.WriteUnaligned((void*) this._head, size);
                result = new KMemorySegment(
                    startAddress,
                    startAddress + (nint) size
                );
            } else {
                if (this._tail < resultSize) {
                    return KMemorySegment.NULL;
                }

                nint startAddress = (nint) (this._segment.startUnsigned + (nuint) sizeof(nuint));
                Unsafe.WriteUnaligned((void*) this._segment.start, size);
                Unsafe.WriteUnaligned((void*) this._head, this._segment.endUnsigned - this._head);
                result = new KMemorySegment(
                    startAddress,
                    startAddress + (nint) size
                );
            }
            
            if (this._tail == 0) {
                this._tail = this._head;
            }

            this._head = result.endUnsigned;
            if (this._head == this._tail) {
                this._full = true;
            }
            
            return result;
        }

    }

    public void free() {
        if (this._tail == 0) {
            return;
        }

        unsafe {
            var sizeToFree = Unsafe.ReadUnaligned<nuint>((void*) this._tail);
            Unsafe.WriteUnaligned<nuint>((void*) this._tail, 0);
            this._tail += sizeToFree + (nuint) sizeof(nuint);
            if (this._tail == this._segment.endUnsigned) {
                this._tail = this._segment.startUnsigned;
            }
        }
    }
    
}
