import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class WordTest {
    final String eos = "^&eos&^";

    @Test
    public void testAddLink() {
        Word word = new Word("Test");
        word.addLink(new Word("Link"));
        assertNotNull(word.getIndexOfLinkOrNull(new Word("Link")));
    }
    
    @Test
    public void checkLinkExistsWorks() {
        Word word = new Word("Mark");
        word.addLink(new Word("Link"));
        assertEquals(0, (Object) word.getIndexOfLinkOrNull(new Word("Link")));
        assertEquals(null, (Object) word.getIndexOfLinkOrNull(new Word("Mary")));
        word.addLink(new Word("Link"));
    }

    @Test
    public void testIncrementLink() {
        Word word = new Word("Test");
        word.addLink(new Word("Link"));
        word.addLink(new Word("Link"));
        Integer linkPosition = word.getIndexOfLinkOrNull(new Word("Link"));

        assertEquals(2, word.getLinks().get(linkPosition).rating);
    }

    @Test
    public void hasEOSLink() {
        Word word = new Word("Word1");
        word.addLink(new Word("Link1"));
        word.addLink(new Word("Link1"));
        word.addLink(new Word("Link2"));
        word.addLink(new Word("Link3"));
        word.addLink(new Word(eos));

        assertNotNull(word.getEOSIndexOrNull());
    }
    
    @Test
    public void canGetNearestEOSLink() {
        Word word = new Word("Mark");
        word.addLink(new Word(eos));
        assertEquals(new Link(new Word(eos)), word.getNearestEOSLink());
    }
}
