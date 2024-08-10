package controller;

import DatabaseAccess.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.User;
import utils.TimeControls;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


/** UpdateController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class UpdateController implements Initializable {


    @FXML
    ComboBox updateChooseComboBox, updateCountryComboBox, updateStateProvinceComboBox, updateStartComboBox, updateEndComboBox,
            updateUserComboBox, updateContactComboBox;
    @FXML
    Button applyUpdateBtn, updateClearBtn, updateHomeBtn;
    @FXML
    TextField updateIDTxtField, updateCustomerNameTxtField, updateAddressTxtField, updateTitleTxtField,
            updateTypeTxtField, updatePostalTxtField, updatePhoneTxtField, updateContactTxtField, updateUserTxtField,
            updateDescriptionTxtField;
    @FXML
    DatePicker updateApptDatePicker;
    @FXML
    TableView<Appointment> updateApptTableView;
    @FXML
    TableColumn<Appointment, Integer> updateApptIDColumn, updateCustomerColumn;
    @FXML
    TableColumn<Appointment, String> updateTitleColumn, updateDescriptionColumn, updateLocationColumn, updateContactColumn,
            updateTypeColumn;
    @FXML
    TableColumn<Appointment, Timestamp> updateStartColumn, updateEndColumn;
    @FXML
    TableView<Customer> updateCustTableView;
    @FXML
    TableColumn<Customer, Integer> updateCustCustomerIDColumn;
    @FXML
    TableColumn<Customer, String> updateCustCustomerNameColumn, updateCustAddressColumn, updateCustFLDivisionColumn,
            updateCustPhoneColumn, updateCustPostalColumn;
    @FXML
    Label updateIDLabel;

    Stage stage;
    Parent scene;
    LocalDate chosenDate;
    /**
     * startTime
     * public variable startTime of type LocalTime.
     * used throughout this (UpdateController) class in association with start combo box items.
     */
    public LocalTime startTime;
    /**
     * endTime
     * public variable endTime of type LocalTime.
     * used throughout this (UpdateController) class in association with end combo box items.
     */
    public LocalTime endTime;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * invalidBizHours
     * Boolean value.
     * Associated with Boolean method validateBizHours.
     */
    public static Boolean invalidBizHrs;
    /**
     * overlap
     * Boolean value.
     * Associated with Boolean method validateOverlap.
     */
    public static Boolean overlap;

    ObservableList<String> allContacts = DBContact.getContactNames();
    ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();
    ObservableList<String> allUsers = DBUser.getUserNames();
    ObservableList<String> choiceList = FXCollections.observableArrayList("Appointment", "Customer");
    ObservableList<String> allCountries = FXCollections.observableArrayList("U.S.", "UK", "Canada");
    ObservableList<String> allDivisionsUS = DBFirstLevelDivision.getDivisionNamesUS();
    ObservableList<String> allDivisionsUK = DBFirstLevelDivision.getDivisionNamesUK();
    ObservableList<String> allDivisionsCanada = DBFirstLevelDivision.getDivisionNamesCanada();
    ObservableList<Appointment> allAppts = FXCollections.observableArrayList();


    /**
     * initialize
     * Initializes the stage/scene.
     * Contains conditions and call to update method.
     * <p>
     * Lambda expression sets start hours in addStartComboBox
     * Lambda expression sets end hours in addEndComboBox
     * Lambda expression event listener to determine if updateIDTxtField is null (alert displayed if null,
     * otherwise continue to next condition of attempted update)
     *
     * @param url            Stage path
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Bind the columns to stretch with the TableView (Appointments)
        updateTitleColumn.prefWidthProperty().bind(updateApptTableView.widthProperty().multiply(0.15));
        updateDescriptionColumn.prefWidthProperty().bind(updateApptTableView.widthProperty().multiply(0.25));
        updateLocationColumn.prefWidthProperty().bind(updateApptTableView.widthProperty().multiply(0.15));
        updateTypeColumn.prefWidthProperty().bind(updateApptTableView.widthProperty().multiply(0.15));

        //Bind the columns to stretch with the TableView (Customers)
        updateCustCustomerIDColumn.prefWidthProperty().bind(updateCustTableView.widthProperty().multiply(0.10));
        updateCustCustomerNameColumn.prefWidthProperty().bind(updateCustTableView.widthProperty().multiply(0.20));
        updateCustAddressColumn.prefWidthProperty().bind(updateCustTableView.widthProperty().multiply(0.30));
        updateCustFLDivisionColumn.prefWidthProperty().bind(updateCustTableView.widthProperty().multiply(0.20));
        updateCustPhoneColumn.prefWidthProperty().bind(updateCustTableView.widthProperty().multiply(0.10));
        updateCustPostalColumn.prefWidthProperty().bind(updateCustTableView.widthProperty().multiply(0.10));

        //set Choice(Appointment or Customer), Country, Contact, and User combo box items
        updateChooseComboBox.setItems(choiceList);
        updateCountryComboBox.setItems(allCountries);
        updateContactComboBox.setItems(allContacts);
        updateUserComboBox.setItems(allUsers);

        //lambdas used to set hours (in conjunction with StartHoursCell and EndHoursCell methods) in time combo boxes
        updateStartComboBox.setCellFactory(cb -> new UpdateController.StartHoursCell());//lambda 1
        updateEndComboBox.setCellFactory(cb -> new UpdateController.EndHoursCell());//lambda 2
        for (int i = 8; i < 22; i++) {//sets start time values from 8 to 21 (8 am to 9 pm)
            updateStartComboBox.getItems().add(i);
        }
        for (int i = 9; i < 23; i++) {//sets end time values from 9 to 22 (9 am to 10 pm)
            updateEndComboBox.getItems().add(i);

            updateIDLabel.setVisible(false);
            updateIDTxtField.setEditable(false);
            updateIDTxtField.setVisible(false);
            updateCustomerNameTxtField.setVisible(false);
            updateTitleTxtField.setVisible(false);
            updateTypeTxtField.setVisible(false);
            updateContactComboBox.setVisible(false);
            updateApptDatePicker.setVisible(false);
            updateStartComboBox.setVisible(false);
            updateEndComboBox.setVisible(false);
            updateDescriptionTxtField.setVisible(false);
            updateApptTableView.setVisible(false);
            updateCustTableView.setVisible(false);
            updateCustTableView.setVisible(false);
            updateAddressTxtField.setVisible(false);
            updateCountryComboBox.setVisible(false);
            updatePhoneTxtField.setVisible(false);
            updatePostalTxtField.setVisible(false);
            updateStateProvinceComboBox.setVisible(false);
            updateUserComboBox.setVisible(false);
            updateContactTxtField.setVisible(false);
            updateUserTxtField.setVisible(false);


        }

        applyUpdateBtn.setOnAction((ActionEvent e) -> {//lambda 3
            if (updateIDTxtField.getText() == null) { /* if this uneditable text field is null, then no row has been selected from a table view
                and there will be no updating*/
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Selection Error");
                alert.setHeaderText("No Appointment or Customer selected");
                alert.setContentText("Please select an Appointment or Customer to update.");
                System.out.println("Awaiting next attempt...");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                    // ... user chose OK

                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            } else if (updateCustTableView.isVisible()) { //if this is visible then Customer has been selected from updateChooseComboBox
                if (updateCustomerNameTxtField.getText() != null) { //if there's something in this text field than a Customer has been chosen

                    //get data from fields
                    int custId = Integer.parseInt(updateIDTxtField.getText());
                    String custNameOriginal = DBCustomer.getSingleCustomerName(custId);
                    String custNameNew = updateCustomerNameTxtField.getText();
                    String address = updateAddressTxtField.getText();
                    String division = updateTitleTxtField.getText();
                    int divisionId = DBCustomer.getDivisionID(division);
                    String postal = updatePostalTxtField.getText();
                    if (postal.length() < 5) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Postal Code Error");
                        alert.setHeaderText("All postal codes must be 5 characters long.");
                        alert.showAndWait();
                    }
                    String phoneNum = updatePhoneTxtField.getText();
                    if (phoneNum.length() < 10 || phoneNum.length() == 11) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Phone Number Error");
                        alert.setHeaderText("U.S. and Canada phone numbers require 10 digits\n" +
                                "International phone numbers require 12");
                        alert.showAndWait();
                    }
                    String phone = null;
                    if (phoneNum.length() == 10) {
                        phone = phoneNum.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");//format phone for North America (123-456-789)
                    }
                    if (updatePhoneTxtField.getText().length() == 12)
                        phone = phoneNum.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3-$4");//format phone number for International (12-345-678-9000)


                    System.out.println(phone);


                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now().format(dtf));
                    String lastUpdatedBy = User.getCurrentUser();

                    if (!(postal.length() < 5) && (!phoneNum.isEmpty()) && (phoneNum.length() != 11) && !(phoneNum.length() < 10)) {
                        //new Customer instance and setting values
                        Customer cust = new Customer();
                        cust.setCustomerID(custId);
                        cust.setCustomerName(custNameNew);
                        cust.setCustomerAddress(address);
                        cust.setDivisionID(divisionId);
                        cust.setPostal(postal);
                        cust.setPhone(phone);
                        cust.setLastUpdate(String.valueOf(lastUpdate));
                        cust.setLastUpdatedBy(lastUpdatedBy);

                        //update Customer and show update confirmation
                        DBCustomer.updateCustomer(cust);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Updated Customer");
                        alert.setHeaderText("Customer " + custNameOriginal + " has been successfully updated...\n " +
                                "Updated Customer Name: " + custNameNew + "\n Updated Address: " + address + "\n" +
                                "Updated State/Province: " + division + "\n Updated Postal: " + postal + "\n" +
                                "Updated Phone: " + phone + "\n");
                        alert.setContentText("Click OK to exit.");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            alert.close();
                            // ... user chose
                        } else {

                            // ... user chose CANCEL or closed the dialog
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Other Error");
                        alert.setHeaderText("A non-SQL error occurred while attempting to update the Customer.\n" +
                                "Please check all fields and drop downs before attempting to apply the update.");
                        alert.showAndWait();
                    }
                }
            } else if (updateApptTableView.isVisible() && updateDescriptionTxtField.getText() != null && !updateIDTxtField.getText().isEmpty()) {
                //if this table view is visible then Appointment was chosen from updateChooseComboBox
                //if the above fields have text then an Appointment row has been chosen

                updateApptToDB();
            }
        });
    }


    /**
     * onActionUpdateChooseComboBox
     * ActionEvent performed when Appointment or Customer is selected from CHOOSE combo box.
     * Sets visibility of scene elements, calls method(s) to populate table views.
     * <p>
     * Lambda expression to call method to fill fields with data when row in visible table view is clicked
     *
     * @param event action event for updateChooseComboBox
     */
    @FXML
    public void onActionUpdateChooseComboBox(ActionEvent event) {
        //if user selects Appointment from combo box
        if (updateChooseComboBox.getSelectionModel().getSelectedItem() == "Appointment") {
            //show only fields used for displaying/gathering appointment data
            updateCustTableView.setVisible(false);
            updateAddressTxtField.setVisible(false);
            updateCountryComboBox.setVisible(false);
            updatePhoneTxtField.setVisible(false);
            updatePostalTxtField.setVisible(true);
            updatePostalTxtField.setPromptText("Location");
            updateStateProvinceComboBox.setVisible(false);
            updateIDLabel.setVisible(true);
            updateIDLabel.setText("Appointment ID");
            updateIDTxtField.setVisible(true);
            updateCustomerNameTxtField.setVisible(true);
            updateCustomerNameTxtField.setEditable(false);
            updateTitleTxtField.setVisible(true);
            updateTitleTxtField.setPromptText("Title");
            updateTypeTxtField.setVisible(true);
            updateContactComboBox.setVisible(true);
            updateApptDatePicker.setVisible(true);
            updateStartComboBox.setVisible(true);
            updateEndComboBox.setVisible(true);
            updateDescriptionTxtField.setVisible(true);
            updateApptTableView.setVisible(true);
            updateUserComboBox.setVisible(true);
            updateContactTxtField.setVisible(true);
            updateUserTxtField.setVisible(true);

            populateAppointmentsTable();

            //lambda to call method to fill fields with data when row in appointments table view is clicked
            updateApptTableView.setOnMouseClicked((MouseEvent ev) -> {//lambda 4
                if (ev.getClickCount() > 0) {
                    onApptRowClicked();
                }
            });

            //if user selects Customer from combo box
        } else if (updateChooseComboBox.getSelectionModel().getSelectedItem() == "Customer") {

            //show only fields used for displaying/gathering customer data
            updateApptTableView.setVisible(false);
            updateAddressTxtField.setVisible(true);
            updateCountryComboBox.setVisible(true);
            updatePhoneTxtField.setVisible(true);
            updatePhoneTxtField.setEditable(false);
            updatePostalTxtField.setPromptText("Postal Code");
            updatePostalTxtField.setVisible(true);
            updatePostalTxtField.setEditable(false);
            updateStateProvinceComboBox.setVisible(true);
            updateIDLabel.setVisible(true);
            updateIDLabel.setText("Customer ID");
            updateIDTxtField.setVisible(true);
            updateCustomerNameTxtField.setVisible(true);
            updateCustomerNameTxtField.setEditable(true);
            updateTitleTxtField.setVisible(false);
            updateTypeTxtField.setVisible(false);
            updateContactComboBox.setVisible(true);
            updateApptDatePicker.setVisible(false);
            updateStartComboBox.setVisible(false);
            updateEndComboBox.setVisible(false);
            updateDescriptionTxtField.setVisible(false);
            updateCustTableView.setVisible(true);
            updateUserComboBox.setVisible(false);
            updateContactComboBox.setVisible(false);
            updateContactTxtField.setVisible(false);
            updateUserTxtField.setVisible(false);


            populateCustomersTable();

            //lambda to fill fields with data when row in customer table view is clicked
            updateCustTableView.setOnMouseClicked((MouseEvent ev) -> {//lambda 5
                if (ev.getClickCount() > 0) {
                    onCustRowClicked();
                }
            });

        }
    }


    private class StartHoursCell extends ListCell<Integer> {

        //lambda listener for start hours combo box
        StartHoursCell() {
            updateEndComboBox.valueProperty().addListener((obs, oldEndHours, newEndHours) -> updateDisableState());//lambda 6
        }

        /**
         * updateItem
         * Updates the hour items.
         *
         * @param hourValue integer value representing an hour
         * @param empty     boolean value
         */
        @Override
        protected void updateItem(Integer hourValue, boolean empty) {//updates the hour items

            super.updateItem(hourValue, empty);
            if (empty) {
                setText(null);
            } else {
                setText(TimeControls.convertComboTimeValue(hourValue));
                updateDisableState();
            }
        }

        private void updateDisableState() {//updates disabling of hours before or at the same time as selected start time
            boolean disable = getItem() != null && updateEndComboBox.getValue() != null
                    && getItem() >= Integer.parseInt(updateEndComboBox.getValue().toString());
            setDisable(disable);
            setOpacity(disable ? 0.5 : 1);
        }

    }

    private class EndHoursCell extends ListCell<Integer> {

        //lambda listener for end hours combo box
        EndHoursCell() {
            updateStartComboBox.valueProperty().addListener((obs, oldEndHours, newEndHours) -> updateDisableState());//lambda 7
        }

        /**
         * updateItem
         * Updates the hour items.
         *
         * @param hourValue integer value representing an hour
         * @param empty     boolean value
         */
        @Override
        protected void updateItem(Integer hourValue, boolean empty) {//updates the hour items

            super.updateItem(hourValue, empty);
            if (empty) {
                setText(null);
            } else {
                setText(TimeControls.convertComboTimeValue(hourValue));
                updateDisableState();
            }
        }

        private void updateDisableState() {//updates disabling of hours (if end is chosen first)
            boolean disable = getItem() != null && updateStartComboBox.getValue() != null
                    && getItem() <= Integer.parseInt(updateStartComboBox.getValue().toString());
            setDisable(disable);
            setOpacity(disable ? 0.5 : 1);

        }
    }


    @FXML
    private void onActionApplyUpdateBtn(ActionEvent event) {
    }


    /**
     * onActionUpdateHomeBtn
     * ActionEvent performed when HOME button is clicked.
     * Switches scene to main screen of application when HOME button is clicked.
     *
     * @param event action event for updateHomeBtn
     * @throws IOException an I/O Exception has occurred
     */
    @FXML
    public void onActionUpdateHomeBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }


    /**
     * onActionUpdateClearBtn
     * ActionEvent performed when CLEAR button is clicked.
     * Clears text fields and combo box selections.
     *
     * @param event action event for updateClearBtn
     */
    @FXML
    public void onActionUpdateClearBtn(ActionEvent event) {
        //clears all fields
        updateIDTxtField.clear();
        updateCustomerNameTxtField.clear();
        updateTitleTxtField.clear();
        updateTypeTxtField.clear();
        updateContactComboBox.getSelectionModel().clearSelection();
        updateApptDatePicker.setValue(null);
        updateStartComboBox.getSelectionModel().clearSelection();
        updateEndComboBox.getSelectionModel().clearSelection();
        updateDescriptionTxtField.clear();
        updateAddressTxtField.clear();
        updateCountryComboBox.getSelectionModel().clearSelection();
        updatePhoneTxtField.clear();
        updatePostalTxtField.clear();
        updateStateProvinceComboBox.getSelectionModel().clearSelection();
        updateContactTxtField.clear();
        updateUserTxtField.clear();
    }

    @FXML
    private void onActionUpdateIDTxtField(ActionEvent event) {
    }

    @FXML
    private void onActionUpdateCustomerNameTxtField(ActionEvent event) {
    }

    @FXML
    private void onActionUpdateAddressTxtField(ActionEvent event) {
    }

    @FXML
    private void onActionUpdateTitleTxtField(ActionEvent event) {
    }

    @FXML
    private void onActionUpdateTypeTxtField(ActionEvent event) {
    }


    @FXML
    private void onActionUpdateContactTxtField(ActionEvent event) {

    }


    @FXML
    private void onActionUpdateUserTxtField(ActionEvent event) {

    }

    /**
     * onActionUpdateStateProvinceComboBox
     * ActionEvent performed when an item is selected in updateCountryComboBox.
     * Sets the postal text field to editable and formats allowable input based on selection.
     * <p>
     * Lambda expression to limit text field to digits only, with a max length of 5 digits, when U.S. or Canada are selected in the combo box.
     * Lambda expression to limit text field to alphanumeric characters only, with a mx length of 5 characters, when UK is selected from the combo box.
     *
     * @param event action event for updateStateProvinceComboBox
     */
    @FXML
    public void onActionUpdateStateProvinceComboBox(ActionEvent event) {
        if (updateCountryComboBox.getSelectionModel().getSelectedItem() != "UK" && updateStateProvinceComboBox.getValue() != null) {
            updatePostalTxtField.setEditable(true);
            updatePostalTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("^|\\d{0,5}") ? c : null));//lambda 8
        } else if (updateCountryComboBox.getSelectionModel().getSelectedItem() == "UK" && updateStateProvinceComboBox.getValue() != null) {
            updatePostalTxtField.setEditable(true);
            updatePostalTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("^|[a-zA-Z0-9]{0,5}$") ? c : null));//lambda 9
        }
    }

    @FXML
    private void onActionUpdateStartComboBox(ActionEvent event) {
    }

    @FXML
    private void onActionUpdateEndComboBox(ActionEvent event) {
    }

    @FXML
    private void onActionUpdatePostalTxtField(ActionEvent event) {
    }

    @FXML
    private void onActionUpdatePhoneTxtField(ActionEvent event) {
    }

    /**
     * onActionUpdateContactComboBox
     * ActionEvent performed when an item is selected from updateContactComboBox.
     * Sets text in updateContactTxtField with the selected contact's name.
     *
     * @param event action event for updateContactComboBox
     */
    @FXML
    public void onActionUpdateContactComboBox(ActionEvent event) {
        if (updateContactComboBox.getValue() != null) {
            updateContactTxtField.setText(updateContactComboBox.getSelectionModel().getSelectedItem().toString());
        }
    }

    /**
     * onActionUpdateCountryComboBox
     * ActionEvent performed when an item is selected in updateCountryComboBox.
     * Sets the phone textfield to editable and formats allowable input based on selection.
     * <p>
     * Lambda expression to limit textfield to digits only, with a max length of 10 digits, when U.S. or Canada are selected in the combo box.
     * Lambda expression to limit textfield to digits only, with a mx length of 12 digits, when UK is selected from the combo box.
     *
     * @param event action event for updateCountryComboBox
     */
    @FXML
    public void onActionUpdateCountryComboBox(ActionEvent event) {
        //populates state/province combo based on country combo choice
        if (updateCountryComboBox.getSelectionModel().getSelectedItem() == "U.S.") {
            updateStateProvinceComboBox.setItems(allDivisionsUS);
            updatePhoneTxtField.clear();
            updatePhoneTxtField.setEditable(true);
            updatePhoneTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches( //lambda 10
                    "^|[0-9]{0,10}") ? c : null));
        } else if (updateCountryComboBox.getSelectionModel().getSelectedItem() == "UK") {
            updateStateProvinceComboBox.setItems(allDivisionsUK);
            updatePhoneTxtField.setEditable(true);
            updatePhoneTxtField.clear();
            updatePhoneTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches( //lambda 11
                    "^|[0-9]{0,12}") ? c : null));
        } else if (updateCountryComboBox.getSelectionModel().getSelectedItem() == "Canada") {
            updateStateProvinceComboBox.setItems(allDivisionsCanada);
            updatePhoneTxtField.clear();
            updatePhoneTxtField.setEditable(true);
            updatePhoneTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches( //lambda 12
                    "^|[0-9]{0,10}") ? c : null));
        }
    }

    @FXML
    private void onActionUpdateDescriptionTxtField(ActionEvent event) {

    }

    /**
     * onActionUpdateStateProvinceComboBox
     * ActionEvent performed when an item is selected in updateUserComboBox.
     * Sets the user textfield with the selected user's name.
     *
     * @param event action event for updateUserComboBox
     */
    @FXML
    public void onActionUpdateUserComboBox(ActionEvent event) {

        if (updateUserComboBox.getValue() != null) {
            updateUserTxtField.setText(updateUserComboBox.getSelectionModel().getSelectedItem().toString());
        }
    }

    /**
     * onActionUpdateApptDatePicker
     * ActionEvent performed when a date is selected from the date picker.
     * Gets value from updateApptDatePicker.
     * If value is null or selected date is today's date or before, an error alert is displayed.
     *
     * @param event action event for updateApptDatePicker
     */
    @FXML
    public void onActionUpdateApptDatePicker(ActionEvent event) {

        //warning message if user chooses today's date or before
        if (updateApptDatePicker.getValue() != null) {
            chosenDate = updateApptDatePicker.getValue();
            System.out.println("Chosen date: " + chosenDate);
            LocalDate today = LocalDate.now();
            System.out.println("Today's date: " + today);

            if (chosenDate.isBefore(today) || today.isEqual(chosenDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date Error");
                alert.setHeaderText("Date cannot be today's date or before.\n" +
                        "Our consultants require at least one day's notice\n" +
                        "before an appointment is scheduled\n" +
                        "to allow for personal time. Please select a date after today's date.");
                alert.setContentText("Click OK to exit.");

                updateApptDatePicker.setValue(null);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                    // ... user chose OK
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        }

    }

    /**
     * onCustRowClicked
     * Method called when row is clicked in customer tableview.
     * Sets text fields with selected customer's data.
     */
    public void onCustRowClicked() {
        // check the customer table's selected item and get selected item
        if (updateCustTableView.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = updateCustTableView.getSelectionModel().getSelectedItem();
            updateIDTxtField.setText(String.valueOf(selectedCustomer.getCustomerID()));
            updateCustomerNameTxtField.setText(selectedCustomer.getCustomerName());
            updateAddressTxtField.setText(selectedCustomer.getCustomerAddress());
            updateTitleTxtField.setText(selectedCustomer.getDivisionName());
            updatePhoneTxtField.setText(selectedCustomer.getPhone());
            updatePostalTxtField.clear();
            updatePostalTxtField.setText(selectedCustomer.getPostal());
        }
    }

    /**
     * onApptRowClicked
     * Method called when row is clicked in appointment tableview.
     * Sets text fields with selected appointment's data.
     */
    public void onApptRowClicked() {
        // check the appointment table's selected item and get selected item
        if (updateApptTableView.getSelectionModel().getSelectedItem() != null) {
            Appointment selectedAppt = updateApptTableView.getSelectionModel().getSelectedItem();
            updateIDTxtField.setText(String.valueOf(selectedAppt.getAppointmentID()));
            updateCustomerNameTxtField.setText(DBCustomer.getSingleCustomerName(selectedAppt.getCustomerID()));
            updateTitleTxtField.setText(selectedAppt.getTitle());
            updateDescriptionTxtField.setText(selectedAppt.getDescription());
            updatePostalTxtField.clear();
            updatePostalTxtField.setText(selectedAppt.getLocation());
            int contactId = selectedAppt.getContactID();
            updateContactTxtField.setText(DBContact.getContactName(contactId));
            int userId = selectedAppt.getUserID();
            updateUserTxtField.setText(DBUser.getSingleUserName(userId));
            updateTypeTxtField.setText(selectedAppt.getType());
            updatePhoneTxtField.setText("Start: " + selectedAppt.getStart() + " End: " + selectedAppt.getEnd());

        }
    }


    /**
     * populateAppointmentsTable
     * Method called when Appointment is selected in CHOOSE combo box.
     * Populates the Appointment tableview with appointments data.
     */
    public void populateAppointmentsTable() {


        allAppts = DBAppointment.getAllAppointmentsRefresh();

        updateApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        updateTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        updateDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        updateLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        updateContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        updateTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        updateStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        updateEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        updateCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        updateApptTableView.setItems(allAppts);
    }

    /**
     * populateCustomersTable
     * Method called when Customer is selected in CHOOSE combobox.
     * Populates the Customer tableview with customer data.
     */
    public void populateCustomersTable() {
        updateCustCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        updateCustCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        updateCustAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        updateCustFLDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        updateCustPostalColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));
        updateCustPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        updateCustTableView.setItems(allCustomers);
    }

    /**
     * updateApptToDB
     * Method called when UPDATE button is clicked and all conditions for updating have been met.
     * If all conditions are met (no empty fields, valid business hours and no overlapping appointments for selected customer),
     * then appointment is updated in the database. Alert message is displayed if a condition fails.
     */
    public void updateApptToDB() {

        if (applyUpdateBtn.isArmed()) {
            if (updateApptDatePicker.getValue() == null | updateStartComboBox.getSelectionModel().isEmpty() | updateEndComboBox.getSelectionModel().isEmpty()
                    | updateCustomerNameTxtField.getText().isEmpty() | updateTitleTxtField.getText().isEmpty() | updateTypeTxtField.getText().isEmpty()
                    | updatePostalTxtField.getText().isEmpty() | updateDescriptionTxtField.getText().isEmpty() | updateContactTxtField.getText().isEmpty()
                    | updateUserTxtField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Field Error");
                alert.setHeaderText("There is at least one empty field or unselected item.\n" +
                        "Please make sure all fields are filled out and all items selected.");
                alert.setContentText("Click OK to exit.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                } else {
                    //... user chose CANCEL or closed the dialog
                }
            } else {
            }

            String customerName = updateCustomerNameTxtField.getText();

            if (updateApptDatePicker.getValue() == null | updateStartComboBox.getValue() == null | updateEndComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Date/Time Confirmation Error");
                alert.setHeaderText("An appointment's date, start, and end times must all be re-entered before updating.");
                alert.showAndWait();
            }
            chosenDate = updateApptDatePicker.getValue();


            //LocalTime versions of start/end combo box values
            int startInt = Integer.parseInt(updateStartComboBox.getSelectionModel().getSelectedItem().toString());
            String startS = TimeControls.convertComboTimeValue(startInt);
            LocalTime startTime = LocalTime.parse(startS);
            int endInt = Integer.parseInt(updateEndComboBox.getSelectionModel().getSelectedItem().toString());
            String endS = TimeControls.convertComboTimeValue(endInt);
            LocalTime endTime = LocalTime.parse(endS);

            //create LocalDateTime from chosenDate and start/end times
            LocalDateTime startDateTime = LocalDateTime.of(chosenDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(chosenDate, endTime);

            //Convert LocalDateTime from EST to UTC
            ZoneId estZoneId = ZoneId.of("America/New_York"); //establish ZoneId of New York for EST
            ZonedDateTime startDateTimeEST = startDateTime.atZone(estZoneId); //this ensures EST start times
            //System.out.println("LDT startDateTime (used to compare against dbLDT in validateOverlap): " + startDateTimeEST);
            ZonedDateTime endDateTimeEST = endDateTime.atZone(estZoneId);
            //System.out.println("LDT endDateTime (used to compare against dbLDT in validateOverlap): " + endDateTimeEST);

            //Convert to UTC
            ZonedDateTime startUTC = startDateTimeEST.withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = endDateTimeEST.withZoneSameInstant(ZoneId.of("UTC"));

            LocalDateTime start = startUTC.toLocalDateTime(); //changed from startZDT.toLocalDateTime()
            LocalDateTime end = endUTC.toLocalDateTime(); //changed from endZDT.toLocalDateTime()


            //Business hours
            ZonedDateTime bizStart = ZonedDateTime.of(chosenDate, LocalTime.of(8, 0), ZoneId.of("America/New_York"));
            ZonedDateTime bizEnd = ZonedDateTime.of(chosenDate, LocalTime.of(22, 0), ZoneId.of("America/New_York"));
            LocalTime bizStartLT = bizStart.toLocalTime();
            LocalTime bizEndLT = bizEnd.toLocalTime();

            //check business hours
            LocalTime startLT = startDateTime.toLocalTime();//changed from startZDT
            LocalTime endLT = endDateTime.toLocalTime();//changed from endZDT

            boolean invalidBizHrs = validateBizHours(startLT, endLT, bizStartLT, bizEndLT);
            if (invalidBizHrs) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Business Hours Error");
                alert.setHeaderText("Your selected hours fall outside of our business hours, which are in EST.\n" +
                        "Please choose a start time between 08:00 and 21:00 / end time between 09:00 and 22:00 EST.");
                alert.setContentText("Click OK to exit.");
                updateStartComboBox.getSelectionModel().clearSelection();
                updateEndComboBox.getSelectionModel().clearSelection();

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                } else {
                    //... user chose CANCEL or closed the dialog
                }
            }


            overlap = validateOverlap(customerName, start, end);
            if (overlap) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Overlapping Appointment Error");
                alert.setHeaderText("Customer " + customerName + "\n" +
                        "already has an appointment scheduled during this window, Please select a different date/time.");
                alert.setContentText("Click OK to exit.");
                updateStartComboBox.getSelectionModel().clearSelection();
                updateEndComboBox.getSelectionModel().clearSelection();

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                } else {
                    //... user chose CANCEL or closed the dialog
                }
            }


            if ((!invalidBizHrs) & (!overlap)) {
                String upTitle = updateTitleTxtField.getText();
                String upType = updateTypeTxtField.getText();
                String upLocation = updatePostalTxtField.getText();
                String upContact = updateContactTxtField.getText();
                String upDescription = updateDescriptionTxtField.getText();
                String lastUpdatedBy = User.getCurrentUser();
                int upCustId = DBCustomer.getCustomerId(customerName);
                int upContactId = DBContact.getContactId(upContact);
                int upUserId = DBUser.getUserId(updateUserTxtField.getText());
                int apptId = Integer.parseInt(updateIDTxtField.getText());

                DBAppointment.apptUpdate(apptId, upTitle, upDescription, upLocation, upType, start,
                        end, lastUpdatedBy, upCustId, upUserId, upContactId);//update appointment and confirm
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Updated");
                alert.setHeaderText("Appointment " + updateIDTxtField.getText() + " with customer" + customerName + " and start and end " +
                        "date/times of\n" + updatePhoneTxtField.getText() + " has been successfully updated.\n");
                alert.setContentText("Click OK to exit.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                    // ... user chose OK

                } else {

                    // ... user chose CANCEL or closed the dialog
                }
            }
        }
    }

    /**
     * validateBizHours
     * Boolean method to check selected start and end times against company's business hours (EST).
     * The start and end times selected from the comboboxes are changed from the user's system default time to EST for comparison.
     *
     * @param startLT    the selected start LocalTime converted to EST
     * @param endLT      the selected end LocalTime converted to EST
     * @param bizStartLT company's EST business hours from 8 am to 9 pm EST
     * @param bizEndLT   company's EST business hours from 9am to 10 pm EST
     * @return returns true if INVALID business hours are selected, else false
     */
    public Boolean validateBizHours(LocalTime startLT, LocalTime endLT, LocalTime bizStartLT, LocalTime bizEndLT) {

        if (startLT.isBefore(bizStartLT)) return true;
        if (endLT.isBefore(bizStartLT)) return true;
        if (startLT.isAfter(bizEndLT)) return true;
        if (endLT.isAfter(bizEndLT)) return true;

        else return false;
    }

    /**
     * validateOverlap
     * Boolean method to check selected appointment date and start/end times against existing appointments
     * for the selected customer during the same time period on the same date.
     *
     * @param customerName name of customer associated with the appointment
     * @param start        the selected date and start time
     * @param end          the selected date and end time
     * @return returns true if there IS an overlap with an existing appointment for this customer, else false
     */
    public static Boolean validateOverlap(String customerName, LocalDateTime start, LocalDateTime end) {

        ObservableList<Appointment> allAppts = DBAppointment.getAllAppointmentsByCustomer(customerName);

        if (!allAppts.isEmpty()) {
            for (Appointment apptmnt : allAppts) {
                LocalDateTime dbStartLDT = apptmnt.getStart();
                LocalDateTime dbEndLDT = apptmnt.getEnd();

                if (start.isBefore(dbStartLDT) && end.isAfter(dbStartLDT)) return true;
                if (start.isBefore(dbStartLDT) && end.isAfter(dbEndLDT)) return true;
                if (start.isAfter(dbStartLDT) && start.isBefore(dbEndLDT)) return true;

            }

        } else return false;


        return false;
    }


}

