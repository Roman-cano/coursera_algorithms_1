import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randomQueue;
    private int len = 0;
    private int size;

    public RandomizedQueue() {
        randomQueue = (Item[]) new Object[10];
        len = randomQueue.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item ne peut pas être null");
        }
        if (size == len) {
            resize(len * 2);
        }
        randomQueue[size] = item;
        size++;
    }

    private void resize(int capacite) {
        Item[] copy = (Item[]) new Object[capacite];
        for (int i = 0; i < size; i++) {
            copy[i] = randomQueue[i];
        }
        randomQueue = copy;
        len = capacite;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("le tableau est vide");
        }
        if (size > 0 && size == len / 4) {
            resize(len / 2);
        }

        int index = StdRandom.uniformInt(size);
        Item save = randomQueue[index];
        randomQueue[index] = randomQueue[size - 1];
        randomQueue[size - 1] = null;
        size--;
        return save;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("le tableau est vide");
        }
        return randomQueue[StdRandom.uniformInt(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int i = 0;
        private final Item[] randomItem;

        private RandomIterator() {
            randomItem = (Item[]) new Object[size];
            System.arraycopy(randomQueue, 0, randomItem, 0, size);

            for (int j = 0; j < size; j++) {
                int rand = StdRandom.uniformInt(j + 1);
                Item temp = randomItem[j];
                randomItem[j] = randomItem[rand];
                randomItem[rand] = temp;
            }
        }

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return randomItem[i++];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(5);
        queue.enqueue(6);

        for (int elt : queue) {
            System.out.println(elt);
        }

        queue.dequeue();
        System.out.println(" ");

        for (int elt : queue) {
            System.out.println(elt);
        }
    }
}
