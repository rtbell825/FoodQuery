package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.control.RadioButton;

public class FoodAnalyzerController {
	@FXML
	private ListView FoodList;
	@FXML
	private ListView MealList;
	@FXML
	private Button AddToMealList;
	@FXML
	private Button RemoveFromMealList;
	@FXML
	private Button AddToFoodList;
	@FXML
	private TextField TotalFoodListItems;
	@FXML
	private TextField SearchFoodName;
	@FXML
	private TextField SearchNutrientName;
	@FXML
	private RadioButton SearchLessThan;
	@FXML
	private RadioButton SearchEqual;
	@FXML
	private RadioButton SearchGreaterThan;
	@FXML
	private TextField SearchNutrientAmount;
	@FXML
	private Button SearchFoodList;
	@FXML
	private Button AnalyzeMeal;

	// Event Listener on Button[#AddToMealList].onAction
	@FXML
	public void addToMealList(ActionEvent event) {
		
	}
	// Event Listener on Button[#RemoveFromMealList].onAction
	@FXML
	public void removeFromMealList(ActionEvent event) {
		
	}
	// Event Listener on Button[#AddToFoodList].onAction
	@FXML
	public void launchAddFoodWindow(ActionEvent event) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("AddFoodItem.fxml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Scene addFoodScene = new Scene(root);
		Stage addFoodStage = new Stage();
		addFoodStage.setTitle("Add Food");
		addFoodStage.setScene(addFoodScene);
		addFoodStage.show();
		addFoodStage.setResizable(false);
	}
	// Event Listener on Button[#SearchFoodList].onAction
	@FXML
	public void searchFoodList(ActionEvent event) {
		
	}
	// Event Listener on Button[#AnalyzeMeal].onAction
	@FXML
	public void launchMealAnalyzerWindow(ActionEvent event) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("AnalyzeMeal.fxml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Scene analyzeMealScene = new Scene(root);
		Stage analyzeMealStage = new Stage();
		analyzeMealStage.setTitle("Analyze Meal");
		analyzeMealStage.setScene(analyzeMealScene);
		analyzeMealStage.show();
		analyzeMealStage.setResizable(false);
	}
}
