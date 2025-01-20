package ex2;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMinHeap<T extends Comparable<T>> implements MyPriorityQueue<T> {
    private int size; // Maintains the size of the data structure
    private T[] arr; // The array containing all items in the data structure
    // index 0 must be utilized
    private Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    public BinaryMinHeap() {
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
     * @param i
     */
    // move the item at index i "rootward" until
    // the heap property holds
    private void percolateUp(int i) {
        T item = arr[i];  // Grab this item
        //  When i is still not at the root and arr[i] is still smaller than it's parent then swap
        while (i > 0 && item.compareTo(arr[(i - 1) / 2]) < 0) {
            arr[i] = arr[(i - 1) / 2];
            arr[(i - 1) / 2] = item;
            // Update Map
            itemToIndex.put(item, (i - 1) / 2);
            itemToIndex.put(arr[i], i);
            // Update i
            i = (i - 1) / 2;
        }
    }

    /**
     *O(log n) because only swap with children
     * @param i
     */
    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i) {

        // guaranteed to have left child
        while ((i + 1) * 2 <= size) {
            int left = i * 2 + 1;
            int right = (i + 1) * 2;
            int target = left;  // swap with left anyway, since left child is guaranteed

            // if there is right child and right child is smaller than left,
            // then swap with right instead of left
            if (right < size && arr[left].compareTo(arr[right]) > 0) {
                target = right;
            }

            // swap with target if target is indeed smaller than arr[i]
            if (arr[target].compareTo(arr[i]) < 0) {

                T item = arr[i];
                arr[i] = arr[target];
                arr[target] = item;

                // update map
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
        if (size >= 0) System.arraycopy(arr, 0, larger, 0, size);  // copy array
        arr = larger;
    }

    /**
     * O(log n) because percolateUp is O(log n)
     * @param item
     */
    public void insert(T item) {
        // resize if array full
        if (size == arr.length - 1) {
            resize();
        }
        // insert item at the end of heap
        arr[size] = item;
        itemToIndex.put(item, size);  // record its index in the map
        // maintain heap
        percolateUp(size);
        size++;  // update heap
    }

    /**
     * O(log n) because percolateDown is O(log n)
     * @return
     */
    public T extract() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Nothing in the heap!");
        }

        T item = arr[0];
        // swap with the last leaf and mark the last leaf as null
        arr[0] = arr[size - 1];
        arr[size - 1] = null;
        size--;
        // maintain heap
        percolateDown(0);
        // update map
        itemToIndex.remove(item);
        return item;
    }

    /**
     * O(log n) because of percolateDown
     * @param index
     * @return
     */
    // Remove the item at the given index.
    // Make sure to maintain the heap property!
    private T remove(int index) {
        T item = arr[index];
        // swap with the last leaf and mark the last leaf as null
        arr[index] = arr[size - 1];
        arr[size - 1] = null;
        size--;
        // maintain heap
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
     * @param index
     */
    // Determine whether to percolate up/down
    // the item at the given index, then do it!
    private void updatePriority(int index) {
        int parent = (index - 1) / 2;
        // if item has parent and parent is bigger
        if (parent >= 0 && arr[index].compareTo(arr[parent]) < 0) {
            percolateUp(index);
            return;
        }

        // if there is left or also right child, then call percolateDown,
        //  percolateDown will decide which child to swap with
        int left = index * 2 + 1;
        int right = (index + 1) * 2;
        if (left < size) {
            if ((right < size && arr[index].compareTo(arr[right]) > 0) || arr[index].compareTo(arr[left]) > 0) {
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
        return arr[0];
    }

    /**
     * O(n) because of copy array
     * @return
     */
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
