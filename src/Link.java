
public class Link {
    public Word otherWord;
    public long rating;

    public Link(Word otherWord) {
        this.otherWord = otherWord;
        this.rating = 1;
    }

    public void plusRate() {
        this.rating++;
    }
    
    public void downRate() {
        this.rating -= (int) (Math.random() * 20) + 1;
    }

    @Override
    public String toString() {
        return this.otherWord.toString() + " : " + this.rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        return otherWord.toString().equals(((Link) o).otherWord.toString());
    }

    @Override
    public int hashCode() {
        int result = otherWord != null ? otherWord.hashCode() : 0;
        result = 31 * result + (int) (rating ^ (rating >>> 32));
        return result;
    }
}
