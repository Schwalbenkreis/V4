package frontend.pages;

import java.util.Locale;

import frontend.NavigationButton;
import frontend.Navigator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.PantryItem;
import models.Route;


import services.MealPlannerService;

public class PantryPage extends Page {
    private ListView<PantryItem> PantryListView;
    private MealPlannerService mealPlanner;
    private Label pantryLabel;

    public PantryPage(Navigator navigator, MealPlannerService mealPlanner) {
        super(navigator);
        this.mealPlanner = mealPlanner;
    }

    @Override
    public Parent getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Left: Recipe List
        VBox leftBox = new VBox(10);
        Label listLabel = new Label("Lebensmittel:");
        listLabel.setStyle("-fx-font-weight: bold;");

        PantryListView = new ListView<>();
        PantryListView.setPrefWidth(300);
        PantryListView.setOnMouseClicked(event -> {
            PantryItem selected = PantryListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showIngriedentDetails(selected);
            }
        });

        leftBox.getChildren().addAll(listLabel, PantryListView);
        root.setLeft(leftBox);

        // Center: Pantry Details 
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(0, 10, 0, 10));
        Label detailsLabel = new Label("Lebensmittel Details:");
        detailsLabel.setStyle("-fx-font-weight: bold;");

        pantryLabel = new Label();
        
        centerBox.getChildren().addAll(detailsLabel, pantryLabel);
        root.setCenter(centerBox);

        // Bottom: Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        Button addButton = new NavigationButton("Lebensmittel hinzufügen", Route.ADD_GROCERY, navigator);
        Button backButton = new NavigationButton("Zurück zum Hauptmenü", Route.MAIN, navigator);

        buttonBox.getChildren().addAll(addButton, backButton);
        root.setBottom(buttonBox);

        return root;

    }

    private void showIngriedentDetails(PantryItem item) {
        pantryLabel.setText(item.getDetails(Locale.GERMAN));
    }

    private void refreshPantry(){
        PantryListView.getItems().clear();
        PantryListView.getItems().addAll(mealPlanner.getPantry());
        pantryLabel.setText("Bitte Lebensmittel auswählen.");
    }

    @Override
    public void onShow() {
        refreshPantry();
    }
}
