package graphic.component;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.MainGraphic;

public class RivalCardGraphic extends AnchorPane implements ComponentLoader {
    @FXML
    private ImageView image;

    public RivalCardGraphic() {
        load("CardLoader");
        image.setImage(new Image(MainGraphic.class.getResource("PNG/hiddenCard.png").toString()));
        image.setPreserveRatio(true);
        image.setFitHeight(160);
        image.setFitWidth(120);
    }

}
