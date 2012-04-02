import junit.framework.Assert;
import org.junit.Test;

/**
* Created by IntelliJ IDEA.
* User: anthony
* Date: 1/4/12
* Time: 8:24 PM
* To change this template use File | Settings | File Templates.
*/
public class LinkTest {
    @Test
    public void shouldMatchShit() {
        Assert.assertEquals(true, "hello my name is Anthony.".matches(".*(?<!Mr|Mrs|Dr|Ms)\\.\\s*.*"));
    }
}
