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

    @Test
    public void canDetermineWhenWordHasEosLink() {
        Word word = new Word("Mark");
        word.addLink(new Word(eos));
        assertEquals(true, word.hasEOSLink());
    }

    @Test
    public void shouldCalculateRandomIndex() {
        Word word = new Word("test");
        word.addLink(new Word("test2"));
        word.addLink(new Word("test3"));
        word.addLink(new Word("test4"));
        word.addLink(new Word("test5"));
        boolean outOfBounds = false;
        for (int i = 0; i < 50; i++) {
            if (word.calcRandomIndex() >= word.links.size() || word.calcRandomIndex() < 0) {
                outOfBounds = true;
            }
        }

        assertEquals(false, outOfBounds);
    }
    
    @Test
    public void calculateRandomWorksWithTwoLinks() {
        Word word = new Word("test");
        word.addLink(new Word("test2"));

    }
}