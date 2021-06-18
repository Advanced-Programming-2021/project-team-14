package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import view.RegistrationMenuController;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        Database.prepareDatabase();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new  RegistrationMenuController().run(primaryStage);
    }
}
