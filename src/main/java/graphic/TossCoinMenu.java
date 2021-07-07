package graphic;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Coin;
import model.User;
import model.game.Duel;
import model.game.Game;
import sample.MainGraphic;

public class TossCoinMenu {

    @FXML
    private AnchorPane view;

    @FXML
    private Circle coinCircle;

    @FXML
    private JFXButton tossButton;


    public void initialize() {

        Image image = new Image(MainGraphic.class.getResource("PNG/coinTail.png").toString());
        Image image2 = new Image(MainGraphic.class.getResource("PNG/coinHead.png").toString());
        coinCircle.setStroke(Color.TRANSPARENT);
        coinCircle.setFill(new ImagePattern(image));

        tossButton.setOnMouseClicked(e -> {
            RotateTransition rotator = createRotator(coinCircle);
            rotator.play();
//            Medias.COIN_FLIP.play(5);
            User mainUser = Duel.getCurrentDuel().getMainUser();
            User rivalUser = Duel.getCurrentDuel().getRivalUser();
            rotator.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    if (Coin.flip().equals("head")) {
                        coinCircle.setFill(new ImagePattern(image2));
                        Duel.getCurrentDuel().setPlayers(mainUser, rivalUser);
                    } else {
                        coinCircle.setFill(new ImagePattern(image));
                        Duel.getCurrentDuel().setPlayers(rivalUser, mainUser);
                    }

                    ScaleTransition scaleTransition = createScaleTransition(coinCircle);

                    scaleTransition.play();

                    scaleTransition.setOnFinished(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            if (Duel.getCurrentDuel().isAI()) {
                                Duel.getCurrentDuel().setGame(new Game(Duel.getCurrentDuel().getFirstPlayer(), Duel.getCurrentDuel().getSecondPlayer(),
                                        Duel.getCurrentDuel(), true));
                            } else {
                                Duel.getCurrentDuel().setGame(new Game(Duel.getCurrentDuel().getFirstPlayer(), Duel.getCurrentDuel().getSecondPlayer(),
                                        Duel.getCurrentDuel(), false));
                            }

                            MainGraphic.setRoot("GamePlay3");

                        }
                    });
                }
            });
        });
    }


    private ScaleTransition createScaleTransition(Node card) {

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), coinCircle);

        scaleTransition.setByY(2);
        scaleTransition.setByX(2);

        return scaleTransition;
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(500), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(10);

        return rotator;
    }

    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }
}
