import java.util.Comparator;

public class LinkComparator implements Comparator {
    @Override
    public int compare(Object o, Object o1) {
        Link a = (Link) o;
        Link b = (Link) o1;

        if(b.rating > a.rating) {
            return 1;
        } else if(a.rating == b.rating) {
            return 0;
        } else {
            return -1;
        }
    }
}