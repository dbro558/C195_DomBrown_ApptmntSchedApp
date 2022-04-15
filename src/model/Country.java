package model;

import java.sql.Timestamp;

/** This class represents a country
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0*/
public class Country {

    private int countryId;
    private String countryName, createdBy, lastUpdatedBy;
    private Timestamp createDate, lastUpdate;

    /**Country constructor
     * Default constructor for country object.
     *
     * */
    public Country(){

    }
    /** Country constructor
     *
     * @param countryId countryId is primary key
     * @param countryName name of country
     * @param createDate date of country object's creation
     * @param createdBy creator of country object
     * @param lastUpdate date/time when country object was  last updated
     * @param lastUpdatedBy user that last updated the country object
     *
     * */
    public Country(Integer countryId, String countryName, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy){
        this.countryId = countryId;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** this sets countryId from the database
     * @param countryId countryId from db*/
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** this sets countryName from the database
     * @param countryName countryName from db*/
    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    /** lastUpdateBy getter
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
