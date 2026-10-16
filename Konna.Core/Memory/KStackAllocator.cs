namespace Konna.Core.Memory;

public sealed class KStackAllocator {

    private readonly KMemorySegment _segment;
    public string AreaName { get; }
    
    private nint _ptr;
    
    internal KStackAllocator(
        KMemorySegment segment,
        string areaName
    ) {
        this._segment = segment;
        this.AreaName = areaName;
        this._ptr = segment.Start;
    }

    public KMemorySegment Allocate(nuint size) {
        nuint end = (nuint) this._ptr + size;
        if (end >= (nuint) this._segment.End) {
            return KMemorySegment.NULL;
        }

        this._ptr = (nint) end;

        return new KMemorySegment(
            this._ptr, (nint) end
        );
    }

    public void Free(KMemorySegment to) {
        if (this._segment.Contains(to)) {
            this._ptr = to.Start;
        }
    }

    public void Clear() {
        this._ptr = this._segment.Start;
    }
    
    public void Flush() {
        KMemory.Free(this._segment);
    }
    

}
