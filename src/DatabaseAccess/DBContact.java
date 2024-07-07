package DatabaseAccess;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class holds SQL database queries dealing directly with contacts
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class DBContact {

    /** getAllContacts
     * Queries database for all data from contacts table.
     * Populates list with contact IDs and contact names.
     *
     * @return returns contactList, a list of contact ID's and names.
     *
     */
    //gets list of all contacts
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("contact_id");
                String contactName = rs.getString("contact_name");

                Contact contact = new Contact();
                contact.setContactId(contactId);
                contact.setContactName(contactName);


                contactList.add(contact);

            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }

    /** getContactNames
     * Queries database for all data from contacts table.
     * Populates list with contact names.
     *
     * @return returns contactNamesList, a list of contact names.
     *
     */
    public static ObservableList<String> getContactNames() {
        ObservableList<String> contactNamesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts;"; //select statement
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String contact = rs.getString("contact_name");

                contactNamesList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactNamesList;
    }

    /** getContactId
     * Queries database for all data from contacts table given contact name.
     * Gets a contact's ID, using a contact name as a parameter.
     *
     * @param contactName the selected contact name.
     *
     * @return returns contId, a single contact ID.
     *
     */
    public static int getContactId(String contactName) {
        int contId = 0;
        try {
            String sql = "SELECT * FROM contacts WHERE contacts.contact_name = ?;"; //select statement
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, contactName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                contId = rs.getInt("contact_id");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return contId;
    }

    /** getContactName
     * Queries database for all data from contacts table given contact ID.
     * Gets a contact ID, using a contact name as a parameter.
     *
     * @param contactId the selected contact ID.
     *
     * @return returns contName, a single contact name.
     *
     */
    public static String getContactName(int contactId) {
        String contName = null;
        try {
            String sql = "SELECT * FROM contacts WHERE contacts.contact_id = ?;"; //select statement
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, contactId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
               contName = rs.getString("contact_name");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return contName;
    }
}

