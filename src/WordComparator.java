import java.util.Comparator;

public class WordComparator implements Comparator{

    @Override
    public int compare(Object aObject, Object bObject) {
        Word a = (Word) aObject;
        Word b = (Word) bObject;

        if (a.getLinks().isEmpty() && b.getLinks().isEmpty())
            return 0;
        else if (b.getLinks().isEmpty())
            return -1;
        else if (a.getLinks().isEmpty())
            return 1;

        if(b.getLinks().get(0).rating > a.getLinks().get(0).rating) {
            return 1;
        } else if(a.getLinks().get(0).rating == b.getLinks().get(0).rating) {
            return 0;
        } else {
            return -1;
        }
    }
}