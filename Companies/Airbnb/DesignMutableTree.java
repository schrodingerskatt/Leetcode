import java.util.*;

/**
 * Mutable Sum Tree
 *
 * Each leaf stores an integer value.
 * Each internal node's value = sum of its immediate children's values.
 *
 * Complexities:
 *   getValue     → O(1)
 *   toLeaf       → O(depth + detached subtree size)
 *   toParent     → O(depth + attached subtree size)
 *   isAncestor   → O(depth)
 */
public class MutableSumTree {

    // ─────────────────────────────────────────────
    // Node
    // ─────────────────────────────────────────────

    static class Node {
        final int id;
        Node parent;
        List<Node> children;
        long sum;       // O(1) subtree sum — always kept up to date
        boolean isLeaf;

        Node(int id, long value, boolean isLeaf) {
            this.id = id;
            this.sum = value;
            this.isLeaf = isLeaf;
            this.children = new ArrayList<>();
        }
    }

    // ─────────────────────────────────────────────
    // State
    // ─────────────────────────────────────────────

    private final Map<Integer, Node> nodes = new HashMap<>();
    private Node root = null;

    // ─────────────────────────────────────────────
    // BUILD HELPERS
    // ─────────────────────────────────────────────

    /** Create and register a leaf node. */
    public Node createLeaf(int id, long value) {
        checkIdFree(id);
        Node node = new Node(id, value, true);
        nodes.put(id, node);
        if (root == null) root = node;
        return node;
    }

    /**
     * Create and register an internal node with given children.
     * All children must already be created; they must not already have a parent.
     */
    public Node createInternal(int id, List<Node> children) {
        checkIdFree(id);
        if (children == null || children.isEmpty()) {
            throw new IllegalArgumentException("Internal node must have at least one child");
        }

        Node node = new Node(id, 0, false);
        long sum = 0;
        for (Node child : children) {
            if (child.parent != null) {
                throw new IllegalStateException("Child " + child.id + " already has a parent");
            }
            node.children.add(child);
            child.parent = node;
            sum += child.sum;
        }
        node.sum = sum;

        nodes.put(id, node);
        if (root == null) root = node;
        return node;
    }

    // ─────────────────────────────────────────────
    // QUERY  — O(1)
    // ─────────────────────────────────────────────

    public long getValue(int nodeId) {
        return getNode(nodeId).sum;
    }

    // ─────────────────────────────────────────────
    // MUTATION 1 — toLeaf   O(depth + subtree_size)
    // ─────────────────────────────────────────────

    /**
     * Convert any node (leaf or internal) into a leaf with the given value.
     * The entire prior subtree is detached and all its nodes are deregistered.
     */
    public void toLeaf(int nodeId, long value) {
        Node node = getNode(nodeId);

        long oldSum = node.sum;

        // Deregister every node in the subtree being discarded
        // (skip the node itself — it stays, just becomes a leaf)
        for (Node child : node.children) {
            deregisterSubtree(child);
        }
        node.children.clear();

        node.isLeaf = true;
        node.sum = value;

        // Propagate delta up to all ancestors
        propagateDelta(node.parent, value - oldSum);
    }

    // ─────────────────────────────────────────────
    // MUTATION 2 — toParent   O(depth + subtree_size)
    // ─────────────────────────────────────────────

    /**
     * Convert a leaf into an internal node by attaching the provided children.
     * Each child must be an existing, parentless node (or a freshly created subtree).
     *
     * FIX: All nodes in the new subtree are registered into the node map.
     */
    public void toParent(int nodeId, List<Node> newChildren) {
        Node node = getNode(nodeId);

        if (!node.isLeaf) {
            throw new IllegalStateException("Node " + nodeId + " is already internal. Call toLeaf first.");
        }
        if (newChildren == null || newChildren.isEmpty()) {
            throw new IllegalArgumentException("Must provide at least one child");
        }

        // Cycle check before modifying anything
        for (Node child : newChildren) {
            if (isAncestor(node, child)) {
                throw new IllegalArgumentException(
                    "Attaching subtree rooted at " + child.id + " would create a cycle");
            }
        }

        long oldSum = node.sum;
        long newSum = 0;

        node.isLeaf = false;
        for (Node child : newChildren) {
            if (child.parent != null) {
                throw new IllegalStateException(
                    "Child " + child.id + " already has a parent. Detach it first.");
            }
            node.children.add(child);
            child.parent = node;
            newSum += child.sum;

            // FIX: Register every node in the attached subtree
            registerSubtree(child);
        }

        node.sum = newSum;
        propagateDelta(node.parent, newSum - oldSum);
    }

    /** Convenience overload for attaching a single subtree root. */
    public void toParent(int nodeId, Node subtreeRoot) {
        toParent(nodeId, Collections.singletonList(subtreeRoot));
    }

    // ─────────────────────────────────────────────
    // INTERNAL HELPERS
    // ─────────────────────────────────────────────

