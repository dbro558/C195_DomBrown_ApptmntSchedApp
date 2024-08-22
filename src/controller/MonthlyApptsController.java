package controller;

import DatabaseAccess.DBAppointment;
import DatabaseAccess.DBCustomer;
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
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;


 /** MonthlyApptsController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class MonthlyApptsController implements Initializable {

    @FXML
    TableView<Appointment> moScreenTableView;
    @FXML
    TableColumn<Appointment, Integer> moTVApptIDColumn, moTVCustIDColumn, moTVUserIDColumn;
    @FXML TableColumn<Appointment, String> moTVTitleColumn, moTVDescriptColumn, moTVLocColumn,
            moTVContactColumn, moTVTypeColumn;
    @FXML TableColumn<Appointment, LocalDateTime> moTVStartColumn, moTVEndColumn;
    @FXML
    Button moGetApptsBtn, moHomeBtn;
    @FXML
    ComboBox MoMonthCombo, moYearCombo;

    Stage stage;
    Parent scene;

    ObservableList<Appointment> monthlyAppts;
    ObservableList<String> yearList = FXCollections.observableArrayList("2011", "2012", "2013", "2014", "2015", "2016",
            "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026");
    ObservableList<String> monthList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07",
                    "08", "09", "10", "11", "12");

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

        //Bind the columns to stretch with the TableView (Monthly Appointments)
        moTVTitleColumn.prefWidthProperty().bind(moScreenTableView.widthProperty().multiply(0.10));
        moTVDescriptColumn.prefWidthProperty().bind(moScreenTableView.widthProperty().multiply(0.25));
        moTVLocColumn.prefWidthProperty().bind(moScreenTableView.widthProperty().multiply(0.15));
        moTVTypeColumn.prefWidthProperty().bind(moScreenTableView.widthProperty().multiply(0.15));
        moTVContactColumn.prefWidthProperty().bind(moScreenTableView.widthProperty().multiply(0.10));

        MoMonthCombo.setItems(monthList);
        moYearCombo.setItems(yearList);

        moScreenTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showAppointmentDetails(newSelection);
            }
        });

    }

    /** onActionMoHomeBtn
     * ActionEvent performed when HOME button is clicked.
     * Switches scene to main screen of application when HOME button is clicked.
     *
     * @param event action event for moHomeBtn
     *
     * @throws IOException an I/O exception occurred
     *
     * */
    @FXML
    public void onActionMoHomeBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    private void onActionMoMonthCombo(ActionEvent event){

    }

    @FXML
    private void onActionMoYearCombo(ActionEvent event){

    }

    /** onActionMoGetApptsBtn
     * ActionEvent performed when GET APPOINTMENTS button is clicked.
     * Calls method to set tableview columns with appointment data for all appointments for selected year and month when moGetApptsBtn is clicked.
     *
     * @param event action event for moGetApptsBtn
     *
     * */
    @FXML
    public void onActionMoGetApptsBtn(ActionEvent event){

        if (moYearCombo.getSelectionModel().getSelectedItem() != null &&
        MoMonthCombo.getSelectionModel().getSelectedItem() != null){
            populateTable();
        }
    }

    /** populateTable
     * Method that populates moScreenTableView.
     * Sets tableview columns with appointment data for all appointments for selected year and month.
     *
     * */
    public void populateTable(){

        String year = moYearCombo.getSelectionModel().getSelectedItem().toString();
        String month = MoMonthCombo.getSelectionModel().getSelectedItem().toString();
        monthlyAppts = DBAppointment.getAllAppointmentsMonthly(year, month);
        //set items in table view with wkly appointment data
        moTVApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        moTVTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        moTVDescriptColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        moTVLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        moTVContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        moTVTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        moTVCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        moTVUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        // Custom cell factories for date time columns
        moTVStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        moTVStartColumn.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>(){
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
        moTVEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        moTVEndColumn.setCellFactory(column -> new TableCell<Appointment, LocalDateTime>(){
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

        moScreenTableView.setItems(monthlyAppts);
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
}
