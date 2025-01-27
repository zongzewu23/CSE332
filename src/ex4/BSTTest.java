package ex4;

public class BSTTest {
    public static void main(String[] args) {
        // Initialize the BinarySearchTree
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();

        // Test 1: Check initial size and isEmpty
        System.out.println("Initial tree size: " + bst.size());
        System.out.println("Is tree empty? " + bst.isEmpty());

        // Test 2: Insert elements
        bst.insert(10, "Ten");
        bst.insert(5, "Five");
        bst.insert(15, "Fifteen");
        bst.insert(7, "Seven");
        bst.insert(3, "Three");
        bst.insert(20, "Twenty");

        // Test 3: Check size and isEmpty after insertions
        System.out.println("Tree size after insertions: " + bst.size());
        System.out.println("Is tree empty? " + bst.isEmpty());

        // Test 4: Get and print keys in sorted order
        System.out.println("Keys in sorted order: " + bst.getKeys());

        // Test 5: Get and print values in sorted key order
        System.out.println("Values in sorted key order: " + bst.getValues());

        // Test 6: Search for specific keys
        System.out.println("Find value for key 10: " + bst.find(10));
        System.out.println("Find value for key 7: " + bst.find(7));
        System.out.println("Find value for non-existing key 25: " + bst.find(25));

        // Test 7: Find next and previous keys
        System.out.println("Next key after 7: " + bst.findNextKey(7));
        System.out.println("Previous key before 7: " + bst.findPrevKey(7));

        // Test 8: Print the tree structure sideways
        System.out.println("\nTree structure (sideways):");
        bst.printSideways();
    }
}
