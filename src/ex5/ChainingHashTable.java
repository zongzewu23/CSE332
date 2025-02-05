package ex5;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable<K, V> implements DeletelessDictionary<K, V> {
    private List<Item<K, V>>[] table; // the table itself is an array of linked lists of items.
    private int size;
    private static int[] primes = {11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853};

    public ChainingHashTable() {
        table = (LinkedList<Item<K, V>>[]) Array.newInstance(LinkedList.class, primes[0]);
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // TODO
    public V insert(K key, V value) {
        // calculate the bucketNum to know where this key should go
        int bucketNum = key.hashCode() % table.length;
        // obtain this chain
        List<Item<K, V>> chain = table[bucketNum];
        // iterate through this chain
        for (Item<K, V> item : chain) {
            // if this chain contains the key, then replace it's old value
            // with new value, and return the old value
            if (item.key.equals(key)) {
                V result = item.value;
                item.value = value;
                return result;
            }
        }
        // if that chain does not contain the key, then add the new item into the chain
        chain.add(new Item<>(key, value));
        size++;  // update size
        // after insert,check the load factor
        double loadFactor = (double) size / table.length;
        // if the load factor is too high then rehash the table
        if (loadFactor > 0.75) {
            rehash();
        }
        // no need to return old value cause there is no old value
        return null;
    }

    // TODO
    public V find(K key) {
        if (isEmpty()) throw new IllegalStateException("Nothing in the HashTable!");
        int bucketNum = key.hashCode() % table.length;  // get bucket number
        List<Item<K, V>> chain = table[bucketNum];  // obtain the chain
        for (Item<K, V> item : chain) {
            // if found then return value
            if (item.key.equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    // TODO
    public boolean contains(K key) {
        int bucketNum = key.hashCode() % table.length;
        List<Item<K, V>> chain = table[bucketNum];
        for (Item<K, V> item : chain) {
            // if fount then reutrn true
            if (item.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    // TODO

    /**
     * it is linear time because the first for-loop is looping
     * through the table, not the chain, which has a size extremely smaller than
     * the size of the chain. O(m)+O(n)+O(n) = O(n)
     *
     * @return
     */
    public List<K> getKeys() {
        List<K> result = new ArrayList<>();
        // iterate through table in order
        for (List<Item<K, V>> chain : table) {
            // iterate through chain in order
            for (Item<K, V> item : chain) {
                result.add(item.key);  // linear add
            }
        }
        return result;
    }

    // TODO

    /**
     * O(m)+O(n)+O(n) = O(n)
     *
     * @return
     */
    public List<V> getValues() {
        List<V> result = new ArrayList<>();
        // same logic, same time complexity
        for (List<Item<K, V>> chain : table) {
            for (Item<K, V> item : chain) {
                result.add(item.value);
            }
        }
        return result;
    }

    public String toString() {
        String s = "{";
        s += table[0];
        for (int i = 1; i < table.length; i++) {
            s += "," + table[i];
        }
        return s + "}";
    }

    private void rehash() {
        int newCapacity = nextPrime(primes, table.length);  // get next prime number
        // create a temporary place to store the rehashed key values
        List<Item<K, V>>[] temp = (LinkedList<Item<K, V>>[]) Array.newInstance(LinkedList.class, newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            temp[i] = new LinkedList<>();  // initialize the List[]
        }

        for (List<Item<K, V>> chain : table) {
            for (Item<K, V> item : chain) {
                // find where it goes
                int bucketNum = item.key.hashCode() % newCapacity;
                // add it to the right chain
                temp[bucketNum].add(item);
            }
        }
        // exchange space
        table = temp;
    }

    private int nextPrime(int[] primes, int tableLength) {
        // use the prime in primes[] if we can find a fit one
        for (int i : primes) {
            if (i > tableLength) {
                return i;
            }
        }
        // run out of prime. use the floor(log2(tablesize)) ^2 + 1
        int n = (int) Math.ceil(Math.log(tableLength) / Math.log(2));
        return (int) Math.pow(2, n) + 1;
    }

}
