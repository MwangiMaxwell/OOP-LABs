/**
* <h1>JavaFX First Example</h1> 
* The MyFirstJavaFXApp program simply demonstrates a basic rich GUI using JavaFX API
* driven by a DB on the back end
* @author  Edmond Menya 
* @version 1.0 
*/
import javafx.application.Application; 
import static javafx.application.Application.launch; 
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField; 
import javafx.scene.layout.GridPane; 
import javafx.scene.text.Text; 
import javafx.scene.control.TextField; 
import javafx.stage.Stage;
import java.sql.*; 
import java.util.*; 

public class Gener extends Application{
   /**
   *  The program begins by declaring some global DB variables
   */
   
   Connection conn = null;	
   String DB_URL = "jdbc:mysql://localhost/vlsrmi";
   String USER = "root"; 
   String  PASS = ""; 
	      
   
   /**
   * This method is used to display the entire GUI on a stage
   * @return void This method returns nothing
   */
    @Override
   public void start(Stage stage) throws Exception { 
      
      try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}catch(ClassNotFoundException ex) {
				System.out.println("Error: unable to load driver class!");
				System.exit(1);
		}
	  
	  
	  //creating label Name 
      Text label_1 = new Text("Name:");       
      
      //creating label Registerd
      Text label_2 = new Text("Registerd:"); 
       
      //Creating Text Filed for Name        
      TextField textField1 = new TextField();       
      
      //Creating Combo Box for Registerd        
      ComboBox comboBox = new ComboBox();
       
      //Creating Buttons 
      Button save_button = new Button("Save"); 
      Button remove_button = new Button("Remove");  
      
	  //creating the save button event handler
	  EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>(){
		  @Override
		  public void handle(MouseEvent e){
			  System.out.println(" ****** Data Sent to DB ********\n");
			  try{ 
              String sql = "INSERT INTO Genres"+"(genre,isactive) VALUES(?,?)";
			  final PreparedStatement stmt = conn.prepareStatement(sql);			  
			  
			  stmt.setString(1,textField1.getText());
			  stmt.setInt(2,1);
			  stmt.executeUpdate();}
			  
			  catch(SQLException se){
				  se.printStackTrace();
				  }
		  }
	  };
	  
	  save_button.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
	  
      //Creating a Grid Pane 
      GridPane gridPane = new GridPane();    
      
      //Setting size for the pane 
      gridPane.setMinSize(600, 400); 
      
      //Setting the padding  
      gridPane.setPadding(new Insets(10, 10, 10, 10)); 
      
      //Setting the vertical and horizontal gaps between the columns 
      gridPane.setVgap(5); 
      gridPane.setHgap(5);       
      
      //Setting the Grid alignment 
      gridPane.setAlignment(Pos.CENTER); 
       
      //Arranging all the nodes in the grid 
      gridPane.add(label_1, 0, 0); 
      gridPane.add(textField1, 1, 0); 
      gridPane.add(save_button, 1, 1); 
	  gridPane.add(label_2,0,2);
	  gridPane.add(comboBox,1,2);
      gridPane.add(remove_button, 1, 3); 
       
      //Styling nodes  
      save_button.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
      remove_button.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
       
      label_1.setStyle("-fx-font: normal bold 20px 'serif' "); 
      label_2.setStyle("-fx-font: normal bold 20px 'serif' ");  
      gridPane.setStyle("-fx-background-color: BEIGE;"); 
       
      //Creating a scene object 
      Scene scene = new Scene(gridPane); 
       
      //Setting title to the Stage 
      stage.setTitle("Genre Registration Panel"); 
         
      //Adding scene to the stage 
      stage.setScene(scene);
      
      //Displaying the contents of the stage 
      stage.show(); 
   }
  /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
    }  
}
