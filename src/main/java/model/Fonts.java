package model;


import java.awt.*;
import java.io.InputStream;

public enum Fonts {
    LALEZAR("lalezar.ttf");

    private Font font;
    private String fontFolderPath = "fonts\\";
    Fonts(String fontName) {

        try {
            InputStream stream = Fonts.class.getResourceAsStream(fontFolderPath + fontName);
            this.font = Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
    }

    public Font getFont() {
        return font;
    }
}
