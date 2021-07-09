package graphic;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.MainGraphic;
import view.Request;

public class EndDuelMenu extends Menu {


    @FXML
    private AnchorPane view;

    @FXML
    private Text text;

    @FXML
    private JFXButton exit;


    @FXML
    public void initialize() throws InterruptedException {

        text.setText(Request.getMessage());

    }

    @FXML
    void exit(ActionEvent event) {
        MainGraphic.setRoot("MainMenu");

    }
}
