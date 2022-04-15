Project Title: C195_DomBrown_ApptmntSchedApp

 - Author: Dominique Brown-Gonzalez
 - Contact Information: dbro558@wgu.edu
 - Application Version: 1.0





Purpose of this Application:

This is the Performance Assessment for Western Governors University's C-195 Software II: Advanced Java Concepts Course.
Demonstrable outcomes of the program:
    - GUI-based application using only POJO, JavaFX SDK, and JDBC MySQL Driver...no other non-API libraries
    - Ability to log into the application with a validated username and password ("test" and "test" for testing purposes)
    - Automated detection of user's ZoneID (with a label displayed on the login form)
    - Automated translation, between English and French, of any text on the login form (including error messages)
      based on the user's computer language settings (.properties-based)
    - Alert message automatically displays if an appointment has been scheduled for within the next 15 minutes (User's local time)
    - CRUD database manipulation through use of the GUI forms (appointment and customer data only)
    - Time conversion, between LocalDateTime, ZonedDateTime and Timestamp, with three time zones in use:
        User's local time
        Fictional consulting agency's business hours in EST
        UTC for insertion into database
    - Input validation and working with foreign key constraints
    - At least 2 lambda expressions. This project uses a total of 28:

      AddController has 11
      at lines 158, 159, 190, 197, 210, 238, 351, 355, 391, 397, 403

      DeleteController has 4
      at lines 193, 224, 376, 388

      ReportsController has 1
      at line 121

      UpdateController has 12
      at lines 129, 130, 163, 310, 352, 367, 402, 532, 536, 586, 592, 598



Description of Additional Report:
    The additional report populates a JavaFX TableView with the contents of a list via the DBAppointment class.
    The list contains the customer ID, customer name, and number of appointments currently scheduled for the 10
    customers with the most appointments booked for the current month, in descending order.

Development Environment:
 - IDE: IntelliJ IDEA Community Edition version 2020.2
 - JDK version: Java SE 17.0.2
 - JavaFX version: JavaFX SDK 17.0.2
 - MySQL Connector Driver version: 8.0.22


How To Run This Program:
