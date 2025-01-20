package ex2;

import java.util.Arrays;

public class TestTopKHeap {
    public static void main(String[] args) {
        // Initialize TopKHeap with k = 3
        TopKHeap<Integer> topKHeap = new TopKHeap<>(3);

        // Test 1: Insert elements
        System.out.println("=== Test 1: Insert ===");
        topKHeap.insert(10);
        topKHeap.insert(5);
        topKHeap.insert(20);
        topKHeap.insert(15);
        topKHeap.insert(25);

        System.out.println("Top K elements: " + topKHeap.topK());
        // Expected: [20, 15, 25] (order may vary)

        // Test 2: Query if an element is in top K
        System.out.println("\n=== Test 2: Is Top K ===");
        System.out.println("Is 20 in top K? " + topKHeap.isTopK(20)); // Expected: true
        System.out.println("Is 5 in top K? " + topKHeap.isTopK(5));   // Expected: false

        // Test 3: Update Priority
        System.out.println("\n=== Test 3: Update Priority ===");
        System.out.println("Before update: " + topKHeap.topK());
        // Update priority of 15 (e.g., increasing it)
        topKHeap.updatePriority(15);
        System.out.println("After update: " + topKHeap.topK());
        // Ensure the top K invariant is maintained

        // Test 4: Remove an element
        System.out.println("\n=== Test 4: Remove Element ===");
        System.out.println("Before remove: " + topKHeap.topK());
        topKHeap.remove(20);
        System.out.println("After remove: " + topKHeap.topK());
        // Ensure 20 is removed and the top K invariant holds

        // Test 5: Insert beyond K and maintain top K
        System.out.println("\n=== Test 5: Insert Beyond K ===");
        topKHeap.insert(30);
        topKHeap.insert(40);
        System.out.println("After insertions: " + topKHeap.topK());
        // Expected: [30, 40, 25] (order may vary)

        // Test 6: Extract All Top K Elements
        System.out.println("\n=== Test 6: Extract All Top K Elements ===");
        while (!topKHeap.topK().isEmpty()) {
            System.out.println("Top K before extraction: " + topKHeap.topK());
            Integer extracted = topKHeap.topK().get(0);
            topKHeap.remove(extracted);
            System.out.println("Extracted: " + extracted);
        }
        System.out.println("Heap after extracting all: " + topKHeap.topK());
        // Expected: []

        // Test 7: Edge Cases
        System.out.println("\n=== Test 7: Edge Cases ===");
        TopKHeap<Integer> edgeHeap = new TopKHeap<>(3);

        // Insert with an empty heap
        edgeHeap.insert(100);
        System.out.println("Inserted into empty heap: " + edgeHeap.topK()); // Expected: [100]

        // Attempt to remove non-existent element
        try {
            edgeHeap.remove(200); // Should throw exception
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // Attempt to update priority of non-existent element
        try {
            edgeHeap.updatePriority(200); // Should throw exception
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // Insert more elements and check correctness
        edgeHeap.insert(50);
        edgeHeap.insert(75);
        edgeHeap.insert(25);
        edgeHeap.insert(10);
        System.out.println("Heap after multiple inserts: " + edgeHeap.topK());
        // Expected: [75, 100, 50] (order may vary)
    }
}
