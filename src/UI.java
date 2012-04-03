import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

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
        SentenceGenerator sg = new SentenceGenerator(WordRepository.getInstance());
        runLoop(sg);
    }

    private static void runLoop(SentenceGenerator sg) {
        while (true) {
            System.out.print("****************************************************");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String name = null;
            try {
                name = br.readLine();
            } catch (IOException e) {
                System.out.println("Error!");
                System.exit(1);
            }
            sg.generate();
        }
    }
}