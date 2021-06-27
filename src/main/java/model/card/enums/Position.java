package model.card.enums;

public enum Position {
    HAND("hand"),
    DECK("deck"),
    GRAVEYARD("graveYard"),
    MONSTER_ZONE("monster"),
    FIELD_ZONE("monster"),
    SPELL_ZONE("spell");

    public final String label;

    Position(String label) {
        this.label = label;
    }
}