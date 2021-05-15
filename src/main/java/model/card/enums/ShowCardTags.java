package model.card.enums;

public enum ShowCardTags {

    HORIZONTAL_LINE("----------------------------\n"),
    NAME("|name: "),
    DESCRIPTION("|description: "),
    NEXT_LINE("|\n"),
    TYPE("|type: "),
    MONSTER_TYPE("|monster type: "),
    PRICE("|price: "),
    FIRST_CHARACTER("|"),
    LEVEL("|level: "),
    ATTACK("|attack: "),
    DEFENSE("|defense: "),
    FREE_SPACE(" "),
    NULL("");

    private final String label;

    ShowCardTags(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
