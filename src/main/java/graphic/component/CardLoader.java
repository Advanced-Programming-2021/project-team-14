package graphic.component;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.card.Card;
import sample.MainGraphic;

public class CardLoader extends AnchorPane implements ComponentLoader {

    private String cardName;
    @FXML
    private ImageView image;

    @FXML
    private Circle cardFreqCircle;

    @FXML
    private Text banSign;


    @FXML
    private Text cardFreq;
    private Card card;

    public CardLoader(Card card, String size, String menu) {
        this.card = card;
        this.cardName = card.getName();
        if (!menu.equals("shop")) {
            load("CardLoader");
        } else {
            load("ShopCards");
            banSign.setVisible(card.isBanned());
            setCardFreq();
        }
        setImage(size, card.getName());

        this.setOnDragDetected((MouseEvent event) -> {
            System.out.println("dragged");
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(this.getParent().getId());
            db.setContent(content);
        });

        this.setOnMouseDragged((MouseEvent event) -> event.setDragDetect(true));
    }

    private void setCardFreq() {
        int counter;

        if ((counter = card.getNumber()) == 0) {
            cardFreqCircle.setFill(Paint.valueOf(Colors.WARNING.getHexCode()));
            cardFreqCircle.setStroke(Paint.valueOf(Colors.WARNING.getHexCode()));
        } else {
            cardFreqCircle.setFill(Paint.valueOf(Colors.SUCCESS.getHexCode()));
            cardFreqCircle.setStroke(Paint.valueOf(Colors.SUCCESS.getHexCode()));
        }

        cardFreq.setText(String.valueOf(counter));
    }

    public void setImageHeightAndWidth(String size) {
        switch (size) {
            case "small":
                image.setFitHeight(96);
                image.setFitWidth(73);
                break;
            case "medium":
                image.setFitHeight(200);
                image.setFitWidth(160);
                break;
            case "large":
                image.setFitHeight(260);
                image.setFitWidth(200);
        }
    }

    public ImageView getImage() {
        return image;
    }

    private void setImage(String size, String cardName) {
        image.setImage(new Image(MainGraphic.class.getResource("PNG/Cards/" + cardName + ".jpg").toString()));
        image.setPreserveRatio(true);
        setImageHeightAndWidth(size);
    }

    public String getName() {
        return cardName;
    }

    public Card getCard() {
        return card;
    }

}

