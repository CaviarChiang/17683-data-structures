/**
 * 17683 Data Structures for Application Programmers.
 * Lab 1 ArrayList time comparison and String manipulation.
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class CompressString1 {
    /**
     * You may change the main method while you are working on.
     * But, when you submit your code, make sure to have the original code of the Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        String str = "aabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcaabbccaabbccaabbccaabbccbbbaaabbbcccaaabbbcccaaabbbcccaaabbbccaaabbbcccaaabbbbccccaaaaabbbbbccccaaabbbcccaaabbbcccaaabbbcccaaabbbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaabbbcccaaannncccaaabbbcccaaabbbcccaaabbbcccaaabbbaaabbbccc";
        Stopwatch timer1 = new Stopwatch();
        System.out.println("output:" + compress(str));
        System.out.print("running time:" + timer1.elapsedTime() + " millisec");
    }

    /**
     * method to compress a string using String.
     * @param str input string that should have at least two characters
     * @return Compressed or original string depending on the size of the compressed
     */
    public static String compress(String str) {
        // initialize compressed string
        String compressed = "";
        char prev = str.charAt(0);
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            char curr = str.charAt(i);
            if (curr == prev) {
                count++;
            } else {
                compressed = compressed + prev + count;
                count = 1;
                prev = curr;
            }
        }
        // concat the last char
        compressed = compressed + prev + count;

        // check length and return accordingly
        // return final string that is compressed
        if (compressed.length() > str.length()) {
            return str;
        }
        return compressed;
    }
}
