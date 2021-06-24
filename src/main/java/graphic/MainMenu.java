package graphic;


import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.MainGraphic;

public class MainMenu extends Menu{

    public AnchorPane view;

    private void setView(String view) {
        if (this.view.getChildren().size() > 0)
            this.view.getChildren().remove(0);
        this.view.getChildren().add(MainGraphic.loadFXML(view));
    }

    public void initialize() {
        setView("DeckMenu");
    }


    public void change(MouseEvent mouseEvent) {
        String nextView;
        switch (((JFXButton)mouseEvent.getSource()).getText()){
            case "Profile":
                nextView = "ProfileMenu";
                break;
            default:
                nextView = "DeckMenu";
        }
        setView(nextView);
    }
}
