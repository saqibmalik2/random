package com.saqib.puzzles;

import java.util.LinkedList;

/**
 * 
 * Print out the contents of a binary tree level by level on separate lines.
 * 
 * E.g. for a tree:
 *                        (1)
 *                       /   \
 *                    (2)     (3)
 *                    / 	 /   \
 *                 (4)     (5)   (6)
 *                              /   \
 *                            (8)   (7)
 *                            
 * The output should be:
 * 
 * 1
 * 23
 * 456
 * 87
 * 
 * @param <T>
 */


class Node<T> {
	
	T Data;
	Node<T> leftNode;
	Node<T> rightNode;
	 
   Node(T data, Node<T> leftNode, Node<T> rightNode) {
		super();
		Data = data;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	public T getData() {
		return Data;
	}
	public void setData(T data) {
		Data = data;
	}
	public Node<T> getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(Node<T> leftNode) {
		this.leftNode = leftNode;
	}
	public Node<T> getRightNode() {
		return rightNode;
	}
	public void setRightNode(Node<T> rightNode) {
		this.rightNode = rightNode;
	}
	
}

class BinaryTree<T> {
	 
	 Node<T> rootNode;
	 
	 BinaryTree(Node<T> rNode){
		 rootNode = rNode;
	 }
	 
	 public void breadthFirstTraversal() {
		 
		 // the LinkedList will hold the nodes on the current level
		 LinkedList<Node<T>> nodeQueue = new LinkedList<>();
		 
		 //add the root node to the queue
		 nodeQueue.add(rootNode);
		 
		 //when the queue is empty it means we have printed off all the nodes in the tree
		 while (!nodeQueue.isEmpty()){
			 //We will be adding nodes from the next level as we print off the ones on the current level,
			 //therefore we need some way of making sure on each pass we only print the current level's nodes.
			 //We do this by making a note of the number of nodes in the queue before we start the iteration
			 //any nodes added wont be printed off until a new line has started for the next level.
			 int currentSize = nodeQueue.size();
			 //the for loop will print off all the nodes in the current level removing them from the queue as
			 //it does so and appending their child nodes in the process. These child nodes wont be processed until
			 //the next iteration.
			 for (int i=0; i<currentSize; i++) {
				 Node<T> currentNode = nodeQueue.removeFirst();
				 System.out.print(currentNode.getData());
				 if (currentNode.getLeftNode() != null) {
					 nodeQueue.add(currentNode.getLeftNode());
				 }
				 if (currentNode.getRightNode() != null) {
					 nodeQueue.add(currentNode.getRightNode());	 
				 }
			 }
			 //start a new line as we're moving to the next level of the tree
			 System.out.println();
		 }
		 
	 }
	 
}

public class BreadthFirstTraversal {
	
	 public static void main(String[] args) {
		 Node<Integer> rootNode = new Node<>(1,null,null);
		 Node<Integer> node1 = new Node<>(2,null,null);
		 Node<Integer> node2 = new Node<>(3,null,null);
		 Node<Integer> node3 = new Node<>(4,null,null);
		 Node<Integer> node4 = new Node<>(5,null,null);
		 Node<Integer> node5 = new Node<>(6,null,null);
		 Node<Integer> node6 = new Node<>(8,null,null);
		 Node<Integer> node7 = new Node<>(7,null,null);
		 
		 rootNode.setLeftNode(node1);
		 rootNode.setRightNode(node2);
		 node1.setLeftNode(node3);
		 node2.setLeftNode(node4);
		 node2.setRightNode(node5);
		 node5.setLeftNode(node6);
		 node5.setRightNode(node7);
		 
		 BinaryTree<Integer> bTree = new BinaryTree<Integer>(rootNode);
		 bTree.breadthFirstTraversal();
		 
	 }
 
}
