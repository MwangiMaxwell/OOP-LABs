import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.rmi.Naming;

/**
 * customer client application
 * this is run by the customer
 */
public class CustomerClient extends Application {

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

        // ---- customer screen with rentals tab ----
        TabPane customerTabs = new TabPane();

        Rentals rentalsView = new Rentals(service);
        Tab rentalsTab = new Tab("Rentals", rentalsView);
        rentalsTab.setClosable(false);

        customerTabs.getTabs().add(rentalsTab);

        Scene customerScene = new Scene(customerTabs, 700, 600);

        // load initial data
        rentalsView.loadData();

        //show the window
        window.setTitle("Video Library - Customer");
        window.setScene(customerScene);
        window.show();
    }

    /** main method - launches the app */
    public static void main(String[] args) {
        launch(args);
    }
}
