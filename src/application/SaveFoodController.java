package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * Simple Controller that allows the user to save the current food list as a .csv file
 * 
 * @author Harrison
 *
 */
public class SaveFoodController {
	@FXML
	private TextField UserFileName;
	@FXML
	private Button Save;
	
	private FoodData food;
	private Stage thisStage;
	private PrimaryGUI primController;

	/**
	 * Constructor to initialize window and provide access to FoodData
	 * 
	 * @param primaryGUI - allows access to primaryGUI, therefore to FoodData
	 */
	public SaveFoodController(PrimaryGUI primaryGUI) {
		thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveFood.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Save Food List");

        } catch (IOException e) {
            e.printStackTrace();
        }
		primController = primaryGUI;
		food = primController.foodDataAccess();
	}

	// Pulls preferred file name from text field and passes to FoodData in order
	// to create a file with said name. Window closes upon saving.
	// Event Listener on Button[#Save].onAction
	@FXML
	public void callToFoodDataSave(ActionEvent event) {
		String fileName = UserFileName.getText();
		String fullFileName = fileName + ".csv";
		food.saveFoodItems(fullFileName);
		thisStage.close();
	}
	public void showStage() {
		thisStage.showAndWait();
		
	}
}
