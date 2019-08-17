import java.util.Comparator;

/**
 * This class sorts words according to their frequencies
 * (a word with highest frequency comes first).
 * This comparator will NEVER be used to build the BST.
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class Frequency implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o2.getFrequency(), o1.getFrequency());
    }

}
