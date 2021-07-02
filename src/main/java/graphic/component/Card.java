package graphic.component;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.MainGraphic;

import java.io.IOException;

public class Card extends AnchorPane implements ComponentLoader{

    private String cardName;
    @FXML
    private ImageView image;

    public Card(String cardName, boolean small) {
        load("Card");
        this.cardName = cardName;
        image.setImage(new Image(MainGraphic.class.getResource("PNG/back.png").toString())); //TODO: set the actual images
        image.setPreserveRatio(true);
        image.setFitHeight(small ? 120 : 160);
        image.setFitWidth(small ? 85 : 120);

        this.setOnDragDetected((MouseEvent event) -> {
            System.out.println("dragged");
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(this.getParent().getId());
            db.setContent(content);
        });

        this.setOnMouseDragged((MouseEvent event) -> event.setDragDetect(true));

    }

    public String getName(){
        return cardName;
    }
}

