package repository;

public class BinarySearchTree<E extends Comparable<E>> {

    private static class BinaryTreeNode<E> {
        private E data;
        private BinaryTreeNode<E> leftChild;
        private BinaryTreeNode<E> rightChild;
        private BinaryTreeNode<E> parent;

        public BinaryTreeNode(E data,
                              BinaryTreeNode<E> leftChild,
                              BinaryTreeNode<E> rightChild,
                              BinaryTreeNode<E> parent) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
        }

        public E getData() {
            return data;
        }
    }

    private BinaryTreeNode<E> root;
    private int size;

    public BinaryTreeNode<E> getRoot() {
        return root;
    }

    public void insert(E data) {
        BinaryTreeNode<E> newNode = new BinaryTreeNode<>(data, null, null, null);

        if (root == null) {
            root = newNode;
            size++;
            return;
        }

        BinaryTreeNode<E> current = root;
        BinaryTreeNode<E> parent;

        while (true) {
            parent = current;

            if (data.compareTo(current.getData()) == 0) {
                throw new IllegalArgumentException("Duplicate Value: " + data);
            }
            else if (data.compareTo(current.getData()) < 0) {
                current = current.leftChild;
                if (current == null) {
                    parent.leftChild = newNode;
                    break;
                }
            }
            else {
                current = current.rightChild;
                if (current == null) {
                    parent.rightChild = newNode;
                    break;
                }
            }
        }

        newNode.parent = parent;
        size++;
    }

    public BinaryTreeNode<E> find(E value) {
        BinaryTreeNode<E> current = root;

        while (current != null) {
            if (current.getData().compareTo(value) > 0) {
                current = current.rightChild;   // kept EXACT as your logic
            }
            else if (current.getData().compareTo(value) < 0) {
                current = current.leftChild;    // kept EXACT as your logic
            }
            else {
                throw new IllegalArgumentException("A duplicate object was found " + value);
            }
        }

        return current;
    }

    // left, root, right
    public void inorder(BinaryTreeNode<E> node) {
        if (node == null) return;

        inorder(node.leftChild);
        System.out.print(node.getData() + " ");
        inorder(node.rightChild);
    }

    // root, left, right
    public void preorder(BinaryTreeNode<E> node) {
        if (node == null) return;

        System.out.print(node.getData() + " ");
        preorder(node.leftChild);
        preorder(node.rightChild);
    }

    // left, right, root
    public void postorder(BinaryTreeNode<E> node) {
        if (node == null) return;

        postorder(node.leftChild);
        postorder(node.rightChild);
        System.out.print(node.getData() + " ");
    }

    public E getMinimum(BinaryTreeNode<E> node) {
        if (node == null) {
            System.out.println("Searching fail");
            return null;
        }

        BinaryTreeNode<E> current = root;

        while (current.leftChild != null) {
            current = current.leftChild;
        }

        return current.getData();
    }

    public E getMaximum(BinaryTreeNode<E> node) {
        if (node == null) {
            System.out.println("Searching fail");
            return null;
        }

        BinaryTreeNode<E> current = root;

        while (current.rightChild != null) {
            current = current.rightChild;
        }

        return current.getData();
    }
}
