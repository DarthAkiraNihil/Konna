namespace Konna.Core.Memory;

public sealed class KBumpAllocator {

    private readonly KMemorySegment _segment;
    private nint _ptr;
    
    public KBumpAllocator(KMemorySegment segment) {
        _segment = segment;
        _ptr = segment.start;
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

    public void free() {
        this._ptr = this._segment.start;
    }

}
