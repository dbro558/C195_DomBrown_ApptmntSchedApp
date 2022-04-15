package DatabaseAccess;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
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
public class DBCountry {

    /** getALlCountries
     * Queries database for all data from countries table.
     * Populates list with country ID's and country names.
     *
     * @return returns countryList, a list of all country ID's and names in the database.
     *
     */
    //gets list of all countries
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country country = new Country();
                country.setCountryId(countryId);
                country.setCountryName(countryName);


                countryList.add(country);

            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }

    /** countryNamesList
     * Queries database for all data from countries table.
     * Populates list with country names.
     *
     * @return returns countryNamesList, a list of all country names in the database.
     *
     */
    //returns list of country names
    public static ObservableList<String> getCountryNames() {
        ObservableList<String> countryNamesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries;"; //select statement
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String countryName = rs.getString("Country");

                countryNamesList.add(countryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryNamesList;
    }
}
