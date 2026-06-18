import java.sql.*;
import java.util.ArrayList;

/**
 * this class is for connecting to the database and doing queries
 * we use it in all the other classes to save and get data
 */
public class DBHelper {

    // our database url, username and password
    static String url = "jdbc:mysql://localhost:3306/vls_db";
    static String user = "root";
    static String password = "";

    /** connects to the mysql database and returns the connection */
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    // ---- genre methods ----

    /** adds a genre to the database */
    public static void addGenre(String name) {
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO Genres (genre) VALUES (?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** gets all the active genre names */
    public static ArrayList<String> getGenreNames() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT genre FROM Genres WHERE isactive = 1");
            while (rs.next()) {
                list.add(rs.getString("genre"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** gets a genre id from its name */
    public static int getGenreId(String name) {
        int id = -1;
        try {
            Connection con = getConnection();
            String sql = "SELECT id FROM Genres WHERE genre = ? AND isactive = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /** removes a genre (sets isactive to 0) */
    public static void removeGenre(String name) {
        try {
            Connection con = getConnection();
            String sql = "UPDATE Genres SET isactive = 0 WHERE genre = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---- movie methods ----

    /** adds a movie under a genre */
    public static void addMovie(String title, int genreId) {
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO Movies (genre_id, Title) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, genreId);
            pst.setString(2, title);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** gets movie titles for a genre */
    public static ArrayList<String> getMovieTitles(int genreId) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            String sql = "SELECT Title FROM Movies WHERE genre_id = ? AND isactive = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, genreId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Title"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** gets a movie id from its title */
    public static int getMovieId(String title) {
        int id = -1;
        try {
            Connection con = getConnection();
            String sql = "SELECT id FROM Movies WHERE Title = ? AND isactive = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, title);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /** removes a movie (sets isactive to 0) */
    public static void removeMovie(String title) {
        try {
            Connection con = getConnection();
            String sql = "UPDATE Movies SET isactive = 0 WHERE Title = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, title);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---- customer methods ----

    /** adds a customer to the database */
    public static void addCustomer(String fullname) {
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO Clients (Fullname) VALUES (?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, fullname);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** gets all active customer names */
    public static ArrayList<String> getCustomerNames() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT Fullname FROM Clients WHERE isactive = 1");
            while (rs.next()) {
                list.add(rs.getString("Fullname"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** gets a customer id from their name */
    public static int getCustomerId(String name) {
        int id = -1;
        try {
            Connection con = getConnection();
            String sql = "SELECT id FROM Clients WHERE Fullname = ? AND isactive = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /** removes a customer (sets isactive to 0) */
    public static void removeCustomer(String name) {
        try {
            Connection con = getConnection();
            String sql = "UPDATE Clients SET isactive = 0 WHERE Fullname = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---- rental methods ----

    /** rents a movie to a customer */
    public static void rentMovie(int clientId, int movieId) {
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO Rentals (client_id, movie_id, Returned) VALUES (?, ?, 0)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, clientId);
            pst.setInt(2, movieId);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** gets borrowed movies for a customer (not returned yet) */
    public static ArrayList<String> getBorrowedMovies(int clientId) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            String sql = "SELECT m.Title FROM Rentals r JOIN Movies m ON r.movie_id = m.id " +
                         "WHERE r.client_id = ? AND r.Returned = 0";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Title"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** gets returned movies for a customer */
    public static ArrayList<String> getReturnedMovies(int clientId) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection con = getConnection();
            String sql = "SELECT m.Title FROM Rentals r JOIN Movies m ON r.movie_id = m.id " +
                         "WHERE r.client_id = ? AND r.Returned = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Title"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** marks a movie as returned */
    public static void returnMovie(int clientId, String movieTitle) {
        try {
            Connection con = getConnection();
            int movieId = getMovieId(movieTitle);
            String sql = "UPDATE Rentals SET Returned = 1 WHERE client_id = ? AND movie_id = ? AND Returned = 0 LIMIT 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, clientId);
            pst.setInt(2, movieId);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}