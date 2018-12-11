package application;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FoodDataTest {
	
	public static FoodData food;
	public static FoodData oneFood;

	@BeforeClass
	public static void startShit() {
		food = new FoodData();
		food.loadFoodItems("foodItems.csv");
		oneFood = new FoodData();
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
	public void testLoadFoodItemsOneKeyBP() {
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

}
