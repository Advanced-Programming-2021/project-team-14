package graphic.component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import graphic.GamePlay;
import graphic.Medias;
import graphic.Menu;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.game.Game;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class Phases extends HBox implements ComponentLoader{

    @FXML
    JFXButton nextPhase;

    private Game game;

    public Phases(Game game, GamePlay gamePlay){
        load("Phases");
        this.game = game;
        deactivateAll();
        for (int i = 1; i < this.getChildren().size(); i++) {
            ((JFXRadioButton) this.getChildren().get(i)).setSelectedColor(Colors.THEME_COLOR.getColor());
            ((JFXRadioButton) this.getChildren().get(i)).setUnSelectedColor(Color.TRANSPARENT);
        }
        nextPhase.setStyle("-fx-text-fill:  " + Colors.GOLD.getHexCode() + ";");
        nextPhase.setOnMouseClicked(event -> nextPhase(gamePlay));
    }

    private void nextPhase(GamePlay gamePlay) {
        Medias.CHANGE_PHASE.play(1);
        Menu.setView(Menus.GAMEPLAY_MENU);
        Request.setCommandTag(CommandTags.NEXT_PHASE);
        Request.send();
        updatePhases();
        gamePlay.update();

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