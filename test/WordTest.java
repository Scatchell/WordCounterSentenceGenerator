import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class WordTest {
    final String eos = "^&eos&^";


    @Test
    public void testAddLink() {
        Word word = new Word("Test");
        word.addLink("Link");

        assertNotNull(word.checkLinkExists("Link"));
    }

    @Test
    public void testIncrementLink() {
        Word word = new Word("Test");
        word.addLink("Link");
        word.addLink("Link");
        Integer linkPosition = word.checkLinkExists("Link");

        assertEquals(2, word.getLinks().get(linkPosition).rating);
    }

    @Test
    public void hasEOSLink() {
        Word word = new Word("Word1");
        word.addLink("Link1");
        word.addLink("Link1");
        word.addLink("Link2");
        word.addLink("Link2");
        word.addLink(eos);

        assertNotNull(word.hasEOSLink());
    }
}
