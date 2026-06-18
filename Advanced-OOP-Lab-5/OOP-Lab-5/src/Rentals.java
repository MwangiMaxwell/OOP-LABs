import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * rentals screen - customer can rent and return movies here
 */
public class Rentals extends GridPane {

    ComboBox customerCombo;
    ComboBox genreCombo;
    ComboBox moviesCombo;
    ComboBox borrowedCombo;
    ComboBox returnedCombo;
    LibraryInterface service;

    /** sets up the rentals gui */
    public Rentals(LibraryInterface service) {
        this.service = service;

        //create labels
        Text text1 = new Text("Customer:");
        Text text2 = new Text("Genre:");
        Text text3 = new Text("Movies:");
        Text text4 = new Text("Borrowed:");
        Text text5 = new Text("Returned:");

        //create combo boxes
        customerCombo = new ComboBox();
        genreCombo = new ComboBox();
        moviesCombo = new ComboBox();
        borrowedCombo = new ComboBox();
        returnedCombo = new ComboBox();

        //create buttons
        Button button1 = new Button("Save");
        Button button2 = new Button("Return");

        //set up the grid
        this.setMinSize(600, 500);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(10);
        this.setHgap(10);
        this.setAlignment(Pos.CENTER);

        //add nodes to grid
        this.add(text1, 0, 0);
        this.add(customerCombo, 1, 0);

        this.add(text2, 0, 1);
        this.add(genreCombo, 1, 1);

        this.add(text3, 0, 2);
        this.add(moviesCombo, 1, 2);

        this.add(button1, 1, 3);

        this.add(text4, 0, 4);
        this.add(borrowedCombo, 1, 4);

        this.add(button2, 1, 5);

        this.add(text5, 0, 6);
        this.add(returnedCombo, 1, 6);

        //styling
        button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif' ");
        text2.setStyle("-fx-font: normal bold 20px 'serif' ");
        text3.setStyle("-fx-font: normal bold 20px 'serif' ");
        text4.setStyle("-fx-font: normal bold 20px 'serif' ");
        text5.setStyle("-fx-font: normal bold 20px 'serif' ");
        this.setStyle("-fx-background-color: BEIGE;");

        //when customer is picked, show their rentals
        customerCombo.setOnAction(e -> {
            loadRentals();
        });

        //when genre is picked, show its movies
        genreCombo.setOnAction(e -> {
            try {
                moviesCombo.getItems().clear();
                String genreName = (String) genreCombo.getValue();
                if (genreName != null) {
                    int genreId = service.getGenreId(genreName);
                    moviesCombo.getItems().addAll(service.getMovieTitles(genreId));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //save button - rents movie to customer
        button1.setOnAction(e -> {
            String customerName = (String) customerCombo.getValue();
            String movieTitle = (String) moviesCombo.getValue();
            if (customerName != null && movieTitle != null) {
                try {
                    int clientId = service.getCustomerId(customerName);
                    int movieId = service.getMovieId(movieTitle);
                    service.rentMovie(clientId, movieId);
                    loadRentals();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //return button - returns the selected movie
        button2.setOnAction(e -> {
            String customerName = (String) customerCombo.getValue();
            String movieTitle = (String) borrowedCombo.getValue();
            if (customerName != null && movieTitle != null) {
                try {
                    int clientId = service.getCustomerId(customerName);
                    service.returnMovie(clientId, movieTitle);
                    loadRentals();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /** loads customers and genres into the dropdowns */
    void loadData() {
        try {
            customerCombo.getItems().clear();
            customerCombo.getItems().addAll(service.getCustomerNames());

            genreCombo.getItems().clear();
            genreCombo.getItems().addAll(service.getGenreNames());

            moviesCombo.getItems().clear();
            borrowedCombo.getItems().clear();
            returnedCombo.getItems().clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** loads borrowed and returned movies for the selected customer */
    void loadRentals() {
        try {
            borrowedCombo.getItems().clear();
            returnedCombo.getItems().clear();
            String customerName = (String) customerCombo.getValue();
            if (customerName != null) {
                int clientId = service.getCustomerId(customerName);
                borrowedCombo.getItems().addAll(service.getBorrowedMovies(clientId));
                returnedCombo.getItems().addAll(service.getReturnedMovies(clientId));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
