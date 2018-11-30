package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		System.out.println("This is a test");
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("FoodAnalyzer.fxml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root, 800, 800);
		stage.setTitle("FXML Welcome");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		//This is a test
	}
}
