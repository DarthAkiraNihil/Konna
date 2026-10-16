using Konna.Core.Tests.Fixtures;

namespace Konna.Core.Tests.Memory;

using Konna.Core.Memory;

public class KBumpAllocatorTests: IDisposable, IClassFixture<KBumpAllocatorFixture> {
    
    private readonly KBumpAllocatorFixture _fixture;

    public KBumpAllocatorTests(KBumpAllocatorFixture fixture) {
        this._fixture = fixture;
    }

    [Fact]
    public void TestAllocateSuccess() {
        
        KMemorySegment seg1 = this._fixture.Allocator.Allocate(16);
        Assert.Equal((nuint) 16, seg1.Size);
        
        KMemorySegment seg2 = this._fixture.Allocator.Allocate(16);
        Assert.NotEqual(seg1.Start, seg2.Start);
        Assert.NotEqual(seg1.End, seg2.End);
        Assert.Equal(seg1.Size, seg2.Size);
        
        this._fixture.Allocator.Free();
        KMemorySegment seg3 = this._fixture.Allocator.Allocate(16);
        Assert.Equal(seg1.Start, seg3.Start);
        Assert.Equal(seg1.End, seg3.End);
        Assert.Equal(seg1.Size, seg3.Size);

    }
    
    [Fact]
    public void TestAllocateFailedBecauseItsFull() {
        
        this._fixture.Allocator.Allocate(16);
        KMemorySegment seg2 = this._fixture.Allocator.Allocate(KMemory.Kilobytes(2));
        Assert.Equal(KMemorySegment.NULL, seg2);
        
        this._fixture.Allocator.Free();
        Assert.Equal(KMemorySegment.NULL, this._fixture.Allocator.Allocate(KMemory.Kilobytes(2)));
        
    }

    public void Dispose() {
        this._fixture.Allocator.Free();
        GC.SuppressFinalize(this);
    }

    

}
