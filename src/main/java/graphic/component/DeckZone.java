package graphic.component;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.game.Game;
import model.game.Player;
import model.game.PlayingDeck;
import sample.MainGraphic;


public class DeckZone extends AnchorPane implements ComponentLoader {

    @FXML
    private ImageView image;
    @FXML
    private Text number;
    private PlayingDeck deck;
    private Game game;
    private Player player;


    public DeckZone(Game game, Player player, boolean isMain) {
        this.game = game;
        this.player = isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        load("DeckZone");
        image.setImage(new Image(MainGraphic.class.getResource("PNG/deckCards.png").toString()));
        update(true);
    }

    public void update(boolean isMain) {
        this.player = isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        this.deck = this.player.getPlayingDeck();
        number.setText(deck.toString());
    }
}
