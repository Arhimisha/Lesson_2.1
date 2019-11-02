package innopolis.stc21.MA;
import java.util.Objects;

public class MyHashMap {

    public static final int DEFAULT_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR =  0.75F;

    //Класс
    static class Node{
        final int hash;
        final String key;
        String value;
        Node next;

        Node (int hash, String key, String value, Node next ){
            this.hash = hash;
            this.key = key;
            this.next = next;
            this.value = value;
        }


        public String getKey() {
            return key;
        }
        public int getHash() {
            return hash;
        }
        public String getValue() {
            return value;
        }
        public Node getNext() {
            return next;
        }

        public final String toString(){
            return key + "=" + value;
        }

        //todo Нужна эта функция?
        public final int hashCode(){
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
        public final String setValue(String newValue){
            String oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }
        public final boolean equals(Object o){
            if (this == o){
                return true;
            }
            if (o instanceof Node){
                Node node = (Node) o;
                if(this.key == node.getKey() && this.value == node.getValue()){
                    return true;
                }
            }
            return false;
        }
    }



    private int capacity;
    private float loadFactor;
    private int size;
    private Node[] Nodes;


    MyHashMap(){
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = 0;
        this.Nodes = new Node[DEFAULT_CAPACITY];
    }
    MyHashMap (int capacity, int loadFactor){

    }

    //todo Добавить конструктор с установкой capacity
    //todo Добавить конструктор с добавлением узлов
    //todo Добавить метод ToString() для полного вывода всей коллекции
}
