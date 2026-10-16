using System.Runtime.CompilerServices;

namespace Konna.Core.Memory;

public sealed class KRingBuffer {

    private readonly KMemorySegment _segment;
    public string AreaName { get; }
    
    private nuint _head;
    private nuint _tail;
    private bool _full;

    internal KRingBuffer(
        KMemorySegment segment,
        string areaName
    ) {
        this._segment = segment;
        this.AreaName = areaName;
        
        this._head = (nuint) segment.Start;
        this._tail = 0;
        this._full = false;
    }

    // okay... this should work
    public KMemorySegment Allocate(nuint size) {

        if (this._full) {
            return KMemorySegment.NULL;
        }

        unsafe {
            KMemorySegment result;
            
            nuint resultSize = size + (nuint) sizeof(nuint);
            bool enoughPlaceWithoutJump = (this._segment.EndUnsigned - this._head) >= resultSize;
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

                nint startAddress = (nint) (this._segment.StartUnsigned + (nuint) sizeof(nuint));
                Unsafe.WriteUnaligned((void*) this._segment.Start, size);
                Unsafe.WriteUnaligned((void*) this._head, this._segment.EndUnsigned - this._head);
                result = new KMemorySegment(
                    startAddress,
                    startAddress + (nint) size
                );
            }
            
            if (this._tail == 0) {
                this._tail = this._head;
            }

            this._head = result.EndUnsigned;
            if (this._head == this._tail) {
                this._full = true;
            }
            
            return result;
        }

    }

    public void Free() {
        if (this._tail == 0) {
            return;
        }

        unsafe {
            var sizeToFree = Unsafe.ReadUnaligned<nuint>((void*) this._tail);
            Unsafe.WriteUnaligned<nuint>((void*) this._tail, 0);
            this._tail += sizeToFree + (nuint) sizeof(nuint);
            if (this._tail == this._segment.EndUnsigned) {
                this._tail = this._segment.StartUnsigned;
            }
        }
    }
    
    public void Flush() {
        KMemory.Free(this._segment);
    }
    
}
