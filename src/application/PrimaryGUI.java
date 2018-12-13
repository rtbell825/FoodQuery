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

/**
 * This class assists in coordinating data changes between what is displayed by the FXML
 * file what what is stored within food data.
 * 
 * @author Harrison
 *
 */
public class PrimaryGUI{

	private FoodData food;
	private final Stage thisStage;
	private ObservableList<FoodItem> foodObList = FXCollections.observableArrayList();
	private ObservableList<FoodItem> mealObList = FXCollections.observableArrayList();
	private ObservableList<FoodItem> searchObList = FXCollections.observableArrayList();
	private boolean searching;
	
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
	@FXML
	private Button ClearMealList;
	@FXML
	private Button ClearSearch;

	/**
	 * Custom constructor for GUI so that a FoodData object can be instantiated.
	 */
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
	// Adds the selected food list object to the meal list
	// Event Listener on Button[#AddToMealList].onAction
	@FXML
	public void addToMealList(ActionEvent event) {
		FoodItem currItem = FoodList.getSelectionModel().getSelectedItem();
		
		mealObList.add(currItem);	
	}
	// removes the selected meal list object from the meal list
	// Event Listener on Button[#RemoveFromMealList].onAction
	@FXML
	public void removeFromMealList(ActionEvent event) {
		FoodItem currItem = MealList.getSelectionModel().getSelectedItem();
		
		mealObList.remove(currItem);
		initialize();
	}
	// Launches a new window when the user wishes to add a new food.
	// Event Listener on Button[#AddToFoodList].onAction
	@FXML
	public void launchAddFoodWindow(ActionEvent event) {
		AddFoodItemController controller2 = new AddFoodItemController(this);
		controller2.showStage();
		initialize();

	}
	// Pulls user input from text fields and radio buttons to construct arguments
	// for query methods within food data. Has three cases.
	// 1. All inputs are valid - display food items that exist in both results
	// 2. Only nutrient arguments are entered
	// 3. Only a food name is entered
	// Event Listener on Button[#SearchFoodList].onAction
	@FXML
	public void searchFoodList(ActionEvent event) {
		List<FoodItem> returnedNameList = new ArrayList<FoodItem>();
		List<FoodItem> returnedNutrientList = new ArrayList<FoodItem>();
		
		
		List<String> rules = new ArrayList<String>();
		searching = true;
		searchObList.clear();
		String comparator = "";
		String foodName = SearchFoodName.getText();
		String nutrientName = SearchNutrientName.getText();
		String nutrientAmount = SearchNutrientAmount.getText();
		if(SearchLessThan.isSelected()) {
			comparator = "<=";
		}
		else if(SearchEqual.isSelected()) {
			comparator = "==";
		}
		else if(SearchGreaterThan.isSelected()) {
			comparator = ">=";
		}
		
		if(nutrientName != null && comparator != "" && nutrientAmount != null && foodName != null) {
			returnedNameList = food.filterByName(foodName);
			
			rules.add(nutrientName);
			rules.add(comparator);
			rules.add(nutrientAmount);
			returnedNutrientList = food.filterByNutrients(rules);
			for(int i = 0; i < returnedNameList.size(); ++i) {
				if(returnedNutrientList.contains(returnedNameList.get(i))) {
					searchObList.add(returnedNameList.get(i));
				}
			}
			
		}
		else if(nutrientName != null && comparator != "" && nutrientAmount != null && foodName == null) {
			rules.add(nutrientName);
			rules.add(comparator);
			rules.add(nutrientAmount);
			returnedNutrientList = food.filterByNutrients(rules);
			searchObList.addAll(returnedNutrientList);
		}
		else if(foodName != null && nutrientName.isEmpty() && nutrientAmount.isEmpty()) {
			returnedNameList = food.filterByName(foodName);
			searchObList.addAll(returnedNameList);
		}
		
		
		initialize();
		
		
		
	}
	//resets all search parameters, both internal data and GUI displayed
	public void clearSearch(ActionEvent event) {
		searching = false;
		SearchLessThan.setSelected(false);
		SearchEqual.setSelected(false);
		SearchGreaterThan.setSelected(false);
		SearchFoodName.clear();
		SearchNutrientName.clear();
		SearchNutrientAmount.clear();
		initialize();
	}
	// launches the meal analyzer window and passes in the current meal list
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
	// Opens a new window that asks user for a desired filename
	@FXML
	public void saveFoodList(ActionEvent event) {
		SaveFoodController controller4 = new SaveFoodController(this);
		controller4.showStage();
	}
	// Utilizes file choser, parses file name as string, passes string as argument to
	// FoodData so that the file may be loaded into the application
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
		}
		initialize();
	}
	@FXML
	public void launchInstructionsWindow(ActionEvent event) {
		
	}
	@FXML
	public void clearMealList(ActionEvent event) {
		mealObList.clear();
	}

	public FoodData foodDataAccess() {
		return food;
	}
	public void showStage() {
		thisStage.showAndWait();
	}
	
	// Method used to initialize application on startup, and then update the GUIs
	// display as data values change.
	public void initialize() {

		foodObList = FXCollections.observableArrayList(food.getAllFoodItems());
		if(searching) {
			FoodList.setItems(searchObList);
		}
		else {
			FoodList.setItems(foodObList);
		}
		MealList.setItems(mealObList);
		TotalFoodListItems.setText(String.valueOf(foodObList.size()));
		
		if(!searchObList.isEmpty()) {
			Collections.sort(searchObList, new Comparator<FoodItem>() {

		        public int compare(FoodItem foodOne, FoodItem foodTwo) {
		            // compare two instance of `Score` and return `int` as result.
		            return foodOne.getName().compareToIgnoreCase(foodTwo.getName());
		        }
		    });
		}
		
		
		Collections.sort(foodObList, new Comparator<FoodItem>() {

	        public int compare(FoodItem foodOne, FoodItem foodTwo) {
	            // compare two instance of `Score` and return `int` as result.
	            return foodOne.getName().compareToIgnoreCase(foodTwo.getName());
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
