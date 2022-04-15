package model;

/** This class represents a monthly type count report
 * @author Dom Brown-Gonzalez
 * @author dbro558@wgu.edu
 * version 1.0*/
public class MonthlyTypeCountReport {

    private int count;
    private String type;
    private String month;
    private String year;

    /** MonthlyTypeCountReport constructor
     * @param year year of appointment type
     * @param month month of appointment type
     * @param type appointment type
     * @param count count of appointment type*/
    public MonthlyTypeCountReport(String year, String month, String type, int count) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /** type getter
     * @return returns type*/
    public String getType() {
        return type;
    }

    /** this sets type from the database
     * @param type type from db*/
    public void setType(String type) {
        this.type = type;
    }

    /** year getter
     * @return returns year*/
    public String getYear() {
        return year;
    }

    /** this sets year from the database
     * @param year year from db*/
    public void setYear(String year) {
        this.year = year;
    }

    /** count getter
     * @return returns count*/
    public int getCount() {
        return count;
    }

    /** this sets count from the database
     * @param count count from db*/
    public void setCount(int count) {
        this.count = count;
    }

    /** month getter
     * @return returns month*/
    public String getMonth() {
        return month;
    }

    /** this sets month from the database
     * @param month month from db*/
    public void setMonth(String month) {
        this.month = month;
    }


}
