package model.card.enums;

public enum MonsterCardType {
    RITUAL("Ritual"),
    EFFECT("Effect"),
    NORMAL("Normal");

    public final String label;

    MonsterCardType(String label) {
        this.label = label;
    }

    public static MonsterCardType fromValue(String givenName) {
        for (MonsterCardType direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
