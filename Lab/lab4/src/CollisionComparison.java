import java.util.Arrays;

/**
 * 17683 Data Structures for Application Programmers.
 * Lab 4 Number of Collisions Comparison and
 * Drawing a simple fractal of n squares with recursion
 *
 * @author Terry Lee
 */
public class CollisionComparison {

    /**
     * Test program to compare number of collisions.
     * @param args arguments
     */
    public static void main(String[] args) {
        int size = 110;
        int keyValue = 200;
        int increment = 5;

        int[] keys = new int[size];

        // initialize array with positive integer key values
        for (int i = 0; i < keys.length; i++) {
            keys[i] = keyValue;
            keyValue += increment;
        }

        System.out.println("****************************************************");
        System.out.print("Key values to insert into hashtable");
        System.out.println(" (number of input values:" + keys.length + ")");
        System.out.println(Arrays.toString(keys));
        System.out.println();

        DataItem[] hashArray1 = new DataItem[100];
        int numberOfCollisions = 0;
        for (int i = 0; i < keys.length; i++) {
            DataItem newItem = new DataItem(keys[i]);
            int hashVal = newItem.key % hashArray1.length;
            if (hashArray1[hashVal] == null) {
                hashArray1[hashVal] = newItem;
            } else {
                numberOfCollisions++;
            }
        }

        System.out.println("****************************************************");
        System.out.println("Number of collisions with HashTable 1: " + numberOfCollisions);
        System.out.println(Arrays.toString(hashArray1));
        System.out.println();

        DataItem[] hashArray2 = new DataItem[101];
        numberOfCollisions = 0;
        for (int i = 0; i < keys.length; i++) {
            DataItem newItem = new DataItem(keys[i]);
            int hashVal = newItem.key % hashArray2.length;
            if (hashArray2[hashVal] == null) {
                hashArray2[hashVal] = newItem;
            } else {
                numberOfCollisions++;
            }
        }

        System.out.println("****************************************************");
        System.out.println("Number of collisions with HashTable 2: " + numberOfCollisions);
        System.out.println(Arrays.toString(hashArray2));
    }

    /**
     * Static nested class for DataItem.
     */
    private static class DataItem {
        /**
         * int key.
         */
        int key;

        /**
         * Constructor with key.
         * @param k int key
         */
        DataItem(int k) {
            key = k;
        }

        /**
         * Returns string representation of DataItem object.
         * @return String value of DataItem Object.
         */
        @Override
        public String toString() {
            return "{" + key + "}";
        }
    }

}
