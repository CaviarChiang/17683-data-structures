import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * An implementation of binary search tree.
 *
 * @param <T> data type of objects
 * @author Yuyan Jiang
 * Andrew ID: yuyanj
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {

    /**
     * Private static nested class representing the nodes of a BST.
     * @param <T> data type of `data` field
     */
    private static class Node<T> {

        /**
         * The current node's value.
         */
        private T data;
        /**
         * The current node's left child.
         */
        private Node<T> left;
        /**
         * The current node's right child.
         */
        private Node<T> right;

        /**
         * Constructor that takes a data value.
         * @param d Data value to store in the node
         */
        Node(T d) {
            this(d, null, null);
        }

        /**
         * Constructor that takes a data value, a left child reference and a right child reference.
         * @param d Data value to store in the node
         * @param l Node reference to the left child
         * @param r Node reference to the right child
         */
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }

    }

    /**
     * Private static nested class that traverses the BST.
     * @param <T> data type of objects
     */
    private static class BSTIterator<T> implements Iterator<T> {

        /**
         * A stack that stores all the left children of
         * the node that was passed to the constructor.
         */
        private Stack<Node<T>> stack;

        /**
         * Constructor that takes a Node object.
         * @param root A Node object that represents the root of a BST
         */
        BSTIterator(Node<T> root) {
            stack = new Stack<>();
            pushAll(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new RuntimeException("No more elements available...");
            }
            Node<T> node = stack.pop();
            pushAll(node.right);
            return node.data;
        }

        /**
         * Helper method that pushes the current node and all its left children to the stack.
         * @param node Current node
         */
        private void pushAll(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

    }

    /**
     * A Node object that represents the root of the BST.
     */
    private Node<T> root;
    /**
     * A comparator object that determines the ordering of the BST.
     */
    private Comparator<T> comparator;

    /**
     * Default constructor.
     */
    public BST() {
        this(null);
    }

    /**
     * Constructor that takes a comparator object.
     * @param comp A comparator object that determines the ordering of the BST
     */
    public BST(Comparator<T> comp) {
        root = null;
        comparator = comp;
    }

    /**
     * Returns the comparator field.
     * @return A comparator object that determines the ordering of the current BST
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * Returns the root value of the current BST.
     * @return The root data value
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }

    /**
     * Returns the height of the current BST.
     * @return The height of the current BST
     */
    public int getHeight() {
        return getHeightHelper(root);
    }

    /**
     * Computes the height of a tree node recursively.
     * @param node A tree root of interest
     * @return The height of the tree node
     */
    private int getHeightHelper(Node<T> node) {
        // Base case: if node is null or node has no children, its height is 0
        if (node == null || (node.left == null && node.right == null)) {
            return 0;
        }
        // Recursive case: height of a tree is the max height among its children plus 1
        return Math.max(getHeightHelper(node.left), getHeightHelper(node.right)) + 1;
    }

    /**
     * Returns the number of nodes in the current BST.
     * @return Number of nodes in the current BST
     */
    public int getNumberOfNodes() {
        return getNumberOfNodesHelper(root);
    }

    /**
     * Computes the number of nodes in a BST recursively.
     * @param node A tree root of interest
     * @return Number of nodes in the tree
     */
    public int getNumberOfNodesHelper(Node<T> node) {
        // Base case: if root is null, then the number of nodes in the tree is 0
        if (node == null) {
            return 0;
        }
        /*
         * Recursive case: number of nodes in the tree =
         *                 number of nodes in the left subtree +
         *                 number of nodes in the right subtree + 1
         */
        return getNumberOfNodesHelper(node.left) + getNumberOfNodesHelper(node.right) + 1;
    }

    /**
     * Given the value (object), tries to find it.
     * @param toSearch Object value to search
     * @return The value (object) of the search result. If not found, returns null
     */
    @Override
    public T search(T toSearch) {
        // Validate input
        if (toSearch == null) {
            return null;
        }
        // Call helper method
        return searchHelper(root, toSearch);
    }

    /**
     * Helper method that recursively searches for a given value in the BST.
     * @param curr Current node
     * @param toSearch Object value to search
     * @return The value (object) of the search result. If not found, returns null
     */
    private T searchHelper(Node<T> curr, T toSearch) {

        /*
         * Base case 1: if current node is null, we've reached the leaf
         * but still couldn't find the search value, so we return null
         */
        if (curr == null) {
            return null;
        }

        // Compare the current node's value with the search value
        int comp;
        if (comparator != null) {
            comp = comparator.compare(curr.data, toSearch);
        } else {
            comp = curr.data.compareTo(toSearch);
        }

        /*
         * Base 2: if found, return the **FOUND VALUE IN THE TREE**.
         * DO NOT RETURN `toSearch` cuz it might only contain partial data.
         * We want to give back the complete data to our user.
         */
        if (comp == 0) {
            return curr.data;
        }

        // Recursive case 1: if toSearch larger, search on the right subtree
        if (comp < 0) {
            return searchHelper(curr.right, toSearch);
        }

        // Recursive case 2: else, search on the left subtree
        return searchHelper(curr.left, toSearch);

    }

    /**
     * Inserts a value (object) to the tree.
     * No duplicates allowed.
     * @param toInsert A value (object) to insert into the tree
     */
    @Override
    public void insert(T toInsert) {
        // Validate input
        if (toInsert == null) {
            return;
        }
        // Base case 0: if current root is null, simply make the new node the root
        if (root == null) {
            root = new Node<>(toInsert);
            return;
        }
        // Call helper method
        insertHelper(null, root, toInsert);
    }

    /**
     * Helper method that recursively inserts a value into the BST.
     * No duplicates allowed.
     * @param prev The parent node of the current node
     * @param curr Current node
     * @param toInsert A value (object) to insert into the tree
     */
    private void insertHelper(Node<T> prev, Node<T> curr, T toInsert) {

        /*
         * Base case 1: if current node is null, we've reached the leaf.
         * Simply insert the value at curr if no duplicate is found.
         */
        if (curr == null) {

            // Compare the parent node's value with the insertion value
            int comp;
            if (comparator != null) {
                comp = comparator.compare(prev.data, toInsert);
            } else {
                comp = prev.data.compareTo(toInsert);
            }

            // If toInsert is larger, insert as the right child
            if (comp < 0) {
                prev.right = new Node<>(toInsert);
            } else if (comp > 0) {
                prev.left = new Node<>(toInsert);
            }

            // If there is already the existing object, keep the old one
            return;

        }

        // Compare the current node's value with the insertion value
        int comp;
        if (comparator != null) {
            comp = comparator.compare(curr.data, toInsert);
        } else {
            comp = curr.data.compareTo(toInsert);
        }

        // Base 2: if current node's value equals the insertion value, keep the only one and return
        if (comp == 0) {
            return;
        }

        /*
         * Recursive cases: if toInsert is larger, insert at the right subtree;
         * else insert at the left subtree
         */
        if (comp < 0) {
            insertHelper(curr, curr.right, toInsert);
        } else {
            insertHelper(curr, curr.left, toInsert);
        }

    }

    /**
     * Iteratively traverses the elements of the BST in in-order sequence.
     * @return A BSTIterator instance that traverses the BST in-order.
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTIterator<>(root);
    }

    /**
     * Simple test program.
     * @param args Arguments
     */
    public static void main(String[] args) {

        BST<String> tree = new BST<>();
        tree.insert("a");
        tree.insert("b");
        tree.insert("c");
        System.out.println(tree.getHeight());

    }

}
