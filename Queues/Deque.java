import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size = 0;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    public Deque() { }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        Node node = new Node();
        node.item = item;

        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.prev = head;
            head.next = node;
            head = node;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        Node node = new Node();
        node.item = item;

        if (tail == null) {
            tail = node;
            head = node;   
        } else {
            node.next = tail;
            tail.prev = node;
            tail = node;
        }

        size++;
    }

    public Item removeFirst() {
        if (size == 0) { throw new NoSuchElementException(); }
        
        Item item = head.item;

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.prev;
            head.next = null;

            if (size == 2) {
                tail = head;
            }
        }

        size--;
        return item;
    }

    public Item removeLast() {
        if (size == 0) { throw new NoSuchElementException(); }
        
        Item item = tail.item;

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.next;
            tail.prev = null;

            if (size == 2) {
                head = tail;
            }
        }

        size--;
        return item;
    }

    public Iterator<Item> iterator() { return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {  return current != null;  }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }

            Item item = current.item;
            current = current.prev;
            return item;
        }
    }
}