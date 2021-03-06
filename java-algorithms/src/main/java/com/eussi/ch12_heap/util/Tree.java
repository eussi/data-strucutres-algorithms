package com.eussi.ch12_heap.util;

/**
 * @author wangxueming
 * @create 2020-03-04 16:33
 * @description
 */
public class Tree {
    private Node1 root;             // first node of tree

    // -------------------------------------------------------------
    public Tree()                  // constructor
    {
        root = null;
    }            // no nodes in tree yet

    // -------------------------------------------------------------

    public Node1 find(int key)      // find node with given key
    {                           // (assumes non-empty tree)
        if (root == null) {
            return null;
        }
        Node1 current = root;               // start at root
        while (current.iData != key)        // while no match,
        {
            if (key < current.iData)         // go left?
                current = current.leftChild;
            else
                // or go right?
                current = current.rightChild;
            if (current == null)             // if no child,
                return null;                 // didn't find it
        }
        return current;                    // found it
    }  // end find()
    // -------------------------------------------------------------

    public void insert(int id) {
        Node1 newNode = new Node1();    // make new node
        newNode.iData = id;           // insert data
        if (root == null)                // no node in root
            root = newNode;
        else                          // root occupied
        {
            Node1 current = root;       // start at root
            Node1 parent;
            while (true)                // (exits internally)
            {
                parent = current;
                if (id < current.iData)  // go left?
                {
                    current = current.leftChild;
                    if (current == null)  // if end of the line,
                    {                 // insert on left
                        parent.leftChild = newNode;
                        return;
                    }
                }  // end if go left
                else                    // or go right?
                {
                    current = current.rightChild;
                    if (current == null)  // if end of the line
                    {                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }  // end else go right
            }  // end while
        }  // end else not root
    }  // end insert()
    // -------------------------------------------------------------

    public boolean delete(int key) // delete node with given key
    {                           // (assumes non-empty list)
        Node1 current = root;
        Node1 parent = root;
        boolean isLeftChild = true;

        while (current.iData != key)        // search for node
        {
            parent = current;
            if (key < current.iData)         // go left?
            {
                isLeftChild = true;
                current = current.leftChild;
            } else                            // or go right?
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null)             // end of the line,
                return false;                // didn't find it
        }  // end while
        // found node to delete

        // if no children, simply delete it
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root)             // if root,
                root = null;                 // tree is empty
            else if (isLeftChild)
                parent.leftChild = null;     // disconnect
            else
                // from parent
                parent.rightChild = null;
        }

        // if no right child, replace with left subtree
        else if (current.rightChild == null)
            if (current == root)
                root = current.leftChild;
            else if (isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;

            // if no left child, replace with right subtree
        else if (current.leftChild == null)
            if (current == root)
                root = current.rightChild;
            else if (isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;

        else  // two children, so replace with inorder successor
        {
            // get successor of node to delete (current)
            Node1 successor = getSuccessor(current);

            // connect parent of current to successor instead
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;

            // connect successor to current's left child
            successor.leftChild = current.leftChild;
        }  // end else two children
        // (successor cannot have a left child)
        return true;                                // success
    }  // end delete()
    // -------------------------------------------------------------
    // returns node with next-highest value after delNode
    // goes to right child, then right child's left descendents

    private Node1 getSuccessor(Node1 delNode) {
        Node1 successorParent = delNode;
        Node1 successor = delNode;
        Node1 current = delNode.rightChild;   // go to right child
        while (current != null)               // until no more
        {                                 // left children,
            successorParent = successor;
            successor = current;
            current = current.leftChild;      // go to left child
        }
        // if successor not
        if (successor != delNode.rightChild)  // right child,
        {                                 // make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    // -------------------------------------------------------------
    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal:  ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
        }
        System.out.println();
    }

    // -------------------------------------------------------------
    private void preOrder(Node1 localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

    // -------------------------------------------------------------
    private void inOrder(Node1 localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }

    // -------------------------------------------------------------
    private void postOrder(Node1 localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }

    // -------------------------------------------------------------
    public void displayTree() {
        inOrder(root);
        System.out.println();
    }  // end displayTree()

    public void displayPostOrder() {
        postOrder(root);
        System.out.println();
    }  // end displayTree()

    // -------------------------------------------------------------
    // =============================================================
    // 编程作业 12.4
    public Node1 removeMax() {
        Node1 grandParent = root;
        Node1 parent = root;
        Node1 current = root;
        while (current != null) {
            grandParent = parent;
            parent = current;
            current = current.rightChild;
        }
        // parent是否为根
        if (parent == root) { // 是根节点
            root = root.leftChild;
        } else { // 不是根节点
            grandParent.rightChild = parent.leftChild;
        }
        return parent;
    }

    public Node1 peekMax() {
        Node1 parent = root;
        Node1 current = root;
        while (current != null) {
            parent = current;
            current = current.rightChild;
        }
        return parent;
    }

    public boolean isEmpty() {
        return root == null;
    }
    // =============================================================

}
