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
            System.out.print("***************Re-Generate from: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            Integer userSelection;

            try {
                userInput = br.readLine();
                if (userInput.equals("exit")) {
                    System.out.println("Goodbye.");
                    break;
                }
                userSelection = Integer.parseInt(userInput);
                sg.regeneratePartialSentence(userSelection);
            } catch (IOException e) {
                System.out.println("Error!");
                System.exit(1);
            } catch (NumberFormatException e) {
                System.out.println("<Number cannot be parsed, generating completely new sentence...>");
                sg.generate();
            }
            
            

        }
    }
}