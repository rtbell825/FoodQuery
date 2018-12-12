package application;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FoodDataTest {
	
	public static FoodData food;

	@BeforeClass
	public static void startShit() {
		food = new FoodData();
		food.loadFoodItems("foodItems.csv");
		
	}
	
	@Test
	public void testLoadFoodItems1Key() {
		if (food.getAllFoodItems().get(0).getID().equals("51c38f5d97c3e6d3d972f08a")) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	@Test
	public void testLoadFoodItemsLastKey() {
		if (food.getAllFoodItems().get(249).getID().equals("52cd67b9d0eefe626b000f60")) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	@Test
	public void testLoadFoodItemsLastKeyandOne() {
		if (food.getAllFoodItems().size() == 250) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	@Test
	public void testLoadFoodItemsOneKeyID() {
		FoodData oneFood = new FoodData();
		FoodItem newFood = new FoodItem("id", "name");
		newFood.addNutrient("calories", 5);
		newFood.addNutrient("fat", 250);
		newFood.addNutrient("carbohydrate", 69);
		newFood.addNutrient("fiber", 50);
		newFood.addNutrient("protein", 40);
		oneFood.addFoodItem(newFood);
		if (oneFood.getAllFoodItems().get(0).getID().equals("id")) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	@Test
	public void testFilterNameTwoKeySort() {
		FoodData oneFood = new FoodData();
		FoodItem newFood = new FoodItem("id", "name");
		newFood.addNutrient("calories", 5);
		newFood.addNutrient("fat", 250);
		newFood.addNutrient("carbohydrate", 69);
		newFood.addNutrient("fiber", 50);
		newFood.addNutrient("protein", 40);
		FoodItem newFood2 = new FoodItem("id2", "nana");
		newFood2.addNutrient("calories", 50);
		newFood2.addNutrient("fat", 2500);
		newFood2.addNutrient("carbohydrate", 690);
		newFood2.addNutrient("fiber", 500);
		newFood2.addNutrient("protein", 400);
		oneFood.addFoodItem(newFood);
		oneFood.addFoodItem(newFood2);
		if (oneFood.filterByName("na").get(1).getID().equals("id2")) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	@Test
	public void testFilterNutrientsTwoKeyBPE() {
		FoodData oneFood = new FoodData();
		FoodItem newFood = new FoodItem("id", "name");
		newFood.addNutrient("calories", 5);
		newFood.addNutrient("fat", 250);
		newFood.addNutrient("carbohydrate", 69);
		newFood.addNutrient("fiber", 50);
		newFood.addNutrient("protein", 40);
		oneFood.addFoodItem(newFood);
		FoodItem newFood2 = new FoodItem("id2", "nana");
		newFood2.addNutrient("calories", 50);
		newFood2.addNutrient("fat", 2500);
		newFood2.addNutrient("carbohydrate", 690);
		newFood2.addNutrient("fiber", 500);
		newFood2.addNutrient("protein", 400);
		oneFood.addFoodItem(newFood2);
		ArrayList<String> rules = new ArrayList<String>();
		rules.add("calories");
		rules.add("==");
		rules.add("5");
		if (oneFood.filterByNutrients(rules).get(0).getID().equals("id") && oneFood.filterByNutrients(rules).size() == 1) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	@Test
	public void testFilterNutrientsTwoKeyBPL() {
		FoodData oneFood = new FoodData();
		FoodItem newFood = new FoodItem("id", "name");
		newFood.addNutrient("calories", 5);
		newFood.addNutrient("fat", 250);
		newFood.addNutrient("carbohydrate", 69);
		newFood.addNutrient("fiber", 50);
		newFood.addNutrient("protein", 40);
		oneFood.addFoodItem(newFood);
		FoodItem newFood2 = new FoodItem("id2", "nana");
		newFood2.addNutrient("calories", 50);
		newFood2.addNutrient("fat", 2500);
		newFood2.addNutrient("carbohydrate", 690);
		newFood2.addNutrient("fiber", 500);
		newFood2.addNutrient("protein", 400);
		oneFood.addFoodItem(newFood2);
		ArrayList<String> rules = new ArrayList<String>();
		rules.add("calories");
		rules.add("<=");
		rules.add("10");
		if (oneFood.filterByNutrients(rules).get(0).getID().equals("id") && oneFood.filterByNutrients(rules).size() == 1) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	@Test
	public void testFilterNutrientsTwoKeyBPG() {
		FoodData oneFood = new FoodData();
		FoodItem newFood = new FoodItem("id", "name");
		newFood.addNutrient("calories", 5);
		newFood.addNutrient("fat", 250);
		newFood.addNutrient("carbohydrate", 69);
		newFood.addNutrient("fiber", 50);
		newFood.addNutrient("protein", 40);
		oneFood.addFoodItem(newFood);
		FoodItem newFood2 = new FoodItem("id2", "nana");
		newFood2.addNutrient("calories", 50);
		newFood2.addNutrient("fat", 2500);
		newFood2.addNutrient("carbohydrate", 690);
		newFood2.addNutrient("fiber", 500);
		newFood2.addNutrient("protein", 400);
		oneFood.addFoodItem(newFood2);
		ArrayList<String> rules = new ArrayList<String>();
		rules.add("calories");
		rules.add(">=");
		rules.add("10");
		if (oneFood.filterByNutrients(rules).get(0).getID().equals("id2") && oneFood.filterByNutrients(rules).size() == 1) {
			assertTrue(true);
		}
		else {
			fail("Fuck...");
		}
	}
	
	
	@Test
	public void testSaveFoodItemsTwoKeys() {
		FoodData oneFood = new FoodData();
		FoodItem newFood = new FoodItem("id", "name");
		newFood.addNutrient("calories", 5);
		newFood.addNutrient("fat", 250);
		newFood.addNutrient("carbohydrate", 69);
		newFood.addNutrient("fiber", 50);
		newFood.addNutrient("protein", 40);
		oneFood.addFoodItem(newFood);
		FoodItem newFood2 = new FoodItem("id2", "nana");
		newFood2.addNutrient("calories", 50);
		newFood2.addNutrient("fat", 2500);
		newFood2.addNutrient("carbohydrate", 690);
		newFood2.addNutrient("fiber", 500);
		newFood2.addNutrient("protein", 400);
		oneFood.addFoodItem(newFood2);
		
		oneFood.saveFoodItems("fileTest.txt");
		oneFood.loadFoodItems("fileTest.txt");
		if (oneFood.getAllFoodItems().get(1).getID().equals("id2")) {
			assertTrue(true);
		}
		else {
			fail("Fuck");
		}
	}

}
