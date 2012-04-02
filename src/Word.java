import java.util.ArrayList;
import java.util.Collections;

public class Word {
    String word;
    ArrayList<Link> links = new ArrayList<Link>();
    final String eos = "^&eos&^";

    public Word(String word) {
        this.word = this.trimWord(word);
    }

    //override so we can compare vs "eos" via .equals()
    public String toString() {
        return this.word;
    }

    public void addLink(Word otherWord) {
        Integer linkPosition = checkLinkExists(otherWord);
        if (linkPosition != null) {
            this.links.get(linkPosition).plusRate();
        } else {
            this.links.add(new Link(otherWord));
        }
        Collections.sort(this.links, new LinksComparator());
    }

    private String trimWord(String word) {
        return word.toLowerCase().replaceFirst("(\\.|\\!|\\?|\\s|\\,)", "");
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

    public String getNearestEOSLink() {
        if (links.isEmpty()) {
            return null;
        } else {
            for (Link next : links) {
                if (next.otherWord.toString().equals(eos)) {
                    return this.word;
                }
            }
        }
        return null;

    }

    public Integer hasEOSLink() {
        if (links.isEmpty()) {
            return null;
        } else {
            int count = 0;

            for (Link link : links) {
                if (link.otherWord.equals(eos)) {
                    return count;
                }
                count++;

            }
        }
        return null;
    }

    public Integer checkLinkExists(Word link) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return eos.equals(word1.eos) && !(links != null ? !links.equals(word1.links) : word1.links != null) && !(word != null ? !word.equals(word1.word) : word1.word != null);
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (links != null ? links.hashCode() : 0);
        result = 31 * result + (eos.hashCode());
        return result;
    }

    public Word getBestLink() {
        //todo make links have a flag that shows if they have already been chosen or not, and only pick links that have not yet been chosen
        return getSingleLink(0);
    }
}
