import java.util.Comparator;

/**
 * This class sorts words by case insensitive alphabetical order.
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class IgnoreCase implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().toLowerCase().compareTo(o2.getWord().toLowerCase());
    }

}
