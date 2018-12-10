package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.util.List;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		//These lines are to test FoodData.java
		//FoodData food = new FoodData();
		//food.loadFoodItems("foodItems.csv");
		//List<FoodItem> foodItems = food.getAllFoodItems();
		//System.out.println("This is a test");
		//for (int i = 0; i < foodItems.size(); ++i) {
		//	System.out.println(foodItems.get(i).getName());
		//}
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("FoodAnalyzer.fxml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root, 800, 800);
		stage.setTitle("Food Analyzer");
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
