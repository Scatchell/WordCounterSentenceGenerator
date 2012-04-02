import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class UI {
    public static void main(String[] args) {
        WordRepository repository = WordRepository.getInstance();
        MyFileReader fr = new MyFileReader(repository);
        ArrayList<Word> words = new ArrayList<Word>();

        try {
            words = fr.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(words, new WordComparator());

        for (Word next : words) {
            System.out.println(next.word + ": " + next.getLinks().toString());
        }

        SentenceGenerator sg = new SentenceGenerator(words);
        sg.generate();
    }


}
