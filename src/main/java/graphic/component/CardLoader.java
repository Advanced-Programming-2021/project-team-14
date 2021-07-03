package graphic.component;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import sample.MainGraphic;

public class CardLoader extends AnchorPane implements ComponentLoader {

    private String cardName;
    @FXML
    private ImageView image;

    @FXML
    private Circle cardFreq;

    public CardLoader(String cardName, String size, String menu) {
        if (!menu.equals("shop")) {
            load("CardLoader");
        } else {
            load("ShopCards");
        }
        this.cardName = cardName;
        image.setImage(new Image(MainGraphic.class.getResource("PNG/back.png").toString())); //TODO: set the actual images
        image.setPreserveRatio(true);
        setImageHeightAndWidth(size);

        this.setOnDragDetected((MouseEvent event) -> {
            System.out.println("dragged");
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(this.getParent().getId());
            db.setContent(content);
        });

        this.setOnMouseDragged((MouseEvent event) -> event.setDragDetect(true));
    }

    private void setImageHeightAndWidth(String size) {
        switch (size) {
            case "small":
                image.setFitHeight(120);
                image.setFitWidth(85);
                break;
            case "medium":
                image.setFitHeight(160);
                image.setFitWidth(120);
                break;
//            case "large":
//                image.setFitHeight(small ? 120 : 160);
//                image.setFitWidth(small ? 85 : 120);
        }
    }

    public String getName() {
        return cardName;
    }
}

