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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class AnalyzeMealController {
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
	
	public FoodData food;
	PrimaryGUI primController;
	private final Stage thisStage;

	public AnalyzeMealController(PrimaryGUI primaryGUI, FoodData food) {
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
		this.food = food;
	}

	// Event Listener on Button[#AnalyzeTotalMeal].onAction
	@FXML
	public void totalNutrientsInMeal(ActionEvent event) {
		int cal = 550;
		int fat = 12;
		int carb = 10;
		int fiber = 6;
		int protein = 22;
		
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
