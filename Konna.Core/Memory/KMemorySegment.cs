using System.Runtime.InteropServices;

namespace Konna.Core.Memory;

/// <summary>
///     Simple wrapper over a raw memory segment that stores end address in addition with
///     start address. However, use <see cref="KMemorySegment.End"/> member since it points
///     to the next address after last address reserved for this segment; 
/// </summary>
/// <remarks>
///     Version: 0.7.0, Author: Darth Akira Nihil
/// </remarks>
[StructLayout((LayoutKind.Sequential))]
public readonly struct KMemorySegment: IEquatable<KMemorySegment> {

    /// <summary>
    ///     Public constant value of a null segment. Does not have size,
    ///     starts and ends with zero.
    /// </summary>
    public static readonly KMemorySegment NULL = new(0, 0);

    private readonly nint _start;
    private readonly nint _end;

    /// <summary>
    ///     Start of this segment.
    /// </summary>
    public nint Start => this._start;
    /// <summary>
    ///     End of this segment. Be careful: it points to the address next to the
    ///     actual end of this segment, so it does not belong to it.
    /// </summary>
    public nint End => this._end;

    /// <summary>
    ///     Size of this memory segment in bytes.
    /// </summary>
    public nuint Size => (nuint) (this._end - this._start);
    
    /// <summary>
    ///     Compares two memory segments on equality. Two segments are
    ///     equal if their start and end addresses are equal.
    /// </summary>
    /// <param name="lhs">Left-hand side segment</param>
    /// <param name="rhs">Right-hand side segment</param>
    /// <returns>Whether segments are equal or not</returns>
    public static bool operator ==(KMemorySegment lhs, KMemorySegment rhs) {
        return lhs.Start == rhs.Start && lhs.End == rhs.End;
    }
    
    /// <summary>
    ///     Compares two memory segments on inequality.
    /// </summary>
    /// <param name="lhs">Left-hand side segment</param>
    /// <param name="rhs">Right-hand side segment</param>
    /// <returns>Whether segments are not equal or not</returns>
    public static bool operator !=(KMemorySegment lhs, KMemorySegment rhs) {
        return !(lhs == rhs);
    }
    
    /// <summary>
    ///     Standard constructor.
    /// </summary>
    /// <param name="start">Start of the memory segment</param>
    /// <param name="end">End of the memory segment (is not included in the segment)</param>
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
