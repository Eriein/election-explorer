package repository;

public class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Node class for the Doubly Linked List.
     */
    public static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = this.prev = null;
        }

        // --- Getters and Setters ---
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
        public Node<T> getNext() { return next; }
        public void setNext(Node<T> next) { this.next = next; }
        public Node<T> getPrev() { return prev; }
        public void setPrev(Node<T> prev) { this.prev = prev; }
    }

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    // ------------------------------------
    // ## Access Methods
    // ------------------------------------
    public Node<T> getTail() {
        return tail;
    }
    public Node<T> getHead() {
        return head;
    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T first() {
        if (size == 0) return null;
        return head.getData();
    }

    public T last() {
        if (size == 0) return null;
        return tail.getData();
    }

    // ------------------------------------
    // ## Display and Search Methods
    // ------------------------------------

    public void displayForward() {
        if (size == 0) {
            System.out.println("List is empty");
            return;
        }
        Node<T> current = head;

        while (current != null) {
            System.out.println(current.getData());
            current = current.next;
        }
        System.out.println();
    }

    public void displayBackward() {
        if (size == 0) {
            System.out.println("List is empty");
            return;
        }
        Node<T> current = tail;

        while (current != null) {
            System.out.println(current.getData());
            current = current.prev;
        }
        System.out.println();
    }

    public Node<T> findNode(T data) {
        if (size == 0) {
            System.out.println("List is empty");
            return null;
        }

        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(data)) {
                System.out.println("Found node with data: " + current.getData());
                return current;
            }
            current = current.next;
        }

        System.out.println("Node with data: " + data + " does not exist");
        return null;
    }

    // ------------------------------------
    // ## Addition Methods
    // ------------------------------------

    public void addAfter(Node<T> node, T data) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null.");
        }

        Node<T> newNode = new Node<>(data);

        // Special Case: Adding at the end (predecessor is the tail
        if (node == tail) {
            addLast(data); // Reuse existing O(1) method, which updates the 'tail' field
            return;
        }

        // General Case: Adding in the middle
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;

        newNode.next = nextNode;
        newNode.prev = prevNode;

        prevNode.next = newNode;
        nextNode.prev = newNode;
        size++;
    }
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (size == 0) {
            head = newNode;
            tail = head;
        } else {
            newNode.next = head; // New node points to old head
            head.prev = newNode; // Old head points back to new node
            head = newNode; // Update head
        }
        size++;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (size == 0) {
            addFirst(data); // Reuse addFirst logic for single-node case
            return;
        } else {
            newNode.prev = tail; // New node points back to old tail
            tail.next = newNode; // Old tail points forward to new node
            tail = newNode; // Update tail
        }
        size++;
    }

    public void addAtPos(int pos, T data) {
        if (pos <= 1) {
            addFirst(data);
            return;
        }
        if (pos > size) {
            addLast(data);
            return;
        }

        // Find the node before the insertion point
        Node<T> current = head;
        for (int count = 1; count < pos - 1; count++) {
            current = current.next;
        }

        Node<T> newNode = new Node<>(data);
        Node<T> successor = current.next;

        // Link new node
        newNode.next = successor;
        newNode.prev = current;

        // Link surrounding nodes
        current.next = newNode;
        successor.prev = newNode;

        size++;
    }

    // ------------------------------------
    // ## Deletion Methods
    // ------------------------------------

    public Node<T> deleteFirst() {
        if (size == 0) {
            System.out.println("List is empty");
            return null;
        }

        Node<T> nodeToDelete = head;
        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null; // New head points to null
            // Explicitly detach deleted node
            nodeToDelete.next = nodeToDelete.prev = null;
        }
        size--;
        return nodeToDelete;
    }

    /**
     * Delete the last node in O(1) time complexity.
     * This is possible in a Doubly Linked List because the tail has a 'prev' pointer.
     */
    public Node<T> deleteLast() {
        if (size == 0) {
            System.out.println("List is empty");
            return null;
        }
        Node<T> nodeToDelete = tail;
        if (size == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null; // New tail points to null
            nodeToDelete.next = nodeToDelete.prev = null;
        }
        size--;
        return nodeToDelete;
    }

    public Node<T> deleteNode(T data) {
        if (size == 0) {
            System.out.println("List is empty");
            return null;
        }

        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(data)) {
                break;
            }
            current = current.next;
        }

        // Case 1: Node not found
        if (current == null) {
            System.out.println("Value does not exist");
            return null;
        }

        // Case 2: Node is the head
        if (current == head) {
            return deleteFirst();
        }

        // Case 3: Node is the tail
        if (current == tail) {
            return deleteLast();
        }

        // Case 4: Node is in the middle (O(1) deletion)
        current.prev.next = current.next;
        current.next.prev = current.prev;

        // Explicitly detach deleted node
        current.next = current.prev = null;

        size--;
        return current;
    }
}