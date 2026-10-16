namespace Konna.Core.Memory;

public sealed class KBumpAllocator {

    private readonly KMemorySegment _segment;
    private nint _ptr;

    public string AreaName { get; }

    internal KBumpAllocator(
        KMemorySegment segment,
        string areaName
    ) {
        this.AreaName = areaName;
        this._segment = segment;
        this._ptr = segment.Start;
    }

    public KMemorySegment Allocate(nuint size) {
        nuint end = (nuint) this._ptr + size;
        if (end >= (nuint) this._segment.End) {
            return KMemorySegment.NULL;
        }
        
        var seg =  new KMemorySegment(
            this._ptr, (nint) end
        );
        this._ptr = (nint) end;

        return seg;
    }

    public T Allocate<T>() where T : unmanaged {

        unsafe {
            KMemorySegment seg = this.Allocate((nuint) sizeof(T));

            return *((T*) seg.Start);
        }
        
    }

    public void Free() {
        this._ptr = this._segment.Start;
    }

    public void Flush() {
        KMemory.Free(this._segment);
    }

}
