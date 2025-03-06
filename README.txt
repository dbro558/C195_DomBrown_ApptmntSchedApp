Internationalized Scheduling Application Version 1.2 – Technical Documentation

1. Overview

 Purpose

The Internationalized Scheduling Application is a Java-based desktop application developed as a performance assessment for Western Governors University’s C-195 Software II: Advanced Java Concepts Course. The application demonstrates advanced Java programming techniques including GUI design with JavaFX, JDBC database connectivity, internationalization, time zone handling, and the use of lambda expressions.

Key Features
•	User Authentication: Provides a secure login using a username and password (default testing credentials: "test"/"test").
•	Internationalization: Automatically detects the user’s system language and translates the login form (including error messages) between English and French using property files.
•	Appointment Alerts: Monitors upcoming appointments and automatically alerts users if an appointment is scheduled within the next 15 minutes (based on the user’s local time).
•	CRUD Operations: Enables users to create, read, update, and delete appointment and customer data via a graphical user interface.
•	Time Zone Conversions: Handles time conversions among the user’s local time, Eastern Standard Time (for the consulting agency’s business hours), and Coordinated Universal Time (UTC) for database storage.
•	Reports: Generates an additional report displaying the top 10 customers (by number of appointments) for the current month in descending order.
•	Modern Java Features: Implements at least 28 lambda expressions spread across various controllers for enhanced readability and concise coding.

________________________________________

2. System Architecture

Application Layers

1.	Presentation Layer (GUI)
 JavaFX & FXML: The user interface is built using JavaFX with FXML files defining various screens. For example:
 	login.fxml – Handles user authentication.
 	mainscreen.fxml – Main application dashboard.
 	add.fxml, update.fxml, delete.fxml – For managing appointments and customer data.
 	Additional FXML files for weekly and monthly appointment views and reports.

2.	Business Logic Layer
	Controller Classes: Located in the src/controller/ directory, these classes (e.g., LoginController.java, AddController.java, UpdateController.java, etc.) manage user      interactions, validate input, trigger business logic, and update the GUI. They also implement lambda expressions to streamline event handling and data processing.
	Utility Classes: Classes such as TimeControls.java and Logger.java in the src/utils/ package assist with time zone conversions, logging, and other common functionalities.

3.	Data Access Layer
 	Database Connectivity: The DatabaseConnection.java class in the src/Database/ package manages JDBC connections to a MySQL database.
 	Data Access Objects (DAO): The src/DatabaseAccess/ package contains classes (e.g., DBAppointment.java, DBCustomer.java, DBUser.java, etc.) that encapsulate SQL operations   and provide an interface to interact with the underlying database.
 	Model Classes: Located in the src/model/ directory, these classes represent the core data entities (e.g., Appointment.java, Customer.java, User.java) and are used by both   the GUI and the data access layer.

4.	Internationalization
 	Resource Bundles: The application uses property files located in src/resources/languages/ (e.g., LabelsBundle.properties for English and LabelsBundle_fr.properties for   French) to manage locale-specific text. When the application starts, it detects the user’s system language and loads the appropriate bundle, ensuring that all labels and  messages are displayed in the user’s language.

________________________________________

3. Development Environment & Technologies

•	Programming Language: Java
•	GUI Framework: JavaFX (SDK version 17.0.2)
•	Database: MySQL (accessed via JDBC using MySQL Connector Driver version 8.0.22)
•	IDE: IntelliJ IDEA Community Edition (version 2020.2)
•	JDK: Java SE 17.0.2

________________________________________

4. Detailed Component Documentation

4.1 Controller Classes

•	LoginController

 	Purpose: Manages the authentication process.
 
  Key Functions:
 	Validates user credentials via the DBUser class.
 	Auto-detects and displays the user’s ZoneID on the login form.
 	Retrieves and applies localized strings (English or French) from resource bundles.
 	Triggers an alert if upcoming appointments are detected within 15 minutes.


