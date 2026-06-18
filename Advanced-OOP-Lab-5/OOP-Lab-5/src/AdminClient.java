import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.rmi.Naming;

/**
 * admin client application
 * this is run by the admin
 */
public class AdminClient extends Application {

    LibraryInterface service;

    @Override
    public void start(Stage window) {
        // connect to the rmi server
        try {
            service = (LibraryInterface) Naming.lookup("rmi://localhost:1099/LibraryService");
        } catch (Exception e) {
            System.out.println("Could not connect to server: " + e.getMessage());
            e.printStackTrace();
            return; // stop if we can't connect
        }

        // ---- admin screen with tabs ----
        TabPane adminTabs = new TabPane();

        Genres genresView = new Genres(service);
        Tab genresTab = new Tab("Genres", genresView);
        genresTab.setClosable(false);

        Movies moviesView = new Movies(service);
        Tab moviesTab = new Tab("Movies", moviesView);
        moviesTab.setClosable(false);

        Customers customersView = new Customers(service);
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

        Scene adminScene = new Scene(adminTabs, 700, 500);

        //show the window
        window.setTitle("Video Library - Admin");
        window.setScene(adminScene);
        window.show();
    }

    /** main method - launches the app */
    public static void main(String[] args) {
        launch(args);
    }
}
