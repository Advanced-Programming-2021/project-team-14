package graphic;

import com.jfoenix.controls.JFXButton;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.MainGraphic;
import view.Console;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.concurrent.atomic.AtomicBoolean;

public class DuelMenu extends Menu {


    @FXML
    private JFXButton onePlayerGame;
    @FXML
    private JFXButton twoPlayerGame;
    @FXML
    private AnchorPane view;


    public void startOnePlayerGame(MouseEvent mouseEvent) {
//        Medias.START_DUEL_CLICK.play(1);
        setView(Menus.DUEL_MENU);
        Request.setCommandTag(CommandTags.START_DUEL_AI);
        Request.addData("rounds", "1");
        Request.send();
        if (Request.isSuccessful()) {
            Console.print(Request.getMessage());
            MainGraphic.setRoot("TossCoin");
        } else
            Console.print(Request.getMessage());
    }

    public void startTwoPlayerGame(MouseEvent mouseEvent) {
//        Medias.START_DUEL_CLICK.play(1);

        AtomicBoolean checker = new AtomicBoolean(false);

        VBox pauseRoot = new VBox(5);
        pauseRoot.setStyle("-fx-background-color: rgba(81, 84, 104, 0.8);");

        pauseRoot.setAlignment(Pos.CENTER);
        pauseRoot.setPadding(new Insets(20));


        TextField textField = new TextField();
        Label label = new Label("Set second player:");

        pauseRoot.getChildren().add(label);
        pauseRoot.getChildren().add(textField);

        Button set = new JFXButton("Set");
        String style = MainGraphic.class.getResource("CSS/GamePlay.css").toString();
        set.setStyle(style);
        Button back = new JFXButton("Back");
        back.setStyle(style);
        pauseRoot.getChildren().add(set);
        pauseRoot.getChildren().add(back);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
//            popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.DARKGREY));

        back.setOnAction(event -> {
            view.setEffect(null);
            popupStage.hide();
        });

        set.setOnAction(event -> {

            checker.set(true);
            setView(Menus.DUEL_MENU);
            Request.setCommandTag(CommandTags.START_DUEL);
            Request.addData("rounds", "1");
            Request.addData("second-player", textField.getText());
            Request.send();
            if (Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS);
                MainGraphic.setRoot("TossCoin");
            } else
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR);
            view.setEffect(null);
            popupStage.hide();
        });

        popupStage.show();

        if (checker.get()) {
            if (Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS);
                MainGraphic.setRoot("TossCoin");
            } else
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR);
        }
    }

}
