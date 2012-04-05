import java.util.ArrayList;
import java.util.Collections;

public class Word {
    String word;
    ArrayList<Link> links = new ArrayList<Link>();
    final String eos = "^&eos&^";

    public Word(String word) {
        this.word = this.trimWord(word);
    }

    public void addLink(Word otherWord) {
        Integer linkPosition = getIndexOfLinkOrNull(otherWord);
        if (linkPosition != null) {
            this.links.get(linkPosition).plusRate();
        } else {
            this.links.add(new Link(otherWord));
        }
        Collections.sort(this.links, new LinkComparator());
    }

    private String trimWord(String word) {
        return word.toLowerCase().replaceAll("(\\.|!|;|\\(|\"|\'|\\)|\\-|:|\\?|\\s|,)", "");
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public Word getSingleLink(int index) {
        if (links.isEmpty()) {
            return null;
        } else {
            return links.get(index).otherWord;
        }
    }

    public Link getNearestEOSLink() {
        if (links.isEmpty()) {
            return null;
        } else {
            for (Link next : links) {
                if (next.otherWord.toString().equals(eos)) {
                    return next;
                }
            }
        }
        return null;
    }

    public Integer getEOSIndexOrNull() {
        if (links.isEmpty()) {
            return null;
        } else {
            int count = 0;
            for (Link link : links) {
                if (link.otherWord.toString().equals(eos)) {
                    return count;
                }
                count++;
            }
        }
        return null;
    }

    public Integer getIndexOfLinkOrNull(Word link) {
        int count = 0;
        Link otherLink = new Link(link);
        for (Link next : links) {
            if (next.equals(otherLink)) {
                return count;
            }
            count++;
        }
        return null;
    }

    public Word getBestLink() {
        Word returnWord = null;
        int index;
        boolean good = false;
        while (!good) {
            index = calcRandomIndex();
            if (links.isEmpty()) {
                return null;
            } else if (getSingleLink(index).isEOS() && links.size() == 1) {
                return null;
            } else if (!getSingleLink(index).isEOS()) {
                good = true;
                returnWord = getSingleLink(index);
                links.get(index).downRate();
                Collections.sort(this.links, new LinkComparator());
            }
        }
        return returnWord;
    }

    public int calcRandomIndex() {
        int index = 0;
        int size = this.links.size();
        int priorityCounter;
        do {
            if (links.size() < 10) {
                return (int) (Math.random() * links.size());
            }
            priorityCounter = priorityCounterGenerate();
            switch (priorityCounter) {
                case 0:
                    index = (int) ((Math.random() * size) / 5.0);
                    break;
                case 1:
                    index = (int) (((Math.random() * size) / 5.0) + (size / 5.0));
                    break;
                case 2:
                    index = (int) (((Math.random() * size) / 5.0) + ((size / 5.0) * 2.0));
                    break;
                case 3:
                    index = (int) (((Math.random() * size) / 5.0) + ((size / 5.0) * 3.0));
                    break;
                case 4:
                    index = (int) (((Math.random() * size) / 5.0) + ((size / 5.0) * 4.0));
                    break;
            }
        } while (index >= size);
        return index;
    }

    private int priorityCounterGenerate() {
        return (int) (Math.random() * 5);
    }

    public boolean isEOS() {
        if (this.word == eos) {
            return true;
        }

        return false;
    }

    public boolean hasEOSLink() {
        for (Link link : links) {
            Word x = link.otherWord;
            if (x.equals(new Word(eos))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return !(word != null ? !word.equals(word1.word) : word1.word != null);
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (links != null ? links.hashCode() : 0);
        result = 31 * result + (eos.hashCode());
        return result;
    }

    //override so we can compare vs "eos" via .equals()
    @Override
    public String toString() {
        return this.word;
    }
}