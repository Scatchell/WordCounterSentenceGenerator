
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
        return rating == link.rating && !(otherWord != null ? !otherWord.equals(link.otherWord) : link.otherWord != null);
    }

    @Override
    public int hashCode() {
        int result = otherWord != null ? otherWord.hashCode() : 0;
        result = 31 * result + (int) (rating ^ (rating >>> 32));
        return result;
    }
}
