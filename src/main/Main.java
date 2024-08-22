
package main;

import Database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.MainscreenController;

/** This class creates an application to add, update, and delete appointment and customer records, as well as generate reports */
public class Main extends Application {

    private static Stage primaryStage;

    /** This method sets the primary stage of the application's user interface. Displays the login form.
     *
     * @param primaryStage the primary stage of the application
     *
     * @throws Exception an Exception has occurred
     * */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        showLoginScreen();
    }

    public static void showLoginScreen() throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("../view/login.fxml"));
        primaryStage.setTitle("OUIJA Global Consulting");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void showMainScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/mainscreen.fxml"));
        Parent root = loader.load();
        MainscreenController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    /** This is the main method. Launches the application.
     *
     * @param args args
     * */
    public static void main(String[] args) {
        DatabaseConnection.startConnection();
        launch(args);
        DatabaseConnection.closeConnection();
    }
}
