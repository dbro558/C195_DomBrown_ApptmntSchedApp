package model;

/** This class represents a contact
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0*/
public class Contact {

    private int contactId;
    private String contactName;

    /** Contact constructor
     * Default constructor for contact object.
     *
     * */
    public Contact(){
    }

    /** Contact constructor
     * @param contactId contactId is primary key
     * @param contactName name of contact*/
    public Contact(Integer contactId, String contactName){

        this.contactId = contactId;
        this.contactName = contactName;
    }

    /** contactId getter
     * @return returns contactId*/
    public int getContactId() {
        return contactId;
    }

    /** this sets contactId from the database
     * @param contactId contactId from db*/
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** contactName getter
     * @return returns contactName*/
    public String getContactName() {
        return contactName;
    }

    /** this sets contactName from the database
     * @param contactName contactName from db*/
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


}
