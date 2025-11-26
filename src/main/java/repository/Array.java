package repository;

import entity.ElectionData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Array<E> {
    private E[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public Array(int capacity) {
        array = (E[]) new Object[capacity];
        size = 0;
    }

    public Array() {
        this(10);
    }

    public boolean isFull() {
        return size == array.length;
    }

    public void add(E data) {
        if (size < array.length) {
            array[size++] = data;
        } else {
            throw new ArrayIndexOutOfBoundsException("Array is Full");
        }
    }

    public int size() {
        return size;
    }

    public E get(int i) {
        return array[i];
    }

    /**
     * Array stores the raw election data
     * @return Array<ElectionData>
     */

    public static Array<ElectionData> loadData() {
        Array<ElectionData> electionData = new Array<>(100);
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader("election_data.csv"))) {
            br.readLine(); // Skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                electionData.add(new ElectionData(
                        Integer.parseInt(values[0]),
                        values[1],
                        values[2],
                        values[3],
                        Long.parseLong(values[4])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return electionData;
    }
}
