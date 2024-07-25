package DatabaseAccess;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.MonthlyTypeCountReport;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** This class holds SQL database queries dealing directly with appointments
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class DBAppointment {
    /** public variable DateTimeFormatter
     *  dtf is used throughout this (DBAppointment) class to format datetimes*/
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   // public static ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault());
    /** ObservableList apptsList
     * This is a list that holds appointment data and is used throughout this (DBAppointment) class*/
    public static ObservableList<Appointment> apptsList = FXCollections.observableArrayList();
    /** ObservableList apptIn15
     * This is a list that holds appointment data and is used in the method appointmentIn15 */
    public static ObservableList<Appointment> apptIn15 = FXCollections.observableArrayList();

    /** getAllAppointments
     * Queries database for all data from appointments table.
     * Populates list with appointments between current localdatetime and one month from current localdatetime.
     *
     * @return returns apptsList, a list of appointment data between SQL NOW() and SQL NOW() plus one month.
     *
     */
    //gets list of all appointments
    public static ObservableList<Appointment> getAllAppointments() {

        apptsList.clear();

        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdtNow = now.atZone(ZoneId.systemDefault());
        ZonedDateTime zdtUTC = zdtNow.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime zdtPlus = zdtUTC.plusMonths(1);
        String startString = zdtUTC.format(dtf);
        String endString = zdtPlus.format(dtf);


        try {
            String sql = "SELECT * FROM sched_app_schema.appointments a JOIN sched_app_schema.contacts c WHERE c.Contact_ID = a.Contact_ID AND (a.Start BETWEEN " +
                    "? AND ?) ORDER BY a.Start ASC;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ps.setString(1, startString);
            ps.setString(2, endString);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zEnd2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");


                Appointment appointment = new Appointment(appointmentId, title, description, location, contactID,
                        type, start, end, customerId, userId, contactName);


                apptsList.add(appointment);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptsList;
    }

    /** appointmentIn15
     * Queries database for appointments within the next 15 minutes.
     * Populates list with appointments between current SQL datetime and 15 minutes from current SQL datetime.
     * Uses LocalDateTime.now() as starting point, which is then turned into a ZonedDateTime, then a string
     * to set into the SQL string
     *
     * @return returns apptIn15, a list of appointment data between SQL NOW() and SQL NOW() plus 15 minutes.
     *
     */
    //returns list of appointments within next 15 minutes
    public static ObservableList<Appointment> appointmentIn15() {

        apptIn15.clear();


        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdtNow = now.atZone(ZoneId.systemDefault());
        ZonedDateTime zdtUTC = zdtNow.withZoneSameInstant(ZoneOffset.UTC); // 7 hours ahead of PST [America/Los Angeles], 4 ahead of EST
        ZonedDateTime zdtPlus = zdtUTC.plusMinutes(15); // 7 hours 15 minutes ahead of PST [America/Los Angeles], 4.25 ahead of EST
        String startString = zdtUTC.format(dtf); // Same as zdtUTC
        String endString = zdtPlus.format(dtf);

        try {
            String sql = "SELECT * FROM sched_app_schema.appointments a " +
                    "WHERE (a.Start BETWEEN ? AND ?) " +
                    "ORDER BY a.Appointment_ID, a.Start ASC;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ps.setString(1, startString);
            ps.setString(2, endString);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zEnd2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = DBContact.getContactName(contactId);

                Appointment upcomingAppointment = new Appointment(appointmentId, title, description, location, contactId, type,
                        start, end, customerId, userId, contactName);


                apptIn15.add(upcomingAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apptIn15;
    }

    /** getAllAppointmentsWkly
     * Queries database for all appointment data given a date parameter.
     * Populates list with appointments for a week given a selected date.
     *
     * @param date The user's selected date.
     *
     * @return returns apptsListWkly, a list of appointment data between selected date and one week from that selected date.
     *
     */
    //gets list of all weekly appointments
    public static ObservableList<Appointment> getAllAppointmentsWkly(String date) {

        ObservableList<Appointment> apptsListWkly = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM sched_app_schema.appointments as a LEFT OUTER JOIN sched_app_schema.contacts as c ON a.Contact_ID = c.Contact_ID \n" +
                    "WHERE start BETWEEN '" + date + "' AND DATE_ADD('"+ date +"', INTERVAL 1 WEEK) ORDER BY start ASC;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zEnd2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");


                Appointment appointment = new Appointment(appointmentId, title, description, location, contactId, type,
                        start, end, customerId, userId, contactName);


                apptsListWkly.add(appointment);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptsListWkly;
    }

    /** getAllAppointmentsMonthly
     * Queries database for all appointment data given year and month parameters.
     * Populates list with a month's worth of appointments given a selected year and month.
     *
     * @param year The user's selected year.
     * @param month The user's selected month.
     *
     * @return returns apptsList, a list of a month's appointments given year and month.
     */
    //returns list of monthly appointments
    public static ObservableList<Appointment> getAllAppointmentsMonthly(String year, String month) {

        ObservableList<Appointment> apptsListMonthly = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM sched_app_schema.appointments AS a LEFT OUTER JOIN sched_app_schema.contacts AS c ON a.Contact_ID = c.Contact_ID \n" +
                    "WHERE YEAR(Start) = '" + year +"' AND MONTH(Start) =  '" + month +"' ORDER BY start ASC;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zStart2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");


                Appointment appointment = new Appointment(appointmentId, title, description, location, contactId,
                        type, start, end, customerId, userId, contactName);


                apptsListMonthly.add(appointment);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptsListMonthly;
    }

    /** getAppointmentsByTypeMonth
     * Queries database for count of appointment types by month.
     * Populates list with appointment types, for display in a tableview.
     *
     * @return returns typeCountReport, a list of appointment types and the count of those types ordered by type, year and month.
     *
     */
    //returns list of appt types, counted and ordered by count and year/month
    public static ObservableList<MonthlyTypeCountReport> getAppointmentsByTypeMonth() {
        ObservableList<MonthlyTypeCountReport> typeCountReport = FXCollections.observableArrayList();

        try {
            String sql = "SELECT DATE_FORMAT(start, '%M') AS 'month', DATE_FORMAT(start, '%Y') AS 'year', type, COUNT(type) AS 'count' "
                    + "FROM appointments "
                    + "GROUP BY year, month, type ORDER BY year DESC, count DESC;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String year = rs.getString("year");
                String month = rs.getString("month");
                String type = rs.getString("type");
                int count = rs.getInt("count");

                MonthlyTypeCountReport tc = new MonthlyTypeCountReport(year, month, type, count);

                typeCountReport.add(tc);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return typeCountReport;
    }

    /** getAllAppointmentsByContact
     * Queries database for appointment data.
     * Populates list with appointments given a contact's name.
     *
     * @param contactName The selected contact name.
     *
     * @return returns apptsByContactList, a list of appointment data dependant upon selected contact name.
     *
     */
    //returns list of selected contact's appointments
    public static ObservableList<Appointment> getAllAppointmentsByContact(String contactName) {
        ObservableList<Appointment> apptsByContactList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT contacts.Contact_Name, customers.Customer_ID," +
                    " appointments.* FROM contacts" +
                    " INNER JOIN appointments ON contacts.Contact_ID = appointments.Contact_ID" +
                    " INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID" +
                    " WHERE contacts.Contact_Name = ?;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zEnd2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");


                Appointment appointment = new Appointment(appointmentId, title, description, type, start, end,
                        customerID);


                apptsByContactList.add(appointment);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptsByContactList;
    }

    /** getAllAppointmentsByCustomer
     * Queries database for appointment data.
     * Populates list with appointments given selected customer name.
     *
     * @param customerName The selected customer name;
     *
     * @return returns apptsByCustomerList, a list of a single customer's appointments.
     *
     */
    //returns list of single customer's appts
    public static ObservableList<Appointment> getAllAppointmentsByCustomer(String customerName) {
        ObservableList<Appointment> apptsByCustomerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT customers.Customer_Name," +
                    " appointments.* FROM customers" +
                    " JOIN appointments ON customers.Customer_ID = appointments.Customer_ID" +
                    " WHERE customers.Customer_Name = ?;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zEnd2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");


                Appointment appointment = new Appointment(appointmentId, title, description, type, start, end,
                        customerID);


                apptsByCustomerList.add(appointment);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptsByCustomerList;
    }

    /** apptAddNew
     * Inserts a new appointment into the database.
     * Adds user input and predetermined values to an appointment object for insertion into database.
     *
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start date/time
     * @param end appointment end date/time
     * @param customerId id of customer associated with appointment
     * @param userId id of user associated with appointment
     * @param contactId id of contact associated with appointment
     *
     */
    //add new appt to db
    public static void apptAddNew(String title, String description, String location,
                                  String type, LocalDateTime start, LocalDateTime end, int customerId,
                                  int userId, int contactId) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {

            String addNewAppt = "INSERT INTO appointments (Title, Description, Location, " +
                    "Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID," +
                    " User_ID, Contact_ID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(addNewAppt);

            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, location);
            stmt.setString(4, type);
            stmt.setTimestamp(5, Timestamp.valueOf(start));
            stmt.setTimestamp(6, Timestamp.valueOf(end));
            stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now().format(dtf)));
            stmt.setString(8, User.getCurrentUser());
            stmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now().format(dtf)));
            stmt.setString(10, User.getCurrentUser());
            stmt.setInt(11, customerId);
            stmt.setInt(12, userId);
            stmt.setInt(13, contactId);

            stmt.executeUpdate();
            stmt.close();


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /** apptDelete
     * Deletes an appointment from the database.
     * Deletes an appointment from the database, given an appointment ID.
     *
     * @param appointmentId The appointment ID of the appointment to be deleted.
     *
     */
    //deletes appointment associated with selected appointment iD
    public static void apptDelete(int appointmentId) {
        try {

            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);

            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /** deleteApptByCustomer
     * Deletes a given customer's appointments from the database.
     * Deletes a given customer's appointments from the database based on selected customer ID.
     *
     * @param customerId The selected customer ID.
     *
     */
    //deletes appointment witch associated customer ID
    public static void deleteApptByCustomer(int customerId) {
        try {
            String sql = "DELETE appointments FROM appointments INNER JOIN customers ON customers.Customer_ID = " +
                    "appointments.Customer_ID WHERE customers.Customer_ID = ?;";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customerId);
            ps.executeUpdate();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** apptUpdate
     * Updates an appointment in the database.
     * Updates data associated with a selected appointment ID.
     *
     * @param appointmentId The appointment ID used to select the appointment.
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the appointment start date/time
     * @param end the appointment end date/time
     * @param lastUpdatedBy user that last updated the appointment
     * @param customerId customer ID associated with the appointment
     * @param userId user ID associated with the appointment
     * @param contactId contact ID associated with the appointment
     *
     */
    //updates appointment in the database
    public static void apptUpdate(int appointmentId, String title, String description, String location, String type, LocalDateTime start,
                                  LocalDateTime end, String lastUpdatedBy, int customerId, int userId, int contactId) {
        try {


            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                    " Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?," +
                    " Contact_ID = ? WHERE Appointment_ID = ?;";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now().format(dtf)));
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, appointmentId);

            ps.executeUpdate();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** getAllAppointmentsRefresh
     * Queries database for all appointment data.
     * Populates list with appointments ordered by appointment ID.
     *
     * @return returns apptsListRefresh, an ordered list of all appointment data.
     *
     */
    //returns list of all appointments
    public static ObservableList<Appointment> getAllAppointmentsRefresh() {
        ObservableList<Appointment> apptsListRefresh = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments a JOIN contacts c WHERE c.Contact_ID = a.Contact_ID ORDER BY" +
                    " a.Appointment_ID;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zEnd2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");


                Appointment appointment = new Appointment(appointmentId, title, description, location,
                        contactId, type, start, end, customerId, userId, contactName);


                apptsListRefresh.add(appointment);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptsListRefresh;
    }

    /** lastUpdatedAppt
     * Queries database for appointment data.
     * Populates list with last updated (newest) appointment.
     *
     * @return returns lastUpdatedList, a list of appointment data between SQL NOW() and SQL NOW() plus one month.
     *
     */
    //returns most recently updated appointment
    public static ObservableList<Appointment> lastUpdatedAppt() {
        ObservableList<Appointment> lastUpdatedList = FXCollections.observableArrayList();
        try {

            String sql = "SELECT * FROM appointments ORDER BY appointments.Last_Update DESC LIMIT 1;";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp apptStart = rs.getTimestamp("Start");
                LocalDateTime ldtStart = apptStart.toLocalDateTime();
                ZonedDateTime zStart2 = ldtStart.atZone(ZoneId.systemDefault());
                LocalDateTime start = zStart2.toLocalDateTime();
                Timestamp apptEnd = rs.getTimestamp("End");
                LocalDateTime ldtEnd = apptEnd.toLocalDateTime();
                ZonedDateTime zEnd2 = ldtEnd.atZone(ZoneId.systemDefault());
                LocalDateTime end = zEnd2.toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUp = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");


                Appointment appointment = new Appointment(appointmentId, title, description, location,
                        type, start, end, customerId, userId, contactId);


                lastUpdatedList.add(appointment);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lastUpdatedList;
    }





}


