using Konna.Core.Memory;

namespace Konna.Core.Tests.Fixtures;

public sealed class KBumpAllocatorFixture: IDisposable{

    public KBumpAllocator Allocator { get; }

    public KBumpAllocatorFixture() {
        this.Allocator = KMemory.CreateBumpAllocator(
            KMemory.Kilobytes(1),
            "TestBumpAllocator"
        );
        
        Assert.Equal("TestBumpAllocator", this.Allocator.AreaName);
    }

    public void Dispose() {
        this.Allocator.Flush();
    }
}
