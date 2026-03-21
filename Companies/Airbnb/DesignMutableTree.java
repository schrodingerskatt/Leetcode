import java.util.*;

public class MutableSumTree {

    static class Node {
        int id;
        Node parent;
        List<Node> children;
        long sum;       // subtree sum
        boolean isLeaf;

        Node(int id, long value) {
            this.id = id;
            this.sum = value;
            this.isLeaf = true;
            this.children = new ArrayList<>();
        }
    }

    private final Map<Integer, Node> nodes = new HashMap<>();

    // ------------------ CREATE ------------------

    public Node createLeaf(int id, long value) {
        Node node = new Node(id, value);
        nodes.put(id, node);
        return node;
    }

    // ------------------ QUERY ------------------

    public long getValue(int nodeId) {
        Node node = getNode(nodeId);
        return node.sum;
    }

    // ------------------ MUTATION 1 ------------------
    // Convert any node into a leaf

    public void toLeaf(int nodeId, long value) {
        Node node = getNode(nodeId);

        long oldSum = node.sum;

        // Detach children
        for (Node child : node.children) {
            child.parent = null; // optional cleanup
        }
        node.children.clear();

        node.isLeaf = true;
        node.sum = value;

        long delta = value - oldSum;
        propagateDelta(node.parent, delta);
    }

    // ------------------ MUTATION 2 ------------------
    // Convert a leaf into a parent by attaching subtree

    public void toParent(int nodeId, Node subtreeRoot) {
        Node node = getNode(nodeId);

        if (!node.isLeaf) {
            throw new IllegalStateException("Node is already internal");
        }

        // Prevent cycles
        if (isAncestor(node, subtreeRoot)) {
            throw new IllegalArgumentException("Cycle detected");
        }

        long oldSum = node.sum;

        node.isLeaf = false;
        node.children.add(subtreeRoot);
        subtreeRoot.parent = node;

        node.sum = subtreeRoot.sum;

        long delta = node.sum - oldSum;
        propagateDelta(node.parent, delta);
    }

    // Optional: attach multiple children
    public void toParent(int nodeId, List<Node> newChildren) {
        Node node = getNode(nodeId);

        if (!node.isLeaf) {
            throw new IllegalStateException("Node is already internal");
        }

        long oldSum = node.sum;

        node.isLeaf = false;
        node.children.clear();

        long newSum = 0;
        for (Node child : newChildren) {
            if (isAncestor(node, child)) {
                throw new IllegalArgumentException("Cycle detected");
            }
            node.children.add(child);
            child.parent = node;
            newSum += child.sum;
        }

        node.sum = newSum;

        long delta = newSum - oldSum;
        propagateDelta(node.parent, delta);
    }

    // ------------------ HELPERS ------------------

    private Node getNode(int id) {
        Node node = nodes.get(id);
        if (node == null) {
            throw new IllegalArgumentException("Node not found: " + id);
        }
        return node;
    }

    // Efficient upward propagation
    private void propagateDelta(Node node, long delta) {
        while (node != null) {
            node.sum += delta;
            node = node.parent;
        }
    }

    // Cycle detection: check if target is in subtree of node
    private boolean isAncestor(Node node, Node target) {
        Node curr = target;
        while (curr != null) {
            if (curr == node) return true;
            curr = curr.parent;
        }
        return false;
    }

    // ------------------ OPTIONAL UTIL ------------------

    // Build a subtree (helper for testing)
    public Node createInternal(int id, List<Node> children) {
        Node node = new Node(id, 0);
        node.isLeaf = false;

        long sum = 0;
        for (Node child : children) {
            node.children.add(child);
            child.parent = node;
            sum += child.sum;
        }
        node.sum = sum;

        nodes.put(id, node);
        return node;
    }
}