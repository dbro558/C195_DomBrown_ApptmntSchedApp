package model;

import java.sql.Timestamp;

/** This class represents a user
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0*/
public class User {

    private int userId;
    private String userName, password, createdBy, lastUpdatedBy;
    private Timestamp createDate, lastUpdate;
    private static String currentUser;

    /**User constructor
     * Default constructor for user object.
     * */
    public User(){
    }

    /** User constructor
     * @param userId userId is primary key
     * @param userName name of user
     * @param password user password
     * @param currentUser logged in user
     * @param createDate date user object was created
     * @param createdBy creator of user object
     * @param lastUpdate date/time of last update to user object
     * @param lastUpdatedBy last user to update the user object*/
    public User(Integer userId, String userName, String password, String currentUser, Timestamp createDate, String createdBy,
                Timestamp lastUpdate, String lastUpdatedBy){

        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.currentUser = currentUser;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** userId getter
     * @return returns userId*/
    public int getUserId() {
        return userId;
    }

    /** this sets userId from the database
     * @param userId userId from db*/
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** userName getter
     * @return returns userName*/
    public String getUserName() {
        return userName;
    }

    /** this sets userName from the database
     * @param userName userName from db*/
    public void setUserName(String userName) {
        this.userName = userName;
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

    /** currentUser getter
     * @return returns currentUser*/
    public static String getCurrentUser() {
        return currentUser;
    }

    /** this sets currentUser from the database
     * @param currentUser currentUser from db*/
    public static void setCurrentUser(String currentUser) {
        User.currentUser = currentUser;
    }

}

