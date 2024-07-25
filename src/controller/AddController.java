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
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;
import utils.TimeControls;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/** AddController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class AddController implements Initializable {

    @FXML
    Button applyAddBtn, addClearBtn, applyHomeBtn;
    @FXML
    ComboBox<String> addChooseComboBox;
    @FXML
    ComboBox<String> addSelectCustomerComboBox;
    @FXML
    ComboBox<String> addCountryComboBox;
    @FXML
    ComboBox<String> addStateProvinceComboBox;
    @FXML
    ComboBox<String> addContactComboBox;

    /** addStartComboBox
     * FXML ComboBox
     * */
    @FXML
    public
    ComboBox addStartComboBox;

    /** addEndComboBox
     * FXML ComboBox
     * */
    @FXML
    public
    ComboBox addEndComboBox;
    @FXML
    ComboBox<String> addUserComboBox;
    @FXML
    TextField addIDTxtField;
    @FXML
    TextField addCustomerNameTxtField;
    @FXML
    TextField addAddressTxtField;
    @FXML
    TextField addTitleTxtField;
    @FXML
    TextField addTypeTxtField;
    @FXML
    TextField addPostalTxtField;
    @FXML
    TextField addLocationTxtField;
    @FXML
    TextField addPhoneTxtField;
    @FXML
    TextField addDescriptTxtField;
    @FXML
    DatePicker addApptDatePicker;
    @FXML
    Label addIDLabel, addTimesLabel;

    Stage stage;
    Parent scene;

    /** chosenDate
     * LocalDate variable used throughout this (AddController) class
     * */
    public static LocalDate chosenDate;

    /** startTime
     * public variable startTime of type LocalTime.
     * used throughout this (AddController) class in association with start combo box items.
     * */
    public static LocalTime startTime;

    /** endTime
     * public variable endTime of type LocalTime.
     * used throughout this (AddController) class in association with end combo box items.
     * */
    public static LocalTime endTime;

    /** dtf
     * public variable dtf of type DateTimeFormatter.
     * used throughout this (AddController) class to format datetimes and datetime strings.
     * */
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** overlap
     * Boolean value associated with validateOverlap()
     * */
    public static Boolean overlap;

    /** invalidBizHrs
     * Boolean value associated with validateBizHours()
     * */
    public static Boolean invalidBizHrs;


    ObservableList<String> allContacts = DBContact.getContactNames();
    ObservableList<String> allCustomerNames = DBCustomer.getCustomerNames();
    ObservableList<String> allUserNames = DBUser.getUserNames();
    ObservableList<String> choiceList = FXCollections.observableArrayList("Appointment", "Customer");
    ObservableList<String> allCountries = FXCollections.observableArrayList("U.S.", "UK", "Canada");
    ObservableList<String> allDivisionsUS = DBFirstLevelDivision.getDivisionNamesUS();
    ObservableList<String> allDivisionsUK = DBFirstLevelDivision.getDivisionNamesUK();
    ObservableList<String> allDivisionsCanada = DBFirstLevelDivision.getDivisionNamesCanada();
    ObservableList<Appointment> mostRecentAppt = FXCollections.observableArrayList();
    ObservableList<Customer> mostRecentCustomer = FXCollections.observableArrayList();


    /** initialize
     Initializes the stage/scene
     *
     * Lambda expression sets start hours in addStartComboBox
     * Lambda expression sets end hours in addEndComboBox
     * Lambda expression listener to match regular expression for digits only (addPhoneTxtField)
     * Lambda expression listener to match regular expression for digits only (addPostalTxtField)
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addChooseComboBox.setItems(choiceList);
        addSelectCustomerComboBox.setItems(allCustomerNames);
        addUserComboBox.setItems(allUserNames);
        addCountryComboBox.setItems(allCountries);
        addContactComboBox.setItems(allContacts);


        addStartComboBox.setCellFactory(cb -> new StartHoursCell());//lambda 1
        addEndComboBox.setCellFactory(cb -> new EndHoursCell());//lambda 2
        for (int i = 8; i < 22; i++) {
           addStartComboBox.getItems().add(i);
        }
        for (int i = 9; i < 23; i++) {
            addEndComboBox.getItems().add(i);
        }

        addIDLabel.setVisible(false);
        addTimesLabel.setVisible(false);
        addIDTxtField.setEditable(false);
        addIDTxtField.setVisible(false);
        addSelectCustomerComboBox.setVisible(false);
        addApptDatePicker.setVisible(false);
        addApptDatePicker.setEditable(false);
        addStartComboBox.setVisible(false);
        addEndComboBox.setVisible(false);
        addDescriptTxtField.setVisible(false);
        addTypeTxtField.setVisible(false);
        addTitleTxtField.setVisible(false);
        addCountryComboBox.setVisible(false);
        addStateProvinceComboBox.setVisible(false);
        addContactComboBox.setVisible(false);
        addCustomerNameTxtField.setVisible(false);
        addAddressTxtField.setVisible(false);
        addPostalTxtField.setVisible(false);
        addLocationTxtField.setVisible(false);
        addPhoneTxtField.setVisible(false);
        addUserComboBox.setVisible(false);


        addPhoneTxtField.textProperty().addListener((observable, oldValue, newValue) -> { //lambda 3
            if (newValue.matches("\\d*")) {
                return;
            }
            addPhoneTxtField.setText(newValue.replaceAll("[^\\d]", ""));//listener that allows only numbers and hyphens
        });

        addPostalTxtField.textProperty().addListener((observable, oldValue, newValue) -> { //lambda 4
            if (newValue.matches("\\d*")) {
                return;
            }
            addPostalTxtField.setText(newValue.replaceAll("[^\\d]", ""));//listener that allows only numbers
        });

    }

    private class StartHoursCell extends ListCell<Integer> {

        //listener for start hours combo
        StartHoursCell() {
            addEndComboBox.valueProperty().addListener((obs, oldEndHours, newEndHours) -> updateDisableState());//lambda 5
        }

        @Override
        protected void updateItem(Integer hourValue, boolean empty) {//updates the hour items

            super.updateItem(hourValue, empty);
            if (empty) {
                setText("");
            } else {
                setText(TimeControls.convertComboTimeValue(hourValue));
                updateDisableState();
            }
        }

        private void updateDisableState() {//updates disabling of hours before or at the same time as selected start time
            boolean disable = getItem() != null && addEndComboBox.getValue() != null
                    && getItem() >= Integer.parseInt(addEndComboBox.getValue().toString());
            setDisable(disable);
            setOpacity(disable ? 0.5 : 1);
        }

    }

    private class EndHoursCell extends ListCell<Integer> {

        //listener for end hours combo
        EndHoursCell() {
            addStartComboBox.valueProperty().addListener((obs, oldEndHours, newEndHours) -> updateDisableState());//lambda 6
        }

        @Override
        protected void updateItem(Integer hourValue, boolean empty) {//updates the hour items

            super.updateItem(hourValue, empty);
            if (empty) {
                setText("");
            } else {
                setText(TimeControls.convertComboTimeValue(hourValue));
                updateDisableState();
            }
        }

        private void updateDisableState() {//updates disabling of hours (if end is chosen first)
            boolean disable = getItem() != null && addStartComboBox.getValue() != null
                    && getItem() <= Integer.parseInt(addStartComboBox.getValue().toString());
            setDisable(disable);
            setOpacity(disable ? 0.5 : 1);

        }
    }

    @FXML
    private void onActionAddChooseComboBox(ActionEvent event) throws IOException {
        if (addChooseComboBox.getSelectionModel().getSelectedItem() == "Customer") {

            addSelectCustomerComboBox.setVisible(false);
            addIDLabel.setVisible(true);
            addIDLabel.setText("Customer ID");
            addTimesLabel.setVisible(false);
            addIDTxtField.setVisible(true);
            addApptDatePicker.setVisible(false);
            addStartComboBox.setVisible(false);
            addEndComboBox.setVisible(false);
            addDescriptTxtField.setVisible(false);
            addTitleTxtField.setVisible(false);
            addTypeTxtField.setVisible(false);
            addCountryComboBox.setVisible(true);
            addStateProvinceComboBox.setVisible(true);
            addPhoneTxtField.setVisible(true);
            addAddressTxtField.setVisible(true);
            addPostalTxtField.setVisible(true);
            addPostalTxtField.setPromptText("Postal Code");
            addPostalTxtField.setEditable(true);
            addLocationTxtField.setVisible(false);
            addContactComboBox.setVisible(false);
            addCustomerNameTxtField.setVisible(true);
            addCustomerNameTxtField.setEditable(true);
            addCustomerNameTxtField.clear();
            addUserComboBox.setVisible(false);


        } else if (addChooseComboBox.getSelectionModel().getSelectedItem() == "Appointment") {

            addSelectCustomerComboBox.setVisible(true);
            addIDLabel.setVisible(true);
            addIDLabel.setText("Appointment ID");
            addTimesLabel.setVisible(true);
            addIDTxtField.setVisible(true);
            addApptDatePicker.setVisible(true);
            addStartComboBox.setVisible(true);
            addEndComboBox.setVisible(true);
            addDescriptTxtField.setVisible(true);
            addTitleTxtField.setVisible(true);
            addTypeTxtField.setVisible(true);
            addCountryComboBox.setVisible(false);
            addStateProvinceComboBox.setVisible(false);
            addPhoneTxtField.setVisible(false);
            addLocationTxtField.setVisible(true);
            addPostalTxtField.setVisible(false);
            addContactComboBox.setVisible(true);
            addAddressTxtField.setVisible(false);
            addCustomerNameTxtField.setVisible(true);
            addCustomerNameTxtField.setEditable(false);
            addUserComboBox.setVisible(true);

        }


    }

    @FXML
    private void onActionAddIDTxtField(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddCustomerNameTxtField(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddAddressTxtField(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddTitleTxtField(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddTypeTxtField(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddStateProvinceComboBox(ActionEvent event) throws IOException {

        if(addCountryComboBox.getSelectionModel().getSelectedItem() != "UK" && addStateProvinceComboBox.getValue() != null) {
            addPostalTxtField.setEditable(true);
            addPostalTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("^|\\d{0,5}") ? c : null));//lambda 7
        }
        else if (addCountryComboBox.getSelectionModel().getSelectedItem() == "UK" && addStateProvinceComboBox.getValue() != null) {
            addPostalTxtField.setEditable(true);
            addPostalTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("^|[a-zA-Z0-9]{0,5}$") ? c : null));//lambda 8
        }
    }

    @FXML
    private void onActionAddStartComboBox(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddEndComboBox(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddPostalTxtField(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddPhoneTxtField(ActionEvent event) throws IOException {

    }

    @FXML
    private void onActionAddContactComboBox(ActionEvent event) throws IOException {

        System.out.println(addContactComboBox.getValue());
    }

    @FXML
    private void onActionAddCountryComboBox(ActionEvent event) throws IOException {
        if(addCountryComboBox.getSelectionModel().getSelectedItem() == "U.S."){
            addStateProvinceComboBox.setItems(allDivisionsUS);
            addPhoneTxtField.clear();
            addPhoneTxtField.setEditable(true);
            addPhoneTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches( //lambda 9
                    "^|[0-9]{0,10}") ? c : null));
        }else if(addCountryComboBox.getSelectionModel().getSelectedItem() == "UK"){
            addStateProvinceComboBox.setItems(allDivisionsUK);
            addPhoneTxtField.setEditable(true);
            addPhoneTxtField.clear();
            addPhoneTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches( //lambda 10
                    "^|[0-9]{0,12}") ? c : null));
        }else if(addCountryComboBox.getSelectionModel().getSelectedItem() == "Canada"){
            addStateProvinceComboBox.setItems(allDivisionsCanada);
            addPhoneTxtField.clear();
            addPhoneTxtField.setEditable(true);
            addPhoneTxtField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches( //lambda 11
                    "^|[0-9]{0,10}") ? c : null));
        }
    }

    @FXML
    private void onActionAddUserComboBox(ActionEvent event) {

        System.out.println(addUserComboBox.getValue().toString());
    }

    @FXML
    private void onActionApplyAddBtn(ActionEvent event) throws IOException, SQLException, ParseException {

            if (addChooseComboBox.getSelectionModel().getSelectedItem() == "Customer") {
                saveCustomerToDB();
            } else if (addChooseComboBox.getSelectionModel().getSelectedItem() == "Appointment") {
                saveApptToDB();
            }

    }

    @FXML
    private void onActionApplyHomeBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();


    }

    @FXML
    private void onActionAddClearBtn(ActionEvent event) throws IOException {

        addIDTxtField.clear();
        addSelectCustomerComboBox.setVisible(false);
        addApptDatePicker.setValue(null);
        addStartComboBox.getSelectionModel().clearSelection();
        addEndComboBox.getSelectionModel().clearSelection();
        addDescriptTxtField.clear();
        addTypeTxtField.clear();
        addTitleTxtField.clear();
        addCountryComboBox.getSelectionModel().clearSelection();
        addStateProvinceComboBox.getSelectionModel().clearSelection();
        addContactComboBox.getSelectionModel().clearSelection();
        addCustomerNameTxtField.clear();
        addCustomerNameTxtField.setPromptText("Customer Name");
        addAddressTxtField.clear();
        addPostalTxtField.clear();
        addPhoneTxtField.clear();
        addLocationTxtField.clear();


    }

    @FXML
    private void onActionAddSelectCustomerComboBox(ActionEvent event) throws NullPointerException {

        String cust = String.valueOf(addSelectCustomerComboBox.getValue());
        try {
            addCustomerNameTxtField.setText(cust);
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }

    @FXML
    private void onActionAddLocationTxtField(ActionEvent event){

    }

    @FXML
    private void onActionAddDescriptTxtField(ActionEvent event) {

    }

    @FXML
    private void onActionAddApptDatePicker(ActionEvent event) throws IOException {

        if (addApptDatePicker.getValue() != null) {
            chosenDate = addApptDatePicker.getValue();
            System.out.println("Chosen date: " + chosenDate);
            LocalDate today = LocalDate.now();
            System.out.println("Today's date: " + today);

            if (chosenDate.isBefore(today) || today.isEqual(chosenDate)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Date");
                alert.setHeaderText("Date cannot be today's date or before.\n" +
                        "Our consultants require at least one day's notice\n" +
                        "before an appointment is scheduled\n" +
                        "to allow for personal time. Please select a date after today's date.");
                alert.setContentText("Click OK to exit.");

                addApptDatePicker.setValue(null);

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

    /** saveApptToDB
     *This method saves a new appointment to the database.
     * If all conditions are met (no empty fields, valid business hours and no overlapping appointments for selected customer),
     * then the appointment is saved to the database. Alert messages are displayed if a condition fails.
     *
     * @throws NullPointerException a NullPointerException is thrown
     *
     */
    public void saveApptToDB() throws NullPointerException {

        if (applyAddBtn.isArmed()) {
            if(addApptDatePicker.getValue() == null | addStartComboBox.getSelectionModel().isEmpty() | addStartComboBox.getValue() == null
                    | addEndComboBox.getSelectionModel().isEmpty() | addEndComboBox.getValue() == null
                    | addCustomerNameTxtField.getText().isEmpty() | addTitleTxtField.getText().isEmpty() | addTypeTxtField.getText().isEmpty()
                    | addLocationTxtField.getText().isEmpty()  | addDescriptTxtField.getText().isEmpty() | addContactComboBox.getSelectionModel().isEmpty()
                    | addUserComboBox.getSelectionModel().isEmpty()){
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
            } else{}

            //LocalTime versions of start/end combo box values
            int startInt = Integer.parseInt(addStartComboBox.getSelectionModel().getSelectedItem().toString());
            String startS = TimeControls.convertComboTimeValue(startInt);
            startTime = LocalTime.parse(startS);
            int endInt = Integer.parseInt(addEndComboBox.getSelectionModel().getSelectedItem().toString());
            String endS = TimeControls.convertComboTimeValue(endInt);
            endTime = LocalTime.parse(endS);

            //create LocalDateTime from chosenDate and start/end times
            LocalDateTime startDateTime = LocalDateTime.of(chosenDate, startTime);
            System.out.println("LDT startDateTime (used to compare against dbLDT in validateOverlap): " + startDateTime);
            LocalDateTime endDateTime = LocalDateTime.of(chosenDate, endTime);
            System.out.println("LDT endDateTime (used to compare against dbLDT in validateOverlap): " + endDateTime);


            //user's time input as zoned date time
            ZonedDateTime startZDT = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
            System.out.println("User's start ZDT: " + startZDT);
            ZonedDateTime endZDT = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());
            System.out.println("User's end ZDT: " + endZDT);

            //user's time input to EST
            ZonedDateTime startInput = startZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            System.out.println("Same start instant EST: " + startInput);
            ZonedDateTime endInput = endZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            System.out.println("Same end instant EST: " + endInput);

            //Hour values from EST version of user's input times (used to check against business hours)
            LocalTime startLT = startInput.toLocalTime();
            LocalTime endLT = endInput.toLocalTime();
            System.out.println("LocalTime of EST start ZDT: " + startLT);
            System.out.println("LocalTime of EST end ZDT: " + endLT);

            //Business hours
            ZonedDateTime bizStart = ZonedDateTime.of(chosenDate, LocalTime.of(8, 0), ZoneId.of("America/New_York"));
            ZonedDateTime bizEnd = ZonedDateTime.of(chosenDate, LocalTime.of(22, 0), ZoneId.of("America/New_York"));
            LocalTime bizStartLT = bizStart.toLocalTime();
            LocalTime bizEndLT = bizEnd.toLocalTime();

            //to UTC
            ZonedDateTime startUTC = startZDT.withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = endZDT.withZoneSameInstant(ZoneId.of("UTC"));

            //get field & combo data
            String customerName = addCustomerNameTxtField.getText();
            String userName = addUserComboBox.getSelectionModel().getSelectedItem();
            String contactName = addContactComboBox.getSelectionModel().getSelectedItem();
            String title = addTitleTxtField.getText();
            String description = addDescriptTxtField.getText();
            String location = addLocationTxtField.getText();
            String type = addTypeTxtField.getText();
            LocalDateTime start = startZDT.toLocalDateTime();
            LocalDateTime end = endZDT.toLocalDateTime();

            int customerId = DBCustomer.getCustomerId(customerName);
            int userId = DBUser.getUserId(userName);
            int contactId = DBContact.getContactId(contactName);

            invalidBizHrs = validateBizHours(startLT, endLT, bizStartLT, bizEndLT);
            if (invalidBizHrs) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Business Hours Error");
                alert.setHeaderText("Your selected hours fall outside of our business hours, which are in EST.\n" +
                        "Please choose a start time between 08:00 and 21:00 / end time between 09:00 and 22:00 EST.");
                alert.setContentText("Click OK to exit.");
                addStartComboBox.getSelectionModel().clearSelection();
                addEndComboBox.getSelectionModel().clearSelection();

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
                addStartComboBox.getSelectionModel().clearSelection();
                addEndComboBox.getSelectionModel().clearSelection();

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                } else {
                    //... user chose CANCEL or closed the dialog
                }
            }

            else if (!invalidBizHrs & (addApptDatePicker.getValue() != null & !addStartComboBox.getSelectionModel().isEmpty() & addStartComboBox.getValue() != null
                    & !addEndComboBox.getSelectionModel().isEmpty() & addEndComboBox.getValue() != null
                    & !addCustomerNameTxtField.getText().isEmpty() & !addTitleTxtField.getText().isEmpty() & !addTypeTxtField.getText().isEmpty()
                    & !addLocationTxtField.getText().isEmpty()  & !addDescriptTxtField.getText().isEmpty() & !addContactComboBox.getSelectionModel().isEmpty()
                    & !addUserComboBox.getSelectionModel().isEmpty())) {
                //add new appt to db
                DBAppointment.apptAddNew(title, description, location, type, start, end, customerId, userId, contactId);
                //get added appointment and display confirmation
                mostRecentAppt = DBAppointment.lastUpdatedAppt();
                for (Appointment appt : mostRecentAppt) {
                    String apptId = String.valueOf(appt.getAppointmentID());
                    int custId = appt.getCustomerID();
                    String custName = DBCustomer.getSingleCustomerName(custId);
                    String apptType = appt.getType();
                    LocalDateTime apptStart = appt.getStart();
                    LocalDateTime apptEnd = appt.getEnd();
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Confirmation");
                    confirmation.setHeaderText("A new Appointment has been successfully added to the database.\n" +
                            "Appointment ID: " + apptId + "\n" +
                            "Customer Name: " + custName + "\n" +
                            "Type: " + apptType + "\n" +
                            "Start: " + apptStart + "\n" +
                            "End: " + apptEnd + "\n");
                    confirmation.setContentText("Click OK to exit.");

                    Optional<ButtonType> result3 = confirmation.showAndWait();
                    if (result3.get() == ButtonType.OK) {
                        confirmation.close();
                        // ... user chose OK
                        System.out.println("Appointment added to database.");
                    } else {
                        // ... user chose CANCEL or closed the dialog
                        System.out.println("Operation cancelled or closed.");
                    }
                }
            }


        }
    }

    /** saveCustomerToDB
     * Method called when ADD button is clicked and all conditions for saving have been met.
     * If all conditions are met (no empty fields, phone number and postal code are correct length),
     * then customer is saved to the database. Alert message is displayed if a condition fails.
     *
     * */
    public void saveCustomerToDB() {

        if (!addAddressTxtField.getText().isEmpty() && !addCustomerNameTxtField.getText().isEmpty() && addCountryComboBox.getValue() != null
                && addStateProvinceComboBox.getValue() != null && !addPostalTxtField.getText().isEmpty() && !addPhoneTxtField.getText().isEmpty()) {

            String address = addAddressTxtField.getText();
            String createdBy = User.getCurrentUser();
            String customerName = addCustomerNameTxtField.getText();
            String division = addStateProvinceComboBox.getValue().toString();
            String lastUpdatedBy = User.getCurrentUser();
            String phoneNum = addPhoneTxtField.getText();
            if (phoneNum.length() < 10 || phoneNum.length() == 11 || phoneNum.length() > 12) {
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
            if (addPhoneTxtField.getText().length() == 12)
                phone = phoneNum.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3-$4");//format phone number for International (12-345-678-9000)

            String postal = addPostalTxtField.getText();
            if (postal.length() < 5) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Postal Code Error");
                alert.setHeaderText("All U.S. postal codes must be 5 characters long.");
                alert.showAndWait();
            }

            if (!(postal.length() < 5) && (!phoneNum.isEmpty()) && (phoneNum.length() != 11) && !(phoneNum.length() < 10)) {
                DBCustomer.addCustomer(address, createdBy, customerName, DBCustomer.getDivisionID(division), lastUpdatedBy, phone, postal);
                mostRecentCustomer = DBCustomer.lastUpdatedCustomer();
                for (Customer customer : mostRecentCustomer) {
                    String custId = String.valueOf(customer.getCustomerID());
                    String custName = customer.getCustomerName();
                    String custAddress = customer.getCustomerAddress();
                    int divId = customer.getDivisionID();
                    String postalCode = customer.getPostal();
                    String phoneNumber = customer.getPhone();

                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Confirmation");
                    confirmation.setHeaderText("A new Customer has been successfully added to the database.\n" +
                            "Customer ID: " + custId + "\n" +
                            "Customer Name: " + custName + "\n" +
                            "Address: " + custAddress + "\n" +
                            "Division: " + divId + "\n" +
                            "Postal: " + postalCode + "\n" +
                            "Phone: " + phoneNumber + "\n");
                    confirmation.setContentText("Click OK to exit.");

                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        confirmation.close();
                        // ... user chose OK
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field or Missing Selection");
            alert.setHeaderText("There is at least one empty field or missing selection.\n " +
                    "All fields and selections are required to add a new Customer.\n");
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

    /** validateBizHours
     * Boolean method to check selected start and end times against company's business hours (EST).
     * The start and end times selected from the comboboxes are changed from the user's system default time to EST for comparison.
     *
     * @param startLT the selected start LocalTime converted to EST
     * @param endLT the selected end LocalTime converted to EST
     * @param bizStartLT company's EST business hours from 8 am to 9 pm EST
     * @param bizEndLT company's EST business hours from 9am to 10 pm EST
     *
     * @return returns true if INVALID business hours are selected, else false
     *
     * */
    public static Boolean validateBizHours(LocalTime startLT, LocalTime endLT, LocalTime bizStartLT, LocalTime bizEndLT) {

        if (startLT.isBefore(bizStartLT)) return true;
        if (endLT.isBefore(bizStartLT)) return true;
        if (startLT.isAfter(bizEndLT)) return true;
        if (endLT.isAfter(bizEndLT)) return true;

        else return false;
    }

    /** validateOverlap
     * Boolean method to check selected appointment date and start/end times against existing appointments
     * for the selected customer during the same time period on the same date.
     *
     * @param customerName name of customer associated with the appointment
     * @param start the selected date and start time
     * @param end the selected date and end time
     *
     * @return returns true if there IS an overlap with an existing appointment for this customer, else false
     *
     * */
    public static Boolean validateOverlap(String customerName, LocalDateTime start, LocalDateTime end) {

        ObservableList<Appointment> allAppts = DBAppointment.getAllAppointmentsByCustomer(customerName);

        if (!allAppts.isEmpty()) {
            for (Appointment apptmnt : allAppts) {
                LocalDateTime dbStartLDT = apptmnt.getStart();//start localdatetime from db
                LocalDateTime dbEndLDT = apptmnt.getEnd();//end localdatetime from db


                if (start.isBefore(dbStartLDT) && end.isAfter(dbStartLDT)) return true;
                if (start.isEqual(dbStartLDT) && end.isEqual(dbEndLDT)) return true;
                if (start.isBefore(dbStartLDT) && end.isAfter(dbEndLDT)) return true;
                if (start.isAfter(dbStartLDT) && start.isBefore(dbEndLDT)) return true;

            }

        } else return false;


        return false;
    }
}
