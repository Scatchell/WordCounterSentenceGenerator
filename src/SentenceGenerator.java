import java.util.ArrayList;

public class SentenceGenerator {
    public WordRepository repository;
    public int numOfWords;
    final static Word endOfSentence = new Word("^&eos&^");
    
    public SentenceGenerator(WordRepository repository) {
        this.repository = repository;
    }
    
    public void generate() {
        numOfWords = (int) ((Math.random() * 7) + 4);
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
        String firstWord = next.toString();
        String firstLetter = firstWord.substring(0,1);
        sb.append(firstWord.replaceFirst(firstLetter, firstLetter.toUpperCase()));
        sb.append(" ");
        ArrayList<Word> usedWords = new ArrayList<Word>();
        usedWords.add(next);
        iterateAndSelectWords(counter, sb, next, usedWords);
        restoreLinkRates(usedWords);
        return cullDuplicatesAndCreateString(sb.toString()).replaceAll(" i\\.", " I.");
    }

    private void iterateAndSelectWords(int counter, StringBuilder sb, Word next, ArrayList<Word> usedWords) {
        for (int i = 0; i < counter; i++) {
            if (i == counter - 1 ) {
                guardAgainstNull(sb, next, usedWords);
                break;
            } else if (next.getBestLink() != null) {
                usedWords.add(next.getBestLink());
                sb.append(next.getBestLink().toString());
                sb.append(" ");
                next = repository.getByName(next.getBestLink().toString());
            } else {
                // sb.append("NO FOLLOWING LINK");  <- commenting out for now
                sb.append("is the end.");
                break;
            }
        }
    }

    private void guardAgainstNull(StringBuilder sb, Word next, ArrayList<Word> usedWords) {
        if (repository.getLikelyEOSWord(next) == null) {
            sb.append("EOS NOT FOUND");
        } else {
            usedWords.add(repository.getLikelyEOSWord(next));
            sb.append(repository.getLikelyEOSWord(next).toString()).append(".");
        }
    }

    private void restoreLinkRates(ArrayList<Word> usedWords) {
        for (Word word : usedWords) {
            for (Link link : word.links) {
                link.restoreRate();
            }
        }
    }

    public String cullDuplicatesAndCreateString(String sentence) {
        String[] words = sentence.split(" ");
        ArrayList<String> returnList = new ArrayList<String>();
        removeDupes(words, returnList);
        StringBuilder sb = new StringBuilder();
        addNonDuplicateWords(returnList, sb);
        return sb.toString().replaceAll(" a a", " an a").replaceAll(" a o", " an o")
                .replaceAll(" a i", " an i").replaceAll(" i ", " I ")
                .replaceAll(" a e", " an e").replaceAll(" a u", " an u");
    }

    private void removeDupes(String[] words, ArrayList<String> returnList) {
        for (int i = 0; i < words.length; i++) {
            if (i >= 1 && (words[i].equals(words[i - 1]))) {
                // ignore - do not add consecutive identical words
            } else {
                returnList.add(words[i]);
            }
        }
    }

    private void addNonDuplicateWords(ArrayList<String> returnList, StringBuilder sb) {
        for (int i = 0; i < returnList.size(); i++) {
            if (i == (returnList.size() - 1)) {
                sb.append(returnList.get(i));
            } else {
                sb.append(returnList.get(i));
                sb.append(" ");
            }
        }
    }
}