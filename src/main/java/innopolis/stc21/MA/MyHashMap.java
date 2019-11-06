package innopolis.stc21.MA;
import java.util.Objects;

public class MyHashMap {

    /**
     * Default capacity for MyHashMap
     */
    static final int DEFAULT_CAPACITY = 16;
    /**
     *Default load factor for MyHashMap
     */
    static final float DEFAULT_LOAD_FACTOR =  0.75F;
    /**
     * Half-size of int type for calculate maximum size of nodes array
     */
    static final int MAX_CAPACITY = 1073741823;

    /**
     * Class for for storing key-value pairs
     */
    static class Node{

        /**
         *Hash code of current node
         */
        final int hash;
        /**
         * Key of current node
         */
        final String key;
        /**
         *Value of current node
         */
        String value;
        /**
         * Link to the next node to resolve collisions
         */
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



        public final String setValue(String newValue){
            String oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }

        public final Node setNext (Node newNext){
            Node oldNext = this.next;
            this.next = newNext;
            return oldNext;
        }

        @Override
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

        @Override
        public final String toString(){
            return key + "=" + value;
        }

        @Override
        public final int hashCode(){
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
    }


    /**
     * Current capacity of MyHashMap
     */
    private int capacity;
    /**
     *Load Factor of MyHashMap
     */
    private float loadFactor;
    /**
     * Count of key-value pairs
     */
    private int size;
    /**
     *Array of key-value pairs.
     */
    private Node[] Nodes;

    /**
     * Default constructor
     */
    public MyHashMap(){
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = 0;
        this.Nodes = new Node[DEFAULT_CAPACITY];
    }

    /**
     * Constructor with custom capacity and loadFactor
     * @param capacity default value is 16
     * @param loadFactor default value is 0.75f
     * @throws  IllegalArgumentException if capacity or loadFactor is nonpositive
     */
    public MyHashMap (int capacity, float loadFactor){
        if (capacity <= 0){
            throw new IllegalArgumentException("capacity is nonpositive");
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)){
            throw new IllegalArgumentException("loadFactor is nonpositive");
        }
        if (capacity > MAX_CAPACITY ){
            capacity = MAX_CAPACITY;
        }

        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.Nodes = new Node[capacity];
    }

    /**
     * Calculate hash from key
     * @param key Key of Node
     * @return hash for Node
     */
    final static int hash(String key){
        int hash = 0;
        if (key != null) {
            for (int i = key.length()-1; i >= 0; i--){
                hash += key.charAt(i);
            }
        }
        if(hash < 0){
            hash = -hash;
        }
        return hash;
    }

    /**
     * Calculate index
     * @param hash source for calculate index
     */
    int index(int hash){
        int length = this.Nodes.length;
        if(length <= 0 ){
            throw new ArrayIndexOutOfBoundsException(length);
        }
        if( hash < 0){
            throw new ArrayIndexOutOfBoundsException(hash);
        }
        return hash % length;
    }

    /**
     * Internal search Node of MyHashMap
     * @param hash hash of the sought Node
     * @param key key of the sought Node
     * @return found Node or null
     */
    Node getNode(int hash, String key){
        Node current = this.Nodes[this.index(hash)];
        while (current != null){
            if (current.getHash() == hash && ((current.getKey() != null && current.getKey().equals(key)) || key == null )){
                return current;
            }
            current = current.getNext();
        }
        return null;
    };

    /**
     * Resize MyHashMap. Doubling the size of the internal array
     */
    final void resize(){
        this.capacity = this.capacity <= MAX_CAPACITY ?
                this.capacity * 2 :
                MAX_CAPACITY * 2;
        this.size = 0;
        Node[] oldNodes = this.Nodes;
        this.Nodes = new Node[this.capacity];
        for (int i = 0; i < oldNodes.length ; i++) {
            Node current = oldNodes[i];
            while (current != null){
                this.add(current.getKey(), current.getValue());
                current = current.getNext();
            }
        }
    }


    /**
     * Return current count of members in map
     */
    public int size(){
        return this.size;
    }

    /**
     * Checking  MyHasMap is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     *  Add Node in MyHashMap
     * @param key Key of node
     * @param value Value of Node
     * @return True if Node has been added. False if Node already has been existed and will not adding
     */
    public boolean add(String key, String value){
        int hash = hash(key);
        Node current = this.getNode(hash, key);
        if (current != null){
            return false;
        }
        int index = index(hash);
        Node newNode = new Node(hash, key, value, Nodes[index]);
        Nodes[index] = newNode;
        this.size++;
        if (size > capacity*loadFactor){
            this.resize();
        }
        return true;
    }

    /**
     * Update the value for key
     * @param key Key for search value
     * @param value New value
     * @return True if a value exist for the key and false if not
     */
    public boolean update(String key, String value){
        int hash = hash(key);
        Node current = this.getNode(hash, key);
        if (current != null){
            current.setValue(value);
            return true;
        }
        return false;
    }

    /**
     * Search the value for the key
     * @param key key of the search
     * @return The value for the key or null
     */
    public String get(String key){
        int hash = hash(key);
        Node current = this.getNode(hash, key);
        if (current != null){
            return current.getValue();
        }
        return null;
    }

    /**
     * Remove value of the key
     * @param key The Key of value to be removed
     * @return True if the value of the key exist and has been removed. False if the value of the kye is not exist
     */
    public boolean remove(String key){
        int hash = hash(key);
        int index = this.index(hash);
        Node parent = null;
        Node current = this.Nodes[index];
        while (current != null){
            if (current.getHash() == hash && ((current.getKey() != null && current.getKey().equals(key)) || key == null )){
                if (parent == null){
                    this.Nodes[index] = current.getNext();
                }
                else {
                    parent.setNext(current.getNext());
                }
                this.size--;
                return true;
            }
            parent = current;
            current = current.getNext();
        }
        return false;
    }

    /**
     * Checking exist the key in MyHashMap
     * @param key The Key for checking
     * @return True if the key exist in MyHashMap and false if not
     */
    public boolean exist(String key){
        int hash = hash(key);
        return this.getNode(hash, key) != null;
    }

    /**
     * Clear MyHashMap and set default settings
     */
    public void clear(){
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = 0;
        this.Nodes = new Node[capacity];
    }

    @Override
    public final String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Nodes.length; i++) {
            Node current = Nodes[i];
            result.append(i + ": ");
            if(current == null){
                result.append("-\n");
            }
            else{
                int lengthOfWhiteSpice = String.valueOf(i).length() + 2;
                boolean isFirstAdd = true;
                while (current != null){
                    if ( !isFirstAdd ){
                        result.append(new String(new char[lengthOfWhiteSpice]).replace('\0', ' '));
                    }
                    isFirstAdd =false;
                    result.append( current.toString() + "\n");
                    current = current.getNext();
                }
            }
        }
        return result.toString();
    }
}
