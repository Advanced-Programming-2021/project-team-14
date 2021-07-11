package graphic.component;

import graphic.GamePlay;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import model.Strings;
import model.game.Cell;
import model.game.Game;
import model.game.Player;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;

public class MonsterZone extends HBox {
    private final GamePlay gamePlay;
    private Game game;
    private Player player, rival;

    public MonsterZone(Game game, boolean isMain, GamePlay gamePlay) {
        this.game = game;
        this.setSpacing(15);
        this.gamePlay = gamePlay;
        init(isMain, true);
    }

    private void directAttack(Cell cell) {
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.setCommandTag(CommandTags.DIRECT_ATTACK);
        Request.send();
        System.out.println(Request.getMessage());
    }

    public void update(boolean isMain) {
        init(isMain, false);
        System.out.println(player);
        ArrayList<Cell> cells = player.getMonsterZone().getCells();
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isEmpty()) {
                ((GraphicCell) this.getChildren().get(i)).removeCard();
            } else {
                ((GraphicCell) this.getChildren().get(i)).setCard(cells.get(i).getCard());
            }
        }
    }

    private void init(boolean isMain, boolean isFirstTime) {
        this.player = isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        this.rival = !isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        player.getMonsterZone().getCells().forEach(cell -> {
            GraphicCell graphicCell = new GraphicCell(gamePlay);
            graphicCell.setOnMouseClicked(e -> {
                if (graphicCell.getCard() != null) {
                    Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
                    Request.addData("area", "monster");
                    Request.addData(Strings.POSITION.getLabel(), String.valueOf(cell.getPosition()));
                    Request.addBooleanData(Strings.OPPONENT_OPTION.getLabel(), false);
                    Request.setCommandTag(CommandTags.SELECT);
                    Request.send();
                    if (rival.getMonsterZone().isEmpty()) {
                        directAttack(cell);
                        System.out.println("direct attack");
                    } else {
                        ((MonsterZone) gamePlay.upperPlayerMonsterZone.getChildren().get(0)).getChildren().forEach(child -> {
                            if (((GraphicCell) child).getCard() != null){
                                child.setEffect(new DropShadow());
                                child.setStyle("-fx-border-color: " + Colors.WARNING.getHexCode() +"; -fx-border-width: 2");
                                child.setOnMouseClicked(event -> indirectAttack(((GraphicCell) child).getCard().getPositionIndex()));
                            }
                        });
                        System.out.println("indirect attack");
                    }
                }
            });
            gamePlay.update();
            if (isFirstTime)
                this.getChildren().add(graphicCell);
        });

    }

    private void indirectAttack(int positionIndex) {
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.setCommandTag(CommandTags.ATTACK);
        Request.addData(Strings.TO.getLabel(), String.valueOf(positionIndex));
        Request.send();
        System.out.println(Request.getMessage());
    }
}