namespace Konna.Core.Memory;

public sealed class KStackAllocator {

    private readonly KMemorySegment _segment;
    public string areaName { get; }
    
    private nint _ptr;
    
    internal KStackAllocator(
        KMemorySegment segment,
        string areaName
    ) {
        this._segment = segment;
        this.areaName = areaName;
        this._ptr = segment.start;
    }

    public KMemorySegment allocate(nuint size) {
        nuint end = (nuint) this._ptr + size;
        if (end >= (nuint) this._segment.end) {
            return KMemorySegment.NULL;
        }

        this._ptr = (nint) end;

        return new KMemorySegment(
            this._ptr, (nint) end
        );
    }

    public void free(KMemorySegment to) {
        if (this._segment.contains(to)) {
            this._ptr = to.start;
        }
    }

    public void clear() {
        this._ptr = this._segment.start;
    }

}
