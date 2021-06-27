package model.card.enums;

public enum Property {
    NORMAL("Normal"),
    CONTINUOUS("Continuous"),
    COUNTER("Counter"),
    QUICK_PLAY("Quick-play"),
    FIELD("Field"),
    EQUIP("Equip"),
    RITUAL("ritual"),
    EFFECT("Effect");

    public final String label;

    Property(String label) {
        this.label = label;
    }

    public static Property fromValue(String givenName) {
        for (Property direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }
}
