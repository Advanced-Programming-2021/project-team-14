package graphic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import graphic.component.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import java.util.Comparator;

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

    private ArrayList<Card> cards = new ArrayList<>();


    private int counter = 0;

    private boolean exit;


    public void initialize() {


        allCards.setHgap(15);
        allCards.setVgap(15);
        allCards.setPadding(new Insets(20, 0, 0, 0));
        initCards();

        buyCardArea.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                CardLoader cardLoader = ((CardLoader) e.getGestureSource());
                if (cardLoader.getCard().getPrice() > currentUser.getWallet().getCash()) {
                    buyCardArea.setStyle("-fx-border-color: " + Colors.WARNING.getHexCode() + ";");
                    buyCardArea.getChildren().forEach(child -> child.setStyle("-fx-fill: " + Colors.WARNING.getHexCode()));
                } else {
                    e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    buyCardArea.setStyle("-fx-border-color: " + Colors.SUCCESS.getHexCode() + ";");
                    buyCardArea.getChildren().forEach(child -> child.setStyle("-fx-fill: " + Colors.SUCCESS.getHexCode()));
                }
            }
        });


        onDragExited(buyCardArea);
        addAreaOnDragDropped();

        exit = false;

        new Thread(() -> {
            System.out.println(Menu.getData());
            while (!exit) {
                update();
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void initCards() {

        getCards();

        if (counter != 0) {
            allCards.getChildren().remove(0, cards.size());
        }

        counter++;

        cards.sort(Comparator.comparing(Card::getName));

        for (Card card : cards) {
            CardLoader cardLoader = new CardLoader(card, CardSize.SMALL.getLabel(), MenuNames.SHOP.getLabel());
            if (currentUser.getWallet().getCash() < card.getPrice()) {
                cardLoader.setOpacity(0.4);
            }
            if (card.getNumber() == 0) {
                cardLoader.setOpacity(0.2);
            }
            cardLoader.setOnMouseEntered(e -> {
                Image image = cardLoader.getImage().getImage();
                setImage(image);
                setSpecification(cardLoader);

            });
            allCards.getChildren().add(cardLoader);
        }
    }

    private void getCards() {

        setView(Menus.SHOP_MENU);
        Request.setCommandTag(CommandTags.GET_ALL_CARDS);
        Request.send();

        cards = new Gson().fromJson(Request.getMessage(), new TypeToken<ArrayList<Monster>>() {
        }.getType());
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
        Request.addData("username", getCurrentUser().getUsername());
        Request.send();
        String message = Request.getMessage();
        if (Request.isSuccessful()) {
            initCards();
            new SnackBarComponent(message, ResultState.SUCCESS, root);
        } else new SnackBarComponent(message, ResultState.ERROR, root);
    }

    private void update() {

        Platform.runLater(() -> {
            initCards();
        });
    }


    public void back(ActionEvent actionEvent) {
        exit = true;
        MainGraphic.setRoot("MainMenu");
    }

    public void adminPanel(ActionEvent actionEvent) {
        exit = true;
        MainGraphic.setRoot("AdminPanel");
    }
}