package controller;

import DatabaseAccess.DBAppointment;
import DatabaseAccess.DBContact;
import DatabaseAccess.DBCustomer;
import DatabaseAccess.DBReports;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Appointment;
import model.MonthlyTypeCountReport;
import model.VIPReport;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/** ReportsController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class ReportsController implements Initializable {

    @FXML
    ComboBox reportsContactSchedComboBox;
    @FXML
    Button reportsTypeCountBtn, reportsVIPBtn, reportsHomeBtn;
    @FXML
    TableView<Appointment> reportsApptsByContactTableView;
    @FXML TableView<MonthlyTypeCountReport> reportsTCTableView;
    @FXML TableView<VIPReport> reportsVIPTableView;
    @FXML
    TableColumn<Appointment, String> reportsTCYearTableColumn, reportsTCMonthTableColumn, reportsTCTypeTableColumn, reportsTitleColumn,
            reportsDescriptionColumn, reportsTypeColumn, reportsVIPCustNameColumn;
    @FXML TableColumn<Appointment, LocalDateTime> reportsStartColumn, reportsEndColumn;
    @FXML TableColumn<Appointment, Integer> reportsTCCountTableColumn, reportsApptIDColumn, reportsVIPCustIDColumn,
            reportsVIPCountTableColumn, reportsCustIDColumn;
    @FXML
    Label reportsScheduleLabel;
    
    Stage stage;
    Parent scene;
    String contactName;
    ObservableList<MonthlyTypeCountReport> typeCountList = DBAppointment.getAppointmentsByTypeMonth();
    ObservableList<String> contactList = DBContact.getContactNames();
    ObservableList<Appointment> apptsByContactList = DBAppointment.getAllAppointmentsByContact(contactName);
    ObservableList<VIPReport> vipList = DBReports.getVIPReport();

    //User's local time zone
    ZoneId userZoneId = ZoneId.systemDefault();


    /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //listener to adjust column widths when the TableView's width changes
        reportsTCTableView.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                adjustColumnWidths();
            }
        });

        //Bind the columns to stretch with the TableView (Appointments by Contact)
        reportsApptIDColumn.prefWidthProperty().bind(reportsApptsByContactTableView.widthProperty().multiply(0.05));
        reportsTitleColumn.prefWidthProperty().bind(reportsApptsByContactTableView.widthProperty().multiply(0.15));
        reportsDescriptionColumn.prefWidthProperty().bind(reportsApptsByContactTableView.widthProperty().multiply(0.25));
        reportsTypeColumn.prefWidthProperty().bind(reportsApptsByContactTableView.widthProperty().multiply(0.15));
        reportsStartColumn.prefWidthProperty().bind(reportsApptsByContactTableView.widthProperty().multiply(0.15));
        reportsEndColumn.prefWidthProperty().bind(reportsApptsByContactTableView.widthProperty().multiply(0.15));
        reportsCustIDColumn.prefWidthProperty().bind(reportsApptsByContactTableView.widthProperty().multiply(0.05));

        // Bind the columns to stretch with the TableView (VIP)
        reportsVIPCustIDColumn.prefWidthProperty().bind(reportsVIPTableView.widthProperty().multiply(0.30));
        reportsVIPCustNameColumn.prefWidthProperty().bind(reportsVIPTableView.widthProperty().multiply(0.35));
        reportsVIPCountTableColumn.prefWidthProperty().bind(reportsVIPTableView.widthProperty().multiply(0.35));



        reportsContactSchedComboBox.setItems(contactList);
        reportsScheduleLabel.setVisible(false);

        reportsApptsByContactTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showAppointmentDetails(newSelection);
            }
        });

    }

    private void adjustColumnWidths() {
        double totalWidth = reportsTCTableView.getWidth();
        double columnWidth = totalWidth / 4;

        reportsTCYearTableColumn.setPrefWidth(columnWidth);
        reportsTCMonthTableColumn.setPrefWidth(columnWidth);
        reportsTCTypeTableColumn.setPrefWidth(columnWidth);
        reportsTCCountTableColumn.setPrefWidth(columnWidth);
    }


    /** onActionReportsTypeCountBtn
     * ActionEvent performed when reportsTypeCountBtn is clicked.
     * Sets reportsTCTableview columns with data from typeCountList when reportsTypeCountBtn is clicked.
     *
     * @param event action event for reportsTypeCountBtn
     *
     * */
    @FXML
    public void onActionReportsTypeCountBtn(ActionEvent event) {

        reportsTCYearTableColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        reportsTCMonthTableColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        reportsTCTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportsTCCountTableColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        reportsTCTableView.setItems(typeCountList);

    }

    /** onActionReportsHomeBtn
     * ActionEvent performed when HOME button is clicked.
     * Switches scene to main screen of application when HOME button is clicked.
     *
     * @param event action event for reportsHomeBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionReportsHomeBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** onActionReportsContactSchedComboBox
     * ActionEvent performed when item in reportsContactSchedComboBox is selected.
     * Populates reportsApptsByContactTableView with selected contact's appointments.
     *
     * Lambda expression listener to detect change in selected contact from combo box.
     *
     * @param event action event for reportsContactSchedComboBox
     *
     * */
    @FXML
    public void onActionReportsContactSchedComboBox(ActionEvent event) {

            String contactName = reportsContactSchedComboBox.getSelectionModel().getSelectedItem().toString();
            reportsContactSchedComboBox.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        apptsByContactList.clear();
                        apptsByContactList = DBAppointment.getAllAppointmentsByContact(String.valueOf(newValue));

                        reportsApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
                        reportsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
                        reportsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                        reportsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                        reportsCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

                        // Custom cell factories for date time columns
                        reportsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
                        reportsStartColumn.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>(){
                            @Override
                            protected void updateItem(LocalDateTime item, boolean empty){
                                super.updateItem(item, empty);
                                if (empty || item == null){
                                    setText(null);
                                } else {
                                    //Convert from UTC to user's LocalDateTime
                                    ZonedDateTime userStartZonedDateTime = item.atZone(ZoneId.of("UTC")).withZoneSameInstant(userZoneId);
                                    setText(userStartZonedDateTime.format(DBAppointment.formatter));
                                }
                            }
                        });
                        reportsEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
                        reportsEndColumn.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>(){
                            @Override
                            protected void updateItem(LocalDateTime item, boolean empty){
                                super.updateItem(item, empty);
                                if (empty || item == null){
                                    setText(null);
                                } else {
                                    //Convert from UTC to user's LocalDateTime
                                    ZonedDateTime userEndZonedDateTime = item.atZone(ZoneId.of("UTC")).withZoneSameInstant(userZoneId);
                                    setText(userEndZonedDateTime.format(DBAppointment.formatter));
                                }
                            }
                        });


                        reportsApptsByContactTableView.setItems(apptsByContactList);
                    });
            reportsScheduleLabel.setText("Now viewing " + contactName + "'s schedule");
            reportsScheduleLabel.setVisible(true);


    }

    /** onActionReportsVIPBtn
     * ActionEvent performed when reportsVIPBtn is clicked.
     * Populates reportsVIPTableView with data from vipList, a list of the 10 customers with the most appointments scheduled for the current month.
     *
     * @param event action event for reportsVIPBtn
     *
     * */
    @FXML
    public void onActionReportsVIPBtn(ActionEvent event) {

        reportsVIPCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        reportsVIPCustNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        reportsVIPCountTableColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        reportsVIPTableView.setItems(vipList);

    }

    private void showAppointmentDetails(Appointment appointment) {
        Stage dialog = new Stage();
        dialog.setTitle("Appointment Details");

        VBox dialogVbox = new VBox();
        dialogVbox.setSpacing(10);
        dialogVbox.setPadding(new Insets(10));

        Label idLabel = new Label("Appointment ID: " + appointment.getAppointmentID());
        Label customerLabel = new Label("Customer Name: " + DBCustomer.getSingleCustomerName(appointment.getCustomerID()));
        Label titleLabel = new Label("Title: " + appointment.getTitle());
        Label typeLabel = new Label("Type: " + appointment.getType());
        Label startLabel = new Label("Start: " + appointment.getFormattedStartString());
        Label endLabel = new Label("End: " + appointment.getFormattedEndString());
        //Label locationLabel = new Label("Location: " + appointment.getLocation());
        //Label contactLabel = new Label("Contact: " + appointment.getContactName());

        TextArea descriptionArea = new TextArea(appointment.getDescription());
        descriptionArea.setWrapText(true);
        descriptionArea.setEditable(false);
        descriptionArea.setPrefHeight(100);

        dialogVbox.getChildren().addAll(idLabel, customerLabel, titleLabel, typeLabel, new Label("Description:"), descriptionArea,
                startLabel, endLabel);

        Scene dialogScene = new Scene(dialogVbox, 400, 300);
        dialog.setScene(dialogScene);
        dialog.setResizable(true);
        dialog.show();
    }
}
