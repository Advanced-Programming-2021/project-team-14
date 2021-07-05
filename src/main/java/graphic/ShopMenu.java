package graphic;

import graphic.component.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.card.Card;
import model.card.Monster;
import model.card.enums.CardType;
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

    @FXML
    private AnchorPane root;


    @FXML
    private Text attack;

    @FXML
    private Text defense;

    @FXML
    private Text price;

    @FXML
    private ImageView selectedCardImage;

    @FXML
    private Text description;


    public void initialize() {

        ArrayList<Card> cards = Card.getCards();
        allCards.setHgap(10);
        allCards.setVgap(10);
        for (Card card : cards) {
            CardLoader cardLoader = new CardLoader(card, CardSize.SMALL.getLabel(), MenuNames.SHOP.getLabel());
            cardLoader.setOnMouseClicked(e -> {


                Image image = cardLoader.getImage().getImage();
                setImage(image);
                setSpecification(cardLoader);
            });
            allCards.getChildren().add(cardLoader);
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

    private void setImage(Image image) {
        selectedCardImage.setImage(image);
    }


    private void setSpecification(CardLoader cardLoader) {

        Card card = Card.getCardByName(cardLoader.getName());
        if (card.getCardType() == CardType.MONSTER) {
            attack.setText(String.valueOf(((Monster) card).getAttack()));
            defense.setText(String.valueOf(((Monster) card).getDefence()));
        } else {
            attack.setText("");
            defense.setText("");
        }
        price.setText(String.valueOf(card.getPrice()));
        description.setText(card.getDescriptionGraphic());
    }


    private void addAreaOnDragDropped() {
        buyCardArea.setOnDragDropped(this::buyCard);
    }

    private void buyCard(DragEvent dragEvent) {

        CardLoader cardLoader = ((CardLoader) dragEvent.getGestureSource());

        setView(Menus.SHOP_MENU);
        Request.setCommandTag(CommandTags.BUY_CARD);
        Request.addData("cardName", cardLoader.getName());
        Request.send();
        if (Request.isSuccessful()) {
            new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, root);
        } else new SnackBarComponent(Request.getMessage(), ResultState.ERROR, root);
    }


    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }
}