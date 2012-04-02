import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: anthony
 * Date: 1/4/12
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    String word;
    ArrayList<Link> links = new ArrayList<Link>();
    final String eos = "^&eos&^";

    public Word(String word) {
        this.word = this.trimWord(word);
    }

    public void addLink(String otherWord) {
        otherWord = trimWord(otherWord);

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

    public String getSingleLink(int index) {
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
            for (Iterator<Link> iterator = links.iterator(); iterator.hasNext(); ) {
                Link next = iterator.next();
                if (next.otherWord == eos) {
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

            for (Iterator<Link> iterator = links.iterator(); iterator.hasNext(); ) {
                Link link = iterator.next();
                if (link.otherWord.equals(eos)) {
                    return count;
                }
                count++;

            }
        }
        return null;
    }

    public Integer checkLinkExists(String link) {
        int count = 0;
        Link otherLink = new Link(link);

        for (Iterator<Link> iterator = links.iterator(); iterator.hasNext(); ) {
            Link next = iterator.next();
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

        if (word != null ? !word.equals(word1.word) : word1.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return word != null ? word.hashCode() : 0;
    }

    public String getBestLink() {
        //todo make links have a flag that shows if they have already been chosen or not, and only pick links that have not yet been chosen
        return getSingleLink(0);
    }
}
