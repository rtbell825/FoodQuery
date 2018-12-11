package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class AddFoodItemController {
	
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

	public AddFoodItemController(PrimaryGUI primaryGUI, FoodData food) {
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
        this.food = food;
	}

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
	}
	
	public void allowFoodDataAccess (FoodData foodData) {
		
	}

	public void showStage() {
		thisStage.showAndWait();
	}
}
