package repository;

public class MyArrayList<E> {

    public static final int CAPACITY = 16;
    private E[] data;
    private int size = 0;

    // Default Constructor
    public MyArrayList() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        this.data = (E[]) new Object[capacity];
    }

    // public methods
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // returns element at index i. Without removing
    public E get(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        return data[i];
    }

    // Replaces element at index i with e, and returns the replaced element
    public E set(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];
        data[i] = e;
        return temp;
    }

    // Adds element e at index i, by shifting elements to the left
    public void add(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size + 1);
        if (size == data.length) {
            resize(2 * data.length);
        }

        for (int k = size - 1; k >= i; k--) {
            data[k + 1] = data[k];
        }

        data[i] = e;
        size++;
    }

    // removes element at index i, by shifting elements to the left
    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];

        for (int k = i; k < size - 1; k++) {
            data[k] = data[k + 1];
        }

        data[size - 1] = null;
        size--;
        return temp;
    }

    // utility methods
    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Illegal index: " + i);
        }
    }

    // Resize with a new capacity
    @SuppressWarnings("unchecked")
    protected void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        for (int k = 0; k < size; k++) {
            temp[k] = data[k];
        }
        this.data = temp;
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < size(); i++) {
            System.out.print(data[i] + " ");
        }
    }
}
