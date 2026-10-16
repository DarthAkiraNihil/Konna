namespace Konna.Core.Memory;

public sealed class KMemory {
    
    // private struct KChunkMetadata {
    //
    //     public KMemorySegment assignedChunk { get; }
    //     public List<KMemorySegment> commitedSubchunks { get; }
    //
    //     public KChunkMetadata(KMemorySegment chunk) {
    //         this.assignedChunk = chunk;
    //         this.commitedSubchunks = [];
    //     }
    //
    // }
    //
    // private const int DEFAULT_CHUNK_CAPACITY = 64;
    // private const int DEFAULT_HUGE_CHUNK_CAPACITY = 64;
    // private const int DEFAULT_CHUNK_SIZE = 256 * 1024 * 1024;
    //
    // // TODO: custom collections instead of this BS (haha, it's not bs but interface is .NET-styled)
    // private static readonly List<KChunkMetadata> _chunks;
    // private static readonly List<KMemorySegment> _hugeChunks;
    //
    // static KMemory() {
    //     // todo: maybe fixed capacity?
    //     KMemory._chunks = new List<KChunkMetadata>(KMemory.DEFAULT_CHUNK_CAPACITY);
    //     KMemory._hugeChunks = new List<KMemorySegment>(KMemory.DEFAULT_HUGE_CHUNK_CAPACITY);
    // }

    // public static void initialize() {
    //     KMemory._chunks.Add(
    //         new KChunkMetadata(
    //             KAllocator.allocateNativeAligned(KMemory.DEFAULT_CHUNK_SIZE)
    //         )
    //     );
    // }
    //
    // public static void finalize() {
    //     foreach (var chunk in KMemory._chunks) {
    //         KAllocator.freeNative(chunk.assignedChunk);
    //     }
    // }

    public static KMemorySegment Allocate(nuint size) {
        return KAllocator.AllocateNativeAligned(size); // TODO: use chunking
    }

    public static void Free(KMemorySegment segment) {
        KAllocator.FreeNative(segment);
    }

    public static KRingBuffer CreateRingBuffer(nuint size, string areaName) {
        return new KRingBuffer(KMemory.Allocate(size), areaName);
    }

    public static KStackAllocator CreateStackAllocator(nuint size, string areaName) {
        return new KStackAllocator(KMemory.Allocate(size), areaName);
    }

    public static KBumpAllocator CreateBumpAllocator(nuint size, string areaName) {
        return new KBumpAllocator(KMemory.Allocate(size), areaName);
    }


}
