package graphic;


import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.MainGraphic;

public class MainMenu extends Menu{

    public AnchorPane view;
    public AnchorPane root;


    private void setView(String view) {
        if (this.view.getChildren().size() > 0)
            this.view.getChildren().remove(0);
        this.view.getChildren().add(MainGraphic.loadFXML(view));
    }

    public void initialize() {
        setRoot(root);
        setView("DeckMenu");
    }


    public void change(MouseEvent mouseEvent) {
        String nextView;
        switch (((JFXButton)mouseEvent.getSource()).getText()){
            case "Profile":
                nextView = "ProfileMenu";
                break;
            case "Scoreboard":
                nextView = "ScoreboardMenu";
                break;
            default:
                nextView = "DeckMenu";
        }
        setView(nextView);
    }
}
