import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;

public class MoviesApp extends Application {

    @Override
    public void start(Stage stage) {

        Text textGenres = new Text("Genres:");
        Text textName = new Text("Name:");
        Text textRegistered = new Text("Registered:");

        ComboBox<String> comboBoxGenre = new ComboBox<>();
        TextField textFieldName = new TextField();
        ComboBox<String> comboBoxRegistered = new ComboBox<>();
        Button buttonSave = new Button("Save Movie");
        Button buttonRemove = new Button("Remove Movie");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 450);
        gridPane.setPadding(new Insets(30, 40, 30, 40));
        gridPane.setVgap(15);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(textGenres, 0, 0);
        gridPane.add(comboBoxGenre, 1, 0);
        gridPane.add(textName, 0, 1);
        gridPane.add(textFieldName, 1, 1);
        gridPane.add(buttonSave, 1, 2);
        gridPane.add(textRegistered, 0, 3);
        gridPane.add(comboBoxRegistered, 1, 3);
        gridPane.add(buttonRemove, 1, 4);

        buttonSave.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        buttonRemove.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        textGenres.setStyle("-fx-font: normal bold 20px 'serif' ");
        textName.setStyle("-fx-font: normal bold 20px 'serif' ");
        textRegistered.setStyle("-fx-font: normal bold 20px 'serif' ");
        gridPane.setStyle("-fx-background-color: BEIGE;");

        comboBoxGenre.setPrefWidth(200);
        textFieldName.setPrefWidth(200);
        comboBoxRegistered.setPrefWidth(200);
        buttonSave.setPrefWidth(200);
        buttonRemove.setPrefWidth(200);

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, genre FROM Genres WHERE isactive = 1");
            while (rs.next()) {
                comboBoxGenre.getItems().add(rs.getString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Title FROM Movies WHERE isactive = 1");
            while (rs.next()) {
                comboBoxRegistered.getItems().add(rs.getString("Title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonSave.setOnAction(e -> {
            String genre = comboBoxGenre.getValue();
            String title = textFieldName.getText();
            try {
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id FROM Genres WHERE genre = '" + genre + "'");
                rs.next();
                int genreId = rs.getInt("id");

                PreparedStatement ps = conn.prepareStatement("INSERT INTO Movies (genre_id, Title, isactive) VALUES (?, ?, 1)");
                ps.setInt(1, genreId);
                ps.setString(2, title);
                ps.executeUpdate();
                comboBoxRegistered.getItems().add(title);
                textFieldName.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        buttonRemove.setOnAction(e -> {
            String selected = comboBoxRegistered.getValue();
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE Movies SET isactive = 0 WHERE Title = ?");
                ps.setString(1, selected);
                ps.executeUpdate();
                comboBoxRegistered.getItems().remove(selected);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane);
        stage.setTitle("Movie Library System - Movies");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
