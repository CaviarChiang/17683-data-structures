import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 2 Solve Josephus problem
 * using different data structures
 * and different algorithms and compare running times
 *
 * I would use `playWithLLAt` method among the three.
 * `playWithLLAt` uses `LinkedList` as a "List",
 * allowing us to use index value to find and remove
 * a person to be executed in the circle.
 *
 * This saves us time as apposed to using deque or queue
 * solutions, in which cases we don't have access to
 * the middle person that's to be removed and thus we
 * have to constantly take out the first person at the
 * front and place him at the rear until we have access
 * to the middle person that's to be removed.
 *
 * However, with `LinkedList` as a "List", we can remove
 * a person at an index. This is more time efficient as
 * finding a person at a specific index and removing it
 * once takes less time than performing multiple removing
 * and adding operations until we get to the person at
 * that specific index.
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class Josephus {

    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {

        // validate inputs

        if (size <= 0) {
            throw new RuntimeException(
                    "Invalid arguments: size must be greater than 0");
        }

        if (rotation <= 0) {
            throw new RuntimeException(
                    "Invalid argument: rotation must be greater than 0");
        }

        // Array Double Ended Queue
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            deque.addLast(i + 1); // add to end of deque
        }

        while (deque.size() != 1) {
            /*
             * finds the elimination position without
             * rotating the circle more than its current size
             */
            for (int i = 0; i < (rotation - 1) % deque.size(); i++) {
                deque.addLast(deque.removeFirst());
            }
            deque.removeFirst(); // remove at the front (similar to `pollFirst()`

        }

        // now deque.size() = 1
        return deque.peekFirst();

    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {

        // validate inputs

        if (size <= 0) {
            throw new RuntimeException(
                    "Invalid arguments: size must be greater than 0");
        }

        if (rotation <= 0) {
            throw new RuntimeException(
                    "Invalid argument: rotation must be greater than 0");
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            queue.add(i + 1); // add to end of queue
        }

        while (queue.size() != 1) {
            /*
             * finds the elimination position without
             * rotating the circle more than its current size
             */
            for (int i = 0; i < (rotation - 1) % queue.size(); i++) {
                queue.add(queue.poll());
            }
            queue.poll(); // poll at the front

        }

        // now linkedList.size() = 1
        return queue.peek();

    }

    /**
     * Uses LinkedList class to find the survivor's position.
     *
     * However, do NOT use the LinkedList as Queue/Deque
     * Instead, use the LinkedList as "List"
     * That means, it uses index value to find and remove a person to be executed in the circle
     *
     * Note: Think carefully about this method!!
     * When in doubt, please visit one of the office hours!!
     *
     * Demo:
     * 1 2 3 4 5 6  removeIdx: 2 = (0 + 3 - 1) % 6
     * 1 2 4 5 6    removeIdx: 4 = (2 + 3 - 1) % 5
     * 1 2 4 5      removeIdx: 2 = (4 + 3 - 1) % 4
     * 1 2 5        removeIdx: 1 = (2 + 3 - 1) % 3
     * 1 5          removeIdx: 1 = (1 + 3 - 1) % 2
     * 1            removeIdx: nah
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {

        // validate inputs

        if (size <= 0) {
            throw new RuntimeException(
                    "Invalid arguments: size must be greater than 0");
        }

        if (rotation <= 0) {
            throw new RuntimeException(
                    "Invalid argument: rotation must be greater than 0");
        }

        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i + 1); // append at the end of the list
        }

        int removeIdx = 0;
        while (list.size() != 1) {
            /*
             * finds the elimination position without
             * rotating the circle more than its current size
             */
            removeIdx = (removeIdx + rotation - 1) % list.size();
            list.remove(removeIdx);

        }

        // now list.size() = 1
        return list.get(0);

    }

    /**
     * Simple test cases for Josephus class.
     * @param args arguments
     */
    public static void main(String[] args) {

        Josephus game = new Josephus();

        System.out.println("=============== Test ArrayDeque Solution ===============");

        System.out.println(game.playWithAD(6, 3));
        System.out.println(game.playWithAD(1, 5));
        System.out.println(game.playWithAD(5, 1));
        System.out.println(game.playWithAD(5, 2));
        System.out.println(game.playWithAD(11, 1));
        System.out.println(game.playWithAD(40, 7));
        System.out.println(game.playWithAD(55, 1));
        System.out.println(game.playWithAD(66, 100));
        System.out.println(game.playWithAD(1000, 1000));
//        System.out.println(game.playWithAD(-1, 2));
//        System.out.println(game.playWithAD(5, 0));

        System.out.println("=============== Test LinkedList Solution ===============");

        System.out.println(game.playWithLL(6, 3));
        System.out.println(game.playWithLL(1, 5));
        System.out.println(game.playWithLL(5, 1));
        System.out.println(game.playWithLL(5, 2));
        System.out.println(game.playWithLL(11, 1));
        System.out.println(game.playWithLL(40, 7));
        System.out.println(game.playWithLL(55, 1));
        System.out.println(game.playWithLL(66, 100));
        System.out.println(game.playWithLL(1000, 1000));

        System.out.println("=============== Test List Solution ===============");

        System.out.println(game.playWithLLAt(6, 3));
        System.out.println(game.playWithLLAt(1, 5));
        System.out.println(game.playWithLLAt(5, 1));
        System.out.println(game.playWithLLAt(5, 2));
        System.out.println(game.playWithLLAt(11, 1));
        System.out.println(game.playWithLLAt(40, 7));
        System.out.println(game.playWithLLAt(55, 1));
        System.out.println(game.playWithLLAt(66, 100));
        System.out.println(game.playWithLLAt(1000, 1000));

    }

}
