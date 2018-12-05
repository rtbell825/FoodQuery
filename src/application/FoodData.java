package application;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    private int branchingFactor;
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList = new ArrayList<FoodItem>();
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        branchingFactor = 3;
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    //need to make a tree for each of the nutrients. Then pass in the nutrient value as the key 
	//and the value as the foodItem that nutrient came from.  
    @Override
    public void loadFoodItems(String filePath) {
    	Scanner scnr = null;
    	try {
    		BPTree<Double, FoodItem> calTree = new BPTree<Double, FoodItem>(branchingFactor); 
    		BPTree<Double, FoodItem> fatTree = new BPTree<Double, FoodItem>(branchingFactor);
    		BPTree<Double, FoodItem> carbTree = new BPTree<Double, FoodItem>(branchingFactor);
    		BPTree<Double, FoodItem> fiberTree = new BPTree<Double, FoodItem>(branchingFactor);
    		BPTree<Double, FoodItem> proTree = new BPTree<Double, FoodItem>(branchingFactor);
    		scnr = new Scanner(new File(filePath));
    		scnr.useDelimiter(",");
    		while (scnr.hasNextLine()) {
    			try {
    			String id = scnr.next();
    			String name = scnr.next();
    			String calories = scnr.next().toLowerCase();
    			double calNum = scnr.nextDouble();
    			String fat = scnr.next().toLowerCase();
    			double fatNum = scnr.nextDouble();
    			String carb = scnr.next().toLowerCase();
    			double carbNum = scnr.nextDouble();
    			String fiber = scnr.next().toLowerCase();
    			double fibNum = scnr.nextDouble();
    			String protin = scnr.next().toLowerCase();
    			double proNum = scnr.nextDouble();
    			FoodItem curr = new FoodItem(id, name);
    			curr.addNutrient(calories, calNum);
    			curr.addNutrient(fat, fatNum);
    			curr.addNutrient(carb, carbNum);
    			curr.addNutrient(fiber, fibNum);
    			curr.addNutrient(protin, proNum);
    			foodItemList.add(curr);
    			calTree.insert(calNum, curr);
    			fatTree.insert(fatNum, curr);
    			carbTree.insert(carbNum, curr);
    			fiberTree.insert(fibNum, curr);
    			proTree.insert(proNum, curr);
    			} catch (Exception e) {
    				continue;
    			}
    		}
    		indexes.put("calories", calTree);
    		indexes.put("fat", fatTree);
    		indexes.put("carbohydrate", carbTree);
    		indexes.put("fiber", fiberTree);
    		indexes.put("protein", proTree);
    	} catch (FileNotFoundException e) {
    		System.out.println("File could not be loaded.");
    	} finally {
    		scnr.close();
    	}
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        ArrayList<FoodItem> returnList = new ArrayList<FoodItem>();
    	substring = substring.toLowerCase();
        for (int i = 0; i < foodItemList.size(); ++i) {
        	if (foodItemList.get(i).getName().toLowerCase().contains(substring)) {
        		returnList.add(foodItemList.get(i));
        	}
        }
        return returnList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
        //TODO Add foodItem to the indexes hash
        return;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }


	@Override
	public void saveFoodItems(String filename) {
		// TODO Auto-generated method stub
		
	}

}
