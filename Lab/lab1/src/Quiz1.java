import java.util.ArrayList;
import java.util.Arrays;

public class Quiz1 {

    public static void main(String[] args) {

        Object[] a = {new ArrayList<>(),  new Integer(1), "17683"};
        Object[] b = a.clone();                  // shallow copy: copy object references
        b[1] = new Integer(1);
        b[2] = "17683";

        System.out.println(a[0] == b[0]);        // true:  `clone` is shallow, b[0] still points to the same mem location as a[0]
        System.out.println(a[1] == b[1]);        // false: Integer objects cannot be compared using `==`; `==` compares their mem locations, which are different
        System.out.println(a[2] == b[2]);        // true:  String object, if constructed with string literal, will be created in the string pool;
                                                 //                        thus a[2] and b[2] point to the same string object in the string pool
                                                 //                       if constructed with `new` keyword, will be created in separate mem locations
        System.out.println(a.equals(b));         // false: equivalent to `a == b`, which compares address
        System.out.println(Arrays.equals(a, b)); // true:  compares content

        System.out.println("=================");

        Integer c = new Integer(3);
        Integer d = new Integer(3);
        System.out.println(c == d);              // false: comparing two Integer objects using `==` is comparing their mem locations; neither will be unboxed

        int e = 3;
        System.out.println(c == e);              // true:  if an Integer object is compared to a primitive int, the Integer object will automatically be unboxed

        String f = new String("haha");
        String g = new String("haha");
        System.out.println(f == g);              // false

    }

}
