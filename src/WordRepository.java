import java.util.ArrayList;

public class WordRepository {
    private static WordRepository instance = null;
    private ArrayList<Word> wordList;

    private WordRepository() {
        this.wordList = new ArrayList<Word>();
    }

    public static WordRepository getInstance() {
        if (instance == null) {
            instance = new WordRepository();
        }
        return instance;
    }

    public void addWord(Word word) {
        this.wordList.add(word);
    }

    public ArrayList<Word> getWordList() {
        return this.wordList;
    }

    public void addOrUpdateWord(Word tempWord, String toAdd) {
        Integer wordPosition = getIndexPositionOfWord(tempWord);

        if (wordPosition == null) {
            addWord(tempWord);
            wordPosition = getIndexPositionOfWord(tempWord);
            wordList.get(wordPosition).addLink(toAdd);
        } else {
            wordList.get(wordPosition).addLink(toAdd);
        }
    }

    public Integer getIndexPositionOfWord(Word word) {
        int count = 0;
        for (Word next : wordList) {
            if (next.equals(word)) {
                return count;
            }
            count++;
        }
        return null;
    }
}
