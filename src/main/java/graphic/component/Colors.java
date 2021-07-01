package graphic.component;

import javafx.scene.paint.Color;

public enum Colors {
    DARK_GRAY(Color.rgb(81, 84, 104)),
    SUCCESS(Color.rgb(27, 215, 112)),
    WARNING(Color.rgb(215, 27, 86));

    private Color color;
    Colors(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
