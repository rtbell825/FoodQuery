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
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList = new ArrayList<FoodItem>();
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
    	Scanner scnr = null;
    	try {
    		scnr = new Scanner(new File(filePath));
    		scnr.useDelimiter(",");
    		while (scnr.hasNextLine()) {
    			String id = scnr.next();
    			String name = scnr.next();
    			String calories = scnr.next();
    			double calNum = scnr.nextDouble();
    			String fat = scnr.next();
    			double fatNum = scnr.nextDouble();
    			String Carb = scnr.next();
    			double CarbNum = scnr.nextDouble();
    			String fiber = scnr.next();
    			double fibNum = scnr.nextDouble();
    			String protin = scnr.next();
    			double proNum = scnr.nextDouble();
    			FoodItem curr = new FoodItem(id, name);
    			curr.addNutrient(calories, calNum);
    			curr.addNutrient(fat, fatNum);
    			curr.addNutrient(Carb, CarbNum);
    			curr.addNutrient(fiber, fibNum);
    			curr.addNutrient(protin, proNum);
    			foodItemList.add(curr);
    			BPTree tree = new BPTree(3); //I am unsure of what the purpose of the BPTree is. What is a good BF?
    			tree.insert(id, curr);
    			indexes.put(id, tree); // Between the previous comment and this one I have no idea if this is correct. 
    		}
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
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
        // TODO : Complete
        return null;
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
