package DatabaseAccess;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/** This class holds SQL database queries dealing directly with customers
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class DBCustomer {

    /** getAllCustomers
     * Queries database for all data from customers table.
     * Populates list with all customer data.
     *
     * @return returns customersList, a list of all customer data from the customers table of the database.
     *
     */
    //gets list of all customers
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS f" +
                    " ON c.Division_ID = f.Division_ID INNER JOIN countries as e" +
                    " ON e.Country_ID = f.COUNTRY_ID ORDER BY c.Customer_ID;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String countryName = rs.getString("Country");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");

                Customer customer = new Customer(customerID, customerName, customerAddress, divisionName, divisionId, countryName, postal, phone);

                customersList.add(customer);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customersList;
    }

    /** getCustomerNames
     * Queries database for all data from customers table.
     *Populates list with all customer names.
     *
     * @return returns customersNamesList, a list of all customer names from the customers table of the database.
     *
     */
    //gets list of customer names
    public static ObservableList<String> getCustomerNames() {
        ObservableList<String> customerNamesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers;"; //select statement
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String customer = rs.getString("Customer_Name");

                customerNamesList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerNamesList;
    }

    /** getCustomerId
     * Queries database for all data from customers table.
     * Returns a single customer ID, using customer name as parameter.
     *
     *@param customerName the selected customer name.
     *
     * @return returns custId, a single customer ID from the customers table of the database.
     *
     */
    //gets single customer ID
    public static int getCustomerId(String customerName) {
        int custId = 0;
        try {
            String sql = "SELECT * FROM customers WHERE customers.Customer_Name = ?;"; //select statement
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, customerName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                custId = rs.getInt("Customer_ID");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return custId;
    }

    /** getSingleCustomerName
     * Queries database for all data from customers table.
     * Returns a single customer name, using customer ID as a  parameter.
     *
     * @param customerId the selected customer ID.
     *
     * @return returns custName, a single customer name from the customers table of the database.
     *
     */
    //gets single customer name
    public static String getSingleCustomerName(int customerId) {
        String custName = null;
        try {
            String sql = "SELECT * FROM customers WHERE customers.Customer_ID = ?;"; //select statement
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                custName = rs.getString("Customer_Name");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return custName;
    }

    /** getDivisionID
     * Queries database for division data from first_level_divisions table.
     * Returns a single division ID, using division name as a parameter, for use in adding a customer.
     *
     * @param division the selected division name.
     *
     *@return returns divId, a single division ID from the first_level_divisions table of the database.
     *
     */
    //gets division ID
    public static int getDivisionID(String division) {
        int divId = 0;
        try {
            String sql = "SELECT Division_ID, DIVISION FROM first_level_divisions WHERE Division = ?;";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, division);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                divId = rs.getInt("Division_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divId;
    }

    /** addCustomer
     * Inserts a new customer object into the customers table.
     * Takes input values and inserts into the customers table as a new customer object.
     *
     * @param address customer address
     * @param createdBy creator of customer object
     * @param customerName customer's name
     * @param divisionId division ID associated with customer
     * @param lastUpdatedBy user that last updated customer
     * @param phone customer's phone number
     * @param postal customer's postal code
     *
     */
    //insert customer into database
    public static void addCustomer(String address, String createdBy, String customerName, int divisionId,
                                   String lastUpdatedBy, String phone, String postal) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        try {
            String addCust = "INSERT INTO customers(Address, Create_Date, Created_By, Customer_Name, Division_ID, " +
                    "Last_Update, Last_Updated_By, Phone, Postal_Code) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(addCust);

            stmt.setString(1, address);
            stmt.setString(2, ZonedDateTime.now(ZoneOffset.UTC).format(dtf));
            stmt.setString(3, createdBy);
            stmt.setString(4, customerName);
            stmt.setInt(5, divisionId);
            stmt.setString(6, ZonedDateTime.now(ZoneOffset.UTC).format(dtf));
            stmt.setString(7, lastUpdatedBy);
            stmt.setString(8, phone);
            stmt.setString(9, postal);


            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** deleteCustomer
     * Deletes a customer from the customers table.
     * Deletes a customer from the customers table using the selected customer's customer ID.
     *
     *@param customer the selected customer object
     *
     */
    //deletes customer from database
    public static void deleteCustomer(Customer customer) {

        try {
            String deleteCust = "DELETE customers FROM customers WHERE customers.Customer_ID = ?;";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(deleteCust);

            stmt.setInt(1, customer.getCustomerID());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /** updateCustomer
     * Updates a customer object in the customers table.
     * Takes input values and updates the customer object.
     *
     * @param customer the selected customer object
     *
     */
    //updates customer in the database
    public static void updateCustomer(Customer customer){

        try{
            String updateCust = "UPDATE customers SET Customer_Name = ?, Address = ?, Division_ID = ?, Postal_Code = ?," +
                    " Phone = ?, Last_Update = ?, Last_Updated_By = ? WHERE customers.Customer_ID = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(updateCust);

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setInt(3, customer.getDivisionID());
            ps.setString(4, customer.getPostal());
            ps.setString(5, customer.getPhone());
            ps.setTimestamp(6, Timestamp.valueOf(customer.getLastUpdate()));
            ps.setString(7, customer.getLastUpdatedBy());
            ps.setInt(8, customer.getCustomerID());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** lastUpdatedCustomer
     * Queries database for all data from customers table.
     * Returns a single customer object's data, the most recently updated customer.
     *
     * @return returns lastUpdatedCust, the last updated customer
     *
     */
    //returns last updated customer object
    public static ObservableList<Customer> lastUpdatedCustomer() {
        ObservableList<Customer> lastUpdatedCust = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers ORDER BY customers.Last_Update DESC LIMIT 1;";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                int divisionId = rs.getInt("Division_ID");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");

                Customer customer = new Customer(customerId, customerName, customerAddress, divisionId,
                        postal, phone);

                lastUpdatedCust.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastUpdatedCust;
    }
}
