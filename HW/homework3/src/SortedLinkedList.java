/**
 * 17683 Data Structures for Applications Programmers.
 * Homework 3 SortedLinkedList Implementation with Recursion
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class SortedLinkedList implements MyListInterface {

    /**
     * Private standalone inner class for SortedLinkedList items.
     */
    private static class Node implements Comparable<Node> {
        /**
         * List item value.
         */
        private String data;
        /**
         * A Node reference that points to the next Node in the list.
         */
        private Node next;

        /**
         * Constructor that takes a single value.
         * @param value A string object representing the value of the list item
         */
        Node(String value) {
            data = value;
            next = null;
        }

        /**
         * Constructor that takes a value and a next pointer.
         * @param value A string object representing the value of the list item
         * @param nextNode A Node reference that points to the next item of the current node
         */
        Node(String value, Node nextNode) {
            data = value;
            next = nextNode;
        }

        /**
         * Compares the current node against another node by their data field.
         * @param o A node object to compare against.
         * @return An integer indicating the comparison result.
         */
        @Override
        public int compareTo(Node o) {
            if (o == null) { // null node is assumed to be inf (placed in the end)
                return -1;
            }
            return this.data.compareTo(o.data);
        }
    }

    /**
     * A node reference that points to the first node in the list.
     */
    private Node head;

    /**
     * Default constructor.
     */
    public SortedLinkedList() {
        head = null;
    }

    /**
     * Constructor that takes an unsorted list of strings.
     * Time complexity: O(n^2)
     * @param words An unsorted array of string objects.
     */
    public SortedLinkedList(String[] words) {
        /*
         * Initialize the head pointer,
         * then add each word in words to the list
         */
        this();
        addFromArray(words, 0);
    }

    /**
     * Recursively calls itself to add a single word from an array
     * to the list until all the words have been added (and sorted).
     * @param words An unsorted array of string objects.
     * @param index The current index of the word to be added to the list.
     */
    private void addFromArray(String[] words, int index) {

        /*
         * Base cases: simply return the method call
         *  - words array is null
         *  - words array is empty
         *  - current index is out of bounds
         */
        if (words == null || words.length == 0 || index >= words.length) {
            return;
        }

        // `add` method will take care of the word validation, duplicates, and sorted order
        this.add(words[index]);

        /*
         * Recursive case: add the word at the current index to the list
         */
        addFromArray(words, index + 1);

    }

    /**
     * Inserts a new String.
     * Do not throw exceptions if invalid word is added (Gently ignore it).
     * No duplicates allowed and maintain the order in ascending order.
     * @param value String to be added.
     */
    @Override
    public void add(String value) {
        // Ignore invalid word or duplicated word
        if (!isWord(value) || this.contains(value)) { // called `contains` in `add`, inefficient
            return;
        }
        Node newNode = new Node(value, null);
        /*
         * If head is null or the value to be added is less than the head value,
         * insert at the front by making the newNode the new head
         */
        if (head == null || newNode.compareTo(head) <= 0) {
            newNode.next = head;
            head = newNode;
            return;
        }
        // Else, recursively find the right position and insert the newNode
        sortedInsert(head, newNode);
    }

    /**
     * Recursively calls itself to add a single word while maintaining the ascending order.
     * @param node The current node that's assumed to be greater than the new node.
     * @param newNode The new node to be added.
     */
    private void sortedInsert(Node node, Node newNode) {
        /*
         * Base cases:
         *  - current node is null: simply return the method call
         *  - newNode is less than the next node: insert newNode after the current node
         */
        if (node == null) {
            return;
        }
        if (newNode.compareTo(node.next) <= 0) { // null node is assumed to be inf (placed in the end)
            newNode.next = node.next;
            node.next = newNode;
            return;
        }
        /*
         * Recursive case: find the right position and insert the newNode after the next node
         */
        sortedInsert(node.next, newNode);
    }

    /**
     * Inserts a new String.
     * No duplicates allowed and maintain the order in ascending order.
     * @param value String to be added.
     */
    public void addWithoutContainsCall(String value){
        // Ignore invalid word
        if (!isWord(value)) {
            return;
        }
        Node newNode = new Node(value, null);
        /*
         * If head is null or the value to be added is **strictly** less than
         * the head value, insert at the front by making the newNode the new head
         */
        if (head == null || newNode.compareTo(head) < 0) { // or simply `newNode.compareTo(head) < 0`
            newNode.next = head;
            head = newNode;
            return;
        }
        // Else, recursively find the right position and insert the newNode
        sortedInsertWithoutContainsCall(head, newNode);
    }

    /**
     * Recursively calls itself to add a single word while maintaining the ascending order.
     * No duplicates are allowed in insertion.
     * @param node The current node that's assumed to be greater than the new node.
     * @param newNode The new node to be added.
     */
    private void sortedInsertWithoutContainsCall(Node node, Node newNode) {
        /*
         * Base cases:
         *  - current node is null: simply return the method call
         *  - newNode is less than the next node: insert newNode after the current node
         *  - newNode is equal to the current node or the next node: simply return the method call
         */
        if (node == null) {
            return;
        }
        if (newNode.compareTo(node.next) < 0) { // null node is assumed to be inf (placed in the end)
            newNode.next = node.next;
            node.next = newNode;
            return;
        }
        if (newNode.compareTo(node) == 0 || newNode.compareTo(node.next) == 0) {
            return;
        }
        /*
         * Recursive case: find the right position and insert the newNode after the next node
         */
        sortedInsert(node.next, newNode);
    }

    /**
     * Checks the size (number of data items) of the list.
     * @return the size of the list
     */
    @Override
    public int size() {
        return size(head);
    }

    /**
     * Recursively calls itself to find the size of the current node.
     * @param node The current node whose size is of interest
     * @return The size of current node
     */
    private int size(Node node) {
        /*
         * Base case:
         *  - node is null: simply returns 0
         */
        if (node == null) {
            return 0;
        }
        /*
         * Recursive case: return the size of the next node plus 1
         */
        return size(node.next) + 1;
    }

    /**
     * Displays the values of the list.
     */
    @Override
    public void display() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        appendNodeData(head, builder);
        builder.append("]");
        System.out.println(builder.toString());
    }

    /**
     * Recursively calls itself to append the current node's value to a StringBuilder object.
     * @param node The current node whose value is to be appended.
     * @param builder A StringBuilder object that stores the node values of the list.
     */
    private void appendNodeData(Node node, StringBuilder builder) {
        /*
         * Base case:
         *  - current node is null: simply returns the function call
         *
         * Recursive case:
         *  - current node isn't null: append its value and the value of its next node
         */
        if (node != null) {
            if (node.next == null) {
                builder.append(node.data);
            } else {
                builder.append(node.data).append(", ");
            }
            appendNodeData(node.next, builder);
        }
    }

    /**
     * Returns true if the key value is in the list.
     * @param key String key to search
     * @return true if found, false if not found
     */
    @Override
    public boolean contains(String key) {
       return containsKey(head, key);
    }

    /**
     * Recursively calls itself to find if the list contains a given key.
     * @param node The current node
     * @param key String key to search
     * @return true if contains the key, false if otherwise
     */
    private boolean containsKey(Node node, String key) {
        /*
         * Base cases:
         *  - if current node is null: returns false
         *  - if current node's value equals key: return true
         */
        if (node == null) {
            return false;
        }
        if (node.data.equals(key)) {
            return true;
        }
        /*
         * Recursive case:
         *  - if current node's value does not equal key, check its next node
         */
        return containsKey(node.next, key);
    }

    /**
     * Returns true is the list is empty.
     * @return true if it is empty, false if it is not empty
     */
    @Override
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    /**
     * Removes and returns the first String object of the list.
     * @return String object that is removed. If the list is empty, returns null
     */
    @Override
    public String removeFirst() {
        if (isEmpty()) {
            return null;
        }
        String data = head.data;
        head = head.next;
        return data;
    }

    /**
     * Removes and returns String object at the specified index.
     * @param index The index to remove String object
     * @return String object that is removed
     * @throws RuntimeException for invalid index value (index < 0 || index >= size())
     */
    @Override
    public String removeAt(int index) {

        // For invalid index value, throws RuntimeException
        if (index < 0 || index >= size()) {
            throw new RuntimeException("Invalid index value: " + index);
        }

//        // But for now, returns null to prevent our program from crashing
//        if (index < 0 || isEmpty()) {
//            return null;
//        }

        // If index is 0, simply removes the head
        if (index == 0) {
            return removeFirst();
        }

        // Else, remove the node whose list index equals argument index ( > 1)
        return removeAt(head, 0, index);

    }

    /**
     * Recursively calls itself to find and remove the node at a given index.
     * @param node The current node
     * @param curIndex The list index of the current node
     *                 (whose index is assumed to not equal the given index)
     * @param index The specified index to remove
     * @return String object that is removed
     */
    private String removeAt(Node node, int curIndex, int index) {
        /*
         * Base cases:
         *  - if current node is null or the last node in the list:
         *      simply returns null to indicate an invalid index (though it will never reach here in our case)
         *  - if the next index equals the given index:
         *      simply removes the next node and returns the removed value
         */
        if (node == null || node.next == null) {
            return null;
        }
        if (curIndex + 1 == index) {
            String data = node.next.data;
            node.next = node.next.next;
            return data;
        }
        /*
         * Recursive case:
         *  - if the next index does not equal the given index:
         *      recursively finds the next index that equals the given index
         *      and removes the node at the given index
         */
        return removeAt(node.next, curIndex + 1, index);
    }

    /**
     * Validates the text string.
     * @param text A string object to validate against
     * @return true if text string contains only letters and false if otherwise
     */
    private static boolean isWord(String text) {
        // nulls are validated to be false; do not allow nulls to be inserted
        if (text == null) {
            return false;
        }
        return text.matches("[a-zA-Z]+");
    }

    /**
     * A simple test program.
     * @param args arguments
     */
    public static void main(String[] args) {
        SortedLinkedList list = new SortedLinkedList();
        list.display();

        String[] words = {"hi", "I'm", "your", "friends", "ah", "friend", "baby", "baby"};
        list = new SortedLinkedList(words);
        list.display();
        System.out.println("Removed at index 5: " + list.removeAt(5));
        System.out.println("Removed at index 0: " + list.removeAt(0));
        // System.out.println("Removed at index 5: " + list.removeAt(5));
        list.display();
        System.out.println("Removed at index 2: " + list.removeAt(2));
        list.display();
    }

}
