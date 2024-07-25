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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
        this.rb = ResourceBundle.getBundle("resources/languages/LabelsBundle", locale);
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
        System.out.println("Username: " + username);
        if (loginUsernameTxtField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(this.rb.getString("loginerruserempty"));
            alert.showAndWait();
        }
        String password = loginPasswordPassField.getText();
        if (loginPasswordPassField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(this.rb.getString("loginerrpassempty"));
            alert.showAndWait();
        }
        validUser = DBUser.login(username, password);

        if (validUser) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainscreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();

            // refresh upcoming appointments list
            upcomingAppts = DBAppointment.appointmentIn15();
            apptIn15 = checkForUpcomingAppts();

            if (apptIn15) {
                for (Appointment appointment : upcomingAppts) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setHeaderText("There is an appointment within the next 15 minutes:\n" +
                            "Consultant: " + appointment.getContactName() + "\n" +
                            "Customer: " + DBCustomer.getSingleCustomerName(appointment.getCustomerID()) + "\n" +
                            "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                            "Start Date/Time: " + appointment.getStart() + "\n" +
                            "End Date/Time: " + appointment.getEnd() + "\n" +
                            "Type: " + appointment.getType() + "\n" +
                            "Description: " + appointment.getDescription() + "\n" +
                            "Location: " + appointment.getLocation());
                    alert.setContentText("Click OK to exit.");
                    alert.showAndWait();
                    System.out.println("Upcoming Appointment: " + appointment.getAppointmentID());
                    System.out.println("with: " + DBCustomer.getSingleCustomerName(appointment.getCustomerID()));
                    System.out.println("from: " + appointment.getStart() + " to " + appointment.getEnd());
                }
            } else {
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
        else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(this.rb.getString("loginerrinvalid")); // invalid login error message
            alert.showAndWait();
        }
    }
    /** checkForUpcomingAppts
     * Boolean method.
     * Checks for the existence of a list of appointments scheduled within the next 15 minutes (user's local time).
     *
     * @return returns true if an appointment exists in the list, else false
     *
     * */
    public boolean checkForUpcomingAppts() {

        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdtNow = now.atZone(ZoneId.systemDefault());
        ZonedDateTime zdtUTC = zdtNow.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime utcNow = zdtUTC.toLocalDateTime();

        for (Appointment appointment : upcomingAppts) {
            LocalDateTime appointmentStart = appointment.getStart();
            if (appointmentStart.isAfter(utcNow) && appointmentStart.isBefore(utcNow.plusMinutes(15))) {
                return true;
            } // checks appointment start times against time between user's current time and 15 minutes after
        }
        return false;
    }

    @FXML
    private void onActionCancelBtn (ActionEvent event){

        DatabaseConnection.closeConnection(); //closes database connection
        Platform.exit(); //exits the application
    }


}
