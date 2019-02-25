import datastructures.BasicStringBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BasicStringBuilderTest {

    @Test
    public void givenEmptyString_WhenInitialized_ShouldHaveLengthZero() {
        BasicStringBuilder bsb = new BasicStringBuilder("");
        Assert.assertEquals(0, bsb.length());
    }

    @Test
    public void givenStringLongerThanDefaultCapacity_WhenAppended_ShouldResize() {
        BasicStringBuilder bsb = new BasicStringBuilder();
        String str = "12345678910";
        bsb.append(str);
        Assert.assertEquals(str, bsb.toString());
        Assert.assertEquals(str.length()*2, bsb.capacity());
    }
}
