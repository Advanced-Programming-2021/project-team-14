package graphic.component;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.game.PlayingDeck;
import sample.MainGraphic;


public class DeckZone extends AnchorPane implements ComponentLoader {

    @FXML
    private ImageView image;
    @FXML
    private Text number;
    private PlayingDeck deck;
    public DeckZone(PlayingDeck deck) {
        load("DeckZone");
        this.deck = deck;
        image.setImage(new Image(MainGraphic.class.getResource("PNG/deckCards.png").toString()));
        update();
    }

    public void update() {
        number.setText(deck.toString());
    }
}
