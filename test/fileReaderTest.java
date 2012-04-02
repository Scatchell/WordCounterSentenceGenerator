import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
* Created by IntelliJ IDEA.
* User: anthony
* Date: 1/4/12
* Time: 9:18 PM
* To change this template use File | Settings | File Templates.
*/
public class fileReaderTest {
    @Test
    public void ShouldKnowIfWordExists() {
        MyFileReader fr = new MyFileReader();
        Word word = new Word("Test");

        fr.addWord(word);
        assertNotNull(fr.checkWordExists(word));
    }
}
