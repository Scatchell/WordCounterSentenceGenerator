import java.util.ArrayList;

public class SentenceGenerator {
    ArrayList<Word> words = new ArrayList<Word>();
    int numOfWords = 10;
    
    public SentenceGenerator(ArrayList<Word> words) {
        this.words = words;
    }
    
    public void generate() {
        StringBuilder sb = new StringBuilder();
        //todo clone words to a temp array so items can be removed without destroying original
        Word word = words.get(0);
        for (int i = 0; i < numOfWords; i++) {

            if (i == numOfWords - 1) {
                for (Word next : words) {
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
}