package controller;

import DatabaseAccess.DBCustomer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



/** CustomersController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class CustomersController implements Initializable {

    @FXML
    TableView<Customer> customersTableView;
    @FXML
    TableColumn<Customer, Integer> custIDTableColumn;
    @FXML TableColumn<Customer, String> custNameTableColumn, custAddressTableColumn, custDivisionTableColumn,
            custCountryTableColumn, custPostalTableColumn, custPhoneTableColumn;
    @FXML
    Button custHomeBtn;

    Stage stage;
    Parent scene;

    ObservableList<Customer> allCusts;

    /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Bind the columns to stretch with the TableView (Customers)
        custIDTableColumn.prefWidthProperty().bind(customersTableView.widthProperty().multiply(0.05));
        custNameTableColumn.prefWidthProperty().bind(customersTableView.widthProperty().multiply(0.25));
        custAddressTableColumn.prefWidthProperty().bind(customersTableView.widthProperty().multiply(0.20));
        custDivisionTableColumn.prefWidthProperty().bind(customersTableView.widthProperty().multiply(0.15));
        custCountryTableColumn.prefWidthProperty().bind(customersTableView.widthProperty().multiply(0.15));
        custPostalTableColumn.prefWidthProperty().bind(customersTableView.widthProperty().multiply(0.10));
        custPhoneTableColumn.prefWidthProperty().bind(customersTableView.widthProperty().multiply(0.10));

        allCusts = DBCustomer.getAllCustomers();
        //set items in table view with customers' data
        custIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custDivisionTableColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        custCountryTableColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        custPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custPostalTableColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customersTableView.setItems(allCusts);
    }

    /** onActionCustHomeBtn
     * ActionEvent that returns the user to the main screen of the application when the HOME button is clicked.
     *
     * @param event action event for custHomeBtn
     *
     * @throws IOException an I/O Exception has occurred
     * */
    @FXML
    public void onActionCustHomeBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }
}
