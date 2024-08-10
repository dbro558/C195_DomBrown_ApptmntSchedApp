package controller;

import DatabaseAccess.DBAppointment;
import DatabaseAccess.DBCustomer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;


/** WeeklyApptsController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class WeeklyApptsController implements Initializable {

    @FXML
    TableView<Appointment> weeklyScreenTableView;
    @FXML
    TableColumn<Appointment, Integer> wklyTVApptIDColumn, wklyTVCustIDColumn, wklyTVUserIDColumn;
    @FXML TableColumn<Appointment, String> wklyTVTitleColumn, wklyTVDescriptColumn, wklyTVLocColumn,
            wklyTVContactColumn, wklyTVTypeColumn;
    @FXML TableColumn<Appointment, LocalDateTime> wklyTVStartColumn, wklyTVEndColumn;
    @FXML
    Button wklyGetApptsBtn, wklyHomeBtn;
    @FXML
    DatePicker wklyDatePicker;

    Stage stage;
    Parent scene;
    String date;

    ObservableList<Appointment> wklyAppts;

    //User's local time zone
    ZoneId userZoneId = ZoneId.systemDefault();


    /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Bind the columns to stretch with the TableView (Weekly Appointments)
        wklyTVTitleColumn.prefWidthProperty().bind(weeklyScreenTableView.widthProperty().multiply(0.10));
        wklyTVDescriptColumn.prefWidthProperty().bind(weeklyScreenTableView.widthProperty().multiply(0.25));
        wklyTVLocColumn.prefWidthProperty().bind(weeklyScreenTableView.widthProperty().multiply(0.15));
        wklyTVTypeColumn.prefWidthProperty().bind(weeklyScreenTableView.widthProperty().multiply(0.15));
        wklyTVContactColumn.prefWidthProperty().bind(weeklyScreenTableView.widthProperty().multiply(0.10));


        weeklyScreenTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showAppointmentDetails(newSelection);
            }
        });
    }

    /** onActionHomeBtn
     * ActionEvent performed when HOME is clicked.
     * Switches scenes to main screen of application.
     *
     * @param event action event for wklyHomeBtn
     *
     * @throws IOException an I/O exception has occurred
     *
     * */
    @FXML
    public void onActionWklyHomeBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    /** onActionMWklyGetApptsBtn
     * ActionEvent performed when GET APPOINTMENTS button is clicked.
     * Calls method populateTable() if date picker's value is not null.
     *
     * @param event action event for wklyApptsBtn
     *
     * */
    @FXML
    public void onActionWklyGetApptsBtn(ActionEvent event){

        if(wklyDatePicker.getValue() != null){
            populateTable();
        }
    }

    /** populateTable
     * Method that populates weeklyScreenTableView.
     * Sets tableview columns with appointment data for all appointments from selected date until one week from selected date.
     *
     * */
    public void populateTable(){

        date = wklyDatePicker.getValue().toString();
        System.out.println(date);

        wklyAppts = DBAppointment.getAllAppointmentsWkly(date);
        //set items in table view with wkly appointment data
        wklyTVApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        wklyTVTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        wklyTVDescriptColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        wklyTVLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        wklyTVContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        wklyTVTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        wklyTVCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        wklyTVUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        // Custom cell factories for date time columns
        wklyTVStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        wklyTVStartColumn.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>(){
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
        wklyTVEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        wklyTVEndColumn.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>(){
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

        weeklyScreenTableView.setItems(wklyAppts);
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
        Label locationLabel = new Label("Location: " + appointment.getLocation());
        Label contactLabel = new Label("Contact: " + appointment.getContactName());

        TextArea descriptionArea = new TextArea(appointment.getDescription());
        descriptionArea.setWrapText(true);
        descriptionArea.setEditable(false);
        descriptionArea.setPrefHeight(100);

        dialogVbox.getChildren().addAll(idLabel, customerLabel, titleLabel, typeLabel, new Label("Description:"), descriptionArea,
                startLabel, endLabel, locationLabel, contactLabel);

        Scene dialogScene = new Scene(dialogVbox, 400, 300);
        dialog.setScene(dialogScene);
        dialog.setResizable(true);
        dialog.show();
    }

    @FXML
    private void onActionWklyDatePicker(ActionEvent event) {
    }

}
