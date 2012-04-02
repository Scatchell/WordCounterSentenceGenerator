//import org.junit.Before;
//import org.junit.Test;
//
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertNotNull;
//
//public class MyFileReaderTest {
//    MyFileReader fr;
//    Word newWord;
//
//    @Before
//    public void setUp() {
//        fr = new MyFileReader(WordRepository.getInstance());
//        newWord = new Word("Test");
//    }
//
//    @Test
//    public void testAddNewWord() {
//        fr.wordRepository.addOrUpdateWord(newWord, null);
//        assertEquals(true, fr.wordRepository.getWordList().contains(newWord));
//    }
//
//    @Test
//    public void testUpdateWord() {
//        fr.wordRepository.addOrUpdateWord(newWord, new Word("foo"));
//        fr.wordRepository.addOrUpdateWord(newWord, new Word("Link"));
//        assertEquals(0, (Object) fr.wordRepository.getWordList().get(fr.wordRepository.getIndexPositionOfWord(newWord)).getIndexOfLinkOrNull(new Word("Link")));
//    }
//
//    @Test
//    public void testCheckWordExists() {
//        fr.wordRepository.addOrUpdateWord(newWord, null);
//
//        assertNotNull(fr.wordRepository.getIndexPositionOfWord(newWord));
//    }
//
//    @Test
//    public void testUpdateWord() {
//        fr.addOrUpdateWord(newWord);
//        newWord.addLink("test link");
//
//        fr.addOrUpdateWord(newWord);
//
//    }
//}
