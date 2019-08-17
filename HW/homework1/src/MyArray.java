/**
 * 17683 Data Structures for Application Programmers.
 * Homework 1 MyArray
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class MyArray {

    /**
     * Default capacity for default constructor.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The array that holds added items.
     */
    private String[] myArray;
    /**
     * Number of items currently in the array.
     */
    private int size;


    /**
     * Default constructor for MyArray.
     */
    public MyArray() {

        // myArray = new String[DEFAULT_CAPACITY];

        // avoid duplicated code by calling the other constructor and passing the default param
        this(DEFAULT_CAPACITY);
        size = 0;

    }


    /**
     * Constructor for MyArray.
     * @param initialCapacity initial array capacity
     */
    public MyArray(int initialCapacity) {

        // validate constructor inputs
        // if (initialCapacity <= 0) {
        if (initialCapacity < 0) {
            myArray = new String[DEFAULT_CAPACITY];
        } else {
            myArray = new String[initialCapacity];
        }

        size = 0;
    }


    /**
     * Add a string object into MyArray.
     * Time complexity: O(1)
     * @param text String object to be added to the array
     */
    public void add(String text) {

        // if text validates to be false, do not add it to myArray
        if (!validateText(text)) {
            return;
        }

        /*
         * if myArray reached current capacity, double up its capacity
         * create a new array of the new capacity
         * then copy over the elements from old array to the new array
         * and set myArray to point to the new array
         * finally add the text to myArray and increment size by 1
         */
        if (size == myArray.length) {
            int newCapacity;
            if (size == 0) {
                newCapacity = 1;
            } else {
                newCapacity = 2 * size;
            }
            String[] newArray = new String[newCapacity];
            System.arraycopy(myArray, 0, newArray, 0, size);
            myArray = newArray;
        }
        myArray[size++] = text;

    }


    /**
     * Search for a string key in MyArray.
     * Time complexity: O(N)
     * @param key the String object to search
     * @return true if the key is present in MyArray or false if otherwise
     */
    public boolean search(String key) {
        // as nulls are not allowed to be inserted in myArray,
        // simply return false when key equals null
        if (key == null) {
            return false;
        }
        // perform linear search for key
        for (String text : myArray) {
            if (text != null && text.equals(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get the number of items MyArray currently holds.
     * Time complexity: O(1)
     * @return current size of MyArray
     */
    public int size() {
        return size;
    }


    /**
     * Get the capacity, or the current length, of myArray.
     * Time complexity: O(1)
     * @return the current length of myArray
     */
    public int getCapacity() {
        return myArray.length;
    }


    /**
     * Display the content in MyArray.
     * Time complexity: O(N)
     */
    public void display() {
        // use StringBuilder object to construct the final display string
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (myArray[i] != null) {
                if (i == size - 1) {
                    builder.append(myArray[i]);
                } else {
                    builder.append(myArray[i]).append(" ");
                }
            }
        }
        System.out.println(builder.toString());
    }


    /**
     * Remove duplicates from myArray (OLD CODE).
     * The first occurrence of each item is preserved.
     * Time complexity: O(N^2)
     */
    public void removeDupsOld() {
        /*
         * keep count of total duplicates detected in myArray
         * use null to indicate a duplicated occurrence
         */
        int dups = 0;
        for (int i = 0; i < size - 1; i++) {
            if (myArray[i] == null) {
                continue;
            }
            for (int j = i + 1; j < size; j++) {
                if (myArray[j] == null) {
                    continue;
                }
                if (myArray[i].equals(myArray[j])) {
                    dups++;
                    myArray[j] = null;
                }
            }
        }

        // decrement size by the number of duplicates
        size -= dups;

        // place nulls to the end of myArray (used extra mem - will deduct points)
        String[] newArray = new String[getCapacity()];
        int i = 0;
        for (String text : myArray) {
            if (text != null) {
                newArray[i++] = text;
            }
        }
        myArray = newArray;
    }


    /**
     * Remove duplicates from myArray (NEW CODE).
     * The first occurrence of each item is preserved.
     * Time complexity: O(N^2)
     */
    public void removeDups() {
        /*
         * keep count of total duplicates detected in myArray
         * use null to indicate a duplicated occurrence
         */
        int dups = 0;
        for (int i = 0; i < size - 1; i++) {
            if (myArray[i] == null) {
                continue;
            }
            for (int j = i + 1; j < size; j++) {
                if (myArray[j] == null) {
                    continue;
                }
                if (myArray[i].equals(myArray[j])) {
                    dups++;
                    myArray[j] = null;
                }
            }
        }

        // decrement size by the number of duplicates
        size -= dups;

        // place nulls to the end of myArray (inplace)
        int nullIdx = 0;
        for (int i = 0; i < size; i++) {
            if (myArray[i] != null) {
                swap(i, nullIdx);
                nullIdx++;
            }
        }

    }


    /**
     * Trim the capacity of an MyArray instance to be the array's current size
     */
    public void trimToSize() {
        int newCapacity;
        if (size == 0) {
            newCapacity = 1;
        } else {
            newCapacity = 2 * size;
        }
        String[] newArray = new String[newCapacity];
        System.arraycopy(myArray, 0, newArray, 0, size);
        myArray = newArray;
    }


    /**
     * Swap words at indices i and j.
     * @param i 1st index
     * @param j 2nd index
     */
    public void swap(int i, int j) {
        String temp = myArray[i];
        myArray[i] = myArray[j];
        myArray[j] = temp;
    }


    /**
     * Validate the input string.
     * @param text a string object to validate against
     * @return true if text string contains only letters and false if otherwise
     */
    private boolean validateText(String text) {
        // nulls are validated to be false; do not allow nulls to be inserted
        if (text == null) {
            return false;
        }
        return text.matches("[a-zA-Z]+");
    }


    /**
     * Test driver for MyArray.
     * @param args arguments
     */
    public static void main(String[] args) {
        MyArray arr = new MyArray(0);
        arr.removeDups();
        arr.display();
        System.out.println("===============");
        arr = new MyArray(3);
        arr.add(null);
        System.out.println("Size of array after inserting null: " + arr.size());
        System.out.println("Capacity of array: " + arr.getCapacity());
        System.out.println("Search for null: " + arr.search(null));
        System.out.println("===============");
        arr.add("hello");
        arr.add("world");
        arr.add("hello");
        arr.add("again");
        arr.add("again");
        arr.add("again");
        arr.add("again");
        arr.add("again");
        arr.add("again");
        arr.add("again");
        System.out.println("Size of array after inserting 10 words: " + arr.size);
        System.out.println("Capacity of array after inserting 10 words: " + arr.getCapacity());
        System.out.print("Display array content: ");
        arr.display();
        System.out.println("===============");
        arr.removeDups();
        System.out.println("Size of array after removing dups: " + arr.size);
        System.out.println("Capacity of array after removing dups: " + arr.getCapacity());
        System.out.print("Display array content: ");
        arr.display();
        arr.trimToSize();
        System.out.println("Capacity of array after calling trimToSize(): " + arr.getCapacity());
    }

}
