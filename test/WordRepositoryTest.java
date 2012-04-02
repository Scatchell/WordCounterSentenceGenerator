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
        assertEquals(2, wordRepository.getWordList().size());
    }

    @Test
    public void whenAddingSameWordTwiceItDoesNotMakeDuplicateEntries() {
        wordRepository.addWord(word);
        wordRepository.addWord(word);
        assertEquals(2, wordRepository.getWordList().size());
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
        assertEquals(3, wordRepository.getWordList().size());
    }
    
    @Test
    public void canGetWordByName() {
        Word foo = new Word("foo");
        wordRepository.addWord(foo);
        assertEquals(foo, wordRepository.getByName("foo"));
    }
    
    @Test
    public void updatesWordsCorrectly() {
        Word foo = new Word("foo");
        Word bar = new Word("bar");
        wordRepository.addWord(foo);
        assertEquals(null, wordRepository.getByName("foo").getBestLink());
        wordRepository.addOrUpdateWord(foo, bar);
        assertEquals(bar, wordRepository.getByName("foo").getBestLink());
        wordRepository.addOrUpdateWord(foo, bar);
        assertEquals(bar, wordRepository.getByName("foo").getBestLink());
    }
}