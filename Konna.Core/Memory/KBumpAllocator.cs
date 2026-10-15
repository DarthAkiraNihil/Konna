namespace Konna.Core.Memory;

public sealed class KBumpAllocator {

    private readonly KMemorySegment _segment;
    private nint _ptr;

    public string areaName { get; }

    internal KBumpAllocator(
        KMemorySegment segment,
        string areaName
    ) {
        this.areaName = areaName;
        this._segment = segment;
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

    public void free() {
        this._ptr = this._segment.start;
    }

}
