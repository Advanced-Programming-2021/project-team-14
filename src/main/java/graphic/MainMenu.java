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
    }

    public void change(MouseEvent mouseEvent) {
        String nextView = null;
        switch (((JFXButton)mouseEvent.getSource()).getText()) {
            case "Profile":
                Medias.USUAL_CLICK.play(1);
                nextView = "ProfileMenu";
                break;
            case "Import\\Export":
                Medias.USUAL_CLICK.play(1);
                nextView = "ImportExportMenu";
                break;
            case "Scoreboard":
                Medias.USUAL_CLICK.play(1);
                nextView = "ScoreboardMenu";
                break;
            case "Shop":
                Medias.USUAL_CLICK.play(1);
                MainGraphic.setRoot("ShopMenu");
                break;
            case "Duel":
                Medias.BACKGROUND.pause();
                Medias.START_DUEL_CLICK.play(1);
                nextView = "DuelMenu";
                break;
            case "Logout":
                Medias.USUAL_CLICK.play(1);
                MainGraphic.setRoot("LoginMenu");
                break;
            case "Deck":
                Medias.USUAL_CLICK.play(1);
                nextView = "DeckMenu";
                break;

        }
        if (nextView != null) {
            setView(nextView);
        }
    }
}