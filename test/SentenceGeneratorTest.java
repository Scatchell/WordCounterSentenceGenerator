import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class SentenceGeneratorTest {
    WordRepository repository;
    SentenceGenerator generator;
    Word mark = new Word("Mark");
    Word joins = new Word("joins");
    Word java = new Word("java");

    @Before
    public void setUp() {
        repository = WordRepoTestClass.getInstance();
        generator = new SentenceGenerator(repository);
        repository.addOrUpdateWord(mark, joins);
        repository.addOrUpdateWord(joins, java);
    }

    @After
    public void tearDown() {
        repository = null;
        generator.repository = null;
    }
    
    @Test
    public void canGrabTheNextLinkFromAWord() {
        assertEquals(joins, generator.getNextLink(mark));
        assertEquals(java, generator.getNextLink(joins));
    }
    
    @Test
    public void returnsEOSIfNextWordIsNull() {
        assertEquals(null, generator.getNextLink(java));
    }

    @Test
    public void canChainTogetherWordsInALine() {
        repository.addOrUpdateWord(mark, joins);
        assertEquals(joins, generator.getNextLink(mark));
        repository.addOrUpdateWord(joins, java);
        assertEquals(java, generator.getNextLink(joins));
        assertEquals(joins, generator.getNextLink(mark));
        assertNotSame(java, generator.getNextLink(mark));
    }
    
    @Test
    public void sentenceBuilderCanMakeAFuckingSentence() {
        repository.addOrUpdateWord(mark, joins);
        repository.addOrUpdateWord(joins, java);
        repository.addOrUpdateWord(java, SentenceGenerator.endOfSentence);
        assertEquals("Mark joins java.", generator.buildString(mark, 2));
    }
    
    @Test
    public void shouldRemoveDuplicateConsecutiveWords() {
        assertEquals("i walked to the store.", new SentenceGenerator(repository).cullDuplicatesAndCreateString("i walked to the the store"));
    }
    
    @Test
    public void shouldNotRemoveNonConsecutiveDuplicates() {
        SentenceGenerator sg = new SentenceGenerator(repository);
        assertEquals("i walked I to the I store walked.", sg.cullDuplicatesAndCreateString("i walked walked i to to the i i store store walked"));
    }
    
    @Test
    public void shouldSubAnForAWhenFollowedByAVowel() {
        SentenceGenerator sg = new SentenceGenerator(repository);
        assertEquals("I have an almost perfect solution to an epic problem.", sg.cullDuplicatesAndCreateString("I have a almost perfect solution to a epic problem"));
    }
    
    @Test
    public void shouldSubThoseAnsForAsContinued() {
        SentenceGenerator sg = new SentenceGenerator(repository);
        assertEquals("a ear a dog an umbrella a toast an interest.", sg.cullDuplicatesAndCreateString("a ear a dog a umbrella a toast a interest"));
    }

}