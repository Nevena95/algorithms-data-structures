package binarytree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST {
    private static class Node {
        int value;
        Node left_child;
        Node right_child;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root = null;

    public void addIterative(int val) {
        Node node = new Node(val);

        if (root == null) {
            root = node;
            return;
        }

        Node current = root;

        while (true) {
            if (current.value == val) {
                throw new RuntimeException("Already exists " + val);
            }

            if (val < current.value) {                  // go to left subtree
                if (current.left_child == null) {
                    current.left_child = node;
                    return;
                }

                current = current.left_child;
            } else {                                    // go to right subtree
                if (current.right_child == null) {
                    current.right_child = node;
                    return;
                }

                current = current.right_child;
            }
        }
    }

    // left -> root -> right
    // usage: print nodes in ascending order
    public void inorder() {
        Stack<Node> stack = new Stack<>();      // Deque
        Node current = root;

        while (current != null || !stack.empty()) {

            while (current != null) {
                stack.push(current);
                current = current.left_child;
            }

            current = stack.pop();
            visit(current);

            current = current.right_child;      // ako je null, preskocice while petlju i pop-ovace sledeceg roditelja
        }
    }

    // root -> left -> right
    // usages: prefix, copy tree
    public void preorder() {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            Node node = stack.pop();
            visit(node);

            if (node.right_child != null) {
                stack.push(node.right_child);
            }

            if (node.left_child != null) {
                stack.push(node.left_child);
            }
        }
    }

    // left -> right -> root
    // usages: postfix, delete tree
    public void postorder() {
        // brutalno resenje https://leetcode.com/problems/binary-tree-postorder-traversal/solutions/45648/3-ways-of-iterative-postorder-traversing-morris-traversal/

        postorder_iterative(root);
    }

    public void levelorder() {          // BFS
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            visit(node);

            if (node.left_child != null) {
                queue.offer(node.left_child);
            }

            if (node.right_child != null) {
                queue.offer(node.right_child);
            }
        }
    }

    private void postorder_iterative(Node node) {
        if (node == null) {
            return;
        }

        postorder_iterative(node.left_child);
        postorder_iterative(node.right_child);
        visit(node);
    }

    private void visit(Node current) {
        System.out.print(current.value + " ");
    }

    public static void main(String[] args) {
        BST bst = new BST();

        bst.addIterative(10);
        bst.addIterative(6);
        bst.addIterative(2);
        bst.addIterative(12);
        bst.addIterative(3);
        bst.addIterative(8);
        bst.addIterative(13);

        System.out.print("Inorder: ");
        bst.inorder();
        System.out.print("\nPreorder: ");
        bst.preorder();
        System.out.print("\nPostorder: ");
        bst.postorder();
        System.out.print("\nLevelorder: ");
        bst.levelorder();
    }
}
