import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: anthony
 * Date: 1/4/12
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyFileReader {
    String filePath = "/home/anthony/JaveSideProjects/WordInformation/testFile";
    final String eos = "^&eos&^";
    ArrayList<Word> words = new ArrayList<Word>();

    public ArrayList<Word> readFile() throws IOException {
        BufferedReader bufferedReader;
        java.io.FileReader fileReader;

        fileReader = new java.io.FileReader(filePath.trim());
        bufferedReader = new BufferedReader(fileReader);

        String currentLine;
        String[] pieces;
        Integer wordPosition;
        Word tempWord;
        String toAdd;

        while ((currentLine = bufferedReader.readLine()) != null) {
            pieces = currentLine.split("\\s+");

            for (int i = 0; i < (pieces.length); i++) {

                tempWord = new Word(pieces[i]);

                if (pieces[i].matches(".*(?<!Mr|Mrs|Dr|Ms)(\\.|\\!|\\?)\\s*.*")) {
                    toAdd = eos;
                } else if (i < pieces.length - 1) {
                    toAdd = pieces[i + 1];
                } else {
                    toAdd = null;
                }

                if (toAdd != null) {
                    addOrUpdateWord(tempWord, toAdd);
                }

            }

        }

        return words;
    }

    public void addOrUpdateWord(Word tempWord, String toAdd) {
        Integer wordPosition = checkWordExists(tempWord);

        if (wordPosition == null) {
            addWord(tempWord);
            wordPosition = checkWordExists(tempWord);
            words.get(wordPosition).addLink(toAdd);
        } else {
            words.get(wordPosition).addLink(toAdd);
        }
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public Integer checkWordExists(Word word) {
        int count = 0;

        for (Iterator<Word> iterator = words.iterator(); iterator.hasNext(); ) {

            Word next = iterator.next();
            if (next.equals(word)) {
                return count;
            }
            count++;
        }

        return null;
    }

}