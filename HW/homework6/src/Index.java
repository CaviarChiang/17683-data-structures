import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A class that is used to build word index using BST.
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class Index {

    /**
     * Parses an input text file (using the same order as they appear in a file)
     * and builds an index tree using a natural alphabetical order.
     * @param fileName A path to the src file
     * @return A BST holding all words from the file
     */
    public BST<Word> buildIndex(String fileName) {

        return buildIndex(fileName, null);

    }

    /**
     * Parses an input text file (using the same order as they appear in a file) and
     * builds an index tree using a specific ordering provided by a specific comparator.
     * We don't use a hash table to store the parsed tokens here because we want to
     * keep the order of tokens as they appear in the file. Hash table breaks any order.
     * @param fileName A path to the src file
     * @param comparator A comparator object that determines the ordering of the BST
     * @return A BST holding all words from the file
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {

        BST<Word> tree = new BST<>(comparator);

        // Validate input
        if (fileName == null || fileName.isEmpty()) {
            return tree;
        }

        /*
         * If `IgnoreCase` comparator is passed, all the words need
         * to be converted into lowercase and then added into the BST.
         * Note that the Frequency comparator will NEVER be used to build the BST.
         */
        boolean toLower = comparator instanceof IgnoreCase;

        // Try-catch with resource
        try (
                BufferedReader br = new BufferedReader(new FileReader(fileName))
        ) {

            int lineNo = 0;

            String line;
            while ((line = br.readLine()) != null) {

                ++lineNo;

                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {

                    // Ignore non-words
                    if (!isValidWord(word)) {
                        continue;
                    }

                    // Convert to lowercase if necessary
                    if (toLower) {
                        word = word.toLowerCase();
                    }

                    // Search in BST and update frequency and positions
                    Word newWord = new Word(word);
                    Word wordInTree = tree.search(newWord);
                    if (wordInTree != null) {
                        wordInTree.setFrequency(wordInTree.getFrequency() + 1);
                        wordInTree.addToIndex(lineNo);
                    } else {
                        newWord.setFrequency(1);
                        newWord.addToIndex(lineNo);
                        tree.insert(newWord);
                    }

                }

            }
        } catch (IOException e) {
            System.err.println("Error reading the file");
        }

        return tree;

    }

    /**
     * Rebuilds index for all words from the given list.
     * @param list A list of Word objects
     * @param comparator A comparator object that determines the ordering of the BST
     * @return A BST holding all words from the list
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {

        BST<Word> tree = new BST<>(comparator);

        // Validate input
        if (list == null || list.size() == 0) {
            return tree;
        }

        /*
         * If `IgnoreCase` comparator is passed, all the words need
         * to be converted into lowercase and then added into the BST.
         * If there is already the existing object in the BST,
         * keep the old one. Do not update or combine.
         */
        boolean toLower = comparator instanceof IgnoreCase;

        if (toLower) {

            for (Word word : list) {
                word.setWord(word.getWord().toLowerCase());
                tree.insert(word);
            }

        } else {

            for (Word word : list) {
                tree.insert(word);
            }

        }

        return tree;

    }

    /**
     * Sorts BST by alphabetical frequency order.
     * @param tree A BST of words
     * @return A sorted list holding all words from the BST
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {

        /*
         * Even though there should be no ties with regard to words in BST,
         * in the spirit of using what you wrote, use AlphaFreq comparator.
         */
        ArrayList<Word> list = new ArrayList<>();

        // Validate input
        if (tree == null || tree.getRoot() == null) {
            return list;
        }

        // Traverse BST and add each node to the result list
        for (Word node : tree) {
            list.add(node);
        }

        // Sort by the AlphaFreq comparator
        list.sort(new AlphaFreq());

        return list;

    }

    /**
     * Sorts BST by descending frequency.
     * @param tree A BST of words
     * @return A sorted list holding all words from the BST
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {

        ArrayList<Word> list = new ArrayList<>();

        // Validate input
        if (tree == null || tree.getRoot() == null) {
            return list;
        }

        // Traverse BST and add each node to the result list
        for (Word node : tree) {
            list.add(node);
        }

        // Sort by the Frequency comparator
        list.sort(new Frequency());

        return list;

    }

    /**
     * Returns the list of words with the highest frequency.
     * @param tree A BST of words
     * @return An array list holding words with the highest frequency from the BST
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {

        ArrayList<Word> res = new ArrayList<>();

        // Sort by frequency first
        List<Word> sortedList = sortByFrequency(tree);
        if (sortedList == null || sortedList.size() == 0) {
            return res;
        }

        // Add the most frequent word to the result list
        int highestFreq = sortedList.get(0).getFrequency();
        res.add(sortedList.get(0));

        // Explore potential words with the same highest frequency
        for (int i = 1; i < sortedList.size(); ++i) {
            if (sortedList.get(i).getFrequency() == highestFreq) {
                res.add(sortedList.get(i));
            } else {
                break;
            }
        }

        return res;

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
