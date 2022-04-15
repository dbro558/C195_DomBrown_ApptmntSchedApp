package model;

import java.sql.Timestamp;

/** This class represents a first level division
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0*/
public class FirstLevelDivision {

    private int divisionId, countryId;
    private String createdBy, divisionName, lastUpdatedBy;
    private Timestamp createDate, lastUpdate;

    /**FirstLevelDivision constructor
     * Default constructor for first level division object.
     * */
    public FirstLevelDivision(){

    }
    /** FirstLevelDivision constructor
     * @param divisionId divisionId is primary key
     * @param countryId countryId is foreign key
     * @param createDate date first level division object was created
     * @param createdBy creator of first level division
     * @param divisionName name of first level division
     * @param lastUpdate date of first level division's last update
     * @param lastUpdatedBy user that last updated the first level division object*/
    public FirstLevelDivision (Integer divisionId, Integer countryId, Timestamp createDate, String createdBy, String divisionName, Timestamp lastUpdate, String lastUpdatedBy){
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.divisionName = divisionName;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy =lastUpdatedBy;
    }

    /** divisionId getter
     * @return returns divisionId*/
    public int getDivisionId() {
        return divisionId;
    }

    /** this sets divisionId from the database
     * @param divisionId divisionId from db*/
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** this sets countryId from the database
     * @param countryId countryId from db*/
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** createdBy getter
     * @return returns createdBy*/
    public String getCreatedBy() {
        return createdBy;
    }

    /** this sets createdBy from the database
     * @param createdBy createdBy from db*/
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** this sets divisionName from the database
     * @param divisionName divisionName from db*/
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** lastUpdatedBy getter
     * @return returns lastUpdatedBy*/
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** this sets lastUpdatedBy from the database
     * @param lastUpdatedBy lastUpdatedBy from db*/
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** createDate getter
     * @return returns createDate*/
    public Timestamp getCreateDate() {
        return createDate;
    }

    /** this sets createDate from the database
     * @param createDate createDate from db*/
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /** lastUpdate getter
     * @return returns lastUpdate*/
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** this sets lastUpdate from the database
     * @param lastUpdate lastUpdate from db*/
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


}
