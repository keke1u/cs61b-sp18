package byog.lab5;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void UnionFindTest() {
        UnionFind u = new UnionFind(10);
        assertEquals(1,u.sizeOf(1));
        assertEquals(-1,u.parent(2));
        u.connect(0,1);
        assertTrue(u.isConnected(0,1));
        assertFalse(u.isConnected(1,2));
        assertEquals(2,u.sizeOf(0));
        assertEquals(u.sizeOf(1),u.sizeOf(0));
        u.connect(4,5);
        assertTrue(u.isConnected(4,5));
        assertFalse(u.isConnected(1,4));
        assertEquals(2,u.sizeOf(4));
        assertEquals(u.sizeOf(4),u.sizeOf(5));
        u.connect(0,2);
        assertTrue(u.isConnected(1,2));
        assertFalse(u.isConnected(2,4));
        assertEquals(3,u.sizeOf(0));
        assertEquals(u.sizeOf(1),u.sizeOf(2));
        u.connect(2,3);
        assertTrue(u.isConnected(1,3));
        assertFalse(u.isConnected(3,4));
        assertEquals(4,u.sizeOf(0));
        assertEquals(u.sizeOf(1),u.sizeOf(2));
        u.connect(4,8);
        assertTrue(u.isConnected(8,5));
        assertFalse(u.isConnected(3,4));
        assertEquals(3,u.sizeOf(5));
        assertEquals(4,u.sizeOf(2));
        u.connect(3,8);
        assertTrue(u.isConnected(8,1));
        assertFalse(u.isConnected(8,9));
        assertEquals(7,u.sizeOf(4));
        assertEquals(7,u.sizeOf(2));
        u.connect(5,9);
        assertTrue(u.isConnected(0,9));
        assertFalse(u.isConnected(7,9));
        assertEquals(8,u.sizeOf(9));
        assertEquals(1,u.sizeOf(7));
        assertEquals(u.sizeOf(7),u.sizeOf(6));
    }
}