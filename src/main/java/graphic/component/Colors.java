package graphic.component;

import javafx.scene.paint.Color;

public enum Colors {
    DARK_GRAY(Color.rgb(81, 84, 104), "#515468"),
    SUCCESS(Color.rgb(27, 215, 112), "#00C57B"),
    WARNING(Color.rgb(215, 27, 86), "#DF295A"),
    GOLD(Color.rgb(226, 200, 139), "#E2C88B"),
    THEME_COLOR(Color.rgb(109, 93, 211)),
    THEME_COLOR_LOWER(Color.rgb(109, 93, 211, 0.5 ));


    private Color color;
    private String hexCode;
    Colors(Color color){
        this.color = color;
    }
    Colors(Color color, String hexCode){
        this.color = color;
        this.hexCode = hexCode;
    }

    public Color getColor() {
        return color;
    }

    public String getHexCode() {
        return hexCode;
    }

}
