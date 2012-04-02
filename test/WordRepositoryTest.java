import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class WordRepositoryTest {
    final WordRepository wordRepository = WordRepository.getInstance();
    Word word;
    Word linkWord;

    @Before
    public void setup() {
        word = new Word("Test");
        linkWord = new Word("Two");
    }
        
    @Test
    public void shouldBeAbleToAddAndRetrieveWords() {
        wordRepository.addOrUpdateWord(word, linkWord);
        assertEquals(true, wordRepository.getWordList().contains(word));
        assertEquals(0, (Object) wordRepository.getWordList().get(0).getIndexOfLinkOrNull(linkWord));
    }
    
    @Test
    public void allMethodsWorkAsExpected() {
        wordRepository.addWord(word);
        assertEquals(true, wordRepository.getWordList().contains(word));
        assertEquals(0, (Object) wordRepository.getIndexPositionOfWord(word));
        wordRepository.addOrUpdateWord(word, linkWord);
        assertEquals(0, (Object) wordRepository.getWordList().get(0).getIndexOfLinkOrNull(linkWord));
        assertEquals(0, (Object) wordRepository.getIndexPositionOfWord(word));
    }
}
