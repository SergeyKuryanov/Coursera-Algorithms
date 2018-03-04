import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] storage;
    private int size = 0;

    public RandomizedQueue() {
        storage = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        storage[size] = item;
        size++;
        resizeIfNeed();
    }

    public Item dequeue() {
        if (size == 0) { throw new NoSuchElementException(); }

        int index = StdRandom.uniform(size);
        Item item = storage[index];
        storage[index] = storage[--size];

        resizeIfNeed();
        return item;
    }   

    public Item sample() {
        if (size == 0) { throw new NoSuchElementException(); }

        return storage[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indices = new int[size];
        private int currentIndex = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < size; i++) {
                indices[i] = i;
            }

            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {  return currentIndex < indices.length;  }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            return storage[indices[currentIndex++]];
        }
    }
    
    private void resizeIfNeed() {
        if (size == storage.length) {
            resize(storage.length * 2);
        } else if (size <= storage.length / 4) {
            resize(storage.length / 2);
        }
    }

    private void resize(int newSize) {
        Item[] newStorage = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newStorage[i] = storage[i];
        }
        storage = newStorage;
    }
}