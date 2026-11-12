using Konna.Core.Memory;

namespace Konna.Core.Tests.Memory;

public class KMemoryTests {

    [Fact]
    public void TestAllocateNormalChunk() {
        KMemorySegment seg1 = KMemory.Allocate(16);
        
        Assert.Equal((nuint) 16, seg1.Size);
        
        KMemory.Free(seg1);
    }

    [Fact]
    public void TestAllocateTwoNormalChunks() {
        
        KMemorySegment seg1 = KMemory.Allocate(16);
        KMemorySegment seg2 = KMemory.Allocate(16);
        
        Assert.Equal((nuint) 16, seg1.Size);
        Assert.Equal(seg2.Size, seg1.Size);
        
        Assert.NotEqual(seg2.Start, seg1.Start);
        Assert.NotEqual(seg2.End, seg1.End);
        
        KMemory.Free(seg1);
        KMemory.Free(seg2);
        
    }

    [Fact]
    public void TestAllocateHugeChunk() {
        KMemorySegment seg1 = KMemory.Allocate(KMemory.Megabytes(257));
        
        Assert.Equal(KMemory.Megabytes(257), seg1.Size);
        
        KMemory.Free(seg1);
    }

    [Fact]
    public void TestAllocateThreeThenFreeOneAndAllocateAgain() {
        
        KMemorySegment seg1 = KMemory.Allocate(16);
        KMemorySegment seg2 = KMemory.Allocate(16);
        KMemorySegment seg3 = KMemory.Allocate(16);
        
        Assert.Equal((nuint) 16, seg1.Size);
        Assert.Equal((nuint) 16, seg2.Size);
        Assert.Equal((nuint) 16, seg3.Size);
        
        KMemory.Free(seg2);
        
        KMemorySegment seg2Again =  KMemory.Allocate(16);
        
        Assert.Equal((nuint) 16, seg2Again.Size);
        Assert.Equal(seg2.Size, seg2Again.Size);
        
        Assert.Equal(seg2.Start, seg2Again.Start);
        Assert.Equal(seg2.End, seg2Again.End);
        
        KMemory.Free(seg2Again);
        KMemory.Free(seg1);
        KMemory.Free(seg3);
        
    }

    [Fact]
    public void TestAllocateFourThenFreeTwoMiddleAndAllocateAgain() {
        
        KMemorySegment seg1 = KMemory.Allocate(16);
        KMemorySegment seg2 = KMemory.Allocate(16);
        KMemorySegment seg3 = KMemory.Allocate(16);
        KMemorySegment seg4 = KMemory.Allocate(16);
        
        Assert.Equal((nuint) 16, seg1.Size);
        Assert.Equal((nuint) 16, seg2.Size);
        Assert.Equal((nuint) 16, seg3.Size);
        Assert.Equal((nuint) 16, seg4.Size);
        
        KMemory.Free(seg2);
        KMemory.Free(seg3);
        
        KMemorySegment seg2Again =  KMemory.Allocate(16);
        KMemorySegment seg3Again =  KMemory.Allocate(16);
        
        Assert.Equal((nuint) 16, seg2Again.Size);
        Assert.Equal((nuint) 16, seg3Again.Size);
        Assert.Equal(seg2.Size, seg2Again.Size);
        Assert.Equal(seg3.Size, seg3Again.Size);
        
        Assert.Equal(seg2.Start, seg2Again.Start);
        Assert.Equal(seg3.Start, seg3Again.Start);
        Assert.Equal(seg2.End, seg2Again.End);
        Assert.Equal(seg3.End, seg3Again.End);
        
        KMemory.Free(seg2Again);
        KMemory.Free(seg3Again);
        KMemory.Free(seg1);
        KMemory.Free(seg4);
        
    }

    [Fact]
    public void TestAllocateZeroSizedChunk() {
        KMemorySegment seg = KMemory.Allocate(0);
        
        Assert.Equal((nuint) 0, seg.Size);
        Assert.Equal(0, seg.Start);
        Assert.Equal(0, seg.Start);
        Assert.Equal(KMemorySegment.NULL, seg);
        
        KMemory.Free(seg);
    }

    [Fact]
    public void TestAllocateChunkThatExceedsOneDefault() {
        KMemorySegment seg1 = KMemory.Allocate(KMemory.Megabytes(256));
        KMemorySegment seg2 = KMemory.Allocate(16);
        
        Assert.Equal(KMemory.Megabytes(256), seg1.Size);
        Assert.Equal((nuint) 16, seg2.Size);
        
        Assert.NotEqual(seg2.Start, seg1.Start);
        Assert.NotEqual(seg2.End, seg1.End);
        
        KMemory.Free(seg1);
        KMemory.Free(seg2);
    }

}
