namespace Konna.Core.Memory;

public static class KMemory {

    public static nuint Megabytes(nuint mb) {
        return mb * 1024 * 1024;
    }

    public static nuint Kilobytes(nuint kb) {
        return kb * 1024;
    }

    private struct KChunk(nint start, nint end) {
        
        public nint Start { get; set; } = start;
        public nint End { get; set; } = end;
        public nuint Size => (nuint) (this.End - this.Start);
        public bool InUse { get; set; } = false;

        public KMemorySegment AsMemorySegment() {
            return new  KMemorySegment(this.Start, this.End);
        }

    }
    
    private const int DEFAULT_CHUNK_STORAGE_CAPACITY = 64;
    private const int DEFAULT_HUGE_CHUNK_STORAGE_CAPACITY = 8;
    private const nuint DEFAULT_CHUNK_SIZE = 256 * 1024 * 1024;
    private const string DEFAULT_AREA_NAME = "default";
    
    private static readonly IList<KChunk> CHUNKS = new List<KChunk>(
        KMemory.DEFAULT_CHUNK_STORAGE_CAPACITY
    );

    private static readonly IList<KMemorySegment> HUGE_CHUNKS = new List<KMemorySegment>(
        KMemory.DEFAULT_HUGE_CHUNK_STORAGE_CAPACITY
    );
    
    public static KMemorySegment Allocate(nuint size) {
        if (size == 0) {
            return KMemorySegment.NULL;
        }

        if (size > KMemory.DEFAULT_CHUNK_SIZE) {
            KMemorySegment hugeChunk = KAllocator.AllocateNativeAligned(size);
            KMemory.HUGE_CHUNKS.Add(hugeChunk);
            return hugeChunk;
        }
        
        if (KMemory.CHUNKS.Count == 0) {
            KMemorySegment rawChunk = KAllocator.AllocateNativeAligned(size);
            var first = new KChunk(rawChunk.Start, rawChunk.Start + (nint) size);
            first.InUse = true;
            var second = new KChunk(first.End, rawChunk.End);
            KMemory.CHUNKS.Add(first);
            KMemory.CHUNKS.Add(second);

            return first.AsMemorySegment();
        }

        for (int i = 0; i < KMemory.CHUNKS.Count; i++) {
            KChunk chunk = KMemory.CHUNKS[i];
            if (chunk.InUse) {
                continue;
            }

            if (chunk.Size < size) {
                continue;
            }
            
            KChunk borrowed = new KChunk(chunk.Start, chunk.Start + (nint) chunk.Size);
            borrowed.InUse = true;
            chunk.Start += (nint) chunk.Size;
            KMemory.CHUNKS[i] = chunk;
            KMemory.CHUNKS.Insert(i, borrowed);

            return borrowed.AsMemorySegment();
        }
        
        KMemorySegment newChunk = KAllocator.AllocateNativeAligned(size);
        KChunk firstC = new KChunk(newChunk.Start, newChunk.Start + (nint) size);
        firstC.InUse = true;
        KChunk secondC = new KChunk(firstC.End, newChunk.End);
        KMemory.CHUNKS.Add(firstC);
        KMemory.CHUNKS.Add(secondC);

        return firstC.AsMemorySegment();
    }

    public static void Free(KMemorySegment segment) {
        if (segment.Size > KMemory.DEFAULT_CHUNK_SIZE) {
            for (var i = 0; i < KMemory.HUGE_CHUNKS.Count; i++) {
                if (KMemory.HUGE_CHUNKS[i] != segment) {
                    continue;
                }
                
                KAllocator.FreeNative(KMemory.HUGE_CHUNKS[i]);
                KMemory.HUGE_CHUNKS.RemoveAt(i);
                return;
            }
        }

        for (var i = 0; i < KMemory.CHUNKS.Count; i++) {
            KChunk chunk = KMemory.CHUNKS[i];
            if (chunk.Start != segment.Start || chunk.End != segment.End) {
                continue;
            }

            chunk.InUse = false;
            KMemory.CHUNKS[i] = chunk;

            break;
        }

        for (var i = 0; i < KMemory.CHUNKS.Count - 1; i++) {
            KChunk thisChunk = KMemory.CHUNKS[i];
            KChunk nextChunk = KMemory.CHUNKS[i + 1];

            if (thisChunk.InUse || nextChunk.InUse) {
                continue;
            }

            thisChunk.End = nextChunk.End;
            KMemory.CHUNKS.RemoveAt(i + 1);
            i--;
        }
    }

    public static KBumpAllocator CreateBumpAllocator(nuint size, string areaName) {
        return new KBumpAllocator(KMemory.Allocate(size), areaName);
    }

    public static KBumpAllocator CreateBumpAllocator(nuint size) {
        return KMemory.CreateBumpAllocator(size, KMemory.DEFAULT_AREA_NAME);
    }


}
