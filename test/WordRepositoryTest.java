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
    public void isASingleton() {
        WordRepository repo = WordRepository.getInstance();
        assertEquals(repo, wordRepository);
    }
        
    @Test
    public void shouldBeAbleToAddAndRetrieveWords() {
        wordRepository.addOrUpdateWord(word, linkWord);
        assertEquals(true, wordRepository.getWordList().contains(word));
        assertEquals(0, (Object) wordRepository.getWordList().get(0).getIndexOfLinkOrNull(linkWord));
        assertEquals(1, wordRepository.getWordList().size());
    }

    @Test
    public void whenAddingSameWordTwiceItDoesNotMakeDuplicateEntries() {
        wordRepository.addWord(word);
        wordRepository.addWord(word);
        assertEquals(1, wordRepository.getWordList().size());
    }
    
    @Test
    public void returnsNullWhenWordNotFound() {
        wordRepository.addWord(word);
        assertEquals(null, wordRepository.getIndexPositionOfWord(new Word("Mark")));
    }
    
    @Test
    public void allMethodsWorkAsExpected() {
        wordRepository.addWord(word);
        assertEquals(true, wordRepository.getWordList().contains(word));
        assertEquals(0, (Object) wordRepository.getIndexPositionOfWord(word));
        wordRepository.addOrUpdateWord(word, linkWord);
        assertEquals(0, (Object) wordRepository.getWordList().get(0).getIndexOfLinkOrNull(linkWord));
        assertEquals(0, (Object) wordRepository.getIndexPositionOfWord(word));
        wordRepository.addWord(new Word("Mark"));
        assertEquals(2, wordRepository.getWordList().size());
    }
}