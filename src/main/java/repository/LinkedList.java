package repository;

public class LinkedList<E> {

    public static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public Node<E> getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        return isEmpty() ? null : head.getData();
    }

    public E last() {
        return isEmpty() ? null : tail.getData();
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("\n[INFO] No data available to display.\n");
            return;
        }
        Node<E> current = head;
        while (current != null) {
            System.out.println(" " + current.getData());
            current = current.getNext();
        }
    }


    public void findNode(E element) {
        Node<E> current = head;
        int position = 0;

        while (current != null) {
            if (current.getData().equals(element)) {
                System.out.println("Found at position: " + position);
                return;
            }
            position++;
            current = current.getNext();
        }
        System.out.println("Element not found.");
    }

    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element, head);
        head = newNode;
        if (size == 0) tail = head;
        size++;
    }

    public void addLast(E element) {
        if (isEmpty()) {
            addFirst(element);
            return;
        }

        Node<E> newNode = new Node<>(element, null);
        tail.setNext(newNode);
        tail = newNode;
        size++;
    }

    public void addNode(E element, int position) {
        if (position <= 1 || isEmpty()) {
            addFirst(element);
            return;
        }

        Node<E> current = head;
        for (int i = 1; i < position - 1 && current.getNext() != null; i++) {
            current = current.getNext();
        }

        Node<E> newNode = new Node<>(element, current.getNext());
        current.setNext(newNode);
        if (newNode.getNext() == null) tail = newNode;
        size++;
    }

    public void removeFirst() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return;
        }

        head = head.getNext();
        if (head == null) tail = null;
        size--;
        System.out.println("First node deleted");
    }

    public void deleteLast() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return;
        }

        if (size == 1) {
            head = tail = null;
        } else {
            Node<E> current = head;
            while (current.getNext() != tail) {
                current = current.getNext();
            }
            current.setNext(null);
            tail = current;
        }

        size--;
        System.out.println("Last node deleted");
    }

    public void removeNode(E element) {
        if (isEmpty()) {
            System.out.println("List is empty");
            return;
        }

        if (head.getData().equals(element)) {
            removeFirst();
            return;
        }

        Node<E> current = head;
        Node<E> previous = null;

        while (current != null && !current.getData().equals(element)) {
            previous = current;
            current = current.getNext();
        }

        if (current == null) {
            System.out.println("Value doesn't exist");
            return;
        }

        previous.setNext(current.getNext());
        if (current == tail) tail = previous;

        size--;
        System.out.println("Node was deleted");
    }
}
