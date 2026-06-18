import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * main class for our video library system
 * you can login as admin or customer
 */
public class VideoLibrarySystem extends Application {

    Stage window;

    /** this is where everything starts when the app opens */
    @Override
    public void start(Stage stage) {
        window = stage;

        // ---- role selection screen ----
        VBox roleBox = new VBox(20);
        roleBox.setAlignment(Pos.CENTER);
        roleBox.setPadding(new Insets(20));

        Text title = new Text("Video Library System");
        title.setStyle("-fx-font: normal bold 24px 'serif' ");

        Button adminBtn = new Button("Client 1 (Admin)");
        adminBtn.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:14pt;");

        Button customerBtn = new Button("Client 2 (Customer)");
        customerBtn.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:14pt;");

        roleBox.getChildren().addAll(title, adminBtn, customerBtn);
        Scene roleScene = new Scene(roleBox, 400, 300);

        // ---- admin screen with tabs ----
        TabPane adminTabs = new TabPane();

        Genres genresView = new Genres();
        Tab genresTab = new Tab("Genres", genresView);
        genresTab.setClosable(false);

        Movies moviesView = new Movies();
        Tab moviesTab = new Tab("Movies", moviesView);
        moviesTab.setClosable(false);

        Customers customersView = new Customers();
        Tab customersTab = new Tab("Customers", customersView);
        customersTab.setClosable(false);

        //refresh data when switching tabs
        moviesTab.setOnSelectionChanged(e -> {
            if (moviesTab.isSelected()) moviesView.loadGenres();
        });
        customersTab.setOnSelectionChanged(e -> {
            if (customersTab.isSelected()) customersView.loadCustomers();
        });

        adminTabs.getTabs().addAll(genresTab, moviesTab, customersTab);

        Button backBtn1 = new Button("Back");
        backBtn1.setOnAction(e -> window.setScene(roleScene));
        VBox adminRoot = new VBox(backBtn1, adminTabs);

        Scene adminScene = new Scene(adminRoot, 700, 500);

        // ---- customer screen with rentals tab ----
        TabPane customerTabs = new TabPane();

        Rentals rentalsView = new Rentals();
        Tab rentalsTab = new Tab("Rentals", rentalsView);
        rentalsTab.setClosable(false);

        customerTabs.getTabs().add(rentalsTab);

        Button backBtn2 = new Button("Back");
        backBtn2.setOnAction(e -> window.setScene(roleScene));
        VBox customerRoot = new VBox(backBtn2, customerTabs);

        Scene customerScene = new Scene(customerRoot, 700, 600);

        // ---- button actions ----
        adminBtn.setOnAction(e -> window.setScene(adminScene));
        customerBtn.setOnAction(e -> {
            rentalsView.loadData();
            window.setScene(customerScene);
        });

        //show the window
        window.setTitle("Video Library Rentals System");
        window.setScene(roleScene);
        window.show();
    }

    /** main method - launches the app */
    public static void main(String[] args) {
        launch(args);
    }
}
