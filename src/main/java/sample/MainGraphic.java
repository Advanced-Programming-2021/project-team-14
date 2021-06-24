package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;

import java.io.IOException;

public class MainGraphic extends Application {
    private static Scene scene;

    public static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    public static void main(String[] args) {
        Database.prepareDatabase();
        launch(args);
    }

    @Override
    public void start(Stage stage){
        scene = new Scene(loadFXML("LoginMenu"));
        stage.setScene(scene);
        stage.show();
    }
    public static FXMLLoader fxmlLoader(String fxml) {
        return new FXMLLoader(MainGraphic.class.getResource("/sample/fxml/" + fxml + ".fxml"));
    }

    public static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = fxmlLoader(fxml);

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
