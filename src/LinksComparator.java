import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: anthony
 * Date: 1/4/12
 * Time: 12:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class LinksComparator implements Comparator {
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
