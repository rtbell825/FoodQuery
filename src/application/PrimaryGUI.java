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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
		
	}
	// Event Listener on Button[#AddToFoodList].onAction
	@FXML
	public void launchAddFoodWindow(ActionEvent event) {
		AddFoodItemController controller2 = new AddFoodItemController(this);
		controller2.showStage();
		
		/*
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodAnalyzer.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AddFoodItemController controller = loader.<AddFoodItemController>getController();
		controller.food = food;
		
		Scene addFoodScene = new Scene(root);
		Stage addFoodStage = new Stage();
		addFoodStage.setTitle("Add Food");
		addFoodStage.setScene(addFoodScene);
		addFoodStage.show();
		addFoodStage.setResizable(false);
		*/
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
		
		/*
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FoodAnalyzer.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AnalyzeMealController controller = loader.<AnalyzeMealController>getController();
		controller.food = food;
		
		Scene analyzeMealScene = new Scene(root);
		Stage analyzeMealStage = new Stage();
		analyzeMealStage.setTitle("Analyze Meal");
		analyzeMealStage.setScene(analyzeMealScene);
		analyzeMealStage.show();
		analyzeMealStage.setResizable(false);
		*/
	}
	@FXML
	public void resetAnalyzer(ActionEvent event) {
		
	}
	@FXML
	public void saveFoodList(ActionEvent event) {
		
	}
	@FXML
	public void loadFoodList(ActionEvent event) {
		
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
		//List<FoodItem> foodTest = new ArrayList<FoodItem>();
		//foodTest = food.getAllFoodItems();
		//System.out.println(foodTest.size());
		foodObList = FXCollections.observableArrayList(food.getAllFoodItems());
		FoodList.setItems(foodObList);
		MealList.setItems(mealObList);
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
