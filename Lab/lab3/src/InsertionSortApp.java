/**
 * 17683 Data Structures for Application Programmers.
 * Lab 3 Simple Sorting and Stability
 *
 * Insertion Sort Implementation
 *
 * Andrew ID: yuyanj
 * @author Yuyan Jiang
 */
public class InsertionSortApp {

    /**
     * test insertion sort and its stability.
     * @param args arguments
     */
    public static void main(String[] args) {
        Employee[] list = new Employee[10];

        // employee data : first name, last name, zip
        list[0] = new Employee("Patty", "Evans", 15213);
        list[1] = new Employee("Doc", "Smith", 15214);
        list[2] = new Employee("Lorraine", "Smith", 15216);
        list[3] = new Employee("Paul", "Smith", 15216);
        list[4] = new Employee("Tom", "Yee", 15216);
        list[5] = new Employee("Sato", "Hashimoto", 15218);
        list[6] = new Employee("Henry", "Stimson", 15215);
        list[7] = new Employee("Jose", "Vela", 15211);
        list[8] = new Employee("Minh", "Vela", 15211);
        list[9] = new Employee("Lucinda", "Craswell", 15210);

        System.out.println("Before Insertion Sorting: ");
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("");

        insertionSort(list, "last");

        System.out.println("After Insertion Sorting by last name: ");
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("");

        insertionSort(list, "zip");

        System.out.println("After Insertion Sorting by zip code: ");
        for (Employee e : list) {
            System.out.println(e);
        }

    }

    /**
     * Sorts employees either by last name or zip using Insertion Sort.
     * @param list list of employee objects
     * @param key key param value should be either "last" or "zip"
     */
    public static void insertionSort(Employee[] list, String key) {
        for (int out = 1; out < list.length; out++) {
            Employee temp = list[out];
            int in = out;
            while (in > 0 && compareEmployeeByKey(list[in - 1], temp, key) > 0) {  // if list[in - 1] > temp
                in--;
            }
            if (out != in) {
                System.arraycopy(list, in, list, in + 1, out - in); // out - 1 - list + 1
                list[in] = temp;
            }
        }
    }

    /**
     * Compare employees either by last name or zip.
     * @param e1 first employee object
     * @param e2 second employee object
     * @param key key param value should be either "last" or "zip"
     * @return an integer indicating the comparison result
     */
    private static int compareEmployeeByKey(Employee e1, Employee e2, String key) {
        int comp;
        if (key.equals("last")) {
            comp = e1.getLastName().compareTo(e2.getLastName());
        } else {
            comp = Integer.compare(e1.getZipCode(), e2.getZipCode());
        }
        return comp;
    }

}
