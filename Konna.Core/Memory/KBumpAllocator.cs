namespace Konna.Core.Memory;

/// <summary>
///     A simple allocator that operates over an allocated buffer. Only can allocate unmanaged
///     C# objects but them cannot be freed in any order. The <code>free</code> operation
///     just moves its allocation pointer (where it can allocate) to the beginning
///     of the assigned memory segment.
/// </summary>
/// <remarks>
///     Version: 0.7.0, Author: Darth Akira Nihil
/// </remarks>
public sealed class KBumpAllocator {

    private readonly KMemorySegment _segment;
    private nint _ptr;

    /// <summary>
    /// Name of area that is captured by this allocator.
    /// </summary>
    public string AreaName { get; }

    internal KBumpAllocator(
        KMemorySegment segment,
        string areaName
    ) {
        this.AreaName = areaName;
        this._segment = segment;
        this._ptr = segment.Start;
    }

    /// <summary>
    ///     Allocates a continuous memory segment from the captured
    ///     allocator buffer. Will return <see cref="KMemorySegment.NULL"/>
    ///     if there is not enough free space in the buffer to fit requested
    ///     memory segment size.
    /// </summary>
    /// <param name="size">Size of allocated segment</param>
    /// <returns>Data of allocated segment</returns>
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

    /// <summary>
    ///     Allocates an unmanaged object in the assigned buffer.
    ///     FIXME Warning! The behavior may be unexpected if there is not
    ///     enough space to fix the requested struct.
    /// </summary>
    /// <typeparam name="T">Type of allocated struct</typeparam>
    /// <returns>Struct allocated on this allocator's buffer</returns>
    public T Allocate<T>() where T : unmanaged {

        unsafe {
            KMemorySegment seg = this.Allocate((nuint) sizeof(T));

            return *((T*) seg.Start);
        }
        
    }

    /// <summary>
    ///     Frees all data that has been allocated by this allocator in its assigned buffer.
    /// </summary>
    public void Free() {
        this._ptr = this._segment.Start;
    }

    /// <summary>
    ///     Frees the assigned buffer and returns it to the <see cref="KMemory"/>
    /// </summary>
    public void Flush() {
        KMemory.Free(this._segment);
    }

}
