package innopolis.stc21.MA;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyHashMapTest {

    @Test(expected = IllegalArgumentException.class)
    public void MyHashMapNegativeLoadFactor() {
        MyHashMap myHashMap = new MyHashMap(10, -0.1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void MyHashMapNegativeCapacity() {
        MyHashMap myHashMap = new MyHashMap(-10, 0.1f);
    }

    @Test
    public void size() {
        MyHashMap myHashMapCustom = new MyHashMap(100, 0.50f);
        assertEquals(0, myHashMapCustom.size());

        MyHashMap myHashMapDefault = new MyHashMap();
        assertEquals(0, myHashMapDefault.size());
        for (int i = 0; i < 1000; i++) {
            myHashMapDefault.add(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(1000, myHashMapDefault.size());
    }

    @Test
    public void hash() {
        String key = "Hello my HashCode";
        int hash = 0;
        for (int i = key.length() - 1; i >= 0; i--) {
            hash += key.charAt(i);
        }
        assertEquals(hash, MyHashMap.hash(key));

        assertEquals(0, MyHashMap.hash(null));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void indexOfNode() {
        MyHashMap myHashMap = new MyHashMap(10, 0.75f);
        assertEquals(0, myHashMap.index(100));
        assertEquals(3, myHashMap.index(103));
        assertEquals(0, myHashMap.index(1100));
        assertEquals(9, myHashMap.index(109));

        myHashMap = new MyHashMap();
        assertEquals(0, myHashMap.index(16));
        assertEquals(3, myHashMap.index(19));
        assertEquals(15, myHashMap.index(175));
        assertEquals(0, myHashMap.index(176));

        myHashMap.index(-1);
    }

    @Test
    public void add() {
        MyHashMap myHashMap = new MyHashMap();
        assertTrue(myHashMap.add("4", "4"));

        assertTrue(myHashMap.add("12", "12"));
        assertTrue(myHashMap.add("21", "21"));

        assertFalse(myHashMap.add("12", "12"));
        assertFalse(myHashMap.add("21", "21"));

        assertTrue(myHashMap.add("123", "123"));
        assertTrue(myHashMap.add("132", "132"));
        assertTrue(myHashMap.add("213", "213"));
        assertTrue(myHashMap.add("231", "231"));
        assertTrue(myHashMap.add("312", "312"));
        assertTrue(myHashMap.add("321", "321"));

        assertFalse(myHashMap.add("123", "123"));
        assertFalse(myHashMap.add("132", "132"));
        assertFalse(myHashMap.add("213", "213"));
        assertFalse(myHashMap.add("231", "231"));
        assertFalse(myHashMap.add("312", "312"));
        assertFalse(myHashMap.add("321", "321"));

        assertTrue(myHashMap.add(null, null));
        assertFalse(myHashMap.add(null, null));
    }

    @Test
    public void clear() {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.add("4", "4");
        myHashMap.add("12", "12");
        myHashMap.add("21", "21");
        assertEquals(3, myHashMap.size());

        myHashMap.clear();
        assertEquals(0, myHashMap.size());

        assertTrue(myHashMap.add("4", "4"));
        assertTrue(myHashMap.add("12", "12"));
        assertTrue(myHashMap.add("21", "21"));
        assertEquals(3, myHashMap.size());
    }


    @Test
    public void resize() {
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 12; i++) {
            myHashMap.add(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(12, myHashMap.size());
        assertEquals(16, myHashMap.toString().split(":").length - 1);
        for (int i = 12; i < 25; i++) {
            myHashMap.add(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(25, myHashMap.size());
        assertEquals(64,myHashMap.toString().split(":").length - 1);
    }

    @Test
    public void get() {
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000; i++) {
            myHashMap.add(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 2000; i++) {
            assertEquals(String.valueOf(i), myHashMap.get(String.valueOf(i)) );
        }

        myHashMap.add(null, "NULL");
        assertEquals("NULL", myHashMap.get(null));

        myHashMap.add("null", null);
        assertNull(myHashMap.get("null"));

    }

    @Test
    public void update() {
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000; i++) {
            myHashMap.add(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 2000; i++) {
            myHashMap.update(String.valueOf(i), "new value");
        }
        assertEquals(2000, myHashMap.size());
        for (int i = 0; i < 2000; i++) {
            assertEquals("new value", myHashMap.get(String.valueOf(i)));
        }
        for (int i = 2000; i < 3000; i++) {
            assertNull(myHashMap.get(String.valueOf(i)));
        }
    }

    @Test
    public void exist() {
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000; i++) {
            myHashMap.add(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 2000; i++) {
            assertTrue(myHashMap.exist(String.valueOf(i)));
        }
        for (int i = 2000; i < 3000; i++) {
            assertFalse(myHashMap.exist(String.valueOf(i)));
        }
    }

    @Test
    public void remove() {
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000; i++) {
            myHashMap.add(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 2000; i < 3000; i++) {
            assertFalse(myHashMap.remove(String.valueOf(i)));
            assertEquals(2000, myHashMap.size());
        }
        for (int i = 0; i < 2000; i++) {
            assertTrue(myHashMap.remove(String.valueOf(i)));
            assertEquals(2000 - i - 1, myHashMap.size());
        }
    }
}