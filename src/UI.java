import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: anthony
 * Date: 1/4/12
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class UI {
    public static void main(String[] args) {
        MyFileReader fr = new MyFileReader();
        ArrayList<Word> words = new ArrayList<Word>();

        try {
            words = fr.readFile();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Collections.sort(words, new WordComparator());

        for (Iterator<Word> iterator = words.iterator(); iterator.hasNext(); ) {
            Word next = iterator.next();
            System.out.println(next.word + ": " + next.getLinks().toString());
        }

        SentenceGenerator sg = new SentenceGenerator(words);
        sg.generate();
    }


}
