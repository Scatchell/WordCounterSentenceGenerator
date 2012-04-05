import java.util.ArrayList;
import java.util.Iterator;
//todo allow partial sentence generator to add words to end of sentence (i.e. the sentence needs 1 more word to be good)
//todo potentially allow to remove words from end of sentence, as well? (i.e. the sentence is great at 6 words and doesn't need the 7'th word)
//todo make sure high eos links are added when the choosing the next word if the next word is the last word in the sentence.
//todo teach sentence generator to learn (change ratings) based on user selections (good if move onto next word, bad if change word)
public class SentenceGenerator {
    public WordRepository repository;
    public int numOfWords;
    final static Word endOfSentence = new Word("^&eos&^");
    String lastSentence;
    ArrayList<Word> exceptions;

    public SentenceGenerator(WordRepository repository) {
        this(repository, ((int) ((Math.random() * 7) + 4)));
    }

    public SentenceGenerator(WordRepository repository, int numOfWords) {
        this.repository = repository;
        System.out.println(numOfWords);
        this.numOfWords = numOfWords;
        this.exceptions = new ArrayList<Word>();
    }

    public void generate() {
        //reset exceptions
        exceptions = new ArrayList<Word>();

        int wordListSize = this.repository.getWordList().size();
        Word word = getWordFromList((int) (Math.random() * wordListSize));
        String sentence = buildString(word, numOfWords, true);
        saveAndPrintSentence(sentence);
    }

    private void saveAndPrintSentence(String sentence) {
        lastSentence = sentence;
        System.out.println(sentence);
        sentence = addNumbersToWords(sentence);
        System.out.println("Select number of word to be changed below");
        System.out.println(sentence);
    }

    private Word getWordFromList(int index) {
        ArrayList<Word> wordsList = (ArrayList<Word>) this.repository.getWordList().clone();
        return wordsList.get(index);
    }


    public void regeneratePartialSentence(Integer userSelection) {
        String[] sentenceArray = lastSentence.split(" ");

//        System.out.println("Sentence array to follow: ");
//        for (int i = 0; i < sentenceArray.length; i++) {
//            String s = sentenceArray[i];
//            System.out.print(s + " ");
//        }
        if (userSelection == 1) {
            generate();
        } else if (userSelection > 1 && userSelection < sentenceArray.length) {
            String partialSentence = getPartialSentence(userSelection - 2, sentenceArray);
            String startingWord = sentenceArray[userSelection - 2];
            ArrayList<Word> exceptions = new ArrayList<Word>();
            exceptions.add(new Word(sentenceArray[userSelection - 1]));
//        System.out.println("Starting word: " + startingWord);

            Word word = this.repository.getByName(startingWord.toLowerCase());
            System.out.println(numOfWords - userSelection);
            String sentence = buildString(word, numOfWords - userSelection + 2, false);
            sentence = partialSentence + sentence;
            lastSentence = sentence;
            saveAndPrintSentence(sentence);
        } else {
            System.out.println("Selection out of bounds, please try again.");
        }
    }

    private String addNumbersToWords(String sentence) {
        String[] pieces = sentence.split(" ");
        Integer numOfLinks;
        StringBuilder sentenceWithWords = new StringBuilder();

        for (int i = 0; i < pieces.length - 1; i++) {
            String piece = pieces[i];
            numOfLinks = repository.getByName(piece.toLowerCase()).getLinks().size();

            sentenceWithWords.append((i + 1) + ":" + piece).append("(" + numOfLinks + ") ");
        }

        return sentenceWithWords.toString();
    }

    private String getPartialSentence(Integer userSelection, String[] sentenceArray) {
        StringBuilder lastSentenceTemp = new StringBuilder();
        for (int i = 0; i < userSelection; i++) {
            String s = sentenceArray[i];
            lastSentenceTemp.append(s + " ");
        }

        return lastSentenceTemp.toString();
    }

    public Word getNextLink(Word first) {
        Word check = repository.getByName(first.toString());
        return check.getBestLink();
    }

    public String buildString(Word start, int numOfWords, boolean firstTime) {
        StringBuilder sb = new StringBuilder();
        Word next = new Word("<WORD_NOT_FOUND>");

        if (repository.getByName(start.toString()) != null) {
            next = repository.getByName(start.toString());
        }

        String firstWord = next.toString();
        if (firstTime) {
            firstWord = upperCaseFirstLetter(firstWord);
        }
        sb.append(firstWord).append(" ");
        exceptions.add(next);
        iterateAndSelectWords(numOfWords, sb, next);
        restoreLinkRates(exceptions);
        return cullDuplicatesAndCreateString(sb.toString()).replaceAll(" i\\.", " I.");
    }

    private String upperCaseFirstLetter(String firstWord) {
        String firstLetter = firstWord.substring(0, 1);
        return firstWord.replaceFirst(firstLetter, firstLetter.toUpperCase());
    }

    private void iterateAndSelectWords(int counter, StringBuilder sb, Word next) {
        for (int i = 0; i < counter; i++) {
            if (i == counter - 1) {
                sb.append(getEOSWord(next));
                break;
            } else {
                Word bestLink = next.getBestLink();
                if (bestLink != null) {
                    Word nextBestWord = bestLink;

                    //todo for some reason, when a very low number of exceptions exist (2), after cycling through all the exceptions and clearing the exception list, all exceptions are added again, and we cannot switch to different links anymore
                    if (!exceptionExists(nextBestWord)) {
                        exceptions.add(nextBestWord);
                        sb.append(nextBestWord.toString());
                        sb.append(" ");

                        next = repository.getByName(bestLink.toString());
                    } else {
                        if (exceptionsForAllLinks(nextBestWord)) {
                            System.out.println("Already cycled through all options, starting from beginning again.");
                            exceptions.clear();
                        } else {
                            System.out.println("Exception exists, trying again!");
                        }

                        i--;
                    }
                } else {
                    sb.append("<NO_FOLLOWING_LINK>");
                    break;
                }
            }
        }
    }

    private boolean exceptionExists(Word nextBestWord) {
        for (Iterator<Word> iterator = exceptions.iterator(); iterator.hasNext(); ) {
            Word next = iterator.next();
            if (nextBestWord == next) {
                return true;
            }
        }

        return false;
    }

    public boolean exceptionsForAllLinks(Word word) {
        ArrayList<Link> links = word.links;
        boolean allExceptions = true;
        for (Iterator<Link> iterator = links.iterator(); iterator.hasNext(); ) {
            Link next = iterator.next();
            if (!exceptionExists(next.otherWord))
                allExceptions = false;
        }

        return allExceptions;
    }

    private String getEOSWord(Word next) {
        //todo make this search for different EOS word if found as null (i.e. get likely eos for a random word)
        if (repository.getLikelyEOSWord(next) == null) {
            return "<EOS_NOT_FOUND>";
        } else {
            exceptions.add(repository.getLikelyEOSWord(next));
            return repository.getLikelyEOSWord(next).toString();
        }
    }

    private void restoreLinkRates(ArrayList<Word> usedWords) {
        for (Word word : usedWords) {
            for (Link link : this.repository.getByName(word.toString()).links) {
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
        return sb.append(".").toString().replaceAll(" a a", " an a").replaceAll(" a o", " an o")
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