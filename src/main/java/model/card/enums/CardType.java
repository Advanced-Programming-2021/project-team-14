package model.card.enums;

public enum CardType {
    MONSTER("Monster"),
    SPELL("Spell"),
    TRAP("Trap");

    public final String label;

    CardType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

    public static CardType fromValue(String givenName) {
        for (CardType direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }


}
