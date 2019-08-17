import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class represents a word and its frequency distribution in a text file.
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class Word implements Comparable<Word> {

    /**
     * A string object representing the word.
     */
    private String word;
    /**
     * A set that contains all of the line numbers for the word.
     */
    private Set<Integer> index;
    /**
     * The word's frequency in the src text.
     */
    private int frequency;

    /**
     * Constructor that takes an input string.
     * @param input A string object representing a word
     */
    public Word(String input) {
        if (!isValidWord(input)) {
            throw new IllegalArgumentException("Input is not considered to be a word: " + input);
        }
        word = input;
        frequency = 0;
        index = new HashSet<>();
    }

    /**
     * Getter method for field `word`.
     * @return A string object representing a word
     */
    public String getWord() {
        return word;
    }

    /**
     * Setter method for field `word`.
     * @param input A string object representing a word
     */
    public void setWord(String input) {
        word = input;
    }

    /**
     * Getter method for field `frequency`.
     * @return Frequency of the word
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Setter method for field `frequency`.
     * @param freq Frequency of the word
     */
    public void setFrequency(int freq) {
        frequency = freq;
    }

    /**
     * Getter method for field `index`.
     * @return A copy of the member variable `index`
     */
    public Set<Integer> getIndex() {
        return new HashSet<>(index);
    }

    /**
     * Adds a new line number for the word to the index.
     * @param line An Integer object that indicates a new line number
     *             for the word to be added to the index
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }

    /**
     * Compares the current Word object to another Word object
     * by alphabetical order of the `word` field.
     * @param o Another Word object
     * @return Negative int if smaller, positive int if larger, and 0 if equal
     */
    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }

    /**
     * Prints the Word object in "word frequency [index, ...]" format.
     * @return A string for display
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(word);
        builder.append(" ").append(frequency).append(" [");

        int i = 0;
        Iterator<Integer> iter = index.iterator();
        while (iter.hasNext()) {
            if (i == index.size() - 1) {
                builder.append(iter.next());
            } else {
                builder.append(iter.next()).append(", ");
            }
            ++i;
        }

        builder.append("]");
        return builder.toString();

    }

    /**
     * Validates the input text string.
     * @param input A string object to validate against
     * @return true if text string contains only letters and false if otherwise
     */
    private static boolean isValidWord(String input) {
        // nulls are validated to be false
        if (input == null) {
            return false;
        }
        return input.matches("[a-zA-Z]+");
    }

}
