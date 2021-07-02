package graphic.component;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Deck;
import model.User;

public class ListItem extends AnchorPane implements ComponentLoader {

    @FXML
    Circle validity;
    @FXML
    Text cardsNum, deckName;
    @FXML
    JFXButton delete, edit;

    public ListItem(Deck deck) {
        load("DeckList");
        cardsNum.setText(String.valueOf(deck.getSize()));
        deckName.setText(deck.getName());
        if (deck.getSize() > 40) {
            validity.setFill(Colors.SUCCESS.getColor());
        } else {
            validity.setFill(Colors.WARNING.getColor());
        }

        delete.setGraphic(generateIcon(FontAwesomeIcon.TRASH));
        edit.setGraphic(generateIcon(FontAwesomeIcon.EDIT));
    }


    public ListItem(User user) {
        load("DeckList");

        cardsNum.setText(String.valueOf(user.getRank()));
        deckName.setText(String.format(" %-20s  %d", user.getNickname(), user.getScore()));
        if (Integer.parseInt(cardsNum.getText()) > 50) {
            validity.setFill(Colors.SUCCESS.getColor());
        } else {
            validity.setFill(Colors.WARNING.getColor());
        }

        delete.setGraphic(generateIcon(FontAwesomeIcon.TRASH));
        edit.setGraphic(generateIcon(FontAwesomeIcon.EDIT));
    }


    public JFXButton getDelete() {
        return delete;
    }

    public JFXButton getEdit() {
        return edit;
    }

    public Text getCardsNum() {
        return cardsNum;
    }

    public String getDeckName() {
        return deckName.getText();
    }

    private FontAwesomeIconView generateIcon(FontAwesomeIcon fontAwesomeIcon) {
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setFill(Colors.DARK_GRAY.getColor());
        icon.setSize("1.25em");
        return icon;
    }
}
