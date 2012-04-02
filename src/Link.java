/**
 * Created by IntelliJ IDEA.
 * User: anthony
 * Date: 1/4/12
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Link {
    String otherWord;

    long rating;

    public Link(String otherWord) {
        this.otherWord = otherWord;
        this.rating = 1;
    }

    public void plusRate() {
        this.rating++;
    }

    @Override
    public String toString() {
        return this.otherWord + " : " + this.rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (otherWord != null ? !otherWord.equals(link.otherWord) : link.otherWord != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return otherWord != null ? otherWord.hashCode() : 0;
    }
}
