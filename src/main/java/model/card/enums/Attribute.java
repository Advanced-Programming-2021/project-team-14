package model.card.enums;

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
        for (Attribute direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
