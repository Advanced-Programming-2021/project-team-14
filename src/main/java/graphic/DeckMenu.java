package graphic;

import com.jfoenix.controls.JFXListView;
import graphic.component.ListItem;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import model.Deck;
import model.Strings;
import sample.MainGraphic;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class DeckMenu extends Menu {
    @FXML
    public JFXListView listView;
    public AnchorPane root;

    @FXML
    public void initialize() {
        listView.setBackground(Background.EMPTY);
        currentUser.getDecks().values().forEach(this::addItem);
        listView.setVerticalGap(10.0);
    }

    private void addItem(Deck deck) {
        ListItem deckListItem = new ListItem(deck, deck.getName().equals(currentUser.getActiveDeck()));
        deckListItem.getDelete().setOnMouseClicked(e -> {
            listView.getItems().remove(deckListItem);
            Request.addData(Strings.DECK.getLabel(), deck.getName());
            Request.setCommandTag(CommandTags.DELETE_DECK);
            setView(Menus.DECK_MENU);
            Request.send();
        });
        deckListItem.getEdit().setOnMouseClicked(e -> {
            addData(deckListItem.getDeckName());
            MainGraphic.setRoot("DeckInside");
        });
        listView.getItems().add(deckListItem);
    }

    public void addDeck(ActionEvent actionEvent) {
        TextInputDialog textInputDialog = new TextInputDialog("enter the deck name");
        textInputDialog.show();
        textInputDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, action -> {
            setView(Menus.DECK_MENU);
            Request.addData(Strings.DECK.getLabel(), textInputDialog.getEditor().getText());
            Request.setCommandTag(CommandTags.CREATE_DECK);
            Request.send();
            if (Request.isSuccessful()){
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS);
                addItem(currentUser.getDeck(textInputDialog.getEditor().getText()));
            }
            else
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR);

        });
    }
}
