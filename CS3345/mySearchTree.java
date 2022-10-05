
/*

 *   Description
 *
   Create a class called MySearchTree.  MySearchTree will implement a binary
   search tree.  MySearchTree will be a generic class storing a value of the
   generic type.
 *
 */

public class MySearchTree<AnyType extends Comparable<? super AnyType>> {

    private static class BinaryNode<AnyType> {
        BinaryNode(AnyType theElement) {
            this(theElement, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        AnyType element; // The data in the node
        BinaryNode<AnyType> left; // Left child
        BinaryNode<AnyType> right; // Right child
    }

    private BinaryNode<AnyType> root;

    public MySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    public AnyType findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return findMin(root).element;
    }

    public AnyType findMax() {
        if (isEmpty()) throw new UnderflowException();
        return findMax(root).element;
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    public static class UnderflowException extends RuntimeException {
        //        public MyException() {
//            super();
//        }
        public UnderflowException() {
            super();
        }
    }

    public void printTree() {
        /* Figure 4.56 */
        if (isEmpty())
            System.out.println("Empty Tree");
        else
            printTree(root);
    }

    private boolean contains(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return false;
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.right);
        else
            return true;
    }

    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        else if (t.left == null)
            return t;
        return findMin(t.left);
    }

    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t != null)
            while (t.right != null)
                t = t.right;
        return t;
    }

    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return new BinaryNode<AnyType>(x, null, null);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        else
            ;  // Duplicate; do nothing
        return t;
    }

    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        /* Figure 4.25 */

        if (t == null)
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) // Two children
        {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else
            t = (t.left != null) ? t.left : t.right;
        return t;
    }

    private void printTree(BinaryNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

//    height of the tree.
    private int height(BinaryNode<AnyType> t) {
        if (t == null)
            return -1;
        else
            return 1 + Math.max(height(t.left), height(t.right));
    }

    public int height() {
        return height(root);
    }

    public int getLeafCount() {
        return getLeafCount(root);
    }

//    count of all of the leaves in the tree
    private int getLeafCount(BinaryNode<AnyType> t) {
        if (t == null)
            return 0;
        if (t.left == null && t.right == null)
            return 1;
        else
            return getLeafCount(t.left) + getLeafCount(t.right);
    }
    public int parentCount() {
        return parentCount(root);
    }

    private int parentCount(BinaryNode<AnyType> t) {
        if (t == null)
            return 0;
        if (t.left == null && t.right == null)
            return 0;
        else
            return parentCount(t.left) + parentCount(t.right) + 1;
    }


    private boolean isPerfect(BinaryNode<AnyType> t) {
//        checks if left & right are equal.
        return (height(t.left) == height(t.right));
    }

    public boolean isPerfect() {
        return isPerfect(root);
    }

    private boolean ancestors(BinaryNode<AnyType> t, AnyType x) {
        if(t == null) {
            return false;
        }
        if(t.element == x) {
            return true;
        }
        boolean left = ancestors(t.left, x);
        boolean right = false;

        if(!left) {
            right = ancestors(t.right, x);
        }
        if( left || right) {
            System.out.println(t.element + " ");
        }
        return left || right;
    }

    public boolean ancestors(AnyType x) {
        return ancestors(root, x);
    }
    private void inOrderPrint(BinaryNode<AnyType> t) {
        if(t == null)
            return;
        inOrderPrint(t.left);
//        1st print data of node
        System.out.println(t.element + " ");
        inOrderPrint(t.right);


    }

    public void inOrderPrint() {
        if(isEmpty())
            System.out.println("In Order Empty");
        inOrderPrint(root);
    }


    private void preOrderPrint(BinaryNode<AnyType> t) {
        if(t == null)
            return;
        //        1st print data of node
        System.out.println(t.element + " ");
        preOrderPrint(t.left);

        preOrderPrint(t.right);


    }

    public void preOrderPrint() {
        if(isEmpty())
            System.out.println("Pre Order Empty");
        preOrderPrint(root);
    }



    public static void main(String[] args) {
        MySearchTree<Integer> t = new MySearchTree<Integer>();
        t.insert(5);
        t.insert(1);
        t.insert(3);
        t.insert(6);
        t.insert(4);
        t.insert(8);
        t.insert(7);
        t.insert(2);



        System.out.println("\t Is 7 on the BST? - " + t.contains(7));
        System.out.println("\t Leaf Count - " + t.getLeafCount());
        System.out.println("\t Parent Counter - " + t.parentCount());

        System.out.println("\t Tree Height: " + t.height());
        System.out.println("\t Is the tree perfect?(Filled @ every lvl) - " + t.isPerfect());
        System.out.println("\t Printing Ancestors 5 - \n" + t.ancestors(5));
        System.out.println("\t Printing Ancestors 8 - \n" + t.ancestors(8));
        System.out.println("\n Printing inOrder");
        t.inOrderPrint();


        System.out.println("\n Printing preOrder");
        t.preOrderPrint();

        System.out.println("/n Printing Tree \n");
        t.printTree();
    }
}
