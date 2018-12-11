package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;


public class Main extends Application {
	
	
	
	@Override
	public void start(Stage stage) {
		
		//These lines are to test FoodData.java
		FoodData food = new FoodData();
		food.loadFoodItems("foodItems.csv");
		
		PrimaryGUI controller = new PrimaryGUI(food);
		controller.showStage();
		
		/*
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodAnalyzer.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrimaryGUI controller = loader.<PrimaryGUI>getController();
		controller.food = food;
		
		Scene scene = new Scene(root, 800, 800);
		stage.setTitle("Food Analyzer");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		*/
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
