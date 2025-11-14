package com.saqib.puzzles;

import java.util.ArrayDeque;
import java.util.Queue;
import com.saqib.puzzles.BinaryTree.*;

/**
 * Prints a binary tree level by level, placing each level on its own line.
 *
 * <p>We use a queue for each level, remembering how many
 * nodes are currently in the queue (that’s the whole level). We print those and
 * enqueue their children for the next line.</p>
 *
 * <p>Example tree:
 * <pre>
 *                        (1)
 *                       /   \
 *                    (2)     (3)
 *                    /      /   \
 *                 (4)     (5)   (6)
 *                              /   \
 *                            (8)   (7)
 * </pre>
 *
 * Expected output:
 * <pre>
 * 1
 * 23
 * 456
 * 87
 * </pre>
 * </p>
 *
 * @param <T> value type stored in each node
 * @author Saqib
 */
final class BinaryTree<T> {

	static final class Node<T> {
	    private T data;
	    private Node<T> left, right;

	    Node(T data) { this.data = data; }
	    Node(T data, Node<T> left, Node<T> right) {
	        this.data = data; this.left = left; this.right = right;
	    }

	    public T getData() { return data; }
	    public Node<T> getLeftNode() { return left; }
	    public Node<T> getRightNode() { return right; }

	    public void setLeftNode(Node<T> left)  { this.left = left; }
	    public void setRightNode(Node<T> right){ this.right = right; }
	}


    private final Node<T> root;

    BinaryTree(Node<T> root) {
        this.root = root;
    }

    /** Print the tree level by level; nodes on the same level are printed contiguously (no spaces). */
    public void printLevels() {
        if (root == null) {
            System.out.println();
            return;
        }

        Queue<Node<T>> q = new ArrayDeque<>();
        q.add(root); // start with just the root; that's level 0

        while (!q.isEmpty()) {
            // At this moment, the queue holds exactly the nodes for THIS level.
            // Capture that count so we only process this level's nodes in this pass.
            int levelSize = q.size();

            for (int i = 0; i < levelSize; i++) {
                Node<T> cur = q.remove();

                // Print the node's value for the current level (no spaces per your format).
                System.out.print(cur.data);

                // IMPORTANT: cur.left and cur.right are DOWNWARD child links,
                // not "left/right neighbors" on the same level.
                // Enqueuing them schedules the NEXT level to be printed
                // in the next outer-while iteration.
                // Note they won't be printed during this iteration as the levelSize has already been set
                // and so when we reach it we will terminate the for loop and return to the while loop.
                // At that point the queue will contain all the nodes for the next level and we can set the new size and go again.
                if (cur.left  != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }

            // End of this level → move to a new line for the next level.
            System.out.println();
        }
    }

}

/** Entry point kept as a public class named BreadthFirstTraversal. */
public class BreadthFirstTraversal {

    public static void main(String[] args) {
    	
            Node<Integer> n1 = new Node<>(1);
            Node<Integer> n2 = new Node<>(2);
            Node<Integer> n3 = new Node<>(3);
            Node<Integer> n4 = new Node<>(4);
            Node<Integer> n5 = new Node<>(5);
            Node<Integer> n6 = new Node<>(6);
            Node<Integer> n8 = new Node<>(8);
            Node<Integer> n7 = new Node<>(7);

            n1.setLeftNode(n2);  n1.setRightNode(n3);
            n2.setLeftNode(n4);
            n3.setLeftNode(n5);  n3.setRightNode(n6);
            n6.setLeftNode(n8);  n6.setRightNode(n7);

            BinaryTree<Integer> tree = new BinaryTree<>(n1);
        
            tree.printLevels();
	        // Expected:
	        // 1
	        // 23
	        // 456
	        // 87
    }
    
}
