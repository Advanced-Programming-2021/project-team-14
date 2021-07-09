package graphic.component;

import graphic.GamePlay;
import javafx.scene.layout.AnchorPane;
import model.card.Card;

public class FieldZone extends AnchorPane {
    private GraphicCell graphicCell;
    public FieldZone(GamePlay gamePlay){
        this.getChildren().add(graphicCell = new GraphicCell(gamePlay));
        graphicCell.setStyle("-fx-border-color: " + Colors.GOLD.getHexCode() + "; -fx-border-radius: 5;");
    }


    public void setCard(Card card){
        graphicCell.setCard(card);
    }
}
