import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    @Test
    public void testisSameNumber() {
        int a = 155;
        int b = 155;
        int c = 555;
        boolean actual = Flik.isSameNumber(a, b);
        boolean actual2 = Flik.isSameNumber(a, c);

        assertTrue(actual);
        assertFalse(actual2);
    }
}