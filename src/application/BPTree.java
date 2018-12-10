package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        root = null;
        this.branchingFactor = branchingFactor;
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        // TODO : Mostly complete
        if (root == null ) { //if root references null...
            LeafNode n = new LeafNode(); //create a new LeafNode and insert the KV pair and set root
            n.insert(key, value);
            root = n;
        }

        if (root.isLeaf()) { //if the root is a leaf, insert directly
            root.insert(key, value);
        } 
        else { //otherwise we must traverse to the correct leaf node
            Node curr = root;
            while (!curr.isLeaf()) { //while loop that traverses to the correct leaf node before inserting
                List<K> cKeys = curr.getKeys();
                List<Node> cChildren = curr.getChildren();
                
                //purposely left as <=, read below
                for (int i = 0; i <= cKeys.size(); i++) { //iterates through curr's list of keys
                    if (i == cKeys.size()) { //if this is true, that means that key was bigger than every key in cKeys
                        curr = cChildren.get(i); //and so we get the "right-most" child
                    }
                    else if (key.compareTo(cKeys.get(i)) == -1) { //key to be inserted is less than a key in the list
                        curr = cChildren.get(i);
                    }  
                }
            }
            curr.insert(key, value); //if we get here, that means that curr is no longer a leaf node (B+ trees
            //...insert from the bottom)
        }  
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        // TODO : Complete
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            keys = new ArrayList<K>();
        }
        
        abstract boolean isLeaf();
        abstract List<Node> getChildren(); //noted on Piazza that we could add methods to the private classes
        abstract List<K> getKeys();
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        InternalNode parent;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            children = new ArrayList<Node>();
            parent = null;
        }
        
        boolean isLeaf() {
            return false;
        }
        
        List<K> getKeys() {
            return keys;
        }
        
        List<Node> getChildren() {
            return children;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            //TODO
            //first leaf key of the subtree? as in the "left-most" key in the subtree?
            return null;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if (keys.size() + 1 >= branchingFactor) {
                return true;
            }
            else {
                return false;
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            // TODO : Complete
            if (isOverflow()) { //if key list will be equal to branching factor...
                insertInOrder(key); //add items to lists anyway
                Node sister = split(); //split the current node into two sibling nodes
                if (parent == null) {
                    parent = new InternalNode();
                    parent.children.add(this); //we know this will be the correct order because the parent node has
                    parent.children.add(sister); //...no children as of yet
                    //need to update root as-well
                }
                else { //current node already has a parent
                    //add the new sister node to the parent's children list
                    //insert the key that corresponds with the first key in the sister?
                }
            }
            else {
                if (keys == null) {
                    keys.add(key);
                }
                else { //this loops adds the keys and values to the list in an ascending order
                    insertInOrder(key);
                }
            }
        }
        
        void insertInOrder(K key) { //helper method that adds item in order
            for(int i = 0; i <= keys.size(); i++) { //NOTE: it is intentionally left as <= ...read here v
                if (i == keys.size()) { //if the index has reached the last element of the list, and the key
                    //...was still greater, add it to the end
                    keys.add(key);
                    break;
                }
                else if (key.compareTo(keys.get(i)) == 1) { //if the key to add is greater than the current keys
                    continue;
                }
                else {
                    keys.add(i, key);
                    break; 
                }
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            // TODO : Complete
            InternalNode sister = new InternalNode();

            int halfIndex = keys.size() / 2; //will yield desired half index for even or odd lists
            for (int i = halfIndex; i < keys.size(); i++) { //adds keys and values to sister leaf node
                //sister.insert(keys.get(i)); PROBLEM: need to insert with a value...but we don't have a value?
            }
            for (int i = keys.size() - 1; i >= halfIndex; i--) { //removes keys from original InternalNode
                keys.remove(i);
            }
            
            return sister;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
            return null;
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        InternalNode parent;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            values = new ArrayList<V>();
            parent = null;
            next = null;
            previous = null;
        }
        
        void setPrev(LeafNode prev) {
            previous = prev;
        }
        
        void setNext(LeafNode next) {
            this.next = next;
        }
        
        boolean isLeaf() {
            return true;
        }
        
        List<K> getKeys() {
            return keys;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            // TODO : Complete
            
            return keys.get(0); //pretty sure this is what this means, since we are already in the leaf?
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if (keys.size() + 1 >= branchingFactor) {
                return true;
            }
            else {  
                return false;
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            // TODO : Mostly Complete
            if (isOverflow()) { //if key list will be equal to branching factor...
                insertInOrder(key, value); //add items to lists anyway
                Node sister = split(); //split the current node into two sibling nodes
                if (parent == null) {
                    parent = new InternalNode();
                    parent.children.add(this); //we know this will be the correct order because the parent node has
                    parent.children.add(sister); //...no children as of yet
                    //need to update root also
                }
                else { //current node already has a parent
                    //add the new sister node to the parent's children list
                    parent.children.add(sister); //is it in order?
                    //insert the key that corresponds with the first key in the sister's list?
                    //parent.insert(sister.getFirstLeafKey(), ); PROBLEM: Need a value?
                }
            }
            else {
                if (keys == null) {
                    keys.add(key);
                }
                else { //this loops adds the keys and values to the list in an ascending order
                    insertInOrder(key, value);
                }
            }
        }
        
        void insertInOrder(K key, V value) { //helper method that adds items in order
            for(int i = 0; i <= keys.size(); i++) { //NOTE: it is intentionally left as <= ...read here v
                if (i == keys.size()) { //if the index has reached the last element of the list, and the key
                    //...was still greater, add it to the end
                    keys.add(key);
                    break;
                }
                else if (key.compareTo(keys.get(i)) == 1) { //if the key to add is greater than the current keys
                    continue;
                }
                else {
                    keys.add(i, key);
                    values.add(i, value); //not sure about this yet?
                    break; 
                }
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            // TODO : Complete
            LeafNode sister = new LeafNode();
            next = sister;
            sister.setPrev(this);

            int halfIndex = keys.size() / 2; //will yield desired half index for even or odd lists
            for (int i = halfIndex; i < keys.size(); i++) { //adds keys and values to sister leaf node
                sister.insert(keys.get(i), values.get(i)); //I think?
            }
            for (int i = keys.size() - 1; i >= halfIndex; i--) { //removes keys from original LeafNode
                keys.remove(i);
                values.remove(i);
            }
            
            return sister;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
            return null;
        }

        @Override
        List<BPTree<K, V>.Node> getChildren() {
            //useless method for this node, couldn't think of another way to get the children from the
            //...internal nodes
            return null;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
