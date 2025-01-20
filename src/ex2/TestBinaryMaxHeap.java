package ex2;

public class TestBinaryMaxHeap {
    public static void main(String[] args) {
        BinaryMaxHeap<Integer> maxHeap = new BinaryMaxHeap<>();

        // Test 1: Insert Elements
        System.out.println("=== Test 1: Insert ===");
        maxHeap.insert(10);
        maxHeap.insert(5);
        maxHeap.insert(20);
        maxHeap.insert(3);
        maxHeap.insert(7);
        System.out.println("Heap after inserts: " + maxHeap);

        // Test 2: Extract Maximum
        System.out.println("\n=== Test 2: Extract ===");
        System.out.println("Extracted: " + maxHeap.extract()); // Expected: 20
        System.out.println("Heap after extract: " + maxHeap);

        // Test 3: Peek
        System.out.println("\n=== Test 3: Peek ===");
        System.out.println("Current Max: " + maxHeap.peek()); // Expected: 10

        // Test 4: Update Priority (if applicable)
        System.out.println("\n=== Test 4: Update Priority ===");
        maxHeap.insert(15);
        maxHeap.insert(2);
        System.out.println("Heap before updating priority: " + maxHeap);
        // Uncomment the next line if you have implemented updatePriority()
        // maxHeap.updatePriority(15);
        System.out.println("Heap after updating priority: " + maxHeap);

        // Test 5: Remove Specific Element
        System.out.println("\n=== Test 5: Remove Element ===");
        maxHeap.remove(7);
        System.out.println("Heap after removing 7: " + maxHeap);

        // Test 6: Check Is Empty and Size
        System.out.println("\n=== Test 6: Is Empty and Size ===");
        System.out.println("Is empty? " + maxHeap.isEmpty()); // Expected: false
        System.out.println("Size: " + maxHeap.size()); // Expected: Current size of the heap

        // Test 7: Extract All Elements
        System.out.println("\n=== Test 7: Extract All ===");
        while (!maxHeap.isEmpty()) {
            System.out.println("Extracted: " + maxHeap.extract());
        }
        System.out.println("Heap after extracting all: " + maxHeap); // Expected: []

        // Test 8: Edge Case - Remove Non-existent Element
        System.out.println("\n=== Test 8: Remove Non-existent Element ===");
        try {
            maxHeap.remove(99); // Expected: Exception
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // Test 9: Edge Case - Extract from Empty Heap
        System.out.println("\n=== Test 9: Extract from Empty Heap ===");
        try {
            maxHeap.extract(); // Expected: Exception
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
