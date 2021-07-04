package graphic.component;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import model.card.Card;
import model.game.Game;

public class Hand extends HBox implements ComponentLoader {

    private Game game;

    public Hand(Game game) {
        this.game = game;
        load("Hand");
        this.setSpacing(-20);
        addAllCards();
        update();
    }

    private void addAllCards() {
        game.getBoard().getMainPlayer().getHand().getCards().forEach(this::addNode);
    }

    private void addNode(Card card) {
        graphic.component.CardLoader cardInHand = new graphic.component.CardLoader(card, CardSize.MEDIUM.getLabel(), "Hand");
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), cardInHand);
        transition.setAutoReverse(true);
        transition.setByY(-10);
        transition.setNode(cardInHand);
        cardInHand.setOnMouseEntered(e -> transition.play());
        cardInHand.setOnMouseExited(e -> {
            transition.stop();
            cardInHand.setTranslateY(0);
        });
        this.getChildren().add(cardInHand);
    }

    private void removeAllCards() {
        this.getChildren().remove(0, this.getChildren().size());
    }

    public void update() {
        removeAllCards();
        addAllCards();
    }
}
