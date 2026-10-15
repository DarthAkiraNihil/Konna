using System.Runtime.InteropServices;

namespace Konna.Core.Memory;

[StructLayout((LayoutKind.Sequential))]
public readonly struct KMemorySegment: IEquatable<KMemorySegment> {

    public static KMemorySegment NULL = new(0, 0);

    private readonly nint _start;
    private readonly nint _end;

    public nint start => this._start;
    public nint end => this._end;
    
    public nuint startUnsigned => (nuint) this._start;
    public nuint endUnsigned => (nuint) this._end;

    public nuint size => (nuint) (this._end - this._start);
    
    public static bool operator ==(KMemorySegment lhs, KMemorySegment rhs) {
        return lhs.start == rhs.start && lhs.end == rhs.end;
    }
    
    public static bool operator !=(KMemorySegment lhs, KMemorySegment rhs) {
        return !(lhs == rhs);
    }
    
    internal KMemorySegment(nint start, nint end) {
        this._start = start;
        this._end = end;
    }

    public bool contains(KMemorySegment other) {
        return
                this._start <= other.start
            &&  other.start < this._end
            &&  this._start <= other.end
            &&  other.end < this._end;
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
