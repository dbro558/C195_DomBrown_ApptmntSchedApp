package model;

/** This class represents a vip report
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0
 *
 * */
public class VIPReport {

    private int customerId;
    private String customerName;
    private int count;

    /** customerName getter
     * @return returns customerName*/
    public String getCustomerName() {
        return customerName;
    }

    /** customerId getter
     * @return returns customerId*/
    public int getCustomerId(){
        return customerId;
    }

    /** count getter
     * @return returns count*/
    public int getCount(){
        return count;
    }

    /** this sets customerName from the database
     * @param customerName customerName from db*/
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** this sets customerId from the database
     * @param customerId customerId from db*/
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    /** this sets count from the database
     * @param count count from db*/
    public void setCount(int count){
        this.count = count;
    }

}
