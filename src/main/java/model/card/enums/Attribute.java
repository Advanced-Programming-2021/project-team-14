package model.card.enums;

import java.util.Locale;

public enum Attribute {
    EARTH("Earth"),
    WATER("Water"),
    DARK("Dark"),
    LIGHT("Light"),
    FIRE("Fire"),
    WIND("wind");

    public final String label;

    Attribute(String label) {
        this.label = label;
    }

    public static Attribute fromValue(String givenName) {
        for (Attribute attribute : values()){
            if (attribute.label.toLowerCase(Locale.ROOT).equals(givenName.toLowerCase(Locale.ROOT)))
                return attribute;
        }
        return null;
    }

    public String getLabel() {
        return label;
    }
}