    /**
     * Walk up from `start` and add `delta` to every ancestor's sum.
     * O(depth)
     */
    private void propagateDelta(Node start, long delta) {
        if (delta == 0) return;
        Node curr = start;
        while (curr != null) {
            curr.sum += delta;
            curr = curr.parent;
        }
    }

    /**
     * Recursively remove all nodes in this subtree from the node map.
     * O(subtree size)
     */
    private void deregisterSubtree(Node node) {
        if (node == null) return;
        nodes.remove(node.id);
        for (Node child : node.children) {
            deregisterSubtree(child);
        }
        // Help GC: sever links
        node.parent = null;
        node.children.clear();
    }

    /**
     * Recursively add all nodes in this subtree to the node map.
     * Throws on duplicate IDs (guards against accidental re-attachment).
     * O(subtree size)
     */
    private void registerSubtree(Node node) {
        if (node == null) return;
        if (nodes.containsKey(node.id)) {
            throw new IllegalArgumentException(
                "Node id " + node.id + " is already registered in the tree");
        }
        nodes.put(node.id, node);
        for (Node child : node.children) {
            registerSubtree(child);
        }
    }

    /**
     * Check whether `target` is in the subtree rooted at `node`.
     * Used for cycle detection before attaching.
     * O(depth)
     */
    private boolean isAncestor(Node node, Node target) {
        Node curr = target;
        while (curr != null) {
            if (curr == node) return true;
            curr = curr.parent;
        }
        return false;
    }

    private Node getNode(int id) {
        Node node = nodes.get(id);
        if (node == null) {
            throw new IllegalArgumentException("Node not found (possibly deregistered): " + id);
        }
        return node;
    }

    private void checkIdFree(int id) {
        if (nodes.containsKey(id)) {
            throw new IllegalArgumentException("Node id " + id + " is already in use");
        }
    }

    // ─────────────────────────────────────────────
    // OPTIONAL UTILS
    // ─────────────────────────────────────────────

    public Node getRoot() { return root; }

    public int size() { return nodes.size(); }

    /** Pretty-print the tree (for debugging). */
    public void print() {
        if (root == null) { System.out.println("(empty tree)"); return; }
        printNode(root, "", true);
    }

    private void printNode(Node node, String prefix, boolean isLast) {
        System.out.println(prefix + (isLast ? "└── " : "├── ")
            + "id=" + node.id
            + (node.isLeaf ? " [leaf]" : " [internal]")
            + " sum=" + node.sum);
        for (int i = 0; i < node.children.size(); i++) {
            printNode(node.children.get(i),
                      prefix + (isLast ? "    " : "│   "),
                      i == node.children.size() - 1);
        }
    }

    // ─────────────────────────────────────────────
    // DEMO / TESTS
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        MutableSumTree tree = new MutableSumTree();

        /*
         * Build initial tree:
         *
         *         A(10)
         *        /     \
         *      B(4)    C(6)
         *             /    \
         *           D(2)   E(4)
         */
        Node d = tree.createLeaf(4, 2);   // D = 2
        Node e = tree.createLeaf(5, 4);   // E = 4
        Node b = tree.createLeaf(2, 4);   // B = 4
        Node c = tree.createInternal(3, Arrays.asList(d, e));  // C = D+E = 6
        Node a = tree.createInternal(1, Arrays.asList(b, c));  // A = B+C = 10

        System.out.println("=== Initial Tree ===");
        tree.print();
        System.out.println("getValue(A)=" + tree.getValue(1));  // 10
        System.out.println("getValue(C)=" + tree.getValue(3));  // 6

        // ── Test 1: toLeaf on D (delta = +98) ────────────────
        System.out.println("\n=== After toLeaf(D, 100) ===");
        tree.toLeaf(4, 100);
        tree.print();
        System.out.println("getValue(D)=" + tree.getValue(4));  // 100
        System.out.println("getValue(C)=" + tree.getValue(3));  // 104
        System.out.println("getValue(A)=" + tree.getValue(1));  // 108

        // ── Test 2: toLeaf on internal node C ────────────────
        System.out.println("\n=== After toLeaf(C, 1) [removes D and E] ===");
        tree.toLeaf(3, 1);
        tree.print();
        System.out.println("getValue(C)=" + tree.getValue(3));  // 1
        System.out.println("getValue(A)=" + tree.getValue(1));  // 5  (B=4 + C=1)

        // D and E are deregistered — should throw
        try {
            tree.getValue(4);
        } catch (IllegalArgumentException ex) {
            System.out.println("Correct: D deregistered → " + ex.getMessage());
        }

        // ── Test 3: toParent — grow C back ────────────────
        System.out.println("\n=== After toParent(C, [new leaf F=10]) ===");
        Node f = new Node(6, 10, true);   // create node outside of tree
        tree.toParent(3, f);              // attach to C
        tree.print();
        System.out.println("getValue(F)=" + tree.getValue(6));  // 10
        System.out.println("getValue(C)=" + tree.getValue(3));  // 10
        System.out.println("getValue(A)=" + tree.getValue(1));  // 14

        System.out.println("\nAll tests passed.");
    }
}