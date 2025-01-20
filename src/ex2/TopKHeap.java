package ex2;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class TopKHeap<T extends Comparable<T>> {
    private BinaryMinHeap<T> topK; // Holds the top k items
    private BinaryMaxHeap<T> rest; // Holds all items other than the top k
    private int size; // Maintains the size of the data structure
    private final int k; // The value of k
    private Map<T, MyPriorityQueue<T>> itemToHeap; // Keeps track of which heap contains each item.

    // Creates a topKHeap for the given choice of k.
    public TopKHeap(int k) {
        topK = new BinaryMinHeap<>();
        rest = new BinaryMaxHeap<>();
        size = 0;
        this.k = k;
        itemToHeap = new HashMap<>();
    }

    /**
     * O(n) because of toList()
     * @return
     */
    // Returns a list containing exactly the
    // largest k items. The list is not necessarily
    // sorted. If the size is less than or equal to
    // k then the list will contain all items.
    // The running time of this method should be O(k).
    public List<T> topK() {
        List<T> list = topK.toList();
        return list;
    }

    /**
     * O(log n + log k) because rest.insert and topK.extract & insert
     * @param item
     */
    // Add the given item into the data structure.
    // The running time of this method should be O(log(n)+log(k)).
    public void insert(T item) {
        if (topK.size() < k) {
            topK.insert(item);
            itemToHeap.put(item,topK);
        } else {
            T topKMin = topK.peek();
            if (item.compareTo(topKMin) > 0) {
                // Replace topKMin with the new item
                topK.extract();
                rest.insert(topKMin);
                itemToHeap.put(topKMin, rest);

                topK.insert(item);
                itemToHeap.put(item, topK);
            } else {
                // Insert directly into rest if it doesn't affect the boundary
                rest.insert(item);
                itemToHeap.put(item, rest);
            }
        }
        size++;
    }

    // Indicates whether the given item is among the 
    // top k items. Should return false if the item
    // is not present in the data structure at all.
    // The running time of this method should be O(1).
    // We have provided a suggested implementation,
    // but you're welcome to do something different!
    public boolean isTopK(T item) {
        return itemToHeap.get(item) == topK;
    }

    /**
     * O(log n + log k) because of rest.insert() & extract(), topK.insert() & extract()
     * @param item
     */
    // To be used whenever an item's priority has changed.
    // The input is a reference to the items whose priority
    // has changed. This operation will then rearrange
    // the items in the data structure to ensure it
    // operates correctly.
    // Throws an IllegalArgumentException if the item is
    // not a member of the heap.
    // The running time of this method should be O(log(n)+log(k)).
    public void updatePriority(T item) {
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
        }
        if (rest.isEmpty()) {
            // nothing in the rest, all ppl topK, update inside topK
            topK.updatePriority(item);
            return;
        }
        // maintain the heap for both rest and topK, for compare each other in the next if statment
        if (itemToHeap.get(item) == topK) {
            topK.updatePriority(item);
        } else {
            rest.updatePriority(item);
        }
        // need to rebalance the rest and topK
        if (topK.peek().compareTo(rest.peek()) < 0) {
            // extract because need to swap element anyway
            T restMax = rest.extract();
            T topKMin = topK.extract();

            // insert the biggest element in rest to topK
            topK.insert(restMax);
            itemToHeap.put(restMax, topK);
            // insert the smallest element in topK to rest
            rest.insert(topKMin);
            itemToHeap.put(topKMin, rest);
        }
    }

    /**
     * O(log n + log k) because rest.extract() and topK.insert()
     * @param item
     */
    // Removes the given item from the data structure
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log(n)+log(k)).
    public void remove(T item) {
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
        }
        // remove item from rest does not impact topK
        if (itemToHeap.get(item) == rest) {
            rest.remove(item);
        } else {
            // remove item from top k, then grab the root from MaxHeap to keep the number of k topK
            topK.remove(item);
            // if rest is empty then topK increment one element, otherwise grab one element from rest
            if (!rest.isEmpty()) {
                T restMax = rest.extract();
                topK.insert(restMax);
                itemToHeap.put(restMax, topK);
            }
        }
        size--;
    }
}
