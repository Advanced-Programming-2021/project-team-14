package model.card.enums;

public enum Position {
    HAND("hand"),
    DECK("deck"),
    GRAVEYARD("graveYard"),
    MONSTER_ZONE("monster"),
    SPELL_ZONE("spell");

    public final String label;

    Position(String label) {
        this.label = label;
    }

    public static Position fromValue(String givenName) {
        for (Position direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }

    public String getLabel() {
        return label;
    }
}