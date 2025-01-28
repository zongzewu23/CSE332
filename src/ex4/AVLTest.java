package ex4;

import java.util.List;

public class AVLTest {

    public static void main(String[] args) {
        AVLTree<Integer, String> avl = new AVLTree<>();

        // Test 1: Insert nodes and print tree
        System.out.println("Test 1: Insert and Print Tree");
        avl.insert(10, "A");
        avl.insert(20, "B");
        avl.insert(30, "C"); // Should trigger a single left rotation
        avl.insert(5, "D");
        avl.insert(15, "E");
        avl.insert(25, "F");
        avl.insert(35, "G");
        avl.insert(26, "H");
        avl.insert(3, "I");
        avl.insert(2, "Z");
        avl.insert(1, "Y");
        avl.insert(13, "Q");
        avl.insert(14, "P");
        avl.insert(11, "Q");
        avl.insert(12, "Q");
        avl.insert(27, "J");
        avl.insert(38, "K");
        avl.printSideways();

        // Test 2: Check getKeys and getValues
        System.out.println("\nTest 2: getKeys and getValues");
        List<Integer> keys = avl.getKeys();
        List<String> values = avl.getValues();
        System.out.println("Keys: " + keys);
        System.out.println("Values: " + values);

        // Test 3: Check find
        System.out.println("\nTest 3: Find Method");
        System.out.println("Find key 15: " + avl.find(15)); // Should return "E"
        System.out.println("Find key 40 (not present): " + avl.find(40)); // Should return null

        // Test 4: Check findPrevKey and findNextKey
        System.out.println("\nTest 4: findPrevKey and findNextKey");
        System.out.println("findPrevKey(15): " + avl.findPrevKey(15)); // Should return 10
        System.out.println("findNextKey(15): " + avl.findNextKey(15)); // Should return 20

        // Test 5: Stress test with sequential insertion
        System.out.println("\nTest 5: Stress Test - Sequential Insertion");
        AVLTree<Integer, String> stressTestTree = new AVLTree<>();
        for (int i = 1; i <= 100; i++) {
            stressTestTree.insert(i, "Value" + i);
        }
        stressTestTree.printSideways();
        System.out.println("Tree size: " + stressTestTree.size());
    }
}
