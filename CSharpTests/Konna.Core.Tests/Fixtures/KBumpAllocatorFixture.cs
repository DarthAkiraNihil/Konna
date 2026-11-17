using Konna.Core.Memory;

namespace Konna.Core.Tests.Fixtures;

public sealed class KBumpAllocatorFixture: IDisposable{

    public KBumpAllocator Allocator { get; }
    public KBumpAllocator AlignedAllocator { get; }

    public KBumpAllocatorFixture() {
        this.Allocator = KMemory.CreateBumpAllocator(
            KMemory.Kilobytes(1),
            "TestBumpAllocator"
        );
        this.AlignedAllocator = KMemory.CreateBumpAllocator(
            KMemory.Kilobytes(1),
            16,
            "TestAlignedAllocator"
        );
        
        Assert.Equal("TestBumpAllocator", this.Allocator.AreaName);
    }

    public void Dispose() {
        this.Allocator.Flush();
    }
}