•	MainscreenController

 	Purpose: Serves as the application’s dashboard post-login.
 
  Key Functions: Loads the primary user interface and navigation controls to access other features such as adding, updating, or deleting records.


•	AddController

 	Purpose: Facilitates the creation of new appointments and customer records.
 
  Key Functions:
 	Validates input data from the user.
 	Uses lambda expressions (11 total in this controller) for event handling and streamlined data processing.
 	Interacts with DBAppointment or DBCustomer to insert records into the database.


•	UpdateController
 
  Purpose: Manages the update process for existing records.
 
  Key Functions:
 	Validates changes and triggers the corresponding DAO update methods.
 	Employs 12 lambda expressions to handle user actions and validations efficiently.


•	DeleteController
 
  Purpose: Enables deletion of appointments and customer data.

  Key Functions:
 	Prompts confirmation before deletion.
 	Uses 4 lambda expressions to manage UI events and perform deletion operations via the respective DAO.


•	CustomersController, MonthlyApptsController, and WeeklyApptsController

 	Purpose: Display and manage listings of customers and appointments.
 
  Key Functions: Populate JavaFX TableViews with data retrieved from the database, allowing users to view and interact with the information.


•	ReportsController

  Purpose: Generates a report of the top 10 customers with the highest appointment counts for the current month.
 
  Key Functions:
 	Aggregates data through DBReports and DBAppointment.
 	Displays the report in a TableView using a single lambda expression for processing the data.

________________________________________

4.2 Data Access Layer

DAO Classes

•	DBAppointment, DBCustomer, DBContact, DBCountry, DBFirstLevelDivision, DBUser, and DBReports

 	Purpose: Encapsulate SQL operations and provide an abstraction layer for database interactions.

  Key Operations:
 	Create: Insert new records.
 	Read: Query and retrieve data.
 	Update: Modify existing records.
 	Delete: Remove records.

 	Implementation Notes:
 	Use of prepared statements to ensure security.
 	Clear separation of SQL logic from business logic.


Model Classes

•	Appointment, Customer, Contact, Country, FirstLevelDivision, User, MonthlyTypeCountReport, and VIPReport
 
  Purpose: Represent the core business entities used throughout the application.

 	Key Functions:
 	Provide getters and setters for properties.
 	Serve as data carriers between the UI, business logic, and the database.

________________________________________

4.3 Utility and Internationalization

Utility Classes

•	Logger
 
  Purpose: Records application events and errors.


•	TimeControls

 	Purpose: Manages time conversions between LocalDateTime, ZonedDateTime, and Timestamp.

 	Key Consideration: Ensures consistent handling of three time zones:
 	User’s local time.
 	Eastern Standard Time (EST) for the consulting agency’s business hours.
 	UTC for database operations.


Internationalization (I18N)

•	Resource Bundles:

 	Files such as LabelsBundle.properties (English) and LabelsBundle_fr.properties (French) are used.

 	At startup, the application detects the system language and loads the appropriate bundle.

 	All UI text, including error messages, is sourced from these property files ensuring localized presentation.

________________________________________

5. API Workflow & Usage

5.1 Authentication Process

1.	User Input:
 	The login screen prompts the user to enter a username and password.
 	Default test credentials: "test" / "test".

2.	Verification:
 	LoginController accesses DBUser to authenticate the credentials.
 	Upon successful login, the user’s local ZoneID is detected and displayed.
 	In case of invalid credentials, a localized error message is shown.

3.	Post-Login:
 	Successful authentication transitions the user to the main screen managed by MainscreenController.


5.2 CRUD Operations

Create (Add Records)
•	Interface:
 	The “Add” view (defined by add.fxml) captures data for new appointments or customers.

•	Processing:
 	AddController validates inputs.
 	On successful validation, data is sent to the appropriate DAO (DBAppointment or DBCustomer) for insertion.
 	Lambda expressions manage events such as button clicks and real-time input validation.

