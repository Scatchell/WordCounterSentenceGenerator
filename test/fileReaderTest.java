import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class fileReaderTest {
    @Test
    public void ShouldKnowIfWordExists() {
        MyFileReader fr = new MyFileReader();
        Word word = new Word("Test");

        fr.addWord(word);
        assertNotNull(fr.getIndexPositionOfWord(word));
    }
}
