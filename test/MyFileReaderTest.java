import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
* Created by IntelliJ IDEA.
* User: anthony
* Date: 1/4/12
* Time: 10:17 PM
* To change this template use File | Settings | File Templates.
*/
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

        assertNotNull(fr.checkWordExists(newWord));
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
