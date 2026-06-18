import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;

public class CustomersApp extends Application {

    @Override
    public void start(Stage stage) {

        Text textName = new Text("Name:");
        Text textPhone = new Text("Phone:");
        Text textEmail = new Text("Email:");
        Text textRegistered = new Text("Registered:");

        TextField textFieldName = new TextField();
        TextField textFieldPhone = new TextField();
        TextField textFieldEmail = new TextField();
        ComboBox<String> comboBoxRegistered = new ComboBox<>();
        Button buttonSave = new Button("Save Customer");
        Button buttonRemove = new Button("Remove Customer");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 500);
        gridPane.setPadding(new Insets(30, 40, 30, 40));
        gridPane.setVgap(15);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(textName, 0, 0);
        gridPane.add(textFieldName, 1, 0);
        gridPane.add(textPhone, 0, 1);
        gridPane.add(textFieldPhone, 1, 1);
        gridPane.add(textEmail, 0, 2);
        gridPane.add(textFieldEmail, 1, 2);
        gridPane.add(buttonSave, 1, 3);
        gridPane.add(textRegistered, 0, 4);
        gridPane.add(comboBoxRegistered, 1, 4);
        gridPane.add(buttonRemove, 1, 5);

        buttonSave.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        buttonRemove.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        textName.setStyle("-fx-font: normal bold 20px 'serif' ");
        textPhone.setStyle("-fx-font: normal bold 20px 'serif' ");
        textEmail.setStyle("-fx-font: normal bold 20px 'serif' ");
        textRegistered.setStyle("-fx-font: normal bold 20px 'serif' ");
        gridPane.setStyle("-fx-background-color: BEIGE;");

        textFieldName.setPrefWidth(200);
        textFieldPhone.setPrefWidth(200);
        textFieldEmail.setPrefWidth(200);
        comboBoxRegistered.setPrefWidth(200);
        buttonSave.setPrefWidth(200);
        buttonRemove.setPrefWidth(200);

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Fullname FROM Clients WHERE isactive = 1");
            while (rs.next()) {
                comboBoxRegistered.getItems().add(rs.getString("Fullname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonSave.setOnAction(e -> {
            String name = textFieldName.getText();
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Clients (Fullname, isactive) VALUES (?, 1)");
                ps.setString(1, name);
                ps.executeUpdate();
                comboBoxRegistered.getItems().add(name);
                textFieldName.clear();
                textFieldPhone.clear();
                textFieldEmail.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        buttonRemove.setOnAction(e -> {
            String selected = comboBoxRegistered.getValue();
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE Clients SET isactive = 0 WHERE Fullname = ?");
                ps.setString(1, selected);
                ps.executeUpdate();
                comboBoxRegistered.getItems().remove(selected);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane);
        stage.setTitle("Movie Library System - Customers");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
