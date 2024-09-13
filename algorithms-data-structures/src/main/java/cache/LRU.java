package cache;

import java.util.HashMap;
import java.util.Map;

public class LRU {

    int capacity;
    Node head = null, tail = null;
    Map<Integer, Node> cache = new HashMap<>();

    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    public LRU(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }

        Node node = cache.get(key);
        if (node != head) {
            remove(node);
            addToFront(node);
        }

        return node.val;
    }

    public void put(int key, int value) {

        // UPDATE
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.val = value;

            remove(node);
            addToFront(node);

            return;
        }

        // INSERT
        if (cache.size() == capacity) {
            cache.remove(tail.key);
            remove(tail);
        }

        Node node = new Node(key, value);
        cache.put(key, node);
        addToFront(node);
    }

    private void remove(Node node) {
        if (node == null) {
            return;     // error
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
    }

    private void addToFront(Node node) {
        if (head == null) {
            head = tail = node;
            return;
        }

        node.next = head;
        head.prev = node;
        head = node;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
