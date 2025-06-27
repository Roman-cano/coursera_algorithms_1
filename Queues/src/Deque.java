import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Node next;
        private Item data;

        private Node(Item data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    private Node searchPrevNode(Node searched) {
        Node node = head;
        if (head == searched) {
            return null;
        }
        while (node.next != searched) {
            node = node.next;
        }
        return node;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item ne peut pas être null");
        }
        Node node = new Node(item);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item ne peut pas être null");
        }
        Node node = new Node(item);
        if (isEmpty()) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque vide");
        }
        Node save = head;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
        }
        size--;
        return save.data;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque vide");
        }
        Node save = tail;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            Node prevLast = searchPrevNode(tail);
            tail = prevLast;
            if (prevLast != null) {
                prevLast.next = null;
            }
        }
        size--;
        return save.data;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = current.data;
                current = current.next;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException(
                        "Vous ne pouvez pas supprimer de donnée par l'iterator. Utilisez la file d'attente."
                );
            }
        };
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);

        System.out.println("Contenu deque :");
        for (int i : deque) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("removeFirst() : " + deque.removeFirst());
        System.out.println("removeLast() : " + deque.removeLast());

        System.out.println("Contenu deque après suppressions :");
        for (int i : deque) {
            System.out.print(i + " ");
        }
    }
}
