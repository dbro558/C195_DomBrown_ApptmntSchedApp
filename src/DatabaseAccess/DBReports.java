package DatabaseAccess;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.VIPReport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class holds SQL database queries dealing directly with the VIP Report
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class DBReports {

    /** getVIPReport
     * Queries database for all data from appointments, customer ID's and names, and count of appointments for current year/month.
     * Returns list of the 10 customers with the most appointments scheduled for the current month, in descending order.
     *
     * @return returns vipReport, a list of the 10 customers with the most appointments scheduled for this month.
     *
     */
    //get list of vip customers and their appointment counts
    public static ObservableList<VIPReport> getVIPReport() {

        ObservableList<VIPReport> vipReport = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.*, customers.Customer_ID, customers.Customer_Name, COUNT(*) AS count\n" +
                    "FROM customers\n" +
                    "JOIN appointments\n" +
                    "ON customers.Customer_ID = appointments.Customer_ID\n" +
                    "WHERE MONTH(appointments.Start) = MONTH(CURRENT_DATE())\n" +
                    "AND YEAR(appointments.Start) = YEAR(CURRENT_DATE())" +
                    "GROUP BY customers.Customer_ID, customers.Customer_Name\n" +
                    "ORDER BY count DESC LIMIT 10;";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int count = rs.getInt("count");
                VIPReport vr = new VIPReport();
                vr.setCustomerId(customerId);
                vr.setCustomerName(customerName);
                vr.setCount(count);

                vipReport.add(vr);

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return vipReport;
    }
}
