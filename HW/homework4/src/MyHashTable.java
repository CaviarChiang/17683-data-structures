/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 4: HashTable Implementation with linear probing
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class MyHashTable implements MyHTInterface {

    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        /**
         * 1-arg constructor.
         * Default frequency is set to 1.
         * @param val String value to be stored
         */
        DataItem(String val) {
            this.value = val;
            this.frequency = 1;
        }

        /**
         * 2-args constructor (for DELETED item).
         * @param val String value to be stored
         * @param freq Specified frequency associated with the value
         */
       DataItem(String val, int freq) {
            this.value = val;
            this.frequency = freq;
        }
    }

    /**
     * Default capacity for the hash array.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Default load factor for resizing/rehashing.
     */
    private static final double DEFAULT_LOAD_FACTOR = 0.5d;
    /**
     * Offset by the difference between the ASCII code of a letter and 96.
     * Assume all lowercase inputs.
     */
    private static final int LETTER_OFFSET = 96; // 'a': 97, 'c': 99
    /**
     * Default multiplier in hashing process using Horner's method.
     */
    private static final int HASH_MULTIPLIER = 27;
    /**
     * Dummy reference to the deleted item(s).
     */
    private static final DataItem DELETED = new DataItem("#DEL#", 0);
    /**
     * Default step length used in linear probing.
     */
    private static final int PROBING_STEP = 1;

    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;
    /**
     * Number of items stored in the table.
     */
    private int size;
    /**
     * Number of collisions encountered when inserting into the table.
     */
    private int numCollision;

    /**
     * Default constructor with no initial capacity.
     */
    public MyHashTable() {
        this.hashArray = new DataItem[DEFAULT_CAPACITY];
        this.size = 0;
        this.numCollision = 0;
    }

    /**
     * Constructor with initial capacity.
     * @param capacity Initial capacity
     */
    public MyHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Initial capacity should be greater than 0, but got " + capacity);
        }
        this.hashArray = new DataItem[capacity];
        this.size = 0;
        this.numCollision = 0;
    }

    /**
     * Instead of using String's hashCode, you are to implement your own here.
     * You need to take the table length into your account in this method.
     *
     * In other words, you are to combine the following two steps into one step.
     * 1. converting Object into integer value
     * 2. compress into the table using modular hashing (division method)
     *
     * Helper method to hash a string for English lowercase alphabet and blank,
     * we have 27 total. But, you can assume that blank will not be added into
     * your table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's method should be applied as follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time, I would encourage you to try with other
     * constant values than 27 and compare the results but it is not required.
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        int n = input.length();
        int hashVal = 0;
        for (int i = 0; i < n; i++) {
            // manually convert char to int using constant offset
            int ch = input.charAt(i) % LETTER_OFFSET;
            if (i == n - 1) {
                hashVal = hashVal + ch;
            } else {
                hashVal = (hashVal + ch) * HASH_MULTIPLIER;
            }
            // to avoid integer overflow, modulo before the next iteration
            hashVal %= hashArray.length;
        }
        return hashVal;
    }

    /**
     * Doubles array length and rehash items whenever the load factor is reached.
     * Note: do not include the number of deleted spaces to check the load factor.
     * Remember that deleted spaces are available for insertion.
     */
    private void rehash() {

        // store the old hash array
        DataItem[] oldArray = new DataItem[hashArray.length];
        System.arraycopy(hashArray, 0, oldArray, 0, hashArray.length);

        // double the hash array length and reset # collisions
        int newLength = nextPrime(2 * hashArray.length);
        hashArray = new DataItem[newLength];
        numCollision = 0;
        System.out.println(String.format("Rehashing %d items, new length is %d", size, newLength));

        // rehash (ignore null or deleted items)
        for (DataItem item : oldArray) {
            if (item == null || item == DELETED) {
                continue;
            }
            int hash = hashFunc(item.value);
            if (hashArray[hash] == null) {
                hashArray[hash] = item;
            } else {
                boolean hasCollision = false; // if collision ever occurs when inserting the current item
                int originalHash = hash;      // the original hash value of the current item
                while (hashArray[hash] != null) {
                    if (hashFunc(hashArray[hash].value) == originalHash) {
                        hasCollision = true;
                    }
                    hash = (hash + PROBING_STEP) % hashArray.length;
                }
                hashArray[hash] = item;
                if (hasCollision) {
                    numCollision++;
                }
            }
        }

    }

    /**
     * Inserts a new String value (word).
     * Frequency of each word to be stored too.
     * To update total # of collisions, think about SEPARATE CHAINING!!!
     * @param value String value to add
     */
    @Override
    public void insert(String value) {

        // validate text input
        if (!validateText(value)) {
            return;
        }

        /**
         * insert the new item when there's certainly no collision (`hashArray[hash] == null`)
         * HOWEVER, when `hashArray[hash] == DELETED`, there might be collisions prior,
         * so we need to search down the table in case the value might already exist
         */
        int hash = hashFunc(value);
        if (hashArray[hash] == null) {
            hashArray[hash] = new DataItem(value);
            size++;
            // check if resize after insertion
            if (size > DEFAULT_LOAD_FACTOR * hashArray.length) {
                rehash();
            }
            return;
        }

        // insert the new item when there might be collision
        boolean hasCollision = false;
        int originalHash = hash;
        /** no need to check `hashArray[hash] == DELETED`, cuz `hashArray[hash] != null` already includes it */
        while (hashArray[hash] != null) {
            if (hashArray[hash].value.equals(value)) {
                // increment the freq
                hashArray[hash].frequency++;
                return;
            }
            if (hashFunc(hashArray[hash].value) == originalHash) {
                hasCollision = true;
            }
            hash = (hash + PROBING_STEP) % hashArray.length;
        }
        hashArray[hash] = new DataItem(value);
        size++;
        // update total of collisions if there is one
        if (hasCollision) {
            numCollision++;
        }
        // check if resize after insertion
        if (size > DEFAULT_LOAD_FACTOR * hashArray.length) {
            rehash();
        }

    }

    /**
     * Returns the size, number of items, of the table.
     * @return the number of items in the table
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Displays the values of the table.
     * If an index is empty, it shows **
     * If previously existed data item got deleted, then it should show #DEL#
     */
    @Override
    public void display() {
        for (int i = 0; i < hashArray.length; i++) {
            DataItem item = hashArray[i];
            if (item == null) {
                System.out.print("**");
            } else if (item == DELETED) {
                System.out.print(DELETED.value);
            } else {
                System.out.print(String.format("[%s, %d]", item.value, item.frequency));
            }
            if (i != hashArray.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /**
     * Returns true if value is contained in the table.
     * @param key String key value to search
     * @return true if found, false if not found.
     */
    @Override
    public boolean contains(String key) {

        // validate text input
        if (!validateText(key)) {
            return false;
        }

        // if initial index is null, returns false
        int hash = hashFunc(key);
        if (hashArray[hash] == null) {
            return false;
        }

        // else, probe and check further
        int originalHash = hash;
        while (hashArray[hash] == DELETED || hashArray[hash] != null) {    // => hashArray[hash] != null
            if (hashArray[hash].value.equals(key)) {
                return true;
            }
            hash = (hash + PROBING_STEP) % hashArray.length;
            // break the loop if we finished a circle (to avoid infinite loop)
            if (hash == originalHash) {
                break;
            }
        }
        // by this time hashArray[hash] != DELETED && hashArray[hash] == null => hashArray[hash] == null
        return false;

    }

    /**
     * Returns the number of collisions in relation to insert and rehash.
     * When rehashing process happens, the number of collisions should be properly updated.
     * @return number of collisions
     */
    @Override
    public int numOfCollisions() {
        return numCollision;
    }

    /**
     * Public interface that returns the hash value of a String.
     * Assume that String value is going to be a word with all lowercase letters.
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    @Override
    public int hashValue(String value) {
        return hashFunc(value);
    }

    /**
     * Returns the frequency of a key String.
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    @Override
    public int showFrequency(String key) {

        // validate text input
        if (!validateText(key)) {
            return 0;
        }

        // if initial index is null, returns 0
        int hash = hashFunc(key);
        if (hashArray[hash] == null) {
            return 0;
        }

        // else, probe and search further
        int originalHash = hash;
        while (hashArray[hash] == DELETED || hashArray[hash] != null) {
            if (hashArray[hash].value.equals(key)) {
                return hashArray[hash].frequency;
            }
            hash = (hash + PROBING_STEP) % hashArray.length;
            // break the loop if we go back to the original hash (to avoid infinite loop)
            if (hash == originalHash) {
                break;
            }
        }
        // by this time hashArray[hash] != DELETED && hashArray[hash] == null
        return 0;

    }

    /**
     * Removes and returns removed value.
     * @param key String to remove
     * @return value that is removed. If not found, return null
     */
    @Override
    public String remove(String key) {

        // validate text input
        if (!validateText(key)) {
            return null;
        }

        // if initial index is null, returns null
        int hash = hashFunc(key);
        if (hashArray[hash] == null) {
            return null;
        }

        // else, probe and remove further
        int originalHash = hash;
        while (hashArray[hash] == DELETED || hashArray[hash] != null) {
            if (hashArray[hash].value.equals(key)) {
                String value = hashArray[hash].value;
                hashArray[hash] = DELETED;
                size--;
                return value;
            }
            hash = (hash + PROBING_STEP) % hashArray.length;
            // break the loop if we go back to the original hash (to avoid infinite loop)
            if (hash == originalHash) {
                break;
            }
        }
        // by this time hashArray[hash] != DELETED && hashArray[hash] == null
        return null;

    }

    /**
     * Finds the next prime that's greater than or equal to the input number.
     * @param num Input number
     * @return The next prime after the input number
     */
    private static int nextPrime(int num) {
        // if num < 2, return 2 as the first prime
        if (num < 2) {
            return 2;
        }
        int nextPrime = num;
        int j = num;
        boolean found = false;
        while (!found) {
            int count = 0;
            for (int i = 2; i * i <= j; i++) {
                if (j % i == 0) {
                    count++;
                    break; // once we found a non-prime, break
                }
            }
            if (count == 0) {
                found = true;
                nextPrime = j;
            }
            j++;
        }
        return nextPrime;
    }

    /**
     * Validate the input string.
     * @param text a string object to validate against
     * @return true if text string contains only letters and false if otherwise
     */
    private static boolean validateText(String text) {
        // nulls are validated to be false
        if (text == null) {
            return false;
        }
        return text.matches("[a-zA-Z]+");
    }

    /**
     * Simple test program.
     * @param args Arguments.
     */
    public static void main(String[] args) {

        MyHashTable ht = new MyHashTable();
        System.out.println("\n=============================");
        System.out.println("Test `hashValue()` function: ");
        System.out.println("Hash value for `cats`: " + ht.hashValue("cats"));
        System.out.println("Hash value for 'increase': " + ht.hashValue("increase"));

        System.out.println("\n=============================");
        System.out.println("Test `nextPrime()` function: ");
        System.out.println(nextPrime(1));  // 2
        System.out.println(nextPrime(3));  // 3
        System.out.println(nextPrime(8));  // 11
        System.out.println(nextPrime(14)); // 17
        System.out.println(nextPrime(17)); // 17
        System.out.println(nextPrime(20)); // 23
        System.out.println(nextPrime(22)); // 23
        System.out.println(nextPrime(40)); // 41
        System.out.println(nextPrime(60)); // 61

        System.out.println("\n=============================");
        System.out.println("Test `insert()` function: ");
        MyHashTable theHashTable = new MyHashTable(2);
        theHashTable.insert("increase");
        theHashTable.remove("increase");
        theHashTable.insert("creeping");
        theHashTable.remove("creeping");
        System.out.println(theHashTable.contains("haha"));
        System.out.println(theHashTable.showFrequency("lala"));
        System.out.println(theHashTable.remove("lala"));
        theHashTable.display();

        System.out.println("\n=============================");
        System.out.println("Testing insert, size, and numOfCollisions methods.....");
        theHashTable = new MyHashTable();
        theHashTable.insert("haha");
        theHashTable.insert("haha");
        theHashTable.insert("ibib");
        System.out.println(theHashTable.hashFunc("haha"));
        System.out.println(theHashTable.hashFunc("ibib"));
        theHashTable.display();
        System.out.println("Size: " + theHashTable.size());
        System.out.println("Collisions: " + theHashTable.numOfCollisions());

        System.out.println("\n=============================");
        System.out.println("Testing insert and remove methods.....");
        theHashTable = new MyHashTable(40);
        System.out.println("Hash value for `increase`: " + theHashTable.hashValue("increase"));
        System.out.println("Hash value for `finished`: " + theHashTable.hashValue("finished"));
        theHashTable.insert("increase");
        theHashTable.insert("finished");
        theHashTable.remove("increase");
        theHashTable.insert("finished");
        theHashTable.display();                                // {finished:2}
        theHashTable.remove("finished");
        theHashTable.display();                                // {}
        System.out.println(theHashTable.contains("finished")); // false

    }

}
