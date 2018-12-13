package application;
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
        if (key == null) //if key is null, end insert method
            return;
        if (root == null ) { //if root references null...
            LeafNode n = new LeafNode(); //create a new LeafNode and insert the KV pair and reset root
            n.insert(key, value);
            root = n;
        }
        else { //otherwise root is not null
            root.insert(key, value); //inserts K:V into root
            if (root.isOverflow()) { //if the root is Overflow
                Node newChild = root.split(); //split the root and save sister node
                K newKey = newChild.getFirstLeafKey(); 
                
                InternalNode newParent = new InternalNode(); //new parent node
                newParent.children.add(root); //add root and sister node to parent's children
                newParent.children.add(newChild);
                newParent.keys.add(newKey); //add key to parent's list of keys
                
                root = newParent; //reset root to parent
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
            !comparator.contentEquals("==") && //If comparator is not one of these three strings, return empty array
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
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            children = new ArrayList<Node>();
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
            if (children.size() > branchingFactor) { //checks if the children size is greater than the branching factor
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
            Node chosenChild = null; //variable that tracks node chosen to insert into
            for (int i = 0; i <= keys.size(); i++) { //iterate through keys list
                if (i == keys.size()) { //if this is true, then key was greater or equal to every key in the key list
                    chosenChild = children.get(i); //chosenChild is set to "right-most" child
                    children.get(i).insert(key, value); //insert at "right-most" child
                    break; //after method calls return, break for loop
                }
                if (key.compareTo(keys.get(i)) <= 0) { //if a key is found in key list that is greater than current key
                    chosenChild = children.get(i); //chosenChild is set to child that pertains to that key
                    children.get(i).insert(key, value); //insert the current key into corresponding child
                    break; //after method calls return, break for loop
                }
            }
            if (chosenChild.isOverflow()) { //checks if the child that was inserted into is Overflow
                Node newChild = chosenChild.split(); //if it is, splits and saves reference to new sibling
                K newKey = newChild.getFirstLeafKey(); //gets first leaf key of new sibling
                for(int j = 0; j <= keys.size(); j++) { //for loop that iterates through keys list of this.node
                    if(j == keys.size()) { //if this is true, then the newKey was greater than every key in key list
                        keys.add(newKey);
                        children.add(newChild);
                        break;
                    }
                    if (newKey.compareTo(keys.get(j)) < 0) { //if newKey is less than a key in list...
                        int c = j; //temp index that starts at j
                        while (children.get(c).getFirstLeafKey().compareTo(newKey) <= 0) {
                            //iterates through children until one is found that is greater than newKey
                            c++;
                        }
                        children.add(c, newChild); //add newChild at proper index
                        keys.add(j, newKey); //same with key
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
            InternalNode sister = new InternalNode(); //new internal for sibling
            int halfKeyIndex = keys.size() / 2; //index halfway through the key list
            int halfChiIndex = children.size() / 2; //index halfway through the children list
            
            sister.keys.addAll(keys.subList(halfKeyIndex + 1, keys.size())); //adds all keys from half index to end of list
                                                                             //...to sister key list
            sister.children.addAll(children.subList(halfChiIndex, children.size())); //adds all children from half index to
                                                                                     //...end of list to sister children list
            
            keys.subList(halfKeyIndex, keys.size()).clear(); //Deletes last half of keys from this nodes key list
            children.subList(halfChiIndex, children.size()).clear(); //deletes last half of children from this nodes child list
            
            return sister;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            for(int i = 0; i <= keys.size(); i++) { //iterates through keys list
                if (i == keys.size()) { //if this is true, that means that key was greater than or equals to
                    //...every key in this node's list of keys
                    return children.get(i).rangeSearch(key, comparator); //call rangeSearch on last node in list
                }
                else if (key.compareTo(keys.get(i)) == -1) { //true if a key in keys list is greater than key param
                    return children.get(i).rangeSearch(key, comparator); //call rangeSearch on node at index
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
            next = null; //next and previous null by default
            previous = null;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0); //first leaf key in the list
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(values.size() >= branchingFactor) { //checks if values list size is greater than or equal to BF
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
            for(int i = 0; i <= keys.size(); i++) { //iterates through keys list
                if (i == keys.size()) { //if the index has reached the last element of the list, and the key
                                        //...was still greater, add it to the end
                    keys.add(key);
                    values.add(value); //add key and value at end of lists
                    break;
                }
                if (key.compareTo(keys.get(i)) <= 0) { //key is less than or equal to key at index i...
                    keys.add(i, key);
                    values.add(i, value); //add key and value in position
                    break;
                }
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            LeafNode sister = new LeafNode(); //new leaf node to store sibling data
            sister.next = this.next; //reset sister's next to this node
            this.next = sister; //reset this node's next to sister
            sister.previous = this; //reset sister's previous to this node

            int halfKeyIndex = keys.size() / 2; //half index of key list
            int halfValIndex = values.size() / 2; //half index of value list
            //Inserts keys and values from half index of list to end into sister.
            sister.keys.addAll(keys.subList(halfKeyIndex, keys.size()));
            sister.values.addAll(values.subList(halfValIndex, values.size()));
            
            //Deletes keys and values added to sister node from original.
            keys.subList(halfKeyIndex, keys.size()).clear();
            values.subList(halfValIndex, values.size()).clear();
            
            return sister;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            if (comparator.contentEquals(">=")) { //return the list of values that is greater than or equal to the key
                List<V> greaterList = new ArrayList<V>(); //list to store greater values
                LeafNode curr = this; //current node set to this node
                do { //do while loop to iterate through all next nodes in linked list
                    for(int i = 0; i < curr.keys.size(); i++) { //iterates through all keys in current node
                        if (key.compareTo(curr.keys.get(i)) <= 0) { //value in key list is equal to or greater than key
                            greaterList.add(curr.values.get(i)); //adds all values "to the right" of initial value
                        }
                    }
                    curr = curr.next; //reset current node to its next node
                } while (curr != null); //while current node isn't equal to null, continue
                return greaterList;
            }
            else if (comparator.contentEquals("<=")) { //return the list of values that is less than or equal to the key
                List<V> lesserList = new ArrayList<V>(); //list to store lesser values
                LeafNode curr = this; //current node set to this node
                do { //do while loop to iterate through all previous nodes in linked list
                    for(int i = curr.keys.size() - 1; i >= 0; i--) { //iterates (in reverse) through all keys in current node
                        if (key.compareTo(curr.keys.get(i)) >= 0) { //value in key list is equal to or less than key
                            lesserList.add(curr.values.get(i)); //adds all values "to the left" of initial value
                        }
                    }
                    curr = curr.previous; //reset current node to its previous node
                } while (curr != null); //while current node isn't equal to null, continue
                //the above loop yields a list in reverse order...
                Collections.reverse(lesserList); //...reverses the list
                return lesserList;
            }
            else { //return a list of values that are equal to the key
                List<V> equalList = new ArrayList<V>(); //list to store equal values
                LeafNode curr = this; //current node set to this node
                do { //do while loop to iterate through all nodes to "right" of current node
                    for(int i = 0; i < curr.keys.size(); i++) { //iterates through key list of current node
                        if (key.compareTo(curr.keys.get(i)) == 0) { //value in key list is equal to key
                            equalList.add(curr.values.get(i)); //adds all values that are equal to list
                        }
                    }
                    curr = curr.next; //reset current node to its next node
                } while (curr != null); //while current node isn't equal to null, continue
                
                //we now have all values equal to key from the "right" side of the linked list
                
                curr = this.previous; //reset current node to this node's previous node
                if (curr != null) { //checks if current node isn't null
                    do { //do while loop to iterate through all nodes to "left" of current node 
                        for(int i = curr.keys.size() - 1; i >= 0; i--) { //iterate through key list of current node backwards
                            if (key.compareTo(curr.keys.get(i)) == 0) { //value in key list is equal to key
                                equalList.add(curr.values.get(i)); //adds all values that are equal to list
                            }
                        }
                        curr = curr.previous; //reset current node to its previous node
                    } while (curr != null); //while current node isn't equal to null, continue
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
    
    /*
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
        for (int i = 0; i < 5; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            BPTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + BPTree.toString());
        }
        List<Double> filteredValues = BPTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }
    */
} // End of class BPTree