package ex4;

public class AVLTree  <K extends Comparable<K>, V> extends BinarySearchTree<K,V> {

    public AVLTree(){
        super();
    }

    //recursive helper
    private V find(K key, TreeNode<K,V> curr){
        if(curr == null){
            return null;
        }
        int currMinusNew = curr.key.compareTo(key); // negative if curr is smaller
        if(curr.key.equals(key)){
            return curr.value;
        } else if(currMinusNew < 0){
            // curr is smaller so key must be to the right.
            return find(key, curr.right);
        } else{
            return find(key, curr.left);
        }
    }

    public V insert(K key, V value){
        V answer = find(key, root);
        if (answer == null) {
            size++;
        }
        root = findInsertRotate(root,key, value);
        root.updateHeight();
        return answer;
    }

    private TreeNode<K, V> findInsertRotate(TreeNode<K, V> node, K key, V value) {
        // Base case 0, return new node, will be assign to parent when backtracking.
        if (node == null) {
            return new TreeNode<>(key, value);
        }

        int nodeMinusNew = node.key.compareTo(key);
        if (nodeMinusNew == 0) {
            node.value = value;
        }
        if (nodeMinusNew > 0) {
            node.left = findInsertRotate(node.left, key, value);
        }
        if (nodeMinusNew < 0) {
            node.right = findInsertRotate(node.right, key, value);
        }
        node.updateHeight();
        if (isUnbalanced(node)) {
            node = rotate(node);
        }
        return node;
    }

    private int getHeight(TreeNode<K, V> node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    private boolean isUnbalanced(TreeNode<K, V> node) {
        int leftheight = getHeight(node.left);
        int rightheight = getHeight(node.right);
        return (leftheight - rightheight > 1 || leftheight - rightheight < -1);
    }
    private TreeNode<K, V> rotate(TreeNode<K, V> subRoot){
        int balanceFactor = getHeight(subRoot.left) - getHeight(subRoot.right);
        if (balanceFactor > 1) {
            subRoot = rotateRight(subRoot);
        } else if (balanceFactor < -1){
            subRoot =rotateLeft(subRoot);
        }
        return subRoot;
    }

    private TreeNode<K,V> rotateLeft(TreeNode<K,V> subRoot){
        TreeNode<K,V> rl = subRoot.right.left;
        TreeNode<K,V> newRoot = subRoot.right;

        subRoot.right = rl;
        newRoot.left = subRoot;

        subRoot.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    private TreeNode<K,V> rotateRight(TreeNode<K,V> subRoot){
        TreeNode<K,V> lr = subRoot.left.right;
        TreeNode<K,V> newRoot = subRoot.left;

        subRoot.left = lr;
        newRoot.right = subRoot;

        subRoot.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }
}

