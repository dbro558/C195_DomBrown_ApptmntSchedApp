package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/** Logger class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 */
public class Logger {
    private static final String FILENAME = "login_activity.txt";

    /** Logger method
     * Default method.
     *
     * */
    public Logger() {
    }

    /**log
     * Logs all attempted logins to the application.
     * User's username, success or failure, and date/time/Zone ID are all collected.
     *
     * @param username username provided by user
     * @param success boolean value
     *
     */
    public static void log(String username, boolean success) {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        String formattedZDT = zonedDateTime.format(dateTimeFormatter);

        try (FileWriter fileWriter = new FileWriter(FILENAME, true);
             BufferedWriter buffWriter = new BufferedWriter(fileWriter);
             PrintWriter pdubs = new PrintWriter(buffWriter)) {
            pdubs.println( "User " + username + " login" + (success ? " Success" : " Failure")
                    + " at " + formattedZDT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