Read (View Records)
•	Interface:
 	The “Customers” view and the weekly/monthly appointments views display data in JavaFX TableViews.

•	Processing:
 	Controllers like CustomersController query data through DAOs and populate the UI components accordingly.

Update (Modify Records)
•	Interface:
 	The “Update” view allows editing of selected records.

•	Processing:
 	UpdateController performs validations and calls update methods in the relevant DAO.
 	Uses lambda expressions to efficiently manage user interactions and real-time data updates.

Delete (Remove Records)
•	Interface:
 	The “Delete” view (associated with delete.fxml) facilitates record removal.

•	Processing:
 	DeleteController confirms the deletion action.
 	Upon confirmation, the corresponding DAO removes the record from the database.
 	Lambda expressions streamline the confirmation and deletion workflow.


5.3 Appointment Alert System

•	Mechanism:
 	Upon successful login, the application checks for any upcoming appointments within the next 15 minutes.
 	If an appointment is found, an alert is automatically displayed.
 	The check is integrated into the login or main screen initialization, ensuring timely notifications.

5.4 Reporting Functionality

•	Additional Report:
 	Accessible via the Reports section.
 	ReportsController collects data (using DBReports and DBAppointment) to generate a report.
 	The report identifies the top 10 customers based on the number of appointments scheduled for the current month.
 	Data is displayed in a JavaFX TableView, providing an easy-to-read summary for the user.

________________________________________

6. Deployment and Running the Application

6.1 Prerequisites

•	Java Development Kit (JDK): Java SE 17.0.2
•	MySQL Database: Ensure a MySQL server is installed and configured.
•	MySQL Connector: Version 8.0.22 must be added to the project libraries.
•	JavaFX SDK: Version 17.0.2 for building the GUI.
•	IDE: IntelliJ IDEA Community Edition is recommended.


6.2 Database Setup

•	Schema Initialization:
 	Import the provided SQL scripts (if available) to create the necessary tables (appointments, customers, users, etc.).

•	Configuration:
 	Update the DatabaseConnection.java file with your MySQL connection details (host, port, database name, username, and password).


6.3 Running the Application

1.	Build the Project:
 	Open the project in IntelliJ IDEA.
 	Confirm that the project SDK is set to Java SE 17.
 	Verify that the JavaFX and MySQL Connector libraries are properly configured.

2.	Start the Application:
 	Execute the Main.java file located in the src/main/ directory.
 	The login screen will appear prompting for authentication.

3.	User Login:
 	Use the default test credentials ("test" / "test") to log in.

4.	Navigating the Application:
 	Once logged in, the main screen provides access to various features:
 
  Manage appointments and customer records.
 
  Generate and view reports.
 
  The application automatically handles alerts and localization based on system settings.


________________________________________

7. Future Enhancements & Considerations

7.1 Potential Enhancements

•	Expanded Reporting:
 	Integrate more detailed analytics and customizable report options.

•	Enhanced Error Handling:
 	Improve logging mechanisms and provide more user-friendly error messages.

•	User Role Management:
 	Introduce role-based access controls to enhance security.

•	UI/UX Improvements:
 	Refine the graphical user interface for improved usability and responsiveness.

•	Localization Support:
 	Extend language support beyond English and French to cover additional locales.


7.2 Key Considerations

•	Security:
 	Ensure that database credentials and sensitive user data are handled securely.

•	Performance Optimization:
 	Review and optimize SQL queries, especially as the dataset grows.

•	Testing:
 	Implement unit and integration tests, focusing on DAO operations and UI event handling.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

This is the legacy README.txt: 


Project Title: C195_DomBrown_ApptmntSchedApp

 - Author: Dominique Brown-Gonzalez
 - Contact Information: dbro558@wgu.edu

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
    - At least 2 lambda expressions. This project uses a total of 28 (on initial project submittal date):

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

