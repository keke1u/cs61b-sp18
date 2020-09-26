import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    private static String message = "";
    private static int callTimes = 500;

    private void randomAdd(double r, Integer i,
                           StudentArrayDeque<Integer> test, ArrayDequeSolution<Integer> right) {
        if (r < 0.5) {
            test.addFirst(i);
            right.addFirst(i);
            message += "\naddFirst(" + i + ") ";
        } else {
            test.addLast(i);
            right.addLast(i);
            message += "\naddLast(" + i + ") ";
        }
    }

    private void randomRemove(double r,
                              StudentArrayDeque<Integer> test, ArrayDequeSolution<Integer> right) {
        Integer expected;
        Integer actual;
        if (r < 0.5) {
            expected = test.removeFirst();
            actual = right.removeFirst();
            message += "\nremoveFirst() ";
        } else {
            expected = test.removeLast();
            actual = right.removeLast();
            message += "\nremoveLast() ";
        }
        assertEquals(message, expected, actual);
    }

    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> test1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> right1 = new ArrayDequeSolution<>();

        for (Integer i = 1; i < callTimes; i += 1) {
            if (test1.isEmpty()) {
                double random = StdRandom.uniform();
                randomAdd(random, i, test1, right1);
            } else {
                double random1 = StdRandom.uniform();
                double random2 = StdRandom.uniform();
                if (random1 < 0.5) {
                    randomAdd(random2, i, test1, right1);
                } else {
                    randomRemove(random2, test1, right1);
                }
            }
        }
    }

}
