package graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import sample.MainGraphic;

public class AuctionInside extends Menu {


    @FXML
    public void initialize() {

    }

    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }

    public void setAsActiveDeck(MouseEvent mouseEvent) {
    }
}
