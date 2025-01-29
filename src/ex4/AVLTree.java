package ex4;

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    public AVLTree() {
        super();
    }

    /**
     * copied from BST
     *
     * @param key
     * @param curr
     * @return
     */
    //recursive helper
    private V find(K key, TreeNode<K, V> curr) {
        if (curr == null) {
            return null;
        }
        int currMinusNew = curr.key.compareTo(key); // negative if curr is smaller
        if (curr.key.equals(key)) {
            return curr.value;
        } else if (currMinusNew < 0) {
            // curr is smaller so key must be to the right.
            return find(key, curr.right);
        } else {
            return find(key, curr.left);
        }
    }

    public V insert(K key, V value) {
        // maintain size here, because we know if we have to actually "insert" a new node in ahead
        V answer = find(key, root);
        if (answer == null) {
            size++;
        }
        // call find&insert&rotate to achieve the goal, and update the root
        root = findInsertRotate(root, key, value);
        // update the root's height
        root.updateHeight();
        return answer;
    }

    private TreeNode<K, V> findInsertRotate(TreeNode<K, V> node, K key, V value) {
        // Base case 0, return new node, will be assigned to parent when backtracking.
        if (node == null) {
            // calm down, this is a recursive function, this node will be appended to the previous caller(a node)
            return new TreeNode<>(key, value);
        }

        // obtain the compare result in advance
        int nodeMinusNew = node.key.compareTo(key);
        // this is the key we are finding, update the value
        if (nodeMinusNew == 0) {
            node.value = value;
        }

        // go to the left tree and see if the key is there
        if (nodeMinusNew > 0) {
            // append the returned node to current node to update the subtree
            node.left = findInsertRotate(node.left, key, value);
        }
        if (nodeMinusNew < 0) {
            // go to the right subtree, and append the returned node to current node to update this subtree
            node.right = findInsertRotate(node.right, key, value);
        }
        // update height after change the subtree
        node.updateHeight();

        // check if it's balanced, if not, rotate it somehow
        if (isUnbalanced(node)) {
            node = rotate(node);
        }
        // return this node to it's parent
        return node;
    }

    // get the current node's height, but return -1 when it's null
    private int getHeight(TreeNode<K, V> node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    // if (leftheight - rightheight)'s absolute value is bigger than one then it's unbalanced
    private boolean isUnbalanced(TreeNode<K, V> node) {
        int leftheight = getHeight(node.left);
        int rightheight = getHeight(node.right);
        return (leftheight - rightheight > 1 || leftheight - rightheight < -1);
    }

    private TreeNode<K, V> rotate(TreeNode<K, V> subRoot) {
        int heightLeft = getHeight(subRoot.left);
        int heightRight = getHeight(subRoot.right);
        int bfRoot = heightLeft - heightRight;  // balance factor of current node

        // balance factor of current node's left child
        int bfLeft = subRoot.left != null ?
                getHeight(subRoot.left.left) - getHeight(subRoot.left.right) : -1;
        // balance factor of current node's right child
        int bfRight = subRoot.right != null ?
                getHeight(subRoot.right.left) - getHeight(subRoot.right.right) : -1;

        // Right
        if (bfRoot > 1 && bfLeft > 0) {
            subRoot = rotateRight(subRoot);
        }

        // Left Right
        if (bfRoot > 1 && bfLeft < 0) {
            subRoot = rotateLR(subRoot);
        }

        // Left
        if (bfRoot < -1 && bfRight < 0) {
            subRoot = rotateLeft(subRoot);
        }

        // Right Left
        if (bfRoot < -1 && bfRight > 0) {
            subRoot = rotateRL(subRoot);
        }
        return subRoot;
    }

    private TreeNode<K, V> rotateLeft(TreeNode<K, V> subRoot) {
        TreeNode<K, V> newRoot = subRoot.right;  // store the right node, because we are going to use it as the new root
        TreeNode<K, V> rl = newRoot.left;  // store the current node's right's left node

        newRoot.left = subRoot;
        subRoot.right = rl;  // append the rl to the current node's right

        // update height after modify it
        subRoot.updateHeight();
        newRoot.updateHeight();

        // return this new root, it will be appended to the parent node later on
        return newRoot;
    }

    // exactly same logic as rotateLeft()
    private TreeNode<K, V> rotateRight(TreeNode<K, V> subRoot) {
        TreeNode<K, V> newRoot = subRoot.left;
        TreeNode<K, V> lr = newRoot.right;

        newRoot.right = subRoot;
        subRoot.left = lr;

        subRoot.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    // rotate parent node to the left first, then rotate the grandparent to the right
    private TreeNode<K, V> rotateLR(TreeNode<K, V> subRoot) {
        subRoot.left = rotateLeft(subRoot.left);
        return rotateRight(subRoot);
    }

    // same logic as above, this makes it a double rotate
    private TreeNode<K, V> rotateRL(TreeNode<K, V> subRoot) {
        subRoot.right = rotateRight(subRoot.right);
        return rotateLeft(subRoot);
    }
}

