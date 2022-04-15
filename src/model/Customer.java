package model;

/** This class represents a customer
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0*/
public class Customer {

    private int customerID, divisionID;
    private String customerName;
    private String countryName;
    private String divisionName;
    private String customerAddress;
    private String phone;
    private String postal;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;

    /**Customer constructor
     * Default constructor for customer object.
     * */
    public Customer() {

    }


    /** Customer constructor
     * Used by DBCustomer.getAllCustomers()
     *
     * @param customerID customerID is primary key
     * @param customerName name of customer
     * @param customerAddress address of customer
     * @param divisionName name of first level division associated with divisionID
     * @param divisionID id of state or province (first level division) associated with customer, divisionID is foreign key
     * @param countryName name of country associated with divisionID associated with customer
     * @param postal postal code of customer
     * @param phone phone number of customer
     *
     * */
    public Customer(int customerID, String customerName, String customerAddress, String divisionName, int divisionID,
                     String countryName, String postal, String phone){
        this.customerName = customerName;
        this.customerID = customerID;
        this.customerAddress = customerAddress;
        this.divisionName = divisionName;
        this.divisionID = divisionID;
        this.countryName = countryName;
        this.phone = phone;
        this.postal = postal;
    }



    /** Customer constructor
     * Used by DBCustomer.lastUpdatedCustomer()
     *
     * @param customerId customerId is primary key
     * @param customerName name of customer
     * @param customerAddress address of customer
     * @param divisionId id of first level division (state/province) associated with customer, divisionId is foreign key
     * @param postal postal code of customer
     * @param phone phone number of customer
     *
     * */
    public Customer(int customerId, String customerName, String customerAddress, int divisionId, String postal, String phone) {
        this.customerID = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.divisionID = divisionId;
        this.postal = postal;
        this.phone = phone;
    }

    /** customerID getter
     * @return returns customerID*/
    public int getCustomerID() {
        return customerID;
    }

    /** this sets customerID from the database
     * @param customerID customerID from db*/
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** divisionID getter
     * @return returns divisionID*/
    public int getDivisionID() {
        return divisionID;
    }

    /** this sets divisionID from the database
     * @param divisionID divisionID form db*/
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** customerName getter
     * @return returns customerName*/
    public String getCustomerName() {
        return customerName;
    }

    /** this sets customerName from the database
     * @param customerName customerName from db*/
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** countryName getter
     * @return returns countryName*/
    public String getCountryName() {
        return countryName;
    }

    /** divisionName getter
     * @return returns divisionName*/
    public String getDivisionName() {
        return divisionName;
    }

    /** customerAddress getter
     * @return returns customerAddress*/
    public String getCustomerAddress() {
        return customerAddress;
    }

    /** this sets customerAddress from the database
     * @param customerAddress customerAddress from db*/
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /** phone getter
     * @return returns phone*/
    public String getPhone() {
        return phone;
    }

    /** this sets phone from the database
     * @param phone phone from db*/
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** postal getter
     * @return returns postal*/
    public String getPostal() {
        return postal;
    }

    /** this sets postal from the database
     * @param postal postal from db*/
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /** createDate getter
     * @return returns createDate*/
    public String getCreateDate() {
        return createDate;
    }

    /** this sets createDate from the database
     * @param createDate createDate from db*/
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    /** lastUpdate getter
     * @return returns lastUpdate*/
    public String getLastUpdate() {
        return lastUpdate;
    }

    /** this sets lastUpdate from the database
     * @param lastUpdate lastUpdate from db*/
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** lastUpdateBy getter
     * @return returns lastUpdatedBy*/
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** this sets lastUpdatedBy from the database
     * @param lastUpdatedBy lastUpdatedBy form db*/
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }





}
