package graphic;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import sample.MainGraphic;

public enum Cursor {

    ACTIVATE(new Image(MainGraphic.class.getResource("PNG/cursor/act.png").toString())),
    SUMMON(new Image(MainGraphic.class.getResource("PNG/cursor/sum.png").toString())),
    SET(new Image(MainGraphic.class.getResource("PNG/cursor/set.png").toString()));

    private Image image;
    Cursor(Image image){
        this.image = image;
    }

    public void setImage() {
        MainGraphic.getScene().setCursor(new ImageCursor(image));
    }
    public static void reset(){
        MainGraphic.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
    }
}
