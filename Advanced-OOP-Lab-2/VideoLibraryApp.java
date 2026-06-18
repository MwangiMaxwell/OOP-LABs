import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;

public class VideoLibraryApp extends Application {

    private GridPane buildGenresPane() {
        Text text1 = new Text("Name:");
        Text text2 = new Text("Registered:");

        TextField textField1 = new TextField();
        ComboBox<String> comboBox = new ComboBox<>();
        Button button1 = new Button("Save");
        Button button2 = new Button("Remove");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(30, 40, 30, 40));
        gridPane.setVgap(15);
        gridPane.setHgap(20);
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

        textField1.setPrefWidth(220);
        comboBox.setPrefWidth(220);
        button1.setPrefWidth(220);
        button2.setPrefWidth(220);

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

        return gridPane;
    }

    private GridPane buildMoviesPane() {
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

        comboBoxGenre.setPrefWidth(220);
        textFieldName.setPrefWidth(220);
        comboBoxRegistered.setPrefWidth(220);
        buttonSave.setPrefWidth(220);
        buttonRemove.setPrefWidth(220);

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

        return gridPane;
    }

    private GridPane buildCustomersPane() {
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

        textFieldName.setPrefWidth(220);
        textFieldPhone.setPrefWidth(220);
        textFieldEmail.setPrefWidth(220);
        comboBoxRegistered.setPrefWidth(220);
        buttonSave.setPrefWidth(220);
        buttonRemove.setPrefWidth(220);

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

        return gridPane;
    }

    private GridPane buildRentalsPane() {
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

        comboBoxCustomer.setPrefWidth(220);
        comboBoxGenre.setPrefWidth(220);
        comboBoxMovies.setPrefWidth(220);
        comboBoxBorrowed.setPrefWidth(220);
        comboBoxReturned.setPrefWidth(220);
        buttonSaveRental.setPrefWidth(220);
        buttonReturnMovie.setPrefWidth(220);

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

        return gridPane;
    }

    @Override
    public void start(Stage stage) {

        TabPane tabPane = new TabPane();

        Tab tabGenres = new Tab("Genres", buildGenresPane());
        tabGenres.setClosable(false);

        Tab tabMovies = new Tab("Movies", buildMoviesPane());
        tabMovies.setClosable(false);

        Tab tabCustomers = new Tab("Customers", buildCustomersPane());
        tabCustomers.setClosable(false);

        Tab tabRentals = new Tab("Rentals", buildRentalsPane());
        tabRentals.setClosable(false);

        tabPane.getTabs().addAll(tabGenres, tabMovies, tabCustomers, tabRentals);
        tabPane.setStyle("-fx-font-size: 13pt; -fx-font-family: 'Serif';");

        Scene scene = new Scene(tabPane);

        stage.setTitle("Movie Library System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
