package controller;

import DatabaseAccess.DBAppointment;
import DatabaseAccess.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

/** DeleteController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class DeleteController implements Initializable {

    @FXML
    ComboBox deleteChooseComboBox, deleteSelectCustomerComboBox;
    @FXML
    Button applyDeleteBtn, deleteClearBtn, deleteHomeBtn;
    @FXML
    TextField deleteIDTxtField, deleteCustomerNameTxtField, deleteAddressTxtField, deleteTitleTxtField,
            deleteCountryTxtField, deleteTypeTxtField, deleteStateProvinceTxtField, deletePostalTxtField,
            deletePhoneTxtField, deleteContactTxtField, deleteStartTxtField, deleteEndTxtField;
    @FXML
    TextArea deleteDescriptionTxtArea;
    @FXML
    TableView<Appointment> deleteTableView;
    @FXML
    TableColumn<Appointment, Integer> deleteApptIDColumn;
    @FXML
    TableColumn<Appointment, String> deleteTitleColumn, deleteDescriptionColumn, deleteLocationColumn, deleteContactColumn,
            deleteTypeColumn;
    @FXML
    TableColumn<Appointment, Timestamp> deleteStartColumn, deleteEndColumn;
    @FXML
    TableView<Customer> deleteCustTableView;
    @FXML
    TableColumn<Customer, Integer> deleteCustCustomerIDColumn;
    @FXML
    TableColumn<Customer, String> deleteCustCustomerNameColumn, deleteCustAddressColumn, deleteCustFLDivisionColumn,
            deleteCustCountryColumn, deleteCustPhoneColumn, deleteCustPostalColumn;
    @FXML
    Label deleteIDLabel;

    Stage stage;
    Parent scene;

    ObservableList<String> choiceList = FXCollections.observableArrayList("Appointment", "Customer");
    ObservableList<String> allCustomerNames = DBCustomer.getCustomerNames();
    ObservableList<Customer> allCustomerObjects = DBCustomer.getAllCustomers();
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        deleteChooseComboBox.setItems(choiceList);
        deleteSelectCustomerComboBox.setVisible(false);
        deleteSelectCustomerComboBox.setItems(allCustomerNames);
        deleteIDLabel.setVisible(false);
        deleteIDTxtField.setEditable(false);
        deleteIDTxtField.setVisible(false);
        deleteCustomerNameTxtField.setVisible(false);
        deleteCustomerNameTxtField.setEditable(false);
        deleteTitleTxtField.setVisible(false);
        deleteTitleTxtField.setEditable(false);
        deleteTypeTxtField.setVisible(false);
        deleteTypeTxtField.setEditable(false);
        deleteContactTxtField.setVisible(false);
        deleteContactTxtField.setEditable(false);
        deleteStartTxtField.setVisible(false);
        deleteStartTxtField.setEditable(false);
        deleteEndTxtField.setVisible(false);
        deleteEndTxtField.setEditable(false);
        deleteDescriptionTxtArea.setVisible(false);
        deleteDescriptionTxtArea.setEditable(false);
        deleteTableView.setVisible(false);
        deleteCustTableView.setVisible(false);
        deleteCustTableView.setVisible(false);
        deleteAddressTxtField.setVisible(false);
        deleteAddressTxtField.setEditable(false);
        deleteCountryTxtField.setVisible(false);
        deleteCountryTxtField.setEditable(false);
        deletePhoneTxtField.setVisible(false);
        deletePhoneTxtField.setEditable(false);
        deletePostalTxtField.setVisible(false);
        deletePostalTxtField.setEditable(false);
        deleteStateProvinceTxtField.setVisible(false);
        deleteStateProvinceTxtField.setEditable(false);

    }

    @FXML
    private void onActionDeleteIDTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteCustomerNameTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteAddressTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteTitleTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteTypeTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeletePostalTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeletePhoneTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteContactTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteCountryTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteStateProvinceTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteStartTxtField(ActionEvent event) {
    }
    @FXML
    private void onActionDeleteEndTxtField(ActionEvent event) {
    }

    /** onActionDeleteChooseComboBox
     * ActionEvent performed when an item is selected from deleteChooseComboBox.
     * Sets visibility of text fields, combo boxes, and tableview when an item is selected from the CHOOSE combobox.
     * It also populates the tableview if Appointment is selected.
     *
     * @param event action event for deleteChooseComboBox
     *
     * Lambda expression to populate text fields when row is clicked in Appointments tableview.
     * Lambda expression to populate text fields when row is clicked in Customer tableview.
     *
     * */
    @FXML
    public void onActionDeleteChooseComboBox(ActionEvent event) {
        if (deleteChooseComboBox.getSelectionModel().getSelectedItem() == "Appointment") {

            deleteCustTableView.setVisible(false);
            deleteAddressTxtField.setVisible(false);
            deleteCountryTxtField.setVisible(false);
            deletePhoneTxtField.setVisible(false);
            deletePostalTxtField.setVisible(true);
            deletePostalTxtField.setPromptText("Location");//used for location with appointments
            deleteStateProvinceTxtField.setVisible(false);
            deleteIDLabel.setVisible(true);
            deleteIDLabel.setText("Appointment ID");
            deleteIDTxtField.setVisible(true);
            deleteIDTxtField.clear();
            deleteCustomerNameTxtField.setVisible(true);
            deleteCustomerNameTxtField.clear();
            deleteTitleTxtField.setVisible(true);
            deleteTypeTxtField.setVisible(true);
            deleteContactTxtField.setVisible(true);
            deleteStartTxtField.setVisible(true);
            deleteEndTxtField.setVisible(true);
            deleteDescriptionTxtArea.setVisible(true);
            deleteTableView.setVisible(true);
            deleteSelectCustomerComboBox.setVisible(false);

            populateAppointmentsTable();

           deleteTableView.setOnMouseClicked((MouseEvent ev) -> {
                if (ev.getClickCount() > 0) {
                    onApptRowClicked();
                }
            });

        } else if (deleteChooseComboBox.getSelectionModel().getSelectedItem() == "Customer") {

            deleteTableView.setVisible(false);
            deleteAddressTxtField.setVisible(true);
            deleteCountryTxtField.setVisible(true);
            deletePhoneTxtField.setVisible(true);
            deletePostalTxtField.setPromptText("Postal Code");
            deletePostalTxtField.setVisible(true);
            deletePostalTxtField.clear();
            deleteStateProvinceTxtField.setVisible(true);
            deleteIDLabel.setVisible(true);
            deleteIDLabel.setText("Customer ID");
            deleteIDTxtField.setVisible(true);
            deleteIDTxtField.clear();
            deleteCustomerNameTxtField.setVisible(true);
            deleteCustomerNameTxtField.clear();
            deleteTitleTxtField.setVisible(false);
            deleteTypeTxtField.setVisible(false);
            deleteContactTxtField.setVisible(false);
            deleteStartTxtField.setVisible(false);
            deleteEndTxtField.setVisible(false);
            deleteDescriptionTxtArea.setVisible(false);
            deleteCustTableView.setVisible(true);
            deleteSelectCustomerComboBox.setVisible(true);

            deleteTableView.setOnMouseClicked((MouseEvent ev) -> {
                if (ev.getClickCount() > 0) {
                    onCustRowClicked();
                }
            });

        }
    }

    /** onActionApplyDeleteBtn
     * ActionEvent that deletes an appointment or customer when DELETE button is clicked.
     * An alert message is displayed if a condition fails.
     * A separate alert message is displayed if conditions are met, asking the user if they want to proceed with the deletion.
     *
     * @param event action event for applyDeleteBtn
     *
     * */
    @FXML
    public void onActionApplyDeleteBtn(ActionEvent event) {

        if(applyDeleteBtn.isArmed()){
            if (deleteIDTxtField.getText() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection Error");
            alert.setHeaderText("No Appointment or Customer selected");
            alert.setContentText("Please select an Appointment or Customer for deletion.");
            System.out.println("Awaiting next attempt...");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
                // ... user chose OK
            }
            }else if ((deleteCustTableView.isVisible() == true) && (deleteCustomerNameTxtField.getText() != null)){

                    String custoName = deleteCustomerNameTxtField.getText();
                    int custId = Integer.parseInt(deleteIDTxtField.getText());
                    Appointment appt = new Appointment();
                    appt.setCustomerID(custId);
                    Customer cust = new Customer();
                    cust.setCustomerID(custId);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Are you certain you want to DELETE this Customer?");
                    alert.setHeaderText("By clicking APPLY DELETE you are confirming your decision to delete this Customer\n " +
                            "and all of their Appointments.\n Deletions are irreversible.\n");
                    alert.setContentText("Click OK to continue deletion.\n" +
                            "Click Cancel to cancel the deletion.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        alert.close();
                        // ... user chose OK
                        DBAppointment.deleteApptByCustomer(custId);
                        DBCustomer.deleteCustomer(cust);
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText("Customer "+ custoName + " has been successfully DELETED.");
                        alert1.showAndWait();
                    } else {

                        // ... user chose CANCEL or closed the dialog
                    }

                }

            else if ((deleteTableView.isVisible() == true) && (deleteIDTxtField.getText() != null)){

                    int apptId = Integer.parseInt(deleteIDTxtField.getText());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Are you certain you want to DELETE this Appointment?");
                    alert.setHeaderText("By clicking APPLY DELETE you are confirming your decision to delete this Appointment.\n " +
                            "Deletions are irreversible.\n");
                    alert.setContentText("Click OK to continue deletion.\n" +
                            "Click Cancel to cancel the deletion.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        alert.close();
                        // ... user chose OK
                        DBAppointment.apptDelete(apptId);
                        deleteTableView.setItems(null);
                        populateAppointmentsTable();
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText("Appointment "+ deleteIDTxtField.getText() +
                                " with type of " + deleteTypeTxtField.getText() +
                                " has been successfully DELETED.");
                        alert1.showAndWait();
                    } else {

                        // ... user chose CANCEL or closed the dialog
                    }

            }
        }


    }

    /**onActionDeleteHomeBtn
     * ActionEvent performed when HOME button is clicked.
     * Returns the user to the main screen of the application.
     *
     * @param event action event for deleteHomeBtn
     *
     * @throws IOException an I/O Exception has occurred
     *
     * */
    @FXML
    public void onActionDeleteHomeBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** onActionDeleteClearBtn
     * ActionEvent performed when CLEAR button is clicked.
     * Clears text fields.
     *
     * @param event action event for deleteClearBtn
     *
     * */
    @FXML
    public void onActionDeleteClearBtn(ActionEvent event) {

        deleteIDTxtField.setText(null);
        deleteCustomerNameTxtField.clear();
        deleteTitleTxtField.clear();
        deleteTypeTxtField.clear();
        deleteContactTxtField.clear();
        deleteStartTxtField.clear();
        deleteEndTxtField.clear();
        deleteDescriptionTxtArea.clear();
        deleteTableView.refresh();
        deleteCustTableView.refresh();
        deleteAddressTxtField.clear();
        deleteCountryTxtField.clear();
        deletePhoneTxtField.clear();
        deletePostalTxtField.clear();
        deleteStateProvinceTxtField.clear();
    }

    /**onActionDeleteSelectCustomerComboBox
     * ActionEvent performed when deleteSelectCustomerComboBox is clicked.
     * Populates Customer tableview when item is selected in deleteSelectCustomerComboBox.
     *
     * @param event action event for deleteSelectCustomerComboBox
     *
     * Lambda expression to filter list of customers by customer ID.
     * Lambda expression to start population of text fields when a row in the tableview is clicked.
     *
     * */
    @FXML
    public void onActionDeleteSelectCustomerComboBox(ActionEvent event) {

        int cust = DBCustomer.getCustomerId(deleteSelectCustomerComboBox.getSelectionModel().getSelectedItem().toString());
        FilteredList<Customer> chosenCustomer = new FilteredList<>(allCustomerObjects, i -> i.getCustomerID() == cust);//lambda

        deleteCustCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        deleteCustCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        deleteCustAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        deleteCustFLDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        deleteCustCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        deleteCustPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        deleteCustPostalColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));
        deleteCustTableView.setItems(chosenCustomer);


        deleteCustTableView.setOnMouseClicked((MouseEvent ev) -> {
            if (ev.getClickCount() > 0) {
                onCustRowClicked();
            }
        });
    }


    /** onCustRowClicked
     * Method called when row is clicked in Customer tableview.
     * Sets text fields' text with selected customer object values.
     *
     * */
    public void onCustRowClicked() {
        // check the table's selected item and get selected item
        if (deleteCustTableView.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = deleteCustTableView.getSelectionModel().getSelectedItem();
            deleteIDTxtField.setText(String.valueOf(selectedCustomer.getCustomerID()));
            deleteCustomerNameTxtField.setText(selectedCustomer.getCustomerName());
            deleteAddressTxtField.setText(selectedCustomer.getCustomerAddress());
            deleteStateProvinceTxtField.setText(selectedCustomer.getDivisionName());
            deleteCountryTxtField.setText(selectedCustomer.getCountryName());
            deletePhoneTxtField.setText(selectedCustomer.getPhone());
            deletePostalTxtField.clear();
            deletePostalTxtField.setText(selectedCustomer.getPostal());
        }
    }

    /** onApptRowClicked
     * Method called when row is clicked in Appointment tableview.
     * Sets text fields' text with selected appointment object values.
     *
     * */
    public void onApptRowClicked() {
        // check the table's selected item and get selected item
        if (deleteTableView.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppt = deleteTableView.getSelectionModel().getSelectedItem();
            int custId = selectedAppt.getCustomerID();
            deleteIDTxtField.setText(String.valueOf(selectedAppt.getAppointmentID()));
            deleteCustomerNameTxtField.setText(DBCustomer.getSingleCustomerName(custId));
            deleteTitleTxtField.setText(selectedAppt.getTitle());
            deleteDescriptionTxtArea.setText(selectedAppt.getDescription());
            deletePostalTxtField.clear();
            deletePostalTxtField.setText(selectedAppt.getLocation());
            deleteContactTxtField.setText(selectedAppt.getContactName());
            deleteTypeTxtField.setText(selectedAppt.getType());
            deleteStartTxtField.setText(selectedAppt.getStart().toString());
            deleteEndTxtField.setText(selectedAppt.getEnd().toString());
        }
    }


    /** populateAppointmentsTable
     * Method called when Appointment is selected in CHOOSE combo box.
     * Populates the Appointment tableview with appointments data.
     *
     * */
    public void populateAppointmentsTable(){
        allAppointments = DBAppointment.getAllAppointmentsRefresh();

        deleteApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        deleteTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        deleteDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        deleteLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        deleteContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        deleteTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        deleteStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        deleteEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        deleteTableView.setItems(allAppointments);
    }




}
