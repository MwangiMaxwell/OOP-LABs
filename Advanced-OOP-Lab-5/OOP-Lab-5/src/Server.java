import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * this is the main server file
 * your teammate will run this file on their laptop
 */
public class Server {
    public static void main(String[] args) {
        try {
            // start the rmi registry on port 1099
            LocateRegistry.createRegistry(1099);

            // create our implementation object
            LibraryImpl libraryService = new LibraryImpl();

            // bind it so the client can find it
            Naming.rebind("rmi://localhost:1099/LibraryService", libraryService);

            System.out.println("RMI Server is running...");
            System.out.println("Waiting for clients to connect...");

        } catch (Exception e) {
            System.out.println("Server failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
