package model;

import java.time.LocalDateTime;

/** This class represents an appointment
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0*/
public class Appointment {


    private int appointmentID, customerID, userID, contactID, count;
    private String description;
    private String location;
    private LocalDateTime start;
    private LocalDateTime end;
    private String title;
    private String type;
    private String customerName;
    private String contactName;


    /** Appointment constructor
     * A default constructor for an Appointment object.
     *
     * */
    public Appointment() {
    }

    /** Appointment constructor
     *
     * @param appointmentID appointmentID is primary key
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param contactID  contactID is foreign key
     * @param type  appointment type
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param customerID customerID is foreign key
     * @param userID userID is foreign key
     * @param contactName name associated with contactID
     *
     * */
    public Appointment(int appointmentID, String title, String description, String location, int contactID,
                       String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, String contactName)
    {
        this.appointmentID = appointmentID;
        this.description = description;
        this.location = location;
        this.contactID = contactID;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactName = contactName;

    }

    /** Appointment constructor
     *
     * @param customerID customerID associated with appointment
     * @param count count of appointments
     *
     * */
    public Appointment(int customerID, int count)
    {
        this.customerID = customerID;
        this.count = count;

    }

    /** Appointment constructor
     * Used by DBAppointment.getAllAppointmentsByContact() and DBAppointment.getAllAppointmentsByCustomer().
     *
     * @param appointmentID appointmentID is primary key
     * @param title appointment title
     * @param description appointment description
     * @param type appointment type
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param customerID  customerID is foreign key
     *
     * */
    public Appointment(int appointmentID, String title, String description,
                       String type, LocalDateTime start, LocalDateTime end, int customerID) {
        this.appointmentID = appointmentID;
        this.description = description;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }

    /** Appointment constructor
     * Used by DBAppointment.lastUpdatedAppt()
     *
     * @param appointmentId appointmentId is primary key
     * @param title  appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param customerId customerId is foreign key
     * @param userId userId is foreign key
     * @param contactId contactId is foreign key
     *
     * */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {

        this.appointmentID = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerId;
        this.userID = userId;
        this.contactID = contactId;
    }

    /** Appointment constructor
     * Used for deleting appointment(s)
     *
     * @param appointmentId appointmentId is primary key
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param customerId customerId is foreign key
     * @param userId userId is foreign key
     * @param contactName name of contact associated with contactId
     *
     * */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customerId, int userId, String contactName) {

        this.appointmentID = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerId;
        this.userID = userId;
        this.contactName = contactName;
    }


    /** this sets the customerID from the database
     * @param customerID customerID from db*/
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** this sets the userID from the database
     * @param userID userID from db*/
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** this sets the appointment description from the database
     * @param description description from db*/
    public void setDescription(String description) {
        this.description = description;
    }

    /** this sets the location from the database
     * @param location location from db*/
    public void setLocation(String location) {
        this.location = location;
    }

    /** this sets the title from the database
     * @param title title from db*/
    public void setTitle(String title) {
        this.title = title;
    }

    /** this sets the start date/time from the database
     * @param start start date/time as a LocalDateTime from db*/
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** this sets the end date/time from the database
     * @param end end date/time as a LocalDateTime from db*/
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** contactName getter
     * @return returns contactName*/
    public String getContactName() {
        return contactName;
    }

    /** appointmentID getter
     * @return returns appointmentID*/
    public int getAppointmentID() {
        return appointmentID;
    }

    /** customerID getter
     * @return returns customerID*/
    public int getCustomerID() {
        return this.customerID;
    }

    /** userID getter
     * @return returns userID*/
    public int getUserID() {
        return userID;
    }

    /** contactID getter
     * @return returns contactID*/
    public int getContactID() {
        return contactID;
    }

    /** description getter
     * @return returns description*/
    public String getDescription() {
        return description;
    }

    /** location getter
     * @return returns location*/
    public String getLocation() {
        return location;
    }

    /** title getter
     * @return returns title*/
    public String getTitle() {
        return title;
    }

    /** type getter
     * @return returns type*/
    public String getType() {
        return type;
    }

    /** this sets the type from the database
     * @param type type from db*/
    public void setType(String type) {
        this.type = type;
    }

    /** start getter
     * @return returns the start date/time*/
    public LocalDateTime getStart() {
        return start;
    }

    /** end getter
     * @return returns the end date/time*/
    public LocalDateTime getEnd() {
        return end;
    }

    /** count getter
     * @return returns count*/
    public int getCount() {
        return count;
    }

    /** this sets the count (number of appointments or appointment types) from the database
     * @param count count from db*/
    public void setCount(int count) {
        this.count = count;
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

}
