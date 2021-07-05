package graphic.component;

import graphic.Cursor;
import graphic.GamePlay;
import javafx.animation.TranslateTransition;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Strings;
import model.card.Card;
import model.card.enums.CardType;
import model.game.Game;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;


public class Hand extends HBox implements ComponentLoader {

    private Game game;
    private boolean isSet;
    private Pane root;

    public Hand(Game game, Pane root) {
        this.root = root;
        this.game = game;
        load("Hand");
        this.setSpacing(-30);
        addAllCards();
        update();
        isSet = false;

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
        cardInHand.setOnMouseEntered(e -> {
            transition.play();
            changeCursor(card);
        });
        cardInHand.setOnMouseExited(e -> {
            transition.stop();
            cardInHand.setTranslateY(0);
            Cursor.reset();
        });
        cardInHand.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                isSet = !isSet;
                changeCursor(card);
            }
            else {
                Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
                Request.addData("area", "hand");
                Request.addData(Strings.POSITION.getLabel(), String.valueOf(cardInHand.getCard().getPositionIndex()));
                Request.addBooleanData(Strings.OPPONENT_OPTION.getLabel(), false);
                Request.setCommandTag(CommandTags.SELECT);
                Request.send();
                Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());

                if (isSet) {
                    Request.setCommandTag(CommandTags.SET);
                    Request.send();
                } else if (card.getCardType() == CardType.MONSTER) {
                    Request.setCommandTag(CommandTags.SUMMON);
                    Request.send();
                } else {
                    Request.setCommandTag(CommandTags.ACTIVATE_EFFECT);
                    Request.send();
                }
                if (Request.isSuccessful()) this.getChildren().remove(cardInHand);
                else {
                    System.out.println("snackbar");
                    new SnackBarComponent(Request.getMessage(), ResultState.ERROR, root);
                }
            }
        });
        this.getChildren().add(cardInHand);
    }

    private void removeAllCards() {
        this.getChildren().remove(0, this.getChildren().size());
    }

    void changeCursor(Card card) {
        if (card.getCardType() == CardType.MONSTER) {
            if (isSet) Cursor.SET.setImage();
            else Cursor.SUMMON.setImage();
        } else {
            if (isSet) Cursor.SET.setImage();
            else Cursor.ACTIVATE.setImage();
        }
    }

    public void update() {
        removeAllCards();
        addAllCards();
    }
}
