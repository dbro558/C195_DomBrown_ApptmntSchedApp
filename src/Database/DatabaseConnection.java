package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/** DatabaseConnection class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 */
public class DatabaseConnection {

    private static final String jdbcProtocol = "jdbc";
    private static final String jdbcVendor = ":mysql:";
    private static final String serverAndPort = "//127.0.0.1:3306/";
    private static final String databaseName = "sched_app_schema";

    private static final String databaseUrl = jdbcProtocol + jdbcVendor + serverAndPort + databaseName;

    private static final String MYSQLJDBCDRIVER = "com.mysql.jdbc.Driver";

    private static final String databaseUserName = "*****";
    private static final String databaseUserPassword = "********";

    private static Connection conn = null;

    /** startConnection
     * Database connection method.
     * Attempts to establish an initial connection to the database.
     *
     * @return conn returns connection conn if successful
     *
     * */
    public static Connection startConnection(){
        try {
            Class.forName(MYSQLJDBCDRIVER);
            conn = DriverManager.getConnection(databaseUrl, databaseUserName, databaseUserPassword);

            System.out.println("Connected to database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /** getConnection
     * Database connection method.
     * Called throughout the application, rather than starting the connection from scratch.
     *
     * @return returns connection conn
     * */
    public static Connection getConnection(){
        return conn;
    }

    /** closeConnection
     * Database connection method.
     * Attempts to close the connection to the database. Used when logging out or closing the application.
     *
     * */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection to database is now closed.");
        } catch (Exception e) {
        //don't need anything here, since connection is closing anyway.
        }
    }
}
