import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * movies screen - admin can add and remove movies here
 * you pick a genre first then add a movie under it
 */
public class Movies extends GridPane {

    ComboBox genreCombo;
    ComboBox registeredCombo;
    LibraryInterface service;

    /** sets up the movies gui */
    public Movies(LibraryInterface service) {
        this.service = service;

        //create labels
        Text text1 = new Text("Genre:");
        Text text2 = new Text("Name:");
        Text text3 = new Text("Registered:");

        //create text field
        TextField textField1 = new TextField();

        //create combo boxes
        genreCombo = new ComboBox();
        registeredCombo = new ComboBox();

        //create buttons
        Button button1 = new Button("Save");
        Button button2 = new Button("Remove");

        //set up the grid
        this.setMinSize(600, 400);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(10);
        this.setHgap(10);
        this.setAlignment(Pos.CENTER);

        //add nodes to grid
        this.add(text1, 0, 0);
        this.add(genreCombo, 1, 0);

        this.add(text2, 0, 1);
        this.add(textField1, 1, 1);
        this.add(button1, 1, 2);

        this.add(text3, 0, 3);
        this.add(registeredCombo, 1, 3);
        this.add(button2, 1, 4);

        //styling
        button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif' ");
        text2.setStyle("-fx-font: normal bold 20px 'serif' ");
        text3.setStyle("-fx-font: normal bold 20px 'serif' ");
        this.setStyle("-fx-background-color: BEIGE;");

        //when genre is picked, show its movies
        genreCombo.setOnAction(e -> {
            loadMovies();
        });

        //save button - adds movie to db
        button1.setOnAction(e -> {
            String genreName = (String) genreCombo.getValue();
            String movieName = textField1.getText();
            if (genreName != null && !movieName.isEmpty()) {
                try {
                    int genreId = service.getGenreId(genreName);
                    service.addMovie(movieName, genreId);
                    textField1.clear();
                    loadMovies();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //remove button - removes selected movie
        button2.setOnAction(e -> {
            String selected = (String) registeredCombo.getValue();
            if (selected != null) {
                try {
                    service.removeMovie(selected);
                    loadMovies();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /** loads genres into the dropdown */
    void loadGenres() {
        try {
            genreCombo.getItems().clear();
            genreCombo.getItems().addAll(service.getGenreNames());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** loads movies for the selected genre */
    void loadMovies() {
        try {
            registeredCombo.getItems().clear();
            String genreName = (String) genreCombo.getValue();
            if (genreName != null) {
                int genreId = service.getGenreId(genreName);
                registeredCombo.getItems().addAll(service.getMovieTitles(genreId));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
