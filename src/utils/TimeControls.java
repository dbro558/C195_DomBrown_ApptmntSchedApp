package utils;


/** TimeControls class
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 */
public class TimeControls {

    /** convertComboTimeValue
     * Switch case method connected to start and end comboboxes.
     * Takes int hour value from selected item in combobox and returns a string representing a time in 24 hour format.
     *
     * @param hourValue int value determined by start or end combobox selection
     *
     * @return returns string representing hour in 24 hour format (08:00 to 22:00), dependent upon combo box selection
     *
     */
    public static String convertComboTimeValue(int hourValue) {

        switch (hourValue) {

            case 8://if int is 8, etc.
                return "08:00";
            case 9:
                return "09:00";
            case 10:
                return "10:00";
            case 11:
                return "11:00";
            case 12:
                return "12:00";
            case 13:
                return "13:00";
            case 14:
                return "14:00";
            case 15:
                return "15:00";
            case 16:
                return "16:00";
            case 17:
                return "17:00";
            case 18:
                return "18:00";
            case 19:
                return "19:00";
            case 20:
                return "20:00";
            case 21:
                return "21:00";
            case 22:
                return "22:00";
            default:
                return null;
        }
    }

    /** convertComboToTime
     * Switch case method connected to start and end combobox selections.
     * Takes string representing an hour value from selected item in combobox and returns an int.
     *
     * @param timeValue String value determined by start or end combobox selection
     *
     * @return returns int representing hour in 24 hour format (08:00 to 22:00), dependent upon combo box selection
     *
     */
    public static int convertComboToTime(String timeValue) {

        switch (timeValue) {

            case "08:00"://if string is "08:00", etc.
                return 8;
            case "09:00":
                return 9;
            case "10:00":
                return 10;
            case "11:00":
                return 11;
            case "12:00":
                return 12;
            case "13:00":
                return 13;
            case "14:00":
                return 14;
            case "15:00":
                return 15;
            case "16:00":
                return 16;
            case "17:00":
                return 17;
            case "18:00":
                return 18;
            case "19:00":
                return 19;
            case "20:00":
                return 20;
            case "21:00":
                return 21;
            case "22:00":
                return 22;
            default:
                return -1;//no selection or null selection
        }
    }

}