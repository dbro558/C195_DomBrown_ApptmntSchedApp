package main;

import Database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class creates an application to add, update, and delete appointment and customer records, as well as generate reports*/
public class Main extends Application {

    /** This method sets the primary stage of the application's user interface. Displays the login form.
     *
     * @param primaryStage the primary stage of the application
     *
     * @throws Exception an Exception has occurred
     * */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("OUIJA Global Consulting");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /** This is the main method. Launches the application.
     *
     * @param args args
     * */
    public static void main(String[] args) {
        DatabaseConnection.startConnection();
        //testing French language bundle
        //Locale.setDefault(new Locale("fr"));
        launch(args);
        DatabaseConnection.closeConnection();
    }
}
