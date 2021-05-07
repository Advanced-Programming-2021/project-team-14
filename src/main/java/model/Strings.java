package model;

public enum Strings {

    SIDE("side"),
    MAIN("main"),
    MAIN_DECK("main deck"),
    DECK("deck"),
    DECK_NAME("deck-name"),
    CARD("card"),
    OPTION("option"),
    TOKEN("token"),
    CARD_PRINT_FORMAT("%s : %s"),
    DECK_CARDS_PRINT_FORMAT("Deck: %s\n%s Deck:\nMonsters:%s\nSpells and Traps:%s"),
    DECK_PRINT_FORMAT("%s: main deck %d, side deck %d, %s"),
    DECKS_PRINT_FORMAT("Decks:\nَActive Deck:%s\nOther Decks:\n%s"),
    SIDE_DECK("side deck"),
    VALID("valid"),
    INVALID("invalid"),
    SIDE_OPTION("side");

    public final String label;

    Strings(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
