package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;

public class PrimaryGUI{

	private FoodData food;
	private final Stage thisStage;
	private ObservableList<FoodItem> foodObList = FXCollections.observableArrayList();
	private ObservableList<FoodItem> mealObList = FXCollections.observableArrayList();
	
	@FXML
	private MenuItem Reset;
	@FXML
	private MenuItem SaveList;
	@FXML
	private MenuItem LoadList;
	@FXML
	private MenuItem Help;
	@FXML
	private ListView<FoodItem> FoodList = new ListView<>();
	@FXML
	private ListView<FoodItem> MealList = new ListView<>();;
	@FXML
	private Button AddToMealList;
	@FXML
	private Button RemoveFromMealList;
	@FXML
	private Button AddToFoodList;
	@FXML
	private Label TotalFoodListItems;
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


	public PrimaryGUI() {
		food = new FoodData();
		food.loadFoodItems("foodItems.csv");
		
		thisStage = new Stage();
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodAnalyzer.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Food Analyzer");

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
	}
	// Event Listener on Button[#AddToMealList].onAction
	@FXML
	public void addToMealList(ActionEvent event) {
		FoodItem currItem = FoodList.getSelectionModel().getSelectedItem();
		
		mealObList.add(currItem);
		
		
	}
	// Event Listener on Button[#RemoveFromMealList].onAction
	@FXML
	public void removeFromMealList(ActionEvent event) {
		FoodItem currItem = MealList.getSelectionModel().getSelectedItem();
		
		mealObList.remove(currItem);
		initialize();
	}
	// Event Listener on Button[#AddToFoodList].onAction
	@FXML
	public void launchAddFoodWindow(ActionEvent event) {
		AddFoodItemController controller2 = new AddFoodItemController(this, FoodList);
		controller2.showStage();
		initialize();

	}
	// Event Listener on Button[#SearchFoodList].onAction
	@FXML
	public void searchFoodList(ActionEvent event) {
		
	}
	// Event Listener on Button[#AnalyzeMeal].onAction
	@FXML
	public void launchMealAnalyzerWindow(ActionEvent event) {
		List<FoodItem> analyzerList = new ArrayList<FoodItem>();
		analyzerList = mealObList;
		AnalyzeMealController controller3 = new AnalyzeMealController(this, analyzerList);
		controller3.showStage();

	}
	@FXML
	public void resetAnalyzer(ActionEvent event) {
		
	}
	@FXML
	public void saveFoodList(ActionEvent event) {
		
	}
	@FXML
	public void loadFoodList(ActionEvent event) {
		String chosenFile = "";
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(thisStage);
		if(file != null) {
			chosenFile = file.getName();
		}
		if(chosenFile != "") {
			food = new FoodData();
			food.loadFoodItems(chosenFile);
			System.out.println(foodObList.size());
		}
		initialize();
	}
	@FXML
	public void launchInstructionsWindow(ActionEvent event) {
		
	}

	public FoodData foodDataAccess() {
		return food;
	}
	public void showStage() {
		thisStage.showAndWait();
	}
	
	public void initialize() {

		foodObList = FXCollections.observableArrayList(food.getAllFoodItems());
		FoodList.setItems(foodObList);
		MealList.setItems(mealObList);
		TotalFoodListItems.setText(String.valueOf(foodObList.size()));
		
		Collections.sort(foodObList, new Comparator<FoodItem>() {

	        public int compare(FoodItem o1, FoodItem o2) {
	            // compare two instance of `Score` and return `int` as result.
	            return o1.getName().compareToIgnoreCase(o2.getName());
	        }
	    });

		FoodList.setCellFactory(new Callback<ListView<FoodItem>, ListCell<FoodItem>>(){
			 
            @Override
            public ListCell<FoodItem> call(ListView<FoodItem> p) {
                 
                ListCell<FoodItem> cell = new ListCell<FoodItem>(){
 
                    @Override
                    protected void updateItem(FoodItem t, boolean flag) {
                        super.updateItem(t, flag);
                        if (t != null) {
                            setText(t.getName());
                        }
                    }
 
                };
                 
                return cell;
            }
        });
		
		MealList.setCellFactory(new Callback<ListView<FoodItem>, ListCell<FoodItem>>(){
			 
            @Override
            public ListCell<FoodItem> call(ListView<FoodItem> a) {
                 
                ListCell<FoodItem> cell = new ListCell<FoodItem>(){
 
                    @Override
                    protected void updateItem(FoodItem t, boolean flag) {
                        super.updateItem(t, flag);
                        if (t != null) {
                            setText(t.getName());
                        }
                    }
 
                };
                 
                return cell;
            }
        });
		
	}
}
