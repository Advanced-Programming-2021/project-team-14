package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;

import java.io.IOException;
import java.util.Objects;

public class MainGraphic extends Application {
    private static Scene scene;
    private static Stage stage;
    public static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    public static void main(String[] args) {
        Database.prepareDatabase();
        launch(args);
    }

    public static Scene getScene() {
        return scene;
    }

    @Override
    public void start(Stage stage){
//        Medias.BACKGROUND.loop();
        MainGraphic.stage = stage;
        scene = new Scene(Objects.requireNonNull(loadFXML("LoginMenu")));
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
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
