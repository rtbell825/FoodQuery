package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * Controller class that allows a user to analyze the nutritional content of their selected
 * meal list
 * 
 * @author Harrison
 *
 */
public class AnalyzeMealController extends PrimaryGUI{
	@FXML
	private Label TotalCalories;
	@FXML
	private Label TotalFat;
	@FXML
	private Label TotalCarbs;
	@FXML
	private Label TotalFiber;
	@FXML
	private Label TotalProtein;
	@FXML
	private Button AnalyzeTotalMeal;
	@FXML
	private PieChart Chart;
	
	private FoodData food;
	private PrimaryGUI primController;
	private final Stage thisStage;
	private List<FoodItem> mealList;

	/**
	 * Constructor to initialize window and assign required parameters to field variables
	 * 
	 * @param primaryGUI - allows access to primaryGUI, therefore to FoodData
	 * @param list - meal list passed in to be analyzed
	 */
	public AnalyzeMealController(PrimaryGUI primaryGUI, List<FoodItem> list) {
		// Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AnalyzeMeal.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Analyze Your Meal");

        } catch (IOException e) {
            e.printStackTrace();
        }
		primController = primaryGUI;
		food = primController.foodDataAccess();
		mealList = list;
		
	}
	
	// Pulls and tracks all nutrient values for each food item in the meal list.
	// Displays total values in GUI through labels and a pie chart.
	// Event Listener on Button[#AnalyzeTotalMeal].onAction
	@FXML
	public void totalNutrientsInMeal(ActionEvent event) {
		Double cal = 0.0;
		Double fat = 0.0;
		Double carb = 0.0;
		Double fiber = 0.0;
		Double protein = 0.0;
		
		for(int i = 0; i < mealList.size(); ++i) {
			cal = cal + mealList.get(i).getNutrientValue("calories");
			fat = fat + mealList.get(i).getNutrientValue("fat");
			carb = carb + mealList.get(i).getNutrientValue("carbohydrate");
			fiber = fiber + mealList.get(i).getNutrientValue("fiber");
			protein = protein + mealList.get(i).getNutrientValue("fiber");
		}

		TotalCalories.setText(String.valueOf(cal));
		TotalFat.setText(String.valueOf(fat));
		TotalCarbs.setText(String.valueOf(carb));
		TotalFiber.setText(String.valueOf(fiber));
		TotalProtein.setText(String.valueOf(protein));
		
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		data.add(new PieChart.Data("Fat", fat));
		data.add(new PieChart.Data("Carbs", carb));
		data.add(new PieChart.Data("Fiber", fiber));
		data.add(new PieChart.Data("Protein", protein));

		Chart.setData(data);
	}

	public void showStage() {
		thisStage.showAndWait();
		
	}
}
