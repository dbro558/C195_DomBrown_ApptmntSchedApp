package DatabaseAccess;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.User;
import utils.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/** This class holds SQL database queries dealing directly with the VIP Report
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class DBUser {

    private static User currentUser;

    /**login
     * Boolean to check for valid username and password when logging into the application.
     * Queries database for username and password from users table.
     * If valid username/password, Boolean returns true. If incorrect username and/or password,
     * user is alerted and asked to attempt login again.
     *
     * @param username the user's username
     * @param password the user's password
     *
     * @return returns true if valid login, else false
     *
     * @throws SQLException a SQL Exception occurred
     *
     */
    //check for valid login
    public static boolean login(String username, String password) throws SQLException {

        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String query = "select * from users where User_Name='" + username + "' and Password='" + password + "';";
            ResultSet results = statement.executeQuery(query);
            if (results.next()) {
                currentUser = new User();
                User.setCurrentUser(results.getString("User_Name"));
                statement.close();
                Logger.log(username, true);
                return true;

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Incorrect Username or Password");
                alert.setContentText("Please try again.");
                System.out.println("Login unsuccessful. Awaiting next login attempt...");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                    // ... user chose OK

                }
                Logger.log(username, false);
                return false;

            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    /** getUserNames
     * Queries database for all data from users table.
     * Returns a list of all user names.
     *
     * @return returns list userNamesList
     *
     */
    //get list of all user's names
    public static ObservableList<String> getUserNames() {

        ObservableList<String> userNamesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users;"; //select statement
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String user = rs.getString("User_Name");

                userNamesList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNamesList;
    }

    /** getSingleUserName
     * Queries database for all data from users table.
     * Returns a single user name, given a selected user ID.
     *
     * @param userId the selected user ID
     *
     * @return returns uName, the single user name associated with the selected user ID
     */
    //gets single user name
    public static String getSingleUserName(int userId){
        String uName = null;
        try {
            String sql = "SELECT * FROM users WHERE users.User_ID = ?;"; //select statement
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                uName = rs.getString("User_Name");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return uName;
    }

    /** getUserId
     * Queries database for all data from users table.
     * Returns a single user ID, given a selected user's name.
     *
     * @param userName the selected user's name
     *
     * @return returns uId, the user ID associated with the selected user's name
     *
     */
    public static int getUserId(String userName) {
        int uId = 0;//user ID initialization
        try {
            String sql = "SELECT * FROM users WHERE users.User_Name = ?;"; //select statement
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                uId = rs.getInt("User_ID");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return uId;
    }

}