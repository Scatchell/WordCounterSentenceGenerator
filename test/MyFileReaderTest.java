import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class MyFileReaderTest {
    MyFileReader fr;
    Word newWord;

    @Before
    public void setUp() {
        fr = new MyFileReader();
        newWord = new Word("Test");
    }

    @Test
    public void testAddNewWord() {
        fr.addOrUpdateWord(newWord, null);

        assertEquals(true, fr.words.contains(newWord));
    }
    
    @Test
    public void testUpdateWord() {
        fr.addOrUpdateWord(newWord, null);

        fr.addOrUpdateWord(newWord, "Link");
    }

    @Test
    public void testCheckWordExists() {
        fr.addOrUpdateWord(newWord, null);

        assertNotNull(fr.getIndexPositionOfWord(newWord));
    }

//    @Test
//    public void testUpdateWord() {
//        fr.addOrUpdateWord(newWord);
//        newWord.addLink("test link");
//
//        fr.addOrUpdateWord(newWord);
//
//    }
}
