import java.util.ArrayList;

public class SentenceGenerator {
    ArrayList<Word> words;
    int numOfWords = 10;
    
    public SentenceGenerator(ArrayList<Word> words) {
        this.words = words;
    }
    
    public void generate() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Word> wordsClone = (ArrayList<Word>) this.words.clone();
        // randomized starting word to get a better feel for what is happening
        Word word = wordsClone.get((int) (Math.random() * wordsClone.size()));
        for (int i = 0; i < numOfWords; i++) {
            if (i == numOfWords - 1) {
                for (Word next : wordsClone) {
                    Integer eosLink = next.getEOSIndexOrNull();
                    if (eosLink != null) {
                        sb.append(next.word).append(".");
                        break;
                    }
                }
            } else {
                //todo words.get has been moved to top.  Change so each link is searched for in the words list, and used as the next word.  Also need to remove words from array as they are used so they are not used twice
                String link = word.getBestLink().toString();
                sb.append(word.word + " " + link + " ");
            }
        }
        System.out.println(sb.toString());
    }


    public Word getNextLink(Word first) {
        if (first.getBestLink() != null) {
            return first.getBestLink();
        } else {
            return Word.endOfSentence;
        }
    }
}