package graphic;

import com.jfoenix.controls.JFXListView;
import graphic.component.DeckListItem;
import javafx.fxml.FXML;
import javafx.scene.layout.Background;
import model.Deck;
import model.Strings;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class DeckMenu extends Menu{
    @FXML
    public JFXListView listView;
    @FXML
    public void initialize(){
        listView.setBackground(Background.EMPTY);
        currentUser.getDecks().values().forEach(this::addItem);
        listView.setVerticalGap(10.0);
    }

    private void addItem(Deck deck) {
        DeckListItem deckListItem = new DeckListItem(deck);
        deckListItem.getDelete().setOnMouseClicked(e -> {
            listView.getItems().remove(deckListItem);
            Request.addData(Strings.DECK.getLabel(), deck.getName());
            Request.setCommandTag(CommandTags.DELETE_DECK);
            setView(Menus.DECK_MENU);
            Request.send();
        });
        listView.getItems().add(deckListItem);
    }
}
