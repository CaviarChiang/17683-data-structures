import java.util.Comparator;

/**
 * This class sorts words according to alphabets first and if there is a tie,
 * then words are sorted by their frequencies in ascending order
 * (a word with lowest frequency comes first).
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class AlphaFreq implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        int comp = o1.getWord().compareTo(o2.getWord());
        if (comp == 0) {
            return Integer.compare(o1.getFrequency(), o2.getFrequency());
        }
        return comp;
    }

}
