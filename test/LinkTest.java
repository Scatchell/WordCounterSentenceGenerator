import junit.framework.Assert;
import org.junit.Test;

public class LinkTest {
    @Test
    public void shouldMatchShit() {
        Assert.assertEquals(true, "hello my name is Anthony.".matches(".*(?<!Mr|Mrs|Dr|Ms)\\.\\s*.*"));
    }
}
