class LRUCache{

    class Node{
        int key;
        int value;
        Node next;
        Node prev;

        Node(int k, int v){
            key = k;
            value = v;
        }
    }

    private Map<Integer, Node>map;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache(int capacity){
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key){
        if(!map.containsKey(key)){
            return -1;
        }
        Node temp = map.get(key);
        remove(temp);
        placeFirst(temp);
        return temp.value;
    }

    public void put(int key, int value){

        if(map.contains(key)){
            Node temp = map.get(key);
            remove(temp);
        }
        Node newNode = new Node(key, value);
        map.put(newNode, value);
        insertToFront(newNode);
        if(map.size() > capacity){
            Node lru = tail.prev;
            remove(tail);
            map.remove(lru.key);
        }
    }

    private void remove(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertToFront(Node node){

        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}