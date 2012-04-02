import java.util.ArrayList;

public class SentenceGenerator {
    WordRepository repository;
    int numOfWords = 10;
    final static Word endOfSentence = new Word("^&eos&^");
    
    public SentenceGenerator(WordRepository repository) {
        this.repository = repository;
    }
    
    public void generate() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Word> wordsClone = (ArrayList<Word>) this.repository.getWordList().clone();
        // randomized starting word to get a better feel for what is happening
        Word word = wordsClone.get((int) (Math.random() * 100));
//        for (int i = 0; i < numOfWords; i++) {
//            if (i == numOfWords - 1) {
//                for (Word next : wordsClone) {
//                    Integer eosLink = next.getEOSIndexOrNull();
//                    if (eosLink != null) {
//                        sb.append(next.word).append(".");
//                        break;
//                    }
//                }
//            } else {
//                //todo words.get has been moved to top.  Change so each link is searched for in the words list, and used as the next word.  Also need to remove words from array as they are used so they are not used twice
//                String link = word.getBestLink().toString();
//                sb.append(word.word + " " + link + " ");
//            }
//        }
        String sentence = buildString(word, numOfWords);
        sb.append(sentence);
        System.out.println(sb.toString());
    }

    public Word getNextLink(Word first) {
        Word check = repository.getByName(first.toString());
        if (check.getBestLink() != null) {
            return check.getBestLink();
        } else {
            return endOfSentence;
        }
    }

    public String buildString(Word start, int counter) {
        StringBuilder sb = new StringBuilder();
        Word next = repository.getByName(start.toString());
        sb.append(next.toString());
        sb.append(" ");
        for (int i = 0; i < counter; i++) {
            if (i == counter - 1 ){
                sb.append(endOfSentence);
                break;
            } else if (next.getBestLink() != null) {
                sb.append(next.getBestLink().toString());
                sb.append(" ");
                next = repository.getByName(next.getBestLink().toString());
            } else {
                sb.append(endOfSentence);
                break;
            }
        }
        return sb.toString();
    }
}