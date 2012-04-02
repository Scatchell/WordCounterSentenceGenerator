import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class fileReaderTest {
    @Test
    public void ShouldKnowIfWordExists() {
        MyFileReader fr = new MyFileReader(WordRepository.getInstance());
        Word word = new Word("Test");

        fr.wordRepository.addWord(word);
        assertNotNull(fr.wordRepository.getIndexPositionOfWord(word));
    }
}
