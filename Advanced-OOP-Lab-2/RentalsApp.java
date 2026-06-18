import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;

public class RentalsApp extends Application {

    @Override
    public void start(Stage stage) {

        Text textCustomer = new Text("Customer:");
        Text textGenre = new Text("Genre:");
        Text textMovies = new Text("Movies:");
        Text textBorrowed = new Text("Borrowed:");
        Text textReturned = new Text("Returned:");

        ComboBox<String> comboBoxCustomer = new ComboBox<>();
        ComboBox<String> comboBoxGenre = new ComboBox<>();
        ComboBox<String> comboBoxMovies = new ComboBox<>();
        ComboBox<String> comboBoxBorrowed = new ComboBox<>();
        ComboBox<String> comboBoxReturned = new ComboBox<>();
        Button buttonSaveRental = new Button("Save Rental");
        Button buttonReturnMovie = new Button("Return Movie");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 600);
        gridPane.setPadding(new Insets(30, 40, 30, 40));
        gridPane.setVgap(15);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(textCustomer, 0, 0);
        gridPane.add(comboBoxCustomer, 1, 0);
        gridPane.add(textGenre, 0, 1);
        gridPane.add(comboBoxGenre, 1, 1);
        gridPane.add(textMovies, 0, 2);
        gridPane.add(comboBoxMovies, 1, 2);
        gridPane.add(buttonSaveRental, 1, 3);
        gridPane.add(textBorrowed, 0, 4);
        gridPane.add(comboBoxBorrowed, 1, 4);
        gridPane.add(buttonReturnMovie, 1, 5);
        gridPane.add(textReturned, 0, 6);
        gridPane.add(comboBoxReturned, 1, 6);

        buttonSaveRental.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        buttonReturnMovie.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        textCustomer.setStyle("-fx-font: normal bold 20px 'serif' ");
        textGenre.setStyle("-fx-font: normal bold 20px 'serif' ");
        textMovies.setStyle("-fx-font: normal bold 20px 'serif' ");
        textBorrowed.setStyle("-fx-font: normal bold 20px 'serif' ");
        textReturned.setStyle("-fx-font: normal bold 20px 'serif' ");
        gridPane.setStyle("-fx-background-color: BEIGE;");

        comboBoxCustomer.setPrefWidth(200);
        comboBoxGenre.setPrefWidth(200);
        comboBoxMovies.setPrefWidth(200);
        comboBoxBorrowed.setPrefWidth(200);
        comboBoxReturned.setPrefWidth(200);
        buttonSaveRental.setPrefWidth(200);
        buttonReturnMovie.setPrefWidth(200);

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Fullname FROM Clients WHERE isactive = 1");
            while (rs.next()) {
                comboBoxCustomer.getItems().add(rs.getString("Fullname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT genre FROM Genres WHERE isactive = 1");
            while (rs.next()) {
                comboBoxGenre.getItems().add(rs.getString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        comboBoxGenre.setOnAction(e -> {
            comboBoxMovies.getItems().clear();
            String selectedGenre = comboBoxGenre.getValue();
            try {
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Title FROM Movies WHERE isactive = 1 AND genre_id = (SELECT id FROM Genres WHERE genre = '" + selectedGenre + "')");
                while (rs.next()) {
                    comboBoxMovies.getItems().add(rs.getString("Title"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Clients.Fullname, Movies.Title FROM Rentals JOIN Clients ON Rentals.client_id = Clients.id JOIN Movies ON Rentals.movie_id = Movies.id WHERE Rentals.Returned = 0");
            while (rs.next()) {
                comboBoxBorrowed.getItems().add(rs.getString("Fullname") + " - " + rs.getString("Title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonSaveRental.setOnAction(e -> {
            String customer = comboBoxCustomer.getValue();
            String movie = comboBoxMovies.getValue();
            try {
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();

                ResultSet rs1 = stmt.executeQuery("SELECT id FROM Clients WHERE Fullname = '" + customer + "'");
                rs1.next();
                int clientId = rs1.getInt("id");

                ResultSet rs2 = stmt.executeQuery("SELECT id FROM Movies WHERE Title = '" + movie + "'");
                rs2.next();
                int movieId = rs2.getInt("id");

                PreparedStatement ps = conn.prepareStatement("INSERT INTO Rentals (client_id, movie_id, Returned) VALUES (?, ?, 0)");
                ps.setInt(1, clientId);
                ps.setInt(2, movieId);
                ps.executeUpdate();

                comboBoxBorrowed.getItems().add(customer + " - " + movie);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        buttonReturnMovie.setOnAction(e -> {
            String selected = comboBoxBorrowed.getValue();
            if (selected != null) {
                String[] parts = selected.split(" - ");
                String clientName = parts[0];
                String movieTitle = parts[1];
                try {
                    Connection conn = DBConnection.getConnection();
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("UPDATE Rentals SET Returned = 1 WHERE client_id = (SELECT id FROM Clients WHERE Fullname = '" + clientName + "') AND movie_id = (SELECT id FROM Movies WHERE Title = '" + movieTitle + "') AND Returned = 0");
                    comboBoxBorrowed.getItems().remove(selected);
                    comboBoxReturned.getItems().add(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(gridPane);
        stage.setTitle("Movie Library System - Rentals");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
