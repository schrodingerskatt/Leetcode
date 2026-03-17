/*
        1
      / | \
     2  3  4
       / \
      5   6
    
    1,3
    2,0
    3,2
    5,0
    6,0
    4,0

    Serialized String : 1,3,2,0,3,2,5,0,6,0,4,0
    Meaning : node value = 1, children = 3
    Serializtion Strategy : for every node store {value, num_of_children}. Then recursively 
                            serialize children.
    
    Pseudo Code :

    serialize(node):

    if node == null:
        return

    append node.val
    append node.children.size()

    for child in node.children
        serialize(child)

    Deserialization Strategy : While reading the serialized string read node value, 
                               read children count, create node, recursively build childrenCount 
                               nodes.
    deserialize():

    read value
    read childrenCount

    node = new Node(value)

    for i in childrenCount
        node.children.add(deserialize())

    return node

*/

class Node{
    public int val;
    public List<Node> children;

    public Node(int val, List<Node>children){
        this.val = val;
        this.children = children;
    }
}

class Solution{

    int index = 0;
    public String serialize(Node node){

        if(node == null) return "";
        StringBuilder sb = new StringBuilder();
        dfsSerialize(node, sb);
        return sb.toString();

    }

    public void dfsSerialize(Node node, StringBuilder sb){
        if(node == null) return;
        sb.append(node.val).append(",");
        sb.append(node.children.size()).append(",");
        for(Node child : node.children){
            dfsSerialize(child, sb);
        }
    }

    public Node deserialize(String data){

        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(",");
        return build(arr);
    }

    public Node build(String[] arr){
        int val = Integer.parseInt(arr[index++]);
        int size = Integer.parseInt(arr[index++]);
        Node node = new Node(val, new ArrayList<>());
        for(int i = 0; i < size; i++){
            node.children.add(build(arr));
        }
    return node;
    }
}