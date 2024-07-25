package DatabaseAccess;

import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class holds SQL database queries dealing directly with first level divisions
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 *
 */
public class DBFirstLevelDivision {

    /** getAllFLDivs
     * Queries database for all data from the countries table.
     * Returns a list with division ID's and names, as well as country ID's.
     *
     *@return returns fldivsList, a list with country/division ID's and division names.
     *
     */
    //gets list of all first level divisions
    public static ObservableList<FirstLevelDivision> getAllFLDivs(){
        ObservableList<FirstLevelDivision> fldivsList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("COUNTRY_ID");
                String divisionName = rs.getString("Division");

                FirstLevelDivision div = new FirstLevelDivision();
                div.setDivisionId(divisionId);
                div.setCountryId(countryId);
                div.setDivisionName(divisionName);


                fldivsList.add(div);

            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fldivsList;
    }

    /** getDivisionNamesUS
     * Queries database for all data from first_level_divisions table.
     * Returns list of division names given a country ID.
     * This country ID returns U.S. state names.
     *
     *@return returns divNamesList, a list of division names given a country ID.
     *
     */
    //gets list of U.S. states
    public static ObservableList<String> getDivisionNamesUS() {
        ObservableList<String> divNamesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1;"; //select statement
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String div = rs.getString("Division");

                divNamesList.add(div);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divNamesList;
    }

    /** getDivisionNamesUK
     * Queries database for all data from first_level_divisions table.
     * Returns list of division names given a country ID.
     * This country ID returns UK province names.
     *
     * @return returns divNamesList, a list of division names given a country ID.
     *
     */
    //gets list of UK provinces
    public static ObservableList<String> getDivisionNamesUK() {
        ObservableList<String> divNamesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3;"; //select statement
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String div = rs.getString("Division");

                divNamesList.add(div);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divNamesList;
    }

    /** getDivisionNamesCanada
     * Queries database for all data from first_level_divisions table.
     * Returns list of division names given a country ID.
     * This country ID returns Canadian province names.
     *
     * @return returns divNamesList, a list of division names given a country ID.
     *
     */
    //gets list of Canadian provinces
    public static ObservableList<String> getDivisionNamesCanada() {
        ObservableList<String> divNamesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2;"; //select statement
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String div = rs.getString("Division");

                divNamesList.add(div);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divNamesList;
    }

    /** getDivisionID
     * Queries database for all data from first_level_divisions table.
     * Returns a single division ID given a division name.
     *
     * @param division the selected division name.
     *
     * @return returns divisionId, a single division ID.
     *
     */
    //gets single division ID
    public static int getDivisionID(String division) {
        int divisionId = 0;
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE first_level_divisions.Division = '" + division;
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, division);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                divisionId = rs.getInt("Division_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionId;
    }
}
