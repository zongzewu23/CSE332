package ex2;

public class TestBinaryMinHeap {
    public static void main(String[] args) {
        BinaryMinHeap<Integer> heap = new BinaryMinHeap<>();

        // Test 1: Insert Elements
        System.out.println("=== Test 1: Insert ===");
        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(3);
        heap.insert(7);
        System.out.println("Heap after inserts: " + heap); // Using toString()

        // Test 2: Extract Minimum
        System.out.println("\n=== Test 2: Extract ===");
        System.out.println("Extracted: " + heap.extract()); // Expected: 3
        System.out.println("Heap after extract: " + heap); // Using toString()

        // Test 3: Peek
        System.out.println("\n=== Test 3: Peek ===");
        System.out.println("Current Min: " + heap.peek()); // Expected: 5

        // Test 4: Update Priority (if implemented)
        System.out.println("\n=== Test 4: Update Priority ===");
        heap.insert(15);
        heap.insert(2);
        System.out.println("Heap before updating priority: " + heap);
        // Assuming updatePriority modifies an element's priority in the heap
        // If you have a method for it, call it here, e.g., heap.updatePriority(15);
        // Uncomment the next line if `updatePriority` is implemented:
        // heap.updatePriority(15);
        System.out.println("Heap after updating priority: " + heap);

        // Test 5: Remove Element
        System.out.println("\n=== Test 5: Remove Element ===");
        heap.remove(20); // Assuming remove works for specific elements
        System.out.println("Heap after removing 20: " + heap);

        // Test 6: Is Empty and Size
        System.out.println("\n=== Test 6: Is Empty and Size ===");
        System.out.println("Is empty? " + heap.isEmpty()); // Expected: false
        System.out.println("Size: " + heap.size()); // Expected: Current size of the heap

        // Test 7: Extract All Elements
        System.out.println("\n=== Test 7: Extract All ===");
        while (!heap.isEmpty()) {
            System.out.println("Extracted: " + heap.extract());
        }
        System.out.println("Heap after extracting all: " + heap); // Expected: []
    }
}
