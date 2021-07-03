package graphic;

import graphic.component.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.card.Card;
import sample.MainGraphic;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;

public class ShopMenu extends Menu {

    @FXML
    private AnchorPane buyCardArea;

    @FXML
    private TilePane allCards;

    @FXML
    private VBox sideCard;


    public void initialize() {

        ArrayList<Card> cards = Card.getCards();
        allCards.setHgap(10);
        allCards.setVgap(10);
        for (Card card : cards) {
            allCards.getChildren().add(new CardLoader(card.getName(), CardSize.MEDIUM.getLabel(), MenuNames.SHOP.getLabel()));
        }

        buyCardArea.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                buyCardArea.setStyle("-fx-border-color: " + Colors.SUCCESS.getHexCode() + ";");
            }
        });


        onDragExited(buyCardArea);
        addAreaOnDragDropped();

    }


    private void addAreaOnDragDropped() {
        buyCardArea.setOnDragDropped(this::buyCard);
    }

    private void buyCard(DragEvent dragEvent) {

        CardLoader cardLoader = ((CardLoader) dragEvent.getGestureSource());
        if (!cardLoader.getParent().equals(allCards)) {
            setView(Menus.SHOP_MENU);
            Request.setCommandTag(CommandTags.BUY_CARD);
            Request.addData("cardName", cardLoader.getName());
            Request.send();
            if (Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS);
            } else new SnackBarComponent(Request.getMessage(), ResultState.ERROR);
        }
    }

    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }
}