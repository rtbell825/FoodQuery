package application;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;


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
    
    private BPTree<Double, FoodItem> calTree;
    private BPTree<Double, FoodItem> fatTree;
    private BPTree<Double, FoodItem> carbTree;
    private BPTree<Double, FoodItem> fiberTree;
    private BPTree<Double, FoodItem> proTree;
    
    /**
     * Public constructor
     * 
     */
    public FoodData() {
        foodItemList = new ArrayList<FoodItem>();
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        branchingFactor = 3;
        calTree = new BPTree<Double, FoodItem>(branchingFactor); 
		fatTree = new BPTree<Double, FoodItem>(branchingFactor);
		carbTree = new BPTree<Double, FoodItem>(branchingFactor);
		fiberTree = new BPTree<Double, FoodItem>(branchingFactor);
		proTree = new BPTree<Double, FoodItem>(branchingFactor);
		indexes.put("calories", calTree);
		indexes.put("fat", fatTree);
		indexes.put("carbohydrate", carbTree);
		indexes.put("fiber", fiberTree);
		indexes.put("protein", proTree);
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     * need to make a tree for each of the nutrients. Then pass in the nutrient value as the key 
	 * and the value as the foodItem that nutrient came from.
     */  
    @Override
    public void loadFoodItems(String filePath) {
    	Scanner scnr = null;
    	try {
    		scnr = new Scanner(new File(filePath));
    		while (scnr.hasNextLine()) {
    			try {
    				String nextLine = scnr.nextLine();
    				if (nextLine.length() < 20) {
        				continue;
        			}
    				String[] inputLine = nextLine.split(",");
	    			String id = inputLine[0];
	    			String name = inputLine[1];
	    			String calories = inputLine[2];
	    			double calNum = Double.parseDouble(inputLine[3]);
	    			String fat = inputLine[4];
	    			double fatNum = Double.parseDouble(inputLine[5]);
	    			String carbohydrate = inputLine[6];
	    			double carbNum = Double.parseDouble(inputLine[7]);
	    			String fiber = inputLine[8];
	    			double fibNum = Double.parseDouble(inputLine[9]);
	    			String protin = inputLine[10];
	    			double proNum = Double.parseDouble(inputLine[11]);
	    			
	    			FoodItem curr = new FoodItem(id, name);
	    			curr.addNutrient(calories, calNum);
	    			curr.addNutrient(fat, fatNum);
	    			curr.addNutrient(carbohydrate, carbNum);
	    			curr.addNutrient(fiber, fibNum);
	    			curr.addNutrient(protin, proNum);
	    			foodItemList.add(curr);
	    			calTree.insert(calNum, curr);
	    			fatTree.insert(fatNum, curr);
	    			carbTree.insert(carbNum, curr);
	    			fiberTree.insert(fibNum, curr);
	    			proTree.insert(proNum, curr);
    			} catch (Exception e) {
    				e.printStackTrace();
    				continue;
    			}
    		}
    		
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
        List<FoodItem> returnList = new ArrayList<FoodItem>();
        // Separate the rules 
        String searchNutrient = rules.get(0).toLowerCase();
        String comparator = rules.get(1);
        double key = Double.parseDouble(rules.get(2));
        // Get BPTree for the specified nutrient
    	BPTree<Double, FoodItem> filterTree = indexes.get(searchNutrient);
    	// Call on BPTree method to do the searching work for me
    	returnList = filterTree.rangeSearch(key, comparator);
        return returnList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
    	// Need to make this alphanumeric? How?
        foodItemList.add(foodItem);
        HashMap<String, Double> nutrients = foodItem.getNutrients();
        indexes.get("calories").insert(nutrients.get("calories"), foodItem);
        indexes.get("fat").insert(nutrients.get("fat"), foodItem);
        indexes.get("carbohydrate").insert(nutrients.get("carbohydrate"), foodItem);
        indexes.get("fiber").insert(nutrients.get("fiber"), foodItem);
        indexes.get("protein").insert(nutrients.get("protein"), foodItem);
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
		File file = null;
		PrintWriter printWriter = null;
		try {
			file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
		    printWriter = new PrintWriter(file);
			for (int i = 0; i < foodItemList.size(); ++i) {
				FoodItem curr = foodItemList.get(i);
				printWriter.println(curr.getID() + "," + curr.getName() + "," + "calories" + "," 
						+ curr.getNutrients().get("calories") + "," + "fat," + curr.getNutrients().get("fat") + "," 
						+ "carbohydrate" + "," + curr.getNutrients().get("carbohydrate") + "," + "fiber" + ","
						+ curr.getNutrients().get("fiber") + "," + "protein" + "," + curr.getNutrients().get("protein"));
			}
			
		} catch (IOException e) {
			System.out.println("File could not be written to.");
		} finally {
			printWriter.close();
		}
	}

}
