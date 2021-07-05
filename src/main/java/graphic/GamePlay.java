package graphic;

import com.jfoenix.controls.JFXProgressBar;
import graphic.component.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.game.Duel;
import model.game.Game;
import sample.MainGraphic;


public class GamePlay extends Menu{
    public AnchorPane view;
    public Pane root;
    public AnchorPane addCardArea;
    private Game game;
    @FXML
    public void initialize(){
        game = Duel.getCurrentDuel().getGame();
        initZones();
        initPhases();
        initHands();
        initDuelistInfo();
    }


    private void initDuelistInfo() {

    }

    private void initZones() {
        view.getChildren().add(new MonsterZone(game));
//        initFieldZone();
    }

//    private void initFieldZone() {
//        ZoneCell fieldZone = new ZoneCell();
//        fieldZone.setLayoutX(0);
//        fieldZone.setLayoutY(0);

////        fieldZone.setOnDragExited(e -> {
////                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
////                System.out.println("drag exit");
////                fieldZone.getStyleClass().add("normal");
////        });
////        fieldZone.setOnDragDropped(e -> {
////                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
////                System.out.println("added to container area: " + ((Card)e.getSource()).getName()); //TODO: do the task
////        });
//        view.getChildren().add(fieldZone);
//    }

    private void initHands() {
        view.getChildren().add(new RivalHand(game));
        view.getChildren().add(new Hand(game, view));
    }

    private void initPhases() {
        view.getChildren().add(new Phases(game));
    }
    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }

}
