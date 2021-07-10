package graphic.component;

import graphic.GamePlay;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.card.Card;
import sample.MainGraphic;
import view.GamePlayMenu;
import view.Request;


public class GraphicCell extends AnchorPane implements ComponentLoader {

    private Card card;
    @FXML
    private ImageView image;

    public GraphicCell(GamePlay gamePlay) {
        load("graphicCell");
        this.setOnMouseEntered(e -> {
            if (card != null) {
                gamePlay.setSpecificationForCard(card);
                gamePlay.setImage(new Image(MainGraphic.class.getResource("PNG/Cards/" + card.getName() + ".jpg").toString()));
            }
        });

        this.setOnMouseClicked(e -> {
            if (card != null) {
                new GamePlayMenu().commandCheckers("select --monster " + card.getPositionIndex());
                if (!gamePlay.getGame().getBoard().getRivalPlayer().getMonsterZone().isEmpty()) {
                    new GamePlayMenu().commandCheckers("attack 1");
                } else {
                    new GamePlayMenu().commandCheckers("attack direct");
                }
                if (Request.isSuccessful()) {
                    new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, gamePlay.getView());
                } else
                    new SnackBarComponent(Request.getMessage(), ResultState.ERROR, gamePlay.getView());
                gamePlay.update();
            }
        });
    }

    public void setCard(Card card) {
        this.card = card;
        this.setStyle("-fx-border-radius: 0");
        image.setImage(new Image(MainGraphic.class.getResource("PNG/Cards/" + card.getName() + ".jpg").toString()));
    }

    public void removeCard() {
        this.setStyle("-fx-border-radius: 5; -fx-border-color: SILVER");
        this.card = null;
        image.setImage(null);
    }
}