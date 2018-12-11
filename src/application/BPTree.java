//package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Collections;

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
        if (root == null ) { //if root references null...
            LeafNode n = new LeafNode(); //create a new LeafNode and insert the KV pair and set root
            n.insert(key, value);
            root = n;
        }
        else {
            root.insert(key, value);
            if (root.isOverflow()) {
                Node newChild = root.split();
                K newKey = newChild.getFirstLeafKey();
                
                InternalNode newParent = new InternalNode();
                newParent.children.add(root);
                newParent.children.add(newChild);
                newParent.keys.add(newKey);
                
                root = newParent;
            }
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
        
        return root.rangeSearch(key, comparator);
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
        
        
        /**
         * Gets child of internal node.
         * @param key
         * @return
         */
        Node getChild (K key) {
            int searchResult = Collections.binarySearch(keys, key);
            if (searchResult >= 0) 
                return children.get(searchResult);
            else
                // If key is not found, binarySearch returns the negative index where the key would be added,
                // minus one. Thus, the if the key is not found, the return value from binarySearch must
                // be made positive again, and have one subtracted in order to insert the key in the proper
                // location.
                return children.get(-searchResult - 1);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            //gets the "left most" key in the subtree
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if (children.size() > branchingFactor) {
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
            Node chosenChild = null;
            for (int i = 0; i <= keys.size(); i++) {
                if (i == keys.size()) { //if this is true, then key was greater or equal to every key in the key list
                    chosenChild = children.get(i);
                    children.get(i).insert(key, value); //insert at "right-most" child
                    break;
                }
                if (key.compareTo(keys.get(i)) < 0) { //if a key is found in key list that is greater than
                    chosenChild = children.get(i);
                    children.get(i).insert(key, value);//the current key, insert into corresponding child
                    break;
                }
            }
            if (chosenChild.isOverflow()) {
                Node newChild = chosenChild.split();
                K newKey = newChild.getFirstLeafKey();
                for(int j = 0; j <= keys.size(); j++) {
                    if(j == keys.size()) {
                        keys.add(newKey);
                        children.add(newChild);
                        break;
                    }
                    if (newKey.compareTo(keys.get(j)) < 0) {
                        keys.add(j, newKey);
                        children.add(j, newChild);
                        break;
                    }
                }
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            InternalNode sister = new InternalNode();
            int halfIndex = keys.size() / 2;
            
            sister.keys.addAll(keys.subList(halfIndex, keys.size()));
            sister.children.addAll(children.subList(halfIndex, keys.size()));
            // Deletes keys and values added to sister node from original.
            keys.subList(halfIndex, keys.size()).clear();
            children.subList(halfIndex, keys.size()).clear();
            
            return sister;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
               for(int i = 0; i <= keys.size(); i++) {
                   if (i == keys.size()) { //if this is true, that means that key was bigger than every key in this node's
                       //...list of keys
                       return children.get(i).rangeSearch(key, comparator);
                   }
                   else if (key.compareTo(keys.get(i)) == -1) {
                       return children.get(i).rangeSearch(key, comparator);
                   }
               }
               return null; //shouldn't ever reach this line
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
            next = null;
            previous = null;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(values.size() >= branchingFactor) {
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
            for(int i = 0; i <= keys.size(); i++) { //NOTE: it is intentionally left as <= ...read here v
                if (i == keys.size()) { //if the index has reached the last element of the list, and the key
                    //...was still greater, add it to the end
                    keys.add(key);
                    values.add(value);
                    break;
                }
                if (key.compareTo(keys.get(i)) == -1) { //key is smaller than key at index i
                    keys.add(i, key);
                    values.add(i, value);
                    break;
                }
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            LeafNode sister = new LeafNode();

            next = sister;
            sister.previous = this;

            int halfIndex = keys.size() / 2; //will yield desired half index for even or odd lists
            // Inserts keys and values from half index of list to end into sister.
            sister.keys.addAll(keys.subList(halfIndex, keys.size()));
            sister.values.addAll(values.subList(halfIndex, keys.size()));
            // Deletes keys and values added to sister node from original.
            keys.subList(halfIndex, keys.size()).clear();
            values.subList(halfIndex, keys.size()).clear();
            
            return sister;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            if (comparator.contentEquals(">=")) {
                //return the list of values that is greater than or equal to the key
                List<V> greaterList = new ArrayList<V>();
                LeafNode curr = this;
                do {
                    for(int i = 0; i < curr.keys.size(); i++) {
                        if (key.compareTo(curr.keys.get(i)) <= 0) { //value in key list is equal to or greater than key
                            //we want to add all values to the "right" of this value to a list and return that list
                            greaterList.add(values.get(i));
                        }
                    }
                    curr = this.next;
                } while (curr != null);
                return greaterList;
            }
            else if (comparator.contentEquals("<=")) {
                //return the list of values that is less than or equal to the key
                List<V> lesserList = new ArrayList<V>();
                LeafNode curr = this;
                do {
                    for(int i = curr.keys.size() - 1; i >= 0; i--) {
                        if (key.compareTo(curr.keys.get(i)) >= 0) { //value in key list is equal to or less than key
                            //we want to add all values to the "left" of this value to a list and return that list
                            lesserList.add(values.get(i));
                        }
                    }
                    curr = this.previous;
                }while (curr != null);
                //the above loop yields a list in reverse order...
                Collections.reverse(lesserList);
                return lesserList;
            }
            else {
                //return a list of values that are equal to the key
                //TODO: Not yet designed to handle duplicate values in multiple leaf nodes, only duplicates in
                //...this leaf node
                List<V> equalList = new ArrayList<V>();
                for (int i = 0; i < keys.size(); i++) {
                    if (key.compareTo(keys.get(i)) == 0) {
                        equalList.add(values.get(i));
                    }
                }
                return equalList;
            }
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
        BPTree<Double, Double> BPTree = new BPTree<>(3);

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
            BPTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + BPTree.toString());
        }
        List<Double> filteredValues = BPTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
