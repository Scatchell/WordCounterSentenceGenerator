import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileReader {
    String filePath = "/home/mbillie/WordCounterSentenceGenerator/testFile";
    final String eos = "^&eos&^";
    private final WordRepository wordRepository;

    public MyFileReader(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public ArrayList<Word> readFile() throws IOException {
        BufferedReader bufferedReader;
        java.io.FileReader fileReader;
        fileReader = new java.io.FileReader(filePath.trim());
        bufferedReader = new BufferedReader(fileReader);

        String currentLine;
        String[] pieces;
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
                    this.wordRepository.addOrUpdateWord(tempWord, toAdd);
                }
            }
        }
        return this.wordRepository.getWordList();
    }

}