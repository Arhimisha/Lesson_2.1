package innopolis.stc21.MA;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyHashMapTest {

    @Test
    public void MyHashMap() {
        try{
            MyHashMap myHashMap = new MyHashMap(10, -0.1f);
            Assert.fail("Expected IllegalArgumentException");
        }
        catch (Exception ex){
            assertEquals("loadFactor is nonpositive", ex.getMessage());
        }
        try{
            MyHashMap myHashMap = new MyHashMap(-10, 0.1f);
            Assert.fail("Expected IllegalArgumentException");
        }
        catch (Exception ex){
            assertEquals("capacity is nonpositive", ex.getMessage());
        }
    }

    @Test
    public void size() {
        MyHashMap myHashMapCustom = new MyHashMap(100, 0.50f);
        assertEquals(myHashMapCustom.size(), 0);

        MyHashMap myHashMapDefault = new MyHashMap();
        assertEquals(myHashMapDefault.size(), 0);
        for (int i = 0; i < 1000 ; i++) {
            myHashMapDefault.add(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(myHashMapDefault.size(), 1000);
    }

    @Test
    public void hash(){
        String key = "Hello my HashCode";
        int hash = 0;
        for (int i = key.length()-1; i >= 0; i--){
            hash += key.charAt(i);
        }
        assertEquals(MyHashMap.hash(key), hash);

        key = null;
        assertEquals(MyHashMap.hash(key), 0);
    }

    @Test
    public void indexOfNodex(){
        MyHashMap myHashMap = new MyHashMap(10, 0.75f);
        assertEquals(myHashMap.index(100),0);
        assertEquals(myHashMap.index(103),3);
        assertEquals(myHashMap.index(1100),0);
        assertEquals(myHashMap.index(109),9);


        myHashMap = new MyHashMap();
        assertEquals(myHashMap.index(16),0);
        assertEquals(myHashMap.index(19),3);
        assertEquals(myHashMap.index(175),15);
        assertEquals(myHashMap.index(176),0);

        try{
            myHashMap.index(-1);
            Assert.fail("Expected ArrayIndexOutOfBoundsException");
        }
        catch (Exception ex){
            assertEquals("Array index out of range: -1", ex.getMessage());
        }
    }

    @Test
    public void add(){
        MyHashMap myHashMap = new MyHashMap();
        assertEquals(myHashMap.add("4","4"), true);
        assertEquals(myHashMap.add("12","12"), true);
        assertEquals(myHashMap.add("21","21"), true);
        assertEquals(myHashMap.add("12","12"), false);
        assertEquals(myHashMap.add("21","21"), false);
        assertEquals(myHashMap.add("123","123"), true);
        assertEquals(myHashMap.add("132","132"), true);
        assertEquals(myHashMap.add("213","213"), true);
        assertEquals(myHashMap.add("231","231"), true);
        assertEquals(myHashMap.add("312","312"), true);
        assertEquals(myHashMap.add("321","321"), true);
        assertEquals(myHashMap.add("123","123"), false);
        assertEquals(myHashMap.add("132","132"), false);
        assertEquals(myHashMap.add("213","213"), false);
        assertEquals(myHashMap.add("231","231"), false);
        assertEquals(myHashMap.add("312","312"), false);
        assertEquals(myHashMap.add("321","321"), false);

        assertEquals(myHashMap.add(null, null), true);
        assertEquals(myHashMap.add(null, null), false);
    }

    @Test
    public void clear(){
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.add("4","4");
        myHashMap.add("12","12");
        myHashMap.add("21","21");
        assertEquals(myHashMap.size(), 3);
        myHashMap.clear();
        assertEquals(myHashMap.size(), 0);
        assertEquals(myHashMap.add("4","4"), true);
        assertEquals(myHashMap.add("12","12"), true);
        assertEquals(myHashMap.add("21","21"), true);
    }


    @Test
    public void resize(){
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 12 ; i++) {
            myHashMap.add(String.valueOf(i),String.valueOf(i));
        }
        assertEquals(myHashMap.size(), 12);
        assertEquals(myHashMap.toString().split(":").length-1, 16);
        for (int i = 12; i < 25 ; i++) {
            myHashMap.add(String.valueOf(i),String.valueOf(i));
        }
        assertEquals(myHashMap.size(), 25);
        assertEquals(myHashMap.toString().split(":").length-1, 64);
    }

    @Test
    public void get(){
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000 ; i++) {
            myHashMap.add(String.valueOf(i),String.valueOf(i));
        }
        for (int i = 0; i < 2000 ; i++) {
            assertEquals(myHashMap.get(String.valueOf(i)), String.valueOf(i));
        }
        myHashMap.add(null, "NULL");
        assertEquals(myHashMap.get(null), "NULL");
        myHashMap.add("null", null);
        assertEquals(myHashMap.get("null"),null);

    }
    @Test
    public void update(){
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000 ; i++) {
            myHashMap.add(String.valueOf(i),String.valueOf(i));
        }
        for (int i = 0; i < 2000 ; i++) {
            myHashMap.update(String.valueOf(i), "new value");
        }
        assertEquals(myHashMap.size(), 2000);
        for (int i = 0; i < 2000 ; i++) {
            assertEquals(myHashMap.get(String.valueOf(i)), "new value");
        }
        for (int i = 2000; i <3000 ; i++) {
            assertEquals(myHashMap.get(String.valueOf(i)), null);
        }
    }

    @Test
    public void exist(){
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000 ; i++) {
            myHashMap.add(String.valueOf(i),String.valueOf(i));
        }
        for (int i = 0; i < 2000 ; i++) {
            assertEquals(myHashMap.exist(String.valueOf(i)), true);
        }
        for (int i = 2000; i <3000 ; i++) {
            assertEquals(myHashMap.exist(String.valueOf(i)), false);
        }
    }

    @Test
    public void remove(){
        MyHashMap myHashMap = new MyHashMap();
        for (int i = 0; i < 2000 ; i++) {
            myHashMap.add(String.valueOf(i),String.valueOf(i));
        }
        for (int i = 2000; i <3000 ; i++) {
            assertEquals(myHashMap.remove(String.valueOf(i)), false);
            assertEquals(myHashMap.size(), 2000);
        }
        for (int i = 0; i < 2000 ; i++) {
            assertEquals(myHashMap.remove(String.valueOf(i)), true);
            assertEquals(myHashMap.size(), 2000 - i -1 );
        }
    }
}