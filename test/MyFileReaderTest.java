import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MyFileReaderTest {
    MyFileReader fr;
    Word newWord;

    @Before
    public void setUp() {
        fr = new MyFileReader(WordRepository.getInstance());
        newWord = new Word("Test");
    }

    @Test
    public void testAddNewWord() {
        fr.wordRepository.addOrUpdateWord(newWord, null);
        assertEquals(true, fr.wordRepository.getWordList().contains(newWord));
    }
}
