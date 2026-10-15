using System.Runtime.InteropServices;

namespace Konna.Core.Memory;

[StructLayout((LayoutKind.Sequential))]
public readonly struct KMemorySegment {

    private readonly nint _start;
    private readonly nint _end;

    public nint start => this._start;
    public nint end => this._end;

    public nuint size => (nuint) (this._end - this._start);
    
    public KMemorySegment(nint start, nint end) {
        this._start = start;
        this._end = end;
    }

}
