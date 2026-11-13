using System.Runtime.InteropServices;

namespace Konna.Core.Memory;

/// <summary>
///     A wrapper class over Konna.Kernel KAllocator API of exported functions.
///     For internal use only (maybe it will be closed by corresponding access modifier).
/// </summary>
/// <remarks>
///     Version: 0.7.0, Author: Darth Akira Nihil
/// </remarks>
public static partial class KAllocator { // todo: maybe internal
    
    /// <summary>
    ///     Allocates a native memory segment with default alignment, provided by Konna.Kernel.
    /// </summary>
    /// <param name="size">Size of allocated segment</param>
    /// <returns>Data of allocated memory segment</returns>
    public static KMemorySegment AllocateNativeAligned(nuint size) {
        return KAllocator.KonnaKernel_Memory_allocateAligned(size);
    }
    
    /// <summary>
    ///     Frees a native memory segment that has been allocated by this class.
    /// </summary>
    /// <param name="segment">Segment to free</param>
    public static void FreeNative(KMemorySegment segment) {
        KAllocator.KonnaKernel_Memory_free(segment);
    }

    [LibraryImport("Konna.Kernel.shared")]
    private static partial KMemorySegment KonnaKernel_Memory_allocateAligned(nuint size);
    
    [LibraryImport("Konna.Kernel.shared")]
    private static partial void KonnaKernel_Memory_free(KMemorySegment segment);
    

}
