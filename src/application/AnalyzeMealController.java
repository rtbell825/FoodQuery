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
		for(int i = 0; i < mealList.size(); ++i) {
			System.out.println(mealList.get(i).getName());
		}
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
