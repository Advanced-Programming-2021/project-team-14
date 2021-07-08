package graphic.component;

import graphic.GamePlay;
import javafx.scene.layout.HBox;
import model.game.Cell;
import model.game.Game;
import model.game.Player;

import java.util.ArrayList;

public class MonsterZone extends HBox{
    private Game game;
    private Player player;
    public MonsterZone(Game game, boolean isMain, GamePlay gamePlay){
        this.game = game;
        this.player = isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        this.setSpacing(15);
        player.getMonsterZone().getCells().forEach(cell -> this.getChildren().add(new GraphicCell(gamePlay)));
    }

    public void update(){
        ArrayList<Cell> cells = player.getMonsterZone().getCells();
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isEmpty()) {
                ((GraphicCell) this.getChildren().get(i)).removeCard();
            }else{
                ((GraphicCell) this.getChildren().get(i)).setCard(cells.get(i).getCard());
            }
        }
    }

}