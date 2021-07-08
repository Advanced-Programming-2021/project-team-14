package model;

import javafx.scene.paint.Color;

public enum Colors {
    DEFAULT(Color.rgb(186, 186, 186), "#00C57B", new java.awt.Color(186, 186, 186)),
    DARK_GRAY(Color.rgb(81, 84, 104), "#515468", new java.awt.Color(81, 84, 104)),
    LIGHT_GRAY(Color.rgb(186, 186, 186), "#00C57B", new java.awt.Color(186, 186, 186)),
    GOLD(Color.rgb(255,215,0), "#E2C88B", new java.awt.Color(226, 200, 139)),
    DARK_RED(Color.rgb(152, 0, 66), "#980042", new java.awt.Color(152, 0, 66)),
    GREEN(Color.rgb(0, 152, 126), "#00987E", new java.awt.Color(0, 152, 126)),
    BLUE_GRAPHIC(new java.awt.Color(0, 60, 129)),
    THEME_COLOR(Color.rgb(109, 93, 211), "#6D5DD3", new java.awt.Color(109, 93, 211)),
    SUCCESS(Color.rgb(27, 215, 112), "#00C57B", new java.awt.Color(27, 215, 112)),
    WARNING(Color.rgb(215, 27, 86), "#DF295A", new java.awt.Color(215, 27, 86));

    private Color color;
    private java.awt.Color graphicColor;
    private String hexCode;
    Colors(java.awt.Color graphicColor){
        this.graphicColor = graphicColor;
    }
    Colors(Color color, String hexCode, java.awt.Color graphicColor){
        this.color = color;
        this.hexCode = hexCode;
        this.graphicColor = graphicColor;
    }

    public Color getColor() {
        return color;
    }
    public String getHexCode() {
        return hexCode;
    }
    public java.awt.Color getColorForGraphic(){
        return graphicColor;
    }
}
