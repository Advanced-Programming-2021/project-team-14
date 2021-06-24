package graphic.component;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Deck;
import sample.Main;

import java.io.IOException;

public class DeckListItem extends AnchorPane implements ComponentLoader{

    @FXML
    Circle validity;
    @FXML
    Text cardsNum, deckName;
    @FXML
    JFXButton delete, edit;

    public DeckListItem(Deck deck) {
        load("DeckList");
        cardsNum.setText(String.valueOf(deck.getSize()));
        deckName.setText(deck.getName());
        if (deck.getSize() > 50) {
            validity.setFill(Colors.SUCCESS.getColor());
        }else{
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

    private FontAwesomeIconView generateIcon(FontAwesomeIcon fontAwesomeIcon) {
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setFill(Colors.DARK_GRAY.getColor());
        icon.setSize("1.25em");
        return icon;
    }
}
