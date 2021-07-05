package graphic.component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import graphic.Menu;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.game.Game;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class Phases extends HBox implements ComponentLoader{

    @FXML
    JFXButton nextPhase;

    private Game game;

    public Phases(Game game){
        load("Phases");
        this.game = game;
        deactivateAll();
        nextPhase.setOnMouseClicked(event -> nextPhase());

    }

    private void nextPhase() {
        Menu.setView(Menus.GAMEPLAY_MENU);
        Request.setCommandTag(CommandTags.NEXT_PHASE);
        Request.send();
        updatePhases();
    }

    private void updatePhases() {
        deactivateAll();
        JFXRadioButton jfxRadioButton = (JFXRadioButton) this.getChildren().get(game.getPhase().getIndex());
        jfxRadioButton.setSelected(true);
        jfxRadioButton.setDisable(false);
    }

    private void deactivateAll() {
        this.getChildren().forEach(c -> {
            if (c instanceof JFXRadioButton) c.setDisable(true);
        });
    }

}