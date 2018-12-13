package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Controller that allows a user to input values in order to create a custom food item
 * and add it to the current food list
 * 
 * @author Harrison
 *
 */
public class AddFoodItemController extends PrimaryGUI{
	
	@FXML
	private TextField FoodName;
	@FXML
	private TextField IDnumber;
	@FXML
	private TextField Calories;
	@FXML
	private TextField Fat;
	@FXML
	private TextField Carbs;
	@FXML
	private TextField Fiber;
	@FXML
	private TextField Protein;
	@FXML
	private Button AddFoodItem;
	
	private final Stage thisStage;
	PrimaryGUI primController;
	FoodData food;


	/**
	 * Initializes window in which to describe food and allows access to FoodData through
	 * the passing of the Primary controller's instance of itself
	 * 
	 * @param primaryGUI - allows access to primaryGUI, therefore to FoodData
	 */
	public AddFoodItemController(PrimaryGUI primaryGUI) {
		// Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFoodItem.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Add A Food Item");

        } catch (IOException e) {
            e.printStackTrace();
        }
        primController = primaryGUI;
        this.food = primController.foodDataAccess();

	}

	// Pulls input from Food name and nutrient value text boxes, then creates a new
	// food item with the desired values. The food item is then added to the food list
	// through calling addFoodItem in FoodData
	// Event Listener on Button[#AddFoodItem].onAction
	@FXML
	public void addFoodItemToFoodList(ActionEvent event) {
		
		String name = FoodName.getText();
		String id = IDnumber.getText();
		Double cals = Double.parseDouble(Calories.getText());
		Double fat = Double.parseDouble(Fat.getText());
		Double carbs = Double.parseDouble(Carbs.getText());
		Double fiber = Double.parseDouble(Fiber.getText());
		Double protein = Double.parseDouble(Protein.getText());
		
		FoodItem addedFood = new FoodItem(id, name);
		addedFood.addNutrient("calories", cals);
		addedFood.addNutrient("fat", fat);
		addedFood.addNutrient("carbohydrate", carbs);
		addedFood.addNutrient("fiber", fiber);
		addedFood.addNutrient("protein", protein);
		
		food.addFoodItem(addedFood);
		
		primController.initialize();
	}

	public void showStage() {
		thisStage.showAndWait();
	}
}
