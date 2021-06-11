package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {


    private Pane view;

    public Pane getPage(String fileName) {

        try {
            URL fileUrl = getClass().getResource("/sample/fxml/" + fileName + ".fxml");

            view = FXMLLoader.load(fileUrl);

        } catch (Exception e) {
            System.out.println("no page with name " + fileName);
        }

        return view;
    }

}
