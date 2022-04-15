package controller;


import Database.DatabaseConnection;
import DatabaseAccess.DBAppointment;
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
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.util.*;


/** MainscreenController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class MainscreenController implements Initializable {

    @FXML TableView <Appointment> mainScreenTableView;
    @FXML TableColumn <Appointment, Integer> mainscreenTVApptIDColumn, mainscreenTVCustIDColumn, mainscreenTVUserIDColumn;
    @FXML TableColumn<Appointment, String> mainscreenTVTitleColumn, mainscreenTVDescriptColumn, mainscreenTVLocColumn,
            mainscreenTVContactColumn, mainscreenTVTypeColumn, mainscreenTVStartColumn, mainscreenTVEndColumn;
    @FXML RadioButton apptsAllRadBtn, apptsByWeekRadBtn, apptsByMonthRadBtn;
    @FXML Button mainscreenAddBtn, mainscreenUpdateBtn, mainscreenDeleteBtn, mainscreenReportsBtn, mainscreenCustomersBtn, mainscreenLogoutBtn;
    @FXML ToggleGroup toggleGroup1;

    Stage stage;
    Parent scene;

    ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    ObservableList<Appointment> upcomingAppts = DBAppointment.getAllAppointmentsRefresh();


     /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainScreenTableView.getItems().clear();
        allAppts = DBAppointment.getAllAppointments();


        //put default tableview view in here (displays all appointments from today's date)

            mainscreenTVApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            mainscreenTVTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            mainscreenTVDescriptColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            mainscreenTVLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            mainscreenTVContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            mainscreenTVTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            mainscreenTVStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            mainscreenTVEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            mainscreenTVCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            mainscreenTVUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            mainScreenTableView.setItems(allAppts);


    }

     /** onActionApptsAllRadBtn
     * ActionEvent performed when radio button is clicked.
     * Sets tableview columns with appointment data for all appointments in the database when apptsAllRadBtn is clicked.
     *
     * @param event action event for apptsAllRadBtn
     *
     * */
    @FXML
    public void onActionApptsAllRadBtn (ActionEvent event){

        if (apptsAllRadBtn.isArmed()){
            mainscreenTVApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            mainscreenTVTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            mainscreenTVDescriptColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            mainscreenTVLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            mainscreenTVContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            mainscreenTVTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            mainscreenTVStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            mainscreenTVEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
            mainscreenTVCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            mainscreenTVUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            mainScreenTableView.setItems(upcomingAppts);


        }

    }

    /** onActionApptsByWeekRadBtn
     * ActionEvent performed when radio button is clicked.
     * Sets tableview columns with appointment data for all appointments in the database when apptsByWeekRadBtn is clicked.
     *
     * @param event action event for apptsByWeekRadBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionApptsByWeekRadBtn (ActionEvent event) throws IOException {

        if(apptsByWeekRadBtn.isSelected()) {
            stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/weeklyAppts.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
            allAppts.clear();
        }

    }

    /** onActionApptsByMonthRadBtn
     * ActionEvent performed when radio button is clicked.
     * Sets tableview columns with appointment data for all appointments in the database when apptsByMonthRadBtn is clicked.
     *
     * @param event action event for apptsByMonthRadBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionApptsByMonthRadBtn (ActionEvent event) throws IOException {

        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/monthlyAppts.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
        allAppts.clear();
    }

     /** onActionMainscreenAddBtn
     * ActionEvent performed when ADD button is clicked.
     * Switches scene to the ADD screen when ADD button is clicked.
     *
     * @param event action event for mainscreenAddBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionMainscreenAddBtn (ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/add.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
        allAppts.clear();

    }

    /** onActionMainscreenUpdateBtn
     * ActionEvent performed when UPDATE button is clicked.
     * Switches scene to the UPDATE screen when UPDATE button is clicked.
     *
     * @param event action event for mainscreenUpdateBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionMainscreenUpdateBtn (ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/update.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
        allAppts.clear();

    }

    /** onActionMainscreenDeleteBtn
     * ActionEvent performed when DELETE button is clicked.
     * Switches scene to the DELETE screen when DELETE button is clicked.
     *
     * @param event action event for mainscreenDeleteBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionMainscreenDeleteBtn (ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/delete.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
        allAppts.clear();

    }

    /** onActionMainscreenReportsBtn
     * ActionEvent performed when REPORTS button is clicked.
     * Switches scene to the REPORTS screen when REPORTS button is clicked.
     *
     * @param event action event for mainscreenReportsBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionMainscreenReportsBtn (ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
        allAppts.clear();

    }

    /** onActionMainscreenCustomersBtn
     * ActionEvent performed when CUSTOMERS button is clicked.
     * Switches scene to the CUSTOMERS screen when CUSTOMERS button is clicked.
     *
     * @param event action event for mainscreenCustomersBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionMainscreenCustomersBtn (ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
        allAppts.clear();

    }

    /** onActionMainscreenLogoutBtn
     * ActionEvent performed when LOGOUT button is clicked.
     * Closes database connection and switches scene to the LOGIN screen when LOGOUT button is clicked.
     *
     * @param event action event for mainscreenLogoutBtn
     *
     * @throws IOException an I/O Exception occurred
     *
     * */
    @FXML
    public void onActionMainscreenLogoutBtn (ActionEvent event) throws IOException {

        DatabaseConnection.closeConnection();
        DatabaseConnection.startConnection();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    private void  initToggleGroup1() {
        toggleGroup1 = new ToggleGroup();

        apptsAllRadBtn.setToggleGroup(toggleGroup1);
        apptsByWeekRadBtn.setToggleGroup(toggleGroup1);
        apptsByMonthRadBtn.setToggleGroup(toggleGroup1);

    }


}
