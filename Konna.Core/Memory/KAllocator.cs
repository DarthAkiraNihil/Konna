using System.Runtime.InteropServices;

namespace Konna.Core.Memory;

public sealed partial class KAllocator {

    public static KMemorySegment allocateNativeAligned(nuint size) {
        return KAllocator.KonnaKernel_Memory_allocateAligned(size);
    }

    public static void freeNative(KMemorySegment segment) {
        KAllocator.KonnaKernel_Memory_free(segment);
    }

    [LibraryImport("Konna.Kernel.shared")]
    private static partial KMemorySegment KonnaKernel_Memory_allocateAligned(nuint size);
    
    [LibraryImport("Konna.Kernel.shared")]
    private static partial void KonnaKernel_Memory_free(KMemorySegment segment);
    

}
