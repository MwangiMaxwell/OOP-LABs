import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;

public class GenresApp extends Application {

    @Override
    public void start(Stage stage) {

        Text text1 = new Text("Name:");
        Text text2 = new Text("Registered:");

        TextField textField1 = new TextField();
        ComboBox<String> comboBox = new ComboBox<>();
        Button button1 = new Button("Save");
        Button button2 = new Button("Remove");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(text1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(button1, 1, 1);
        gridPane.add(text2, 0, 2);
        gridPane.add(comboBox, 1, 2);
        gridPane.add(button2, 1, 3);

        button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif' ");
        text2.setStyle("-fx-font: normal bold 20px 'serif' ");
        gridPane.setStyle("-fx-background-color: BEIGE;");

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT genre FROM Genres WHERE isactive = 1");
            while (rs.next()) {
                comboBox.getItems().add(rs.getString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        button1.setOnAction(e -> {
            String name = textField1.getText();
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Genres (genre, isactive) VALUES (?, 1)");
                ps.setString(1, name);
                ps.executeUpdate();
                comboBox.getItems().add(name);
                textField1.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        button2.setOnAction(e -> {
            String selected = comboBox.getValue();
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE Genres SET isactive = 0 WHERE genre = ?");
                ps.setString(1, selected);
                ps.executeUpdate();
                comboBox.getItems().remove(selected);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane);
        stage.setTitle("Movie Library System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
