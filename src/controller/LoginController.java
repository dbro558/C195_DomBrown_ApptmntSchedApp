package controller;

import Database.DatabaseConnection;
import DatabaseAccess.DBAppointment;
import DatabaseAccess.DBCustomer;
import DatabaseAccess.DBUser;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/** LoginController class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class LoginController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Label loginUsernameLabel, loginPasswordLabel, consultingFirmLabel, zoneIDLabel;
    @FXML
    private TextField loginUsernameTxtField;
    @FXML
    private PasswordField loginPasswordPassField;
    @FXML
    private Button loginBtn, cancelBtn;
    @FXML
    private ImageView handIcon;

    Stage stage;
    Parent scene;
    ResourceBundle rb;
    Locale locale;
    boolean validUser;
    String userTimeZone = ZoneId.systemDefault().toString();
    static ObservableList<Appointment> upcomingAppts = DBAppointment.appointmentIn15();

    /** apptIn15
     * Boolean value associated with checkForUpcomingAppts()
     * */
    public static Boolean apptIn15;

    /** initialize
     Initializes the stage/scene
     *
     * @param url Stage path
     * @param resourceBundle resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.locale = Locale.getDefault();
        this.rb = ResourceBundle.getBundle("resources/languages/LabelsBundle");
        this.consultingFirmLabel.setText(this.rb.getString("loginglobcons"));
        this.zoneIDLabel.setText(this.rb.getString("location") + " " + userTimeZone);
        this.loginUsernameLabel.setText(this.rb.getString("username"));
        this.loginPasswordLabel.setText(this.rb.getString("password"));
        this.loginBtn.setText(this.rb.getString("login"));
        this.cancelBtn.setText(this.rb.getString("cancel"));
    }
    @FXML
    private void onActionUsernameTxtField(ActionEvent event) throws IOException{
    }
    @FXML
    private void onActionPasswordPassField(ActionEvent event) throws IOException{
    }
    @FXML
    private void onActionLoginBtn(ActionEvent event) throws IOException, SQLException {

        //validate username and password
        String username = loginUsernameTxtField.getText();
        String password = loginPasswordPassField.getText();
        validUser = DBUser.login(username, password);

        if (validUser) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();

            apptIn15 = checkForUpcomingAppts();
            if (apptIn15) {
                for (Appointment appointment : upcomingAppts) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setHeaderText("There is an appointment within the next 15 minutes:\n" +
                            "Consultant: " + appointment.getContactName() + "\n" +
                            "Customer: " + DBCustomer.getSingleCustomerName(appointment.getCustomerID()) + "\n" +
                            "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                            "Appointment Start Date/Time: " + appointment.getStart() + "\n" +
                            "Appointment End Date/Time: " + appointment.getEnd() + "\n");
                    alert.setContentText("Click OK to exit.");
                    alert.showAndWait();

                }
                } else{
                    Alert noUpcoming = new Alert(Alert.AlertType.INFORMATION);
                    noUpcoming.setTitle("");
                    noUpcoming.setHeaderText("No appointments scheduled within the next 15 minutes.");
                    noUpcoming.setContentText("Click OK to exit");

                    Optional<ButtonType> result = noUpcoming.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        noUpcoming.close();
                        // ... user chose OK
                    }
                }
            }


            //error messages vvv

            //if login successful: alert popup with 15 minutes/no upcoming appts message



    }

    /** checkForUpcomingAppts
     * Boolean method.
     * Checks for the existence of a list of appointments scheduled within the next 15 minutes (user's local time).
     *
     * @return returns true if an appointment exists in the list, else false
     *
     * */
    public boolean checkForUpcomingAppts() {

        if(!upcomingAppts.isEmpty()){
        return true;
    }

        return false;
    }

    @FXML
    private void onActionCancelBtn (ActionEvent event){

        DatabaseConnection.closeConnection(); //closes database connection
        Platform.exit(); //exits the application
    }


}
