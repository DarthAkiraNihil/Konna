using Konna.Core.Memory;

namespace Konna.Core.Tests.Memory;

public class KMemorySegmentAdditionalTests {

    [Fact]
    public void TestEqualsOperator() {
        KMemorySegment s1 =  new KMemorySegment(0, 0);
        KMemorySegment s2 =  new KMemorySegment(0, 1);
        KMemorySegment s3 =  new KMemorySegment(1, 0);
        KMemorySegment s4 =  new KMemorySegment(1, 1);
        KMemorySegment same1 =  new KMemorySegment(0, 0);
        
        Assert.True(s1 != s2);
        Assert.True(s1 != s3);
        Assert.True(s1 != s4);
        Assert.True(s1 == same1);
    }
    
    [Fact]
    public void TestEqualsAndHashCode() {
        KMemorySegment s1 =  new KMemorySegment(0, 0);
        KMemorySegment s2 =  new KMemorySegment(0, 1);
        KMemorySegment s3 =  new KMemorySegment(1, 0);
        KMemorySegment s4 =  new KMemorySegment(1, 1);
        KMemorySegment same1 =  new KMemorySegment(0, 0);
        
        Assert.False(s1.Equals(null));
        Assert.False(s1.Equals(1));
        
        Assert.False(s1.Equals(s2));
        Assert.False(s1.Equals(s3));
        Assert.False(s1.Equals(s4));
        Assert.True(s1.Equals(same1));
        Assert.True(s1.Equals(s1));
        
        Assert.True(s1.GetHashCode() != s2.GetHashCode());
        Assert.True(s1.GetHashCode() != s3.GetHashCode());
        Assert.True(s1.GetHashCode() != s4.GetHashCode());
        Assert.True(s1.GetHashCode() == same1.GetHashCode());
    }

}
