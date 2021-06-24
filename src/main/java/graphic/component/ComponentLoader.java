package graphic.component;

import javafx.fxml.FXMLLoader;
import sample.Main;
import sample.MainGraphic;
import view.Request;
import view.enums.Menus;

import java.io.IOException;

public interface ComponentLoader {
    public default void load(String component) {
        FXMLLoader loader = MainGraphic.fxmlLoader("/component/" + component);
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


}
