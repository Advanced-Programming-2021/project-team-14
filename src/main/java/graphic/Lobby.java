package graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.MainGraphic;

public class Lobby extends Menu {

    @FXML
    public void initialize() {

    }


    @FXML
    void chatRoom(ActionEvent event) {

        Medias.USUAL_CLICK.play(1);
        if (getViewMenu().getChildren().size() > 0)
            getViewMenu().getChildren().remove(0);
        getViewMenu().getChildren().add(MainGraphic.loadFXML("ChatRoom"));
    }

    @FXML
    void sendRequest(ActionEvent event) {


    }
}