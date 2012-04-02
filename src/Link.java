
public class Link {
    Word otherWord;
    long rating;

    public Link(Word otherWord) {
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
