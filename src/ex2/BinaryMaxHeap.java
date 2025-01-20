package ex2;
/*
 * Comments in this file will be less than the BinaryMinHeap.java
 * Because the only difference between this MaxHeap implementation and MinHeap
 * implementation is all the ">" or "<" in compareTo() "?" 0 are reversed to ">" or "<"
 * Other code are all the same
 */

import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMaxHeap<T extends Comparable<T>> implements MyPriorityQueue<T> {
    private int size; // Maintains the size of the data structure
    private T[] arr; // The array containing all items in the data structure
    // index 0 must be utilized
    private Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    public BinaryMaxHeap() {
        // This line just creates an array of type T. We're doing it this way just
        // because of weird java generics stuff (that I frankly don't totally understand)
        // If you want to create a new array anywhere else (e.g. to resize) then
        // You should mimic this line. The second argument is the size of the new array.
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    /**
     * O(log n) because only swap with parent
     *
     * @param i
     */
    // move the item at index i "rootward" until
    // the heap property holds
    private void percolateUp(int i) {
        T item = arr[i];
        while (i > 0 && item.compareTo(arr[(i - 1) / 2]) > 0) {
            arr[i] = arr[(i - 1) / 2];
            arr[(i - 1) / 2] = item;
            itemToIndex.put(item, (i - 1) / 2);
            itemToIndex.put(arr[i], i);
            i = (i - 1) / 2;
        }
    }

    /**
     * O(log n) because only swap with children
     *
     * @param i
     */
    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i) {

        while ((i + 1) * 2 <= size) {
            int left = i * 2 + 1;
            int right = (i + 1) * 2;
            int target = left;

            if (right < size && arr[left].compareTo(arr[right]) < 0) {
                target = right;
            }

            // target swap
            if (arr[target].compareTo(arr[i]) > 0) {

                T item = arr[i];
                arr[i] = arr[target];
                arr[target] = item;

                itemToIndex.put(item, target);
                itemToIndex.put(arr[i], i);

                i = target;
            } else {
                break;
            }
        }
    }

    /**
     * O(n) because copy array
     */
    // copy all items into a larger array to make more room.
    private void resize() {
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length * 2);
        for (int i = 0; i < size; i++) {
            larger[i] = arr[i];
        }
        arr = larger;
    }

    /**
     * O(log n) because percolateUp is O(log n)
     *
     * @param item
     */
    public void insert(T item) {
        if (size == arr.length - 1) {
            resize();
        }
        arr[size] = item;
        itemToIndex.put(item, size);
        percolateUp(size);
        size++;
    }

    /**
     * O(log n) because percolateDown is O(log n)
     *
     * @return
     */
    public T extract() {
        if (isEmpty()) {
            throw new IllegalStateException("Nothing in the heap!");
        }

        T item = arr[0];
        arr[0] = arr[size - 1];
        arr[size - 1] = null;
        size--;
        percolateDown(0);
        itemToIndex.remove(item);
        return item;
    }

    /**
     * O(log n) because of percolateDown
     *
     * @param index
     * @return
     */
    // Remove the item at the given index.
    // Make sure to maintain the heap property!
    private T remove(int index) {
        T item = arr[index];
        arr[index] = arr[size - 1];
        arr[size - 1] = null;
        size--;
        percolateDown(index);
        itemToIndex.remove(item);
        return item;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void remove(T item) {
        if (!itemToIndex.containsKey(item)) {
            throw new IllegalArgumentException("Item not found in the heap!");
        }
        remove(itemToIndex.get(item));
    }

    /**
     * O(log n) because either call the percolateUp or percolateDown
     *
     * @param index
     */
    // Determine whether to percolate up/down
    // the item at the given index, then do it!
    private void updatePriority(int index) {
        int parent = (index - 1) / 2;
        if (parent >= 0 && arr[index].compareTo(arr[parent]) > 0) {
            percolateUp(index);
            return;
        }

        int left = index * 2 + 1;
        int right = (index + 1) * 2;
        if (left < size) {
            if ((right < size && arr[index].compareTo(arr[right]) < 0) || arr[index].compareTo(arr[left]) < 0) {
                percolateDown(index);
            }
        }
    }

    // This method gets called after the client has
    // changed an item in a way that may change its
    // priority. In this case, the client should call
    // updatePriority on that changed item so that
    // the heap can restore the heap property.
    // Throws an IllegalArgumentException if the given
    // item is not an element of the priority queue.
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void updatePriority(T item) {
        if (!itemToIndex.containsKey(item)) {
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
        }
        updatePriority(itemToIndex.get(item));
    }


    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public boolean isEmpty() {
        return size == 0;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public int size() {
        return size;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Nothing in the Heap!s");
        }
        return arr[0];
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public List<T> toList() {
        List<T> copy = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            copy.add(i, arr[i]);
        }
        return copy;
    }

    // For debugging
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String str = "[(" + arr[0] + " " + itemToIndex.get(arr[0]) + ")";
        for (int i = 1; i < size; i++) {
            str += ",(" + arr[i] + " " + itemToIndex.get(arr[i]) + ")";
        }
        return str + "]";
    }


}
