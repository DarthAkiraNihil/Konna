using System.Runtime.InteropServices;

namespace Konna.Core.Memory;

[StructLayout((LayoutKind.Sequential))]
public readonly struct KMemorySegment: IEquatable<KMemorySegment> {

    public static readonly KMemorySegment NULL = new(0, 0);

    private readonly nint _start;
    private readonly nint _end;

    public nint Start => this._start;
    public nint End => this._end;

    public nuint Size => (nuint) (this._end - this._start);
    
    public static bool operator ==(KMemorySegment lhs, KMemorySegment rhs) {
        return lhs.Start == rhs.Start && lhs.End == rhs.End;
    }
    
    public static bool operator !=(KMemorySegment lhs, KMemorySegment rhs) {
        return !(lhs == rhs);
    }
    
    public KMemorySegment(nint start, nint end) {
        this._start = start;
        this._end = end;
    }

    public bool Equals(KMemorySegment other) {
        return this._start == other._start && this._end == other._end;
    }

    public override bool Equals(object? obj) {
        return obj is KMemorySegment other && this.Equals(other);
    }

    public override int GetHashCode() {
        return HashCode.Combine(this._start, this._end);
    }

}
