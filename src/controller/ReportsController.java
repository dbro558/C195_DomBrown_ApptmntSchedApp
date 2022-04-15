package controller;

import DatabaseAccess.DBAppointment;
import DatabaseAccess.DBContact;
import DatabaseAccess.DBReports;
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
import model.MonthlyTypeCountReport;
import model.VIPReport;
import java.io.IOException;
import java.net.URL;
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
            reportsDescriptionColumn, reportsTypeColumn, reportsStartColumn, reportsEndColumn,
            reportsVIPCustNameColumn;
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


    /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        reportsContactSchedComboBox.setItems(contactList);
        reportsScheduleLabel.setVisible(false);

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
                        reportsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
                        reportsEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
                        reportsCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
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
}
