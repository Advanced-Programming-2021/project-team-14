package graphic;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXRadioButton;
import graphic.component.Card;
import graphic.component.Colors;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Deck;
import model.Strings;
import model.game.Game;
import view.Request;
import view.enums.CommandTags;


public class DeckInside extends Menu {
    @FXML
    public VBox allCardsList;
    public ScrollPane allCardsContainer;
    public AnchorPane addCardArea, removeCardArea;
    public JFXRadioButton mainRadioButton;
    public JFXRadioButton sideRadioButton;
    public Text deckTitle;
    public ToggleGroup toggleGroup;
    public TilePane mainCardContainer;
    public TilePane sideCardContainer;
    public ScrollPane main;
    public ScrollPane side;
    private String deckName;
    @FXML
    public void initialize() {
        deckName = getData();
        main.setVisible(false);
        side.setVisible(false);
        new Thread(() -> {
            Deck deck = currentUser.getDeck(getData());
            mainCardContainer.setHgap(10);
            mainCardContainer.setVgap(10);
            for (int i = 0; i < deck.getCards(false).size(); i++) {
                mainCardContainer.getChildren().add(new Card(deck.getCards(false).get(i), true));
            }

            sideCardContainer.setHgap(10);
            sideCardContainer.setVgap(10);
            for (int i = 0; i < deck.getCards(true).size(); i++) {
                sideCardContainer.getChildren().add( new Card(deck.getCards(true).get(i), true));
            }
            main.setVisible(true);

        }).start();

        allCardsList.setSpacing(10);
        currentUser.getWallet().getCards().forEach(card -> allCardsList.getChildren().add(new Card(card, false)));


        initRadioButtons();
        initAreas();

    }

    private void initRadioButtons() {

        mainRadioButton.setSelected(true);
        sideRadioButton.setSelected(false);
        sideRadioButton.setSelectedColor(Colors.THEME_COLOR.getColor());
        mainRadioButton.setSelectedColor(Colors.THEME_COLOR.getColor());
        mainRadioButton.setUnSelectedColor(Color.TRANSPARENT);
        sideRadioButton.setUnSelectedColor(Color.TRANSPARENT);
        sideRadioButton.setOnMouseClicked(e -> {
            mainCardContainer.setDisable(true);
            mainCardContainer.setVisible(false);
            sideCardContainer.setDisable(false);
            sideCardContainer.setVisible(true);
        });
        mainRadioButton.setOnMouseClicked(e -> {
            mainCardContainer.setDisable(false);
            mainCardContainer.setVisible(true);
            sideCardContainer.setDisable(true);
            sideCardContainer.setVisible(false);
        });

    }

    private void initAreas() {

        addCardArea.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                addCardArea.setStyle("-fx-border-color: " + Colors.SUCCESS.getHexCode() + ";");
            }
        });
        removeCardArea.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                removeCardArea.setStyle("-fx-border-color: " + Colors.WARNING.getHexCode() + ";");
            }
        });

        onDragExited(addCardArea);
        onDragExited(removeCardArea);

        addAreaOnDragDropped();
        removeCardAreaOnDragDropped();
    }

    private void removeCardFromDeck(DragEvent e) {
        Card card = ((Card)e.getGestureSource());
        if (!card.getParent().equals(allCardsList)){
            System.out.println(deckTitle);
            System.out.println("deleting the " + card.getName() + "from " + (mainRadioButton.isSelected() ? "main" : "side"));
        }
    }

    private void addCardToDeck(DragEvent e) {
        Card card = ((Card)e.getGestureSource());
        System.out.println(card.getParent().toString() + allCardsList);
        if (card.getParent().equals(allCardsList)){
            System.out.println(deckTitle);
            System.out.println("adding the " + card.getName() + "to " + (mainRadioButton.isSelected() ? "main" : "side"));
            (mainRadioButton.isSelected() ? mainCardContainer : sideCardContainer).getChildren().add(new Card(card.getName(), true));
        }
    }

    private void removeCardAreaOnDragDropped() {
        //TODO: delete the card. NOTICE: check the event resource
        removeCardArea.setOnDragDropped(this::removeCardFromDeck);
    }

    private void addAreaOnDragDropped() {
        //TODO: add card to deck. NOTICE: check the event resource
        addCardArea.setOnDragDropped(this::addCardToDeck);
    }

    private void onDragExited(AnchorPane area) {
        area.setOnDragExited(e -> area.setStyle("-fx-border-color: " + Colors.DARK_GRAY.getHexCode() + ";"));
    }

    public void setAsActiveDeck(MouseEvent mouseEvent) {
        //TODO: set as active deck
    }
}
