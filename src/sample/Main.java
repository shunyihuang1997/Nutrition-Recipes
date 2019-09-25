package sample;

import java.sql.*;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.table.TableColumn;

import static com.sun.tools.internal.xjc.reader.Ring.add;

////password health_or_hedonism  asd13368989012
public class Main extends Application implements EventHandler<ActionEvent> {
    static final String databasePrefix ="test_health_hedo"; // need to change
    static final String netID ="root"; // Please enter your netId
    static final String hostName ="localhost"; //washington.uww.edu
    static final String databaseURL ="jdbc:mysql://"+hostName+"/"+databasePrefix+"?autoReconnect=true & useSSL=false&serverTimezone=CST";
    static final String password="asd13368989012"; // please enter your own password
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static PreparedStatement preparedStmt = null;
    static sample.AlertBox alertbox= new sample.AlertBox();
    static int curUser = 0;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("databaseURL"+ databaseURL);
            connection = DriverManager.getConnection(databaseURL, netID, password);
            System.out.println("Successfully connected to the database");
            launch(args);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Welcome to the recipes selection");
        first(primaryStage);

    }


    public void first(Stage primaryStage) throws Exception {
        // Button setup
        Button login = new Button();
        login.setText("Log in");
        Button sign_up = new Button();
        sign_up.setText("Sign up");
        login.setOnAction(this);
        sign_up.setOnAction(this);


        //TODO: exception

        sign_up.setOnAction(e->sign_upButton(primaryStage));


        // Define the root for the scene
        VBox root = new VBox(); // The overall layout of the scene
        root.setPadding(new Insets(350, 0, 0, 0));

        // The first part, title HBox
        HBox titleHBox = new HBox(20);
        titleHBox.setPadding(new Insets(0, 0, 0, 250));
        // TODO: Set style to the HBox

        Text leftText = new Text("Healthism");
        leftText.setFont(Font.font("Verdana", 40));
        // TODO:set style to the left text
        titleHBox.getChildren().add(leftText);

        Text midText = new Text("or");
        midText.setFont(Font.font("Verdana", 40));
        // TODO:set style to the left text
        titleHBox.getChildren().add(midText);

        Text rightText = new Text("Hedonism");
        rightText.setFont(Font.font("Verdana", 40));
        // TODO:set style to the left text
        titleHBox.getChildren().add(rightText);

        // After all components have added to the titleHBox, add the finished HBox to the root VBox
        root.getChildren().add(titleHBox);


        //Setting Vbox, and put two Hbox
        HBox UserHBox = new HBox();
        UserHBox.setSpacing(20);
        HBox PasswordHBox = new HBox();
        PasswordHBox.setSpacing(20);

        // Username
        Label name = new Label("Username");

        // Username input
        TextField nameInput = new TextField("Type in your username");

        // Password
        Label password = new Label("Password");

        // Password input
        //TextField passwordInput = new TextField("Type in your password");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Type in your password");


        login.setOnAction(e->loginButton(primaryStage,nameInput.getText(), passwordInput.getText()));

        UserHBox.getChildren().addAll(name, nameInput, login);
        UserHBox.setPadding(new Insets(20,0,0,300));
        PasswordHBox.getChildren().addAll(password, passwordInput, sign_up);
        PasswordHBox.setPadding(new Insets(10,0,0,300));

        root.getChildren().addAll(UserHBox, PasswordHBox);

        Scene loginScene = new Scene(root,1000,1000); // Set the layout to the scene
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public void second(Stage primaryStage) throws Exception {
        // Set a Vbox as the general structure
        VBox generalStructure = new VBox();

        // Set a Hbox to put the title in
        Text title = new Text("Which one do you prefer today?");
        title.setFont(Font.font("Verdana", 40));
        HBox hb = new HBox(20);
        hb.setPadding(new Insets(200, 0, 50, 0));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(title);
        generalStructure.getChildren().add(hb);

        // Set a Hbox for the buttons of healthism and hedonism
        HBox HH = new HBox();
        HH.setSpacing(20);

        Text or = new Text("OR");
        Label HealthnismButton = new Label("Healthism");
        Label HedonismButton = new Label("Hedonism");
        HealthnismButton.setFont(Font.font("Verdana", 40));
        HedonismButton.setFont(Font.font("Verdana", 40));
        or.setFont(Font.font("Verdana", 40));

        Button healthism = new Button();
        healthism.setOnAction(e -> healthnismButton(primaryStage));
        healthism.setStyle("-fx-font-size:32");
        healthism.setText("Healthism");
        healthism.setTextFill(Color.GREEN);
        healthism.setMinSize(200, 150);
        healthism.setPadding(new Insets(100, 20, 100, 20));
        Button hedonism = new Button();
        hedonism.setOnAction(e -> hedonismButton(primaryStage));
        hedonism.setStyle("-fx-font-size:32");
        hedonism.setText("Hedonism");
        hedonism.setTextFill(Color.RED);
        hedonism.setMinSize(200, 150);
        hedonism.setPadding(new Insets(100, 20, 100, 20));

        Image image = new Image("junk.jpeg");
        ImageView IV = new ImageView(image);
        IV.setFitWidth(220);
        IV.setFitHeight(220);

        Image image2 = new Image("health.jpeg");
        ImageView IV2 = new ImageView(image2);
        IV2.setFitWidth(220);
        IV2.setFitHeight(220);

        HH.getChildren().addAll(IV, healthism, or, hedonism, IV2);
        HH.setAlignment(Pos.CENTER);
        generalStructure.getChildren().add(HH);

        // Another Hbox for My favorite button
        HBox Fhb = new HBox();
        Fhb.setPadding(new Insets(100, 0, 0, 0));
        Fhb.setSpacing(20);
        Fhb.setAlignment(Pos.CENTER);
        Button favorite = new Button();
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("My favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(300, 100);
        favorite.setOnAction(e -> favoriteButton(primaryStage));
        Fhb.getChildren().add(favorite);
        generalStructure.getChildren().add(Fhb);

        Scene Selection = new Scene(generalStructure, 1000, 1000);
        primaryStage.setScene(Selection);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    //Third scene
    public void third(Stage primaryStage) throws Exception {
        List<Category> categoryList = new ArrayList<>();
        try{
            String sqlQuery = " select * from categories";
            preparedStmt = connection.prepareStatement(sqlQuery);
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Category newCategory = new Category();
                newCategory.category_ID = resultSet.getInt(1);
                newCategory.display_name = resultSet.getString(2);
                categoryList.add(newCategory);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }



        // Using borderpane
        BorderPane bp = new BorderPane();
        //initialize the SelectedInfo
        sample.SelectedInfo selectedInfo = new sample.SelectedInfo();

        // Put title in a Hbox
        HBox Thb = new HBox();
        Thb.setAlignment(Pos.CENTER);
        Text title = new Text("Welcome to our healthism recipes");
        title.setFont(Font.font("Verdana", 40));
        Thb.getChildren().add(title);
        bp.setTop(Thb);

        // My favorite, Hedonism and logout button by using Vbox
        VBox Vfb = new VBox();

        Button favorite = new Button();
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("My favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(150, 100);
        favorite.setOnAction(e -> favoriteButton(primaryStage));

        Button hedonism = new Button();
        hedonism.setStyle("-fx-font-size:20");
        hedonism.setText("Hedonism");
        hedonism.setTextFill(Color.BLUE);
        hedonism.setMinSize(150, 100);
        hedonism.setOnAction(e -> hedonismButton(primaryStage));

        Button logout = new Button();
        logout.setStyle("-fx-font-size:20");
        logout.setText("Logout");
        logout.setTextFill(Color.BLUE);
        logout.setMinSize(150, 100);
        logout.setOnAction(e -> logoutButton(primaryStage));

        Vfb.getChildren().addAll(favorite, hedonism, logout);
        Vfb.setPadding(new Insets(100, 0, 0, 0));
        bp.setLeft(Vfb);

        // Setting calories using Vbox

        VBox calories = new VBox(30);
        HBox midHBox = new HBox();
        Text cal = new Text("Calories");
        midHBox.getChildren().add(cal);
        cal.setFont(Font.font("Verdana", 30));
        calories.setPadding(new Insets(100, 0, 0, 200));
        bp.setCenter(calories);

        // the toggle group
        ToggleGroup caloriesGroup = new ToggleGroup();
        RadioButton toggle1 = new RadioButton("High(700 Kcal)");
        toggle1.setToggleGroup(caloriesGroup);
        //toggle1.setSelected(true);
        toggle1.setUserData(new Integer (700));
        RadioButton toggle2 = new RadioButton("Medium(500 Kcal)");
        toggle2.setToggleGroup(caloriesGroup);
        toggle2.setUserData(new Integer (500));
        RadioButton toggle3 = new RadioButton("Low(200 Kcal)");
        toggle3.setToggleGroup(caloriesGroup);
        toggle3.setUserData(new Integer (200));
        caloriesGroup.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    if (selectedInfo.isSpecific == false) {
                        selectedInfo.selectedCalories = (int) caloriesGroup.getSelectedToggle().getUserData();
                    }
                });

        //specific caloires
        VBox specificVBox = new VBox();
        specificVBox.setMaxWidth(170);
        Label specificLabel = new Label("Search by calorie threshold");
        TextField specificText = new TextField();

        //update the selectedCalories when textfield changed
        specificText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("")) {
                    selectedInfo.isSpecific = false;
                } else {
                    try {
                        int value = Integer.parseInt(newValue);
                        selectedInfo.selectedCalories = value;
                        selectedInfo.isSpecific = true;
                    } catch(NumberFormatException e) {
                        alertbox.display("Error", "The input of specific calories must be integer");
                    }
                }
            }
        });
        specificVBox.getChildren().addAll(specificLabel, specificText);
        specificVBox.setSpacing(20);
        specificVBox.setPadding(new Insets(50, 0, 0, 0));

        calories.setPadding(new Insets(100, 0, 0, 200));
        bp.setCenter(calories);
        calories.getChildren().addAll(midHBox, toggle1, toggle2, toggle3, specificVBox);

        // Setting another Vbox for lk and dislk
        VBox right = new VBox();


        // create the data to show in the CheckComboBox
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < categoryList.size(); i++) {
            items.add(categoryList.get(i).display_name);
        }

        // Create the checkComboBox for category
        VBox category = new VBox();
        Label categoryLabel = new Label("Included categories");
        CheckComboBox<String> lkCategoryCheckComboBox = new CheckComboBox<String>(items);
        category.getChildren().add(categoryLabel);
        category.getChildren().add(lkCategoryCheckComboBox);
        //category.setPadding(new Insets(100,0,0,0));


        //Create the dislike category checkbox
        VBox category2 = new VBox();
        Label categoryLabel2 = new Label("Excluded categories");
        CheckComboBox<String> dislkCategoryCheckComboBox = new CheckComboBox<String>(items);
        category2.getChildren().add(categoryLabel2);
        category2.getChildren().add(dislkCategoryCheckComboBox);
        category2.setPadding(new Insets(50,0,0,0));




        //Put a textfiled to search by the recipe's name
        VBox titleSearch = new VBox();
        Label recipeName = new Label("Search by name of recipe");
        TextField titleField = new TextField();
        titleSearch.getChildren().add(recipeName);
        titleField.setMaxWidth(170);



        //update the tile data in selectedInfo
        titleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedInfo.title  = titleField.getText();
            }
        });

        titleSearch.getChildren().add(titleField);
        titleSearch.setPadding(new Insets(30,0,0,0));
        calories.getChildren().add(titleSearch);


        // Put search button in a hbox
        Button search = new Button();
        search.setMinSize(400, 100);
        HBox searchBox = new HBox();
        search.setText("Search");
        searchBox.getChildren().add(search);
        searchBox.setPadding(new Insets(-400, 0, 0, 250));
        search.setOnAction(e -> {
            searchButton(primaryStage, selectedInfo, lkCategoryCheckComboBox.getCheckModel().getCheckedIndices(), dislkCategoryCheckComboBox.getCheckModel().getCheckedIndices());
        });
        bp.setBottom(searchBox);

        // putting those sub box into the general one
        right.getChildren().addAll( category,category2);
        right.setPadding(new Insets(100, 50, 0, 0));
        right.setSpacing(80);
        bp.setRight(right);

        Scene inHealthnism = new Scene(bp, 1000, 1000);
        primaryStage.setScene(inHealthnism);
        primaryStage.setResizable(true);
        primaryStage.show();

    }


    //fourth scene
    public void fourth(Stage primaryStage) throws Exception {
        List<Category> categoryList = new ArrayList<>();
        try{
            String sqlQuery = " select * from categories";
            preparedStmt = connection.prepareStatement(sqlQuery);
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Category newCategory = new Category();
                newCategory.category_ID = resultSet.getInt(1);
                newCategory.display_name = resultSet.getString(2);
                categoryList.add(newCategory);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }



        // Using borderpane
        BorderPane bp = new BorderPane();
        //initialize the SelectedInfo
        sample.SelectedInfo selectedInfo = new sample.SelectedInfo();

        // Put title in a Hbox
        HBox Thb = new HBox();
        Thb.setAlignment(Pos.CENTER);
        Text title = new Text("Welcome to our hedonism recipes");
        title.setFont(Font.font("Verdana", 40));
        Thb.getChildren().add(title);
        bp.setTop(Thb);

        // My favorite, Hedonism and logout button by using Vbox
        VBox Vfb = new VBox();

        Button favorite = new Button();
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("My favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(150, 100);
        favorite.setOnAction(e -> favoriteButton(primaryStage));

        Button hedonism = new Button();
        hedonism.setStyle("-fx-font-size:20");
        hedonism.setText("Healthism");
        hedonism.setTextFill(Color.BLUE);
        hedonism.setMinSize(150, 100);
        hedonism.setOnAction(e -> healthnismButton(primaryStage));

        Button logout = new Button();
        logout.setStyle("-fx-font-size:20");
        logout.setText("Logout");
        logout.setTextFill(Color.BLUE);
        logout.setMinSize(150, 100);
        logout.setOnAction(e -> logoutButton(primaryStage));

        Vfb.getChildren().addAll(favorite, hedonism, logout);
        Vfb.setPadding(new Insets(100, 0, 0, 0));
        bp.setLeft(Vfb);

        // Setting calories using Vbox

        VBox calories = new VBox(30);
        HBox midHBox = new HBox();
        Text cal = new Text("Calories");
        midHBox.getChildren().add(cal);
        cal.setFont(Font.font("Verdana", 30));
        calories.setPadding(new Insets(100, 0, 0, 200));
        bp.setCenter(calories);

        // the toggle group
        ToggleGroup caloriesGroup = new ToggleGroup();
        RadioButton toggle1 = new RadioButton("High(2000 Kcal)");
        toggle1.setToggleGroup(caloriesGroup);
        toggle1.setUserData(new Integer (2000));
        RadioButton toggle2 = new RadioButton("Medium(1700 Kcal)");
        toggle2.setToggleGroup(caloriesGroup);
        toggle2.setUserData(new Integer (1700));
        RadioButton toggle3 = new RadioButton("Low(1500 Kcal)");
        toggle3.setToggleGroup(caloriesGroup);
        toggle3.setUserData(new Integer (1500));
        caloriesGroup.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    if (selectedInfo.isSpecific == false) {
                        selectedInfo.selectedCalories = (int) caloriesGroup.getSelectedToggle().getUserData();
                    }
                });

        //specific caloires
        VBox specificVBox = new VBox();
        specificVBox.setMaxWidth(170);
        Label specificLabel = new Label("Search by calorie threshold");
        TextField specificText = new TextField();

        //update the selectedCalories when textfield changed
        specificText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("")) {
                    selectedInfo.isSpecific = false;
                } else {
                    try {
                        int value = Integer.parseInt(newValue);
                        selectedInfo.selectedCalories = value;
                        selectedInfo.isSpecific = true;
                    } catch(NumberFormatException e) {
                        alertbox.display("Error", "The input of specific calories must be integer");
                    }
                }
            }
        });
        specificVBox.getChildren().addAll(specificLabel, specificText);
        specificVBox.setSpacing(20);
        specificVBox.setPadding(new Insets(50, 0, 0, 0));

        calories.setPadding(new Insets(100, 0, 0, 200));
        bp.setCenter(calories);
        calories.getChildren().addAll(midHBox, toggle1, toggle2, toggle3, specificVBox);

        // Setting another Vbox for lk and dislk
        VBox right = new VBox();


        // create the data to show in the CheckComboBox
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < categoryList.size(); i++) {
            items.add(categoryList.get(i).display_name);
        }

        // Create the checkComboBox for category
        VBox category = new VBox();
        Label categoryLabel = new Label("Included categories");
        CheckComboBox<String> lkCategoryCheckComboBox = new CheckComboBox<String>(items);
        category.getChildren().add(categoryLabel);
        category.getChildren().add(lkCategoryCheckComboBox);
        //category.setPadding(new Insets(100,0,0,0));


        //Create the dislike category checkbox
        VBox category2 = new VBox();
        Label categoryLabel2 = new Label("Excluded Categories");
        CheckComboBox<String> dislkCategoryCheckComboBox = new CheckComboBox<String>(items);
        category2.getChildren().add(categoryLabel2);
        category2.getChildren().add(dislkCategoryCheckComboBox);
        category2.setPadding(new Insets(50,0,0,0));




        //Put a textfiled to search by the recipe's name
        VBox titleSearch = new VBox();
        Label recipeName = new Label("Search by name of recipe");
        TextField titleField = new TextField();
        titleSearch.getChildren().add(recipeName);
        titleField.setMaxWidth(170);



        //update the tile data in selectedInfo
        titleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedInfo.title  = titleField.getText();
            }
        });

        titleSearch.getChildren().add(titleField);
        titleSearch.setPadding(new Insets(30,0,0,0));
        calories.getChildren().add(titleSearch);


        // Put search button in a hbox
        Button search = new Button();
        search.setMinSize(400, 100);
        HBox searchBox = new HBox();
        search.setText("Search");
        searchBox.getChildren().add(search);
        searchBox.setPadding(new Insets(-400, 0, 0, 250));
        search.setOnAction(e -> {
            searchButton(primaryStage, selectedInfo, lkCategoryCheckComboBox.getCheckModel().getCheckedIndices(), dislkCategoryCheckComboBox.getCheckModel().getCheckedIndices());
        });
        bp.setBottom(searchBox);

        // putting those sub box into the general one
        right.getChildren().addAll( category,category2);
        right.setPadding(new Insets(100, 50, 0, 0));
        right.setSpacing(80);
        bp.setRight(right);

        Scene inHealthnism = new Scene(bp, 1000, 1000);
        primaryStage.setScene(inHealthnism);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void fifth(Stage primaryStage, ResultSet recipeResultSet) throws Exception {
        Set<sample.Recipe> recipeSet = new HashSet<sample.Recipe>();
        while (recipeResultSet.next()) {
            sample.Recipe newRecipe = new sample.Recipe();
            newRecipe.recipe_ID = recipeResultSet.getInt(1);
            newRecipe.recipe_title = recipeResultSet.getString(2);
            newRecipe.calories = recipeResultSet.getInt(3);
            newRecipe.directions = recipeResultSet.getString(4);
            newRecipe.ingredients = recipeResultSet.getString(5);
            recipeSet.add(newRecipe);
        }
        List<sample.Recipe> recipeList = new ArrayList<>(recipeSet);


        HBox hbox = new HBox();
        Button favorite = new Button("My Favorite");
        favorite = new Button();
        favorite.setOnAction(e -> favoriteButton(primaryStage));
        favorite.setStyle("-fx-font-size:20");
        favorite.setText("favorite");
        favorite.setTextFill(Color.BLUE);
        favorite.setMinSize(150, 100);
        Button back = new Button("Back");
        back.setStyle("-fx-font-size:20");
        back.setTextFill(Color.BLUE);
        back.setMinSize(150, 100);
        back.setOnAction(e -> backButton(primaryStage));

        // left part two button
        VBox left = new VBox();
        left.setPadding(new Insets(20, 20, 0, 0));
        left.getChildren().add(favorite);
        left.getChildren().add(back);
        left.setSpacing(10);

        // mid part
        VBox mid = new VBox(100);
        mid.setPadding(new Insets(20, 20, 0, 0));
        mid.setSpacing(10);
        mid.setMaxHeight(1000);
        Label resultLabel = new Label("Result");
        resultLabel.setTextAlignment(TextAlignment.CENTER);
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < recipeList.size(); i++) {
            items.add(recipeList.get(i).recipe_title);
        }

        list.setItems(items);
        list.prefHeight(300);
        mid.getChildren().addAll(resultLabel, list);

        // right part
        VBox right = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        ScrollPane ingreSp = new ScrollPane();
        scrollPane.setPrefSize(400,490);
        ingreSp.setPrefSize(400, 300);

        right.getChildren().add(scrollPane);
        right.getChildren().add(ingreSp);
        right.setSpacing(10);
        right.setMaxHeight(1000);
        right.setMinWidth(500);
        right.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        mid.setPadding(new Insets(20, 20, 0, 0));
        Text recipe = new Text("Detail here");
        recipe.setWrappingWidth(490);
        recipe.setFont(Font.font("Verdana", 16));

        recipe.prefHeight(490);
        recipe.prefWidth(400);
        scrollPane.setContent(recipe);


        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            int index = list.getSelectionModel().getSelectedIndex();
            String detail = "";
            detail += "TITLE: " + recipeList.get(index).recipe_title + '\n';
            detail += "\nCALORIES: " + recipeList.get(index).calories + '\n';
            detail += "\nINGREDIENT: \n" + recipeList.get(index).ingredients.replace('|', '\n');
            detail += "\n" + "\nDIRECTIONS: \n";
            detail += recipeList.get(index).directions.replace('|', '\n');
            recipe.setText(detail);
        });


        //Ingredient display box
        List<sample.Ingredient> ingredientList = new ArrayList<>();
        Label ingredientLabel = new Label("Ingredients");
        Text ingredient = new Text();
        ingredient.prefHeight(300);
        ingredient.prefWidth(400);
        ingreSp.setContent(ingredient);

        String sqlQuery = "Select * from ingredients order by name ASC";
        preparedStmt = connection.prepareStatement(sqlQuery);
        resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            sample.Ingredient newIngredient = new sample.Ingredient();
            newIngredient.Ingredeient_ID = resultSet.getInt(1);
            for(int i = 2; i <= 77; i++){
                newIngredient.strings.add(resultSet.getString(i));
            }
            ingredientList.add(newIngredient);
        }


        ListView<String> ingreList = new ListView<>();
        ingreList.prefHeight(300);
        ObservableList<String> ingreItems = FXCollections.observableArrayList();
        for (int i = 0; i < ingredientList.size(); i++) {
            ingreItems.add(ingredientList.get(i).strings.get(0));
        }
        ingreList.setItems(ingreItems);
        ingreList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            int index = ingreList.getSelectionModel().getSelectedIndex();
            String detail = "";
            for (int i = 0; i < 76; i++) {
                detail += ingredientList.get(index).name[i] + ": ";
                detail += ingredientList.get(index).strings.get(i) + "\n";
            }
            ingredient.setText(detail.replace('|', '\n'));
        });
        mid.getChildren().addAll(ingredientLabel, ingreList);



        // Love button
        Button like = new Button();
        like.setMinHeight(50);
        like.setMinWidth(50);
        like.setText("like");
        like.setOnAction(e-> likeButton(primaryStage, recipeList, list.getSelectionModel().getSelectedIndex()));
        VBox likeBox = new VBox();
        likeBox.setPadding(new Insets(10, 0, 0, 0));
        likeBox.getChildren().add(like);



        hbox.getChildren().add(left);
        hbox.getChildren().add(mid);
        hbox.getChildren().add(right);
        hbox.getChildren().add(likeBox);
        Scene scene = new Scene(hbox, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    //My favorite scene
    public void sixth(Stage primaryStage) throws Exception {
        List<sample.Recipe> recipeList = new ArrayList<>();
        try {
            String sqlQuery = " select * from recipes where recipe_ID in (select f.recipe_ID from recipes r, favorite f where f.recipe_ID = r.recipe_ID and f.user_ID = ?)";
            preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString(1, String.valueOf(curUser));
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                sample.Recipe newRecipe = new sample.Recipe();
                newRecipe.recipe_ID = resultSet.getInt(1);
                newRecipe.recipe_title = resultSet.getString(2);
                newRecipe.calories = resultSet.getInt(3);
                newRecipe.directions = resultSet.getString(4);
                newRecipe.ingredients = resultSet.getString(5);
                recipeList.add(newRecipe);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        HBox hbox = new HBox();
        Button back = new Button("Back");
        back.setStyle("-fx-font-size:20");
        back.setTextFill(Color.BLUE);
        back.setMinSize(150, 100);
        back.setOnAction(e -> backButton(primaryStage));

        // left part two button
        VBox left = new VBox();
        left.setPadding(new Insets(20, 20, 0, 0));
        left.getChildren().add(back);
        left.setSpacing(10);

        // mid part
        VBox mid = new VBox();
        mid.setPadding(new Insets(20, 20, 0, 0));
        mid.setSpacing(10);
        mid.setMaxHeight(500);
        Text result = new Text("Result");
        result.setTextAlignment(TextAlignment.CENTER);
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < recipeList.size(); i++) {
            items.add(recipeList.get(i).recipe_title);
        }
        list.setItems(items);
        mid.getChildren().add(result);
        mid.getChildren().add(list);

        // right part
        VBox right = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(400,490);

        right.getChildren().add(scrollPane);
        right.setSpacing(10);
        right.setMaxHeight(500);
        right.setMinWidth(500);
        right.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        mid.setPadding(new Insets(20, 20, 0, 0));
        Text recipe = new Text("Detail here");
        recipe.setWrappingWidth(490);
        recipe.setFont(Font.font("Verdana", 16));

        recipe.prefHeight(490);
        recipe.prefWidth(400);
        scrollPane.setContent(recipe);

        list.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    int index = list.getSelectionModel().getSelectedIndex();
                    String detail = "";
                    detail += "TITLE: " + recipeList.get(index).recipe_title + '\n';
                    detail += "\nCALORIES: " + recipeList.get(index).calories + '\n';
                    detail += "\nINGREDIENT: \n" + recipeList.get(index).ingredients.replace('|', '\n');
                    detail += "\n" + "\nDIRECTIONS: \n";
                    detail += recipeList.get(index).directions.replace('|', '\n');
                    recipe.setText(detail);
                });

        // disLike button
        Button dislike = new Button();
        dislike.setMinHeight(50);
        dislike.setMinWidth(50);
        dislike.setText("dislike");
        dislike.setOnAction(e-> dislikeButton(primaryStage, recipeList, list.getSelectionModel().getSelectedIndex()));
        VBox likeBox = new VBox();
        likeBox.setPadding(new Insets(10, 0, 0, 0));
        likeBox.getChildren().add(dislike);

        hbox.getChildren().add(left);
        hbox.getChildren().add(mid);
        hbox.getChildren().add(right);
        hbox.getChildren().add(likeBox);
        Scene scene = new Scene(hbox, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //the register scene
    public void seventh(Stage primaryStage) throws Exception {
        // Button setup
        Button register = new Button();
        register.setText("Register");
        Button back = new Button();
        back.setText("Back");
        register.setOnAction(this);
        back.setOnAction(this);


        //TODO: exception

        back.setOnAction(e->seventhBackButton(primaryStage));



        // Define the root for the scene
        VBox root = new VBox(); // The overall layout of the scene
        root.setPadding(new Insets(350, 0, 0, 0));

        // The first part, title HBox
        HBox titleHBox = new HBox(20);
        titleHBox.setPadding(new Insets(0, 0, 0, 250));
        // TODO: Set style to the HBox

        Text leftText = new Text("Healthism");
        leftText.setFont(Font.font("Verdana", 40));
        // TODO:set style to the left text
        titleHBox.getChildren().add(leftText);

        Text midText = new Text("or");
        midText.setFont(Font.font("Verdana", 40));
        // TODO:set style to the left text
        titleHBox.getChildren().add(midText);

        Text rightText = new Text("Hedonism");
        rightText.setFont(Font.font("Verdana", 40));
        // TODO:set style to the left text
        titleHBox.getChildren().add(rightText);

        // After all components have added to the titleHBox, add the finished HBox to the root VBox
        root.getChildren().add(titleHBox);


        //Setting Vbox, and put two Hbox
        HBox UserHBox = new HBox();
        UserHBox.setSpacing(20);
        HBox PasswordHBox = new HBox();
        PasswordHBox.setSpacing(20);

        // Username
        Label name = new Label("Username");

        // Username input
        TextField nameInput = new TextField("Type in your username");

        // Password
        Label password = new Label("Password");

        // Password input
        //TextField passwordInput = new TextField("Type in your password");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Type in your password");

        register.setOnAction(e->{
            try {
                registerButton(primaryStage,nameInput.getText(),passwordInput.getText());
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        UserHBox.getChildren().addAll(name, nameInput, register);
        UserHBox.setPadding(new Insets(20,0,0,300));
        PasswordHBox.getChildren().addAll(password, passwordInput, back);
        PasswordHBox.setPadding(new Insets(10,0,0,300));

        root.getChildren().addAll(UserHBox, PasswordHBox);

        Scene registerScene = new Scene(root,1000,1000); // Set the layout to the scene
        primaryStage.setScene(registerScene);
        primaryStage.setResizable(true);
        primaryStage.show();

    }


    public void loginButton(Stage stage, String username, String password) {
        try {
            String sqlQuery = " select user_ID from user where username = ? and password = ?";
            preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            resultSet = preparedStmt.executeQuery();
            if (!resultSet.next()) {
                alertbox.display("Error", "Username or password is wrong!");
                return;
            }
            curUser = resultSet.getInt(1);
            second(stage);
        } catch (Exception e) {
            return;
        }
        ;

    }

    public void sign_upButton(Stage stage) {
        try {
            seventh(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void healthnismButton(Stage stage) {
        try {
            third(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void hedonismButton(Stage stage) {
        try {
            fourth(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void favoriteButton(Stage stage) {
        try {
            sixth(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void logoutButton(Stage stage) {
        try {
            first(stage);
            curUser = 0;
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void searchButton(Stage stage, sample.SelectedInfo data, ObservableList<Integer> lkIndex, ObservableList<Integer> dislkIndex ) {
        System.out.println("Calories: " + data.selectedCalories);
        System.out.println("Title: " + data.title);
        System.out.println("lkCatagory" + lkIndex.toString());
        System.out.println("dislkCatagory" + dislkIndex.toString());
        System.out.println(lkIndex.toString());
        System.out.println(dislkIndex.toString());
        resultSet = null;

//        //create the mysql insert preparedstatement
        String select_sqlQuery = "SELECT Distinct * ";
        String from_sqlQuery;
        try {
            if (data.title == null || data.title.isEmpty()) {

                if (lkIndex == null || lkIndex.size() == 0) {

                    if (dislkIndex == null || dislkIndex.size() == 0) {
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? order by r.calories ASC";
                    } else if (dislkIndex.size() == 1) {
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND NOT rc.category_ID = ?";
                    } else { //> 1
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND NOT rc.category_ID in (?";
                        for (int i = 1; i < dislkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';


                    }
                } else if (lkIndex.size() == 1) {
                    if (dislkIndex == null || dislkIndex.size()== 0) {
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND rc.category_ID = ?";
                    } else if (dislkIndex.size() == 1) {
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND rc.category_ID = ? AND NOT rc.category_ID = ?";
                    } else { //> 1
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND rc.category_ID = ? AND NOT rc.category_ID in (?";
                        for (int i = 1; i < dislkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                    }

                } else { //>1
                    if (dislkIndex == null || dislkIndex.size()== 0) {
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND rc.category_ID in (?";
                        for (int i = 1; i < lkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                    } else if (dislkIndex.size() == 1) {
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND rc.category_ID in(?";
                        for (int i = 1; i < lkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                        from_sqlQuery = from_sqlQuery + " AND NOT rc.category_ID = ?";
                    } else { //dis > 1
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND NOT rc.category_ID in (?";
                        for (int i = 1; i < lkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                        from_sqlQuery = from_sqlQuery + " AND NOT rc.category_ID in (?";
                        for (int i = 1; i < dislkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';

                    }
                }
            } else {
                if (lkIndex == null || lkIndex.size()== 0) {

                    if (dislkIndex == null || dislkIndex.size()==0) {
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND r.recipe_title like ?";
                    } else if (dislkIndex.size() == 1) {
                        from_sqlQuery =  "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ?  AND r.recipe_title like ? AND NOT rc.category_ID = ?";
                    } else { //> 1
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ?  AND r.recipe_title like ? AND NOT rc.category_ID in (?";
                        for (int i = 1; i < dislkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';


                    }
                } else if (lkIndex.size() == 1) {
                    if (dislkIndex == null || dislkIndex.size()== 0) {
                        from_sqlQuery =  "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND r.recipe_title like ? AND rc.category_ID = ?";
                    } else if (dislkIndex.size() == 1) {
                        from_sqlQuery ="FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND r.recipe_title like ? AND rc.category_ID = ? AND NOT rc.category_ID = ?";
                    } else { //> 1
                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND r.recipe_title like ? AND rc.category_ID = ? AND NOT rc.category_ID in (?";
                        for (int i = 1; i < dislkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                    }

                } else { //>1
                    if (dislkIndex == null || dislkIndex.size()== 0) {

                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND r.recipe_title like ? AND rc.category_ID in (?";
                        for (int i = 1; i < lkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                    } else if (dislkIndex.size() == 1) {
                        from_sqlQuery =  "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND r.recipe_title like ? AND  rc.category_ID in (?";
                        for (int i = 1; i < lkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                        from_sqlQuery = from_sqlQuery + " AND NOT rc.category_ID = ?";

                    } else { //> 1

                        from_sqlQuery = "FROM recipes r, recipe_category rc WHERE r.recipe_ID=rc.recipe_ID AND r.calories <= ? AND r.recipe_title like ? AND rc.category_ID in (?";
                        for (int i = 1; i < lkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';
                        from_sqlQuery = from_sqlQuery + " AND NOT rc.category_ID in(?";
                        for (int i = 1; i < dislkIndex.size(); i++) {
                            from_sqlQuery = from_sqlQuery + ",?";
                        }
                        from_sqlQuery = from_sqlQuery + ')';

                    }
                }

            }

            if (data.title != null) {
                String sqlQuery = select_sqlQuery + from_sqlQuery ;
                System.out.println("SQLQuery" + sqlQuery);
                data.title = "%" + data.title + "%";
                preparedStmt = connection.prepareStatement(sqlQuery);
                preparedStmt.setInt(1, data.selectedCalories);
                preparedStmt.setString(2, data.title);
                int i = 0;
                for (; i < lkIndex.size(); i++) {
                    preparedStmt.setString(3 + i, String.valueOf(lkIndex.get(i)));
                }
                for (int j = 0; j < dislkIndex.size(); j++) {
                    preparedStmt.setString(3 + i + j, String.valueOf(lkIndex.get(j)));
                }
                resultSet = preparedStmt.executeQuery();
            } else { // title null
                String sqlQuery = select_sqlQuery + from_sqlQuery ;
                System.out.println("SQLQuery" + sqlQuery);
                preparedStmt = connection.prepareStatement(sqlQuery);
                preparedStmt.setString(1, String.valueOf(data.selectedCalories));
                int i = 0;
                for (; i < lkIndex.size(); i++) {
                    preparedStmt.setInt(2 + i, lkIndex.get(i));
                }
                for (int j = 0; j < dislkIndex.size(); j++) {
                    preparedStmt.setString(2 + i + j, String.valueOf(lkIndex.get(j)));
                }
                resultSet = preparedStmt.executeQuery();
            }
            resultSet = null;
            resultSet = preparedStmt.executeQuery();
            fifth(stage, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        return;

    }

    public void backButton(Stage stage) {
        try {
            second(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    public void likeButton(Stage stage, List<sample.Recipe> recipeList, int index) {
        int id = recipeList.get(index).recipe_ID;
        System.out.println("ID is " + id);
        System.out.println("Userid is " + curUser);
        try {
            String sqlQuery = "insert into favorite (recipe_ID, user_ID)"
                    + " values (?, ?)";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString (1, String.valueOf(id));
            preparedStmt.setString (2, String.valueOf(curUser));
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }

    public void dislikeButton(Stage stage, List<sample.Recipe> recipeList, int index) {
        int id = recipeList.get(index).recipe_ID;
        try {
            String sqlQuery = " delete from favorite where recipe_ID = ? and user_ID = ?";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString (1, String.valueOf(id));
            preparedStmt.setString (2, String.valueOf(curUser));
            preparedStmt.execute();
            sixth(stage);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void registerButton(Stage stage, String username, String password) {

        try {
            String sqlQuery = " select user_ID from user where username = ?";
            preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString(1, username);
            resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) {
                alertbox.display("Error", "This account is existed");
                return;
            }

            sqlQuery = " insert into user (username, password)"
                    + " values (?, ?)";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(sqlQuery);
            preparedStmt.setString (1, username);
            preparedStmt.setString (2, password);
            // execute the preparedstatement
            preparedStmt.execute();
            alertbox.display("Congratulation", "Registered successfully ");
            first(stage);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void seventhBackButton(Stage stage) {
        try {
            first(stage);
        } catch (Exception e) {
            return;
        }
        ;
    }

    @Override
    public void handle(ActionEvent event) {
        // TODO Auto-generated method stub

    }

}
