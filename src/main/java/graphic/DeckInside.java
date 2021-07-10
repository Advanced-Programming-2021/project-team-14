package graphic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import graphic.component.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import model.card.Card;
import sample.MainGraphic;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;


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
    public JFXButton activateDeck;
    public AnchorPane rootPane;

    @FXML
    public void initialize() {
        deckTitle.setText(getData());
        main.setVisible(false);
        side.setVisible(false);
        Platform.runLater(() -> {
            Deck deck = currentUser.getDeck(getData());
            mainCardContainer.setHgap(10);
            mainCardContainer.setVgap(10);
            for (int i = 0; i < deck.getCards(false).size(); i++) {
                mainCardContainer.getChildren().add(new CardLoader(Card.getCardByName(deck.getCards(false).get(i)), CardSize.SMALL.getLabel(), MenuNames.DECK.getLabel()));
            }

            sideCardContainer.setHgap(10);
            sideCardContainer.setVgap(10);
            for (int i = 0; i < deck.getCards(true).size(); i++) {
                sideCardContainer.getChildren().add(new CardLoader(Card.getCardByName(deck.getCards(true).get(i)), CardSize.SMALL.getLabel(), MenuNames.DECK.getLabel()));
            }
            main.setVisible(true);

        });


        allCardsList.setSpacing(10);
        currentUser.getWallet().getCards().forEach(card -> allCardsList.getChildren().add(new CardLoader(Card.getCardByName(card), CardSize.MEDIUM.getLabel(), MenuNames.DECK.getLabel())));


        initRadioButtons();
        initAreas();
        initActivateDeckButton();
    }

    private void initActivateDeckButton() {
        activateDeck.setDisable(!currentUser.getDeck(deckTitle.getText()).isValid());
    }

    private void initRadioButtons() {

        mainRadioButton.setSelected(true);
        sideRadioButton.setSelected(false);
        sideRadioButton.setSelectedColor(Colors.THEME_COLOR.getColor());
        mainRadioButton.setSelectedColor(Colors.THEME_COLOR.getColor());
        mainRadioButton.setUnSelectedColor(Color.TRANSPARENT);
        sideRadioButton.setUnSelectedColor(Color.TRANSPARENT);
        sideRadioButton.setOnMouseClicked(e -> {
            main.setDisable(true);
            main.setVisible(false);
            side.setDisable(false);
            side.setVisible(true);
        });
        mainRadioButton.setOnMouseClicked(e -> {
            main.setDisable(false);
            main.setVisible(true);
            side.setDisable(true);
            side.setVisible(false);
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
        CardLoader cardLoader = ((CardLoader) e.getGestureSource());
        if (!cardLoader.getParent().equals(allCardsList)) {
            setView(Menus.DECK_MENU);
            Request.setCommandTag(CommandTags.REMOVE_CARD);
            Request.addData(Strings.DECK.getLabel(), deckTitle.getText());
            Request.addData(Strings.CARD.getLabel(), cardLoader.getName());
            Request.addBooleanData(Strings.SIDE_OPTION.getLabel(), sideRadioButton.isSelected());
            Request.send();
            if (Request.isSuccessful()) {
                (sideRadioButton.isSelected() ? sideCardContainer : mainCardContainer).getChildren().remove(cardLoader);
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, rootPane);
            } else new SnackBarComponent(Request.getMessage(), ResultState.ERROR, rootPane);
        }
    }

    private void addCardToDeck(DragEvent dragEvent) {
        CardLoader cardLoader = ((CardLoader) dragEvent.getGestureSource());
        System.out.println(cardLoader.getParent().toString() + allCardsList);
        if (cardLoader.getParent().equals(allCardsList)) {
            setView(Menus.DECK_MENU);
            Request.setCommandTag(CommandTags.ADD_CARD);
            Request.addData(Strings.DECK.getLabel(), deckTitle.getText());
            Request.addData(Strings.CARD.getLabel(), cardLoader.getName());
            Request.addBooleanData(Strings.SIDE_OPTION.getLabel(), sideRadioButton.isSelected());
            Request.send();
            if (Request.isSuccessful()) {
                (mainRadioButton.isSelected() ? mainCardContainer : sideCardContainer).getChildren().add(new CardLoader(Card.getCardByName(cardLoader.getName()), CardSize.SMALL.getLabel(), MenuNames.DECK.getLabel()));
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, rootPane);
            } else new SnackBarComponent(Request.getMessage(), ResultState.ERROR, rootPane);
        }
    }

    private void removeCardAreaOnDragDropped() {
        removeCardArea.setOnDragDropped(this::removeCardFromDeck);
    }


    private void addAreaOnDragDropped() {
        addCardArea.setOnDragDropped(this::addCardToDeck);
    }


    public void setAsActiveDeck(MouseEvent mouseEvent) {
        setView(Menus.DECK_MENU);
        Request.setCommandTag(CommandTags.ACTIVATE_DECK);
        Request.addData(Strings.DECK.getLabel(), deckTitle.getText());
        Request.send();
        new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, rootPane);

    }

    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }
}
