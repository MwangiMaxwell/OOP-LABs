import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * the remote interface for our video library system
 * both client and server need this file
 */
public interface LibraryInterface extends Remote {

    // ---- genre methods ----
    void addGenre(String name) throws RemoteException;
    ArrayList<String> getGenreNames() throws RemoteException;
    int getGenreId(String name) throws RemoteException;
    void removeGenre(String name) throws RemoteException;

    // ---- movie methods ----
    void addMovie(String title, int genreId) throws RemoteException;
    ArrayList<String> getMovieTitles(int genreId) throws RemoteException;
    int getMovieId(String title) throws RemoteException;
    void removeMovie(String title) throws RemoteException;

    // ---- customer methods ----
    void addCustomer(String fullname) throws RemoteException;
    ArrayList<String> getCustomerNames() throws RemoteException;
    int getCustomerId(String name) throws RemoteException;
    void removeCustomer(String name) throws RemoteException;

    // ---- rental methods ----
    void rentMovie(int clientId, int movieId) throws RemoteException;
    ArrayList<String> getBorrowedMovies(int clientId) throws RemoteException;
    ArrayList<String> getReturnedMovies(int clientId) throws RemoteException;
    void returnMovie(int clientId, String movieTitle) throws RemoteException;
}
