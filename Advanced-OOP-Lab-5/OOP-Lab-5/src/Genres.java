import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * genres screen - admin can add and remove genres here
 */
public class Genres extends GridPane {

    ComboBox comboBox;

    /** sets up the genres gui */
    public Genres() {

        //create labels
        Text text1 = new Text("Name:");
        Text text2 = new Text("Registered:");

        //create text field
        TextField textField1 = new TextField();

        //create combo box and load genres
        comboBox = new ComboBox();
        loadGenres();

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
        this.add(textField1, 1, 0);
        this.add(button1, 1, 1);

        this.add(text2, 0, 2);
        this.add(comboBox, 1, 2);
        this.add(button2, 1, 3);

        //styling
        button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif' ");
        text2.setStyle("-fx-font: normal bold 20px 'serif' ");
        this.setStyle("-fx-background-color: BEIGE;");

        //save button - adds genre to db
        button1.setOnAction(e -> {
            String name = textField1.getText();
            if (!name.isEmpty()) {
                DBHelper.addGenre(name);
                textField1.clear();
                loadGenres();
            }
        });

        //remove button - removes selected genre
        button2.setOnAction(e -> {
            String selected = (String) comboBox.getValue();
            if (selected != null) {
                DBHelper.removeGenre(selected);
                loadGenres();
            }
        });
    }

    /** refreshes the combo box with genres from db */
    void loadGenres() {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(DBHelper.getGenreNames());
    }
}
