import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * customers screen - admin can register and remove customers
 */
public class Customers extends GridPane {

    ComboBox comboBox;

    /** sets up the customers gui */
    public Customers() {

        //create labels
        Text text1 = new Text("Name:");
        Text text2 = new Text("Phone:");
        Text text3 = new Text("Email:");
        Text text4 = new Text("Registered:");

        //create text fields
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();

        //create combo box and load customers
        comboBox = new ComboBox();
        loadCustomers();

        //create buttons
        Button button1 = new Button("Save");
        Button button2 = new Button("Remove");

        //set up the grid
        this.setMinSize(600, 400);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(10);
        this.setHgap(10);
        this.setAlignment(Pos.CENTER);

        //add nodes to grid
        this.add(text1, 0, 0);
        this.add(textField1, 1, 0);

        this.add(text2, 0, 1);
        this.add(textField2, 1, 1);

        this.add(text3, 0, 2);
        this.add(textField3, 1, 2);

        this.add(button1, 1, 3);

        this.add(text4, 0, 4);
        this.add(comboBox, 1, 4);
        this.add(button2, 1, 5);

        //styling
        button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:13pt;");
        text1.setStyle("-fx-font: normal bold 20px 'serif' ");
        text2.setStyle("-fx-font: normal bold 20px 'serif' ");
        text3.setStyle("-fx-font: normal bold 20px 'serif' ");
        text4.setStyle("-fx-font: normal bold 20px 'serif' ");
        this.setStyle("-fx-background-color: BEIGE;");

        //save button - adds customer to db
        button1.setOnAction(e -> {
            String name = textField1.getText();
            if (!name.isEmpty()) {
                DBHelper.addCustomer(name);
                textField1.clear();
                textField2.clear();
                textField3.clear();
                loadCustomers();
            }
        });

        //remove button - removes selected customer
        button2.setOnAction(e -> {
            String selected = (String) comboBox.getValue();
            if (selected != null) {
                DBHelper.removeCustomer(selected);
                loadCustomers();
            }
        });
    }

    /** refreshes the combo box with customers from db */
    void loadCustomers() {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(DBHelper.getCustomerNames());
    }
}
