using System.Runtime.InteropServices;

namespace Konna.Core.Memory;

[StructLayout((LayoutKind.Sequential))]
public readonly struct KMemorySegment(nint start, nint end) {

    public nint start { get; } = start;
    public nint end { get; } = end;
    
    public nuint size { get; } = (nuint) (end - start);

}
