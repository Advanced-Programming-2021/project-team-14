package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/MainMenu.fxml"));
        primaryStage.setTitle("Yu-Gi-Oh!");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
//        scene.getStylesheets().add(MainMenuController.class.getResource("/sample/CSS/main.css").toExternalForm());
        primaryStage.show();
    }
}
