package controller;

import DatabaseAccess.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
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
            wklyTVContactColumn, wklyTVTypeColumn, wklyTVStartColumn, wklyTVEndColumn;
    @FXML
    Button wklyGetApptsBtn, wklyHomeBtn;
    @FXML
    DatePicker wklyDatePicker;

    Stage stage;
    Parent scene;
    String date;

    ObservableList<Appointment> wklyAppts;


    /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       
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
        wklyTVStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        wklyTVEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        wklyTVCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        wklyTVUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        weeklyScreenTableView.setItems(wklyAppts);
    }

    @FXML
    private void onActionWklyDatePicker(ActionEvent event) {
    }
}
