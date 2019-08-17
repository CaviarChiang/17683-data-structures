import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Homework Assignment 5 Document Similarity.
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class Similarity {

    /**
     * Default distance between two frequency vectors that don't overlap at all (dot product equals 0).
     */
    private static final double DEFAULT_DIST = Math.PI / 2;

    /**
     * Map object that represents the frequency vector of a text / text file.
     * I use `Map` data structure, more specifically, `HashMap`, from Java Collections Framework
     * to store the frequency distribution of the input texts. With `HashMap`, I can store both
     * the word and its frequency, and I can also have constant-time access to a word's frequency
     * given that word as an input.
     */
    private Map<String, BigInteger> similarityMap;
    /**
     * Number of lines from the original text / text file.
     */
    private int numOfLines;
    /**
     * Total number of words from the original text / text file.
     * I use `BigInteger` class here because it will not result in overflow
     * if we have an extremely large text file as the input.
     */
    private BigInteger numOfWords;

    /**
     * Constructor that takes a String object.
     * @param string A string object of interest
     */
    public Similarity(String string) {

        // Initialize member variables
        similarityMap = new HashMap<>();
        numOfLines = 1;
        numOfWords = new BigInteger("0");

        // Check input validity. If null or empty, simply returns
        if (string == null || string.isEmpty()) {
            return;
        }

        // Convert tokens to lower case, ignore non-words, and update member variables
        String[] wordsFromText = string.split("\\W");
        for (String word : wordsFromText) {
            word = word.toLowerCase();
            if (!isValidWord(word)) {
                continue;
            }
            numOfWords = numOfWords.add(BigInteger.ONE);
            similarityMap.put(word, similarityMap.getOrDefault(word, BigInteger.ZERO).add(BigInteger.ONE));
        }

    }

    /**
     * Constructor that takes a File object.
     * @param file A File object of interest
     */
    public Similarity(File file) {

        // Initialize member variables
        similarityMap = new HashMap<>();
        numOfLines = 0;
        numOfWords = new BigInteger("0");

        // Check input validity. If null, simply returns
        if (file == null) {
            return;
        }

        // Try-catch with resource
        try (
                BufferedReader br = new BufferedReader(new FileReader(file))
                ) {
            // Read line by line, convert tokens to lower case, ignore non-words, and update member variables
            String line;
            while ((line = br.readLine()) != null) {
                numOfLines++;
                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {
                    word = word.toLowerCase();
                    if (!isValidWord(word)) {
                        continue;
                    }
                    numOfWords = numOfWords.add(BigInteger.ONE);
                    similarityMap.put(word, similarityMap.getOrDefault(word, BigInteger.ZERO).add(BigInteger.ONE));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file");
        }

    }

    /**
     * Returns the number of lines from the original input text / file.
     * @return Number of lines from the original input text / file
     */
    public int numOfLines() {
        return numOfLines;
    }

    /**
     * Returns the total number of words from the original input text / file.
     * @return Total number of words from the original input text / file
     */
    public BigInteger numOfWords() {
        return numOfWords;
    }

    /**
     * Returns the number of distinct words from the original input text / file.
     * @return Number of words without duplicates from the original input text / file
     */
    public int numOfWordsNoDups() {
        return similarityMap.size();
    }

    /**
     * Calculates the current Euclidean norm that is the length of the current frequency vector.
     * I don't make a member variable for euclidean norm because the frequency vector isn't final,
     * which means we might want to add/remove a word from the vector, resulting the norm to be
     * changed accordingly.
     * @return A double value representing the Euclidean norm of frequency vector (similarityMap)
     */
    public double euclideanNorm() {
        BigInteger squaredDist = BigInteger.ZERO;
        for (BigInteger value : similarityMap.values()) {
            squaredDist = squaredDist.add(value.multiply(value));
        }
        return Math.sqrt(squaredDist.doubleValue());
    }

    /**
     * Calculates the dot product between the current frequency vector and another frequency vector.
     * My implementation does not fall into the quadratic running time complexity on average because
     * I am only iterating the `this.similarityMap` object once, and inside the loop, accessing a given
     * word's frequency from the other `map` object is constant-time on average. Therefore, the overall
     * time complexity of this method is O(n), where n is the size of the `this.similarityMap` object.
     *
     * @param map A Map object representing another frequency vector
     * @return A double value representing the dot product between two frequency vectors
     */
    public double dotProduct(Map<String, BigInteger> map) {

        // Check inputs validity: if either vector is null or empty, simply returns 0
        if (similarityMap == null || map == null || similarityMap.isEmpty() || map.isEmpty()) {
            return 0;
        }

        // Compute sum of products
        BigInteger product = BigInteger.ZERO;
        for (Map.Entry<String, BigInteger> entry : similarityMap.entrySet()) {
            String word = entry.getKey();
            BigInteger freq = entry.getValue();
            product = product.add(freq.multiply(map.getOrDefault(word, BigInteger.ZERO)));
        }

        // Return the double value of result
        return product.doubleValue();

    }

    /**
     * Calculates the distance between the current frequency vector and another frequency vector.
     * @param map A Map object representing another frequency vector
     * @return A double value representing the Euclidean distance between two frequency vectors
     */
    public double distance(Map<String, BigInteger> map) {

        /*
         * Edge case 1: if the two frequency vectors have nothing in common,
         * simply returns DEFAULT_DIST for two vectors that don't overlap at all.
         * `dotProduct` method also checks for null or empty frequency vectors.
         */
        double product = dotProduct(map);
        if (product == 0) {
            return DEFAULT_DIST;
        }

        /*
         * Edge case 2: if the two frequency vectors are identical, simply returns 0.
         * This check comes after the 1st check, and it assumes `similarityMap` object isn't null.
         * Note that two empty vectors are not considered to be identical.
         */
        if (similarityMap.equals(map)) {
            return 0;
        }

        // Compute the Euclidean norm of the other frequency vector
        BigInteger squaredDist = BigInteger.ZERO;
        for (BigInteger value : map.values()) {
            squaredDist = squaredDist.add(value.multiply(value));
        }
        double otherEuclideanNorm = Math.sqrt(squaredDist.doubleValue());

        // Return the double value of the cosine similarity
        return Math.acos(product / (euclideanNorm() * otherEuclideanNorm));

    }

    /**
     * Returns a copy of the member variable `similarityMap`, which is the current frequency vector.
     * @return A copy of the member variable `similarityMap`, which represents the current frequency vector
     */
    public Map<String, BigInteger> getMap() {
        return new HashMap<>(similarityMap);
    }

    /**
     * Validate the input text string.
     * @param text A string object to validate against
     * @return true if text string contains only letters and false if otherwise
     */
    private static boolean isValidWord(String text) {
        // nulls are validated to be false
        if (text == null) {
            return false;
        }
        return text.matches("[a-zA-Z]+");
    }

    /**
     * Simple test program.
     * @param args Arguments
     */
    public static void main(String[] args) {

        System.out.println("=====================--");
        System.out.println("Testing identical texts");

        String text1 = "hello there nice to meet you you look nice";
        String text2 = "hello there nice to meet you you look nice";

        Similarity s1 = new Similarity(text1);
        Similarity s2 = new Similarity(text2);

        System.out.println(s1.dotProduct(s2.getMap()) + " dot product.");
        System.out.println(s1.distance(s2.getMap()) + " distance.");

        System.out.println("===================");
        System.out.println("Testing empty files");

        File file = new File("empty.txt");
        Similarity s3 = new Similarity(file);

        System.out.println(s1.dotProduct(s3.getMap()) + " dot product.");
        System.out.println(s1.distance(s3.getMap()) + " distance.");

        Similarity s4 = new Similarity(file);

        System.out.println(s4.dotProduct(s3.getMap()) + " dot product.");
        System.out.println(s4.distance(s3.getMap()) + " distance.");

    }

}
