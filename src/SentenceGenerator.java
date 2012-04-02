import java.util.ArrayList;

public class SentenceGenerator {
    WordRepository repository;
    public int numOfWords = 10;
    final static Word endOfSentence = new Word("^&eos&^");
    
    public SentenceGenerator(WordRepository repository) {
        this.repository = repository;
    }
    
    public void generate() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Word> wordsClone = (ArrayList<Word>) this.repository.getWordList().clone();
        Word word = wordsClone.get((int) (Math.random() * wordsClone.size()));
        String sentence = buildString(word, numOfWords);
        sb.append(sentence);
        System.out.println(sb.toString());
    }

    public Word getNextLink(Word first) {
        Word check = repository.getByName(first.toString());
        return check.getBestLink();
    }

    public String buildString(Word start, int counter) {
        StringBuilder sb = new StringBuilder();
        Word next = repository.getByName(start.toString());
        sb.append(next.toString());
        sb.append(" ");
        for (int i = 0; i < counter; i++) {
            if (i == counter - 1 ) {
                if (repository.getLikelyEOSWord(next) == null) {
                    sb.append("EOS NOT FOUND");
                    break;
                } else {
                    sb.append(repository.getLikelyEOSWord(next).toString()).append(".");
                    break;
                }
            } else if (next.getBestLink() != null) {
                sb.append(next.getBestLink().toString());
                sb.append(" ");
                next = repository.getByName(next.getBestLink().toString());
            } else {
                // sb.append("NO FOLLOWING LINK");  <- commenting out for now
                sb.append("is the end.");
                break;
            }
        }
        return sb.toString();
    }
}