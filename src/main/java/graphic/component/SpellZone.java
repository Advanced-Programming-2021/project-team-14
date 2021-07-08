package graphic.component;

import javafx.scene.layout.HBox;
import model.game.Cell;
import model.game.Game;
import model.game.Player;

import java.util.ArrayList;

public class SpellZone extends HBox {
    private Game game;
    private Player player;

    public SpellZone(Game game, boolean isMain) {
        this.game = game;
        this.player = isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        this.setSpacing(15);
        player.getSpellZone().getCells().forEach(cell -> this.getChildren().add(new GraphicCell()));
    }

    public void update() {
        ArrayList<Cell> cells = player.getSpellZone().getCells();
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isEmpty()) {
                ((GraphicCell) this.getChildren().get(i)).removeCard();
            } else {
                ((GraphicCell) this.getChildren().get(i)).setCard(cells.get(i).getCard());
            }
        }
    }
}