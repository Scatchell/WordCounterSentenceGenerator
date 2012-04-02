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
        if (!this.wordList.contains(word)) {
            this.wordList.add(word);
        }
    }

    public ArrayList<Word> getWordList() {
        return this.wordList;
    }

    public void addOrUpdateWord(Word anchorWord, Word linkedWord) {
        Integer wordPosition = getIndexPositionOfWord(anchorWord);
        if (wordPosition == null) {
            addWord(anchorWord);
            addWord(linkedWord);
            wordPosition = getIndexPositionOfWord(anchorWord);
            wordList.get(wordPosition).addLink(linkedWord);
        } else {
            addWord(linkedWord);
            wordList.get(wordPosition).addLink(linkedWord);
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

    public Word getByName(String wordName) {
        for (Word word : wordList) {
            if (word.toString().equals(wordName)) {
                return word;
            }
        }
        return null;
    }
}
