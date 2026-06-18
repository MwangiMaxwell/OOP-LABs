import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * the server implementation of our video library service
 * it just calls DBHelper to do the actual database work
 */
public class LibraryImpl extends UnicastRemoteObject implements LibraryInterface {

    public LibraryImpl() throws RemoteException {
        super();
    }

    // ---- genre methods ----

    @Override
    public void addGenre(String name) throws RemoteException {
        DBHelper.addGenre(name);
    }

    @Override
    public ArrayList<String> getGenreNames() throws RemoteException {
        return DBHelper.getGenreNames();
    }

    @Override
    public int getGenreId(String name) throws RemoteException {
        return DBHelper.getGenreId(name);
    }

    @Override
    public void removeGenre(String name) throws RemoteException {
        DBHelper.removeGenre(name);
    }

    // ---- movie methods ----

    @Override
    public void addMovie(String title, int genreId) throws RemoteException {
        DBHelper.addMovie(title, genreId);
    }

    @Override
    public ArrayList<String> getMovieTitles(int genreId) throws RemoteException {
        return DBHelper.getMovieTitles(genreId);
    }

    @Override
    public int getMovieId(String title) throws RemoteException {
        return DBHelper.getMovieId(title);
    }

    @Override
    public void removeMovie(String title) throws RemoteException {
        DBHelper.removeMovie(title);
    }

    // ---- customer methods ----

    @Override
    public void addCustomer(String fullname) throws RemoteException {
        DBHelper.addCustomer(fullname);
    }

    @Override
    public ArrayList<String> getCustomerNames() throws RemoteException {
        return DBHelper.getCustomerNames();
    }

    @Override
    public int getCustomerId(String name) throws RemoteException {
        return DBHelper.getCustomerId(name);
    }

    @Override
    public void removeCustomer(String name) throws RemoteException {
        DBHelper.removeCustomer(name);
    }

    // ---- rental methods ----

    @Override
    public void rentMovie(int clientId, int movieId) throws RemoteException {
        DBHelper.rentMovie(clientId, movieId);
    }

    @Override
    public ArrayList<String> getBorrowedMovies(int clientId) throws RemoteException {
        return DBHelper.getBorrowedMovies(clientId);
    }

    @Override
    public ArrayList<String> getReturnedMovies(int clientId) throws RemoteException {
        return DBHelper.getReturnedMovies(clientId);
    }

    @Override
    public void returnMovie(int clientId, String movieTitle) throws RemoteException {
        DBHelper.returnMovie(clientId, movieTitle);
    }
}
