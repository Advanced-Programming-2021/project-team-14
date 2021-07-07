package graphic.component;

import javafx.scene.layout.AnchorPane;
import model.card.Card;

public class FieldZone extends AnchorPane {
    private GraphicCell graphicCell;
    public FieldZone(){
        this.getChildren().add(graphicCell = new GraphicCell());
        graphicCell.setStyle("-fx-border-color: " + Colors.GOLD.getHexCode() + "; -fx-border-radius: 5;");
    }


    public void setCard(Card card){
        graphicCell.setCard(card);
    }
}
