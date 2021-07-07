package graphic.component;


import javafx.scene.layout.HBox;
import model.card.Card;
import model.game.Game;

public class RivalHand extends HBox implements ComponentLoader{
    public RivalHand(Game game) {
        load("Hand");
        addAllCards(game);
        this.setSpacing(-60);
        this.setLayoutX(200);
        this.setLayoutY(0);
        this.setTranslateY(-60);
    }

    private void addAllCards(Game game) {
        game.getBoard().getRivalPlayer().getHand().getCards().forEach(this::addNode);
    }
    private void addNode(Card card) {
        this.getChildren().add(new RivalCardGraphic());
    }
}
