import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LinkTest {
    @Test
    public void shouldMatchShit() {
        assertEquals(true, "hello my name is Anthony.".matches(".*(?<!Mr|Mrs|Dr|Ms)\\.\\s*.*"));
    }
    
    @Test
    public void shouldBeAbleToAddWordsInLinks() {
        Link link = new Link(new Word("mark"));
        assertEquals(new Word("mark"), link.otherWord);
    }
    
    @Test
    public void shouldBeAbleToPlusRate() {
        Link link = new Link(new Word("mark"));
        link.plusRate();
        assertEquals(2, link.rating);
    }
}
