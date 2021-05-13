package model;

public enum Strings {

    SIDE("side"),
    MAIN("main"),
    MAIN_DECK("main"),
    DECK("deck"),
    DECK_NAME("deck-name"),
    CARD("card"),
    OPTION("option"),
    TOKEN("token"),
    CARD_PRINT_FORMAT("%s : %s"),
    DECK_CARDS_PRINT_FORMAT("Deck: %s\n%s Deck:\nMonsters:%s\nSpells and Traps:%s"),
    DECK_PRINT_FORMAT("%s: main deck %d, side deck %d, %s"),
    DECKS_PRINT_FORMAT("Decks:\nَActive Deck:%s\nOther Decks:\n%s"),
    GRAVEYARD_PRINT_FORMAT("%d"),
    FIELD_ZONE_PRINT_FORMAT("%s"),
    PLAYING_DECK_PRINT_FORMAT("%d"),
    HAND_PRINT_FORMAT("\t%s\t%s\t%s\t%s\t%s\t%s"),
    SIDE_DECK("side"),
    VALID("valid"),
    INVALID("invalid"),
    SIDE_OPTION("side"),
    PLAYER_FORMAT("%s : %s"),
    ZONE_PRINT_FORMAT("\t%s\t%s\t%s\t%s\t%s\t"),
    BOARD_STRUCTURE("\t\t\t%s\n%s : %s\n\t%s\n%s\n%s\n%s\n%s\t\t\t\t\t\t%s\n\n--------------------------\n\n%s\t\t\t\t\t\t%s\n%s\n%s\n\t\t\t\t\t\t%s\n%s\n%s : %s");

    public final String label;

    Strings(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
