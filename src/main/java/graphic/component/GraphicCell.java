package graphic.component;

import graphic.GamePlay;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.card.Card;
import model.card.enums.State;
import sample.MainGraphic;



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


    }

    public void setCard(Card card) {
        this.card = card;
        this.setStyle("-fx-border-radius: 0");
        if (card.getState() == State.DEFENSIVE_HIDDEN || card.getState() == State.HIDDEN){
            image.setImage(new Image(MainGraphic.class.getResource("PNG/Cards/hidden2.png").toString()));
            image.setRotate(0);
        }
        else {
            image.setRotate(0);
            image.setImage(new Image(MainGraphic.class.getResource("PNG/Cards/" + card.getName() + ".jpg").toString()));
        }
        if (card.getState() == State.DEFENSIVE_HIDDEN || card.getState() == State.DEFENSIVE_OCCUPIED) {
            image.setRotate(90);
        }
    }

    public Card getCard() {
        return card;
    }

    public void removeCard() {
        System.out.println("removing the card");
        this.setStyle("-fx-border-radius: 5; -fx-border-color: SILVER");
        this.card = null;
        image.setImage(null);
    }
}