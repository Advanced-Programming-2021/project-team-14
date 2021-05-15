package model;

public enum Strings {

    CARD_NOT_EXIST_IN_HAND("you can’t summon this card_ hand"),
    POSITION_CHANGED_SUCCESSFULLY("monster card position changed successfully"),
    POSITION_ALREADY_CHANGED("you already changed this card position in this turn"),
    ATTACK_OPTION("attack"),
    ALREADY_SUMMONED("you already summoned/set on this turn"),
    NO_CARD_TO_ATTACK("there is no card to attack here"),
    ALREADY_ATTACKER("this card already attacked"),
    SET_SUCCESSFULLY("set successfully"),
    CANNOT_TAKE_ACTION_IN_THIS_PHASE("you can’t do this action in this phase"),
    CARD_NOT_EXIST_IN_MONSTER_ZONE("you can’t change this card position"),
    CANNOT_ATTACK_WITH_THIS_CARD("you can’t attack with this card"),
    ACTION_NOT_ALlOWED_IN_THIS_PHASE("action not allowed in this phase"),
    MONSTER_ZONE_FULL("monster card zone is full"),
    SPELL_ZONE_FULL("spell card zone is full"),
    ALREADY_POSITIONED("this card is already in the wanted position"),
    CANNOT_CHANGE_POSITION("you can’t change this card position"),
    INVALID_SELECTION("invalid selection"),
    MONSTER_POSITION("monster"),
    MONSTER_POSITION_CHANGED("monster position changed successfully"),
    HAND_POSITION("hand"),
    SPELL_POSITION("spell"),
    TO("to"),
    CARD_SELECTED("card selected"),
    OPPONENT_OPTION("opponent"),
    NO_CARD_FOUND("no card found in the given position"),
    AREA("area"),
    POSITION("position"),


    SIDE("side"),
    COMMAND("command"),
    MAIN("main"),
    MAIN_DECK("main"),
    DECK("deck"),
    DECK_NAME("deck-name"),
    CARD("card"),
    OPTION("option"),
    ROUNDS_NUMBER("rounds"),
    TOKEN("token"),
    SECOND_PLAYER("second-player"),
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
    START_DUEL("duel started between %s and %s"),
    INVALID_DECK("%s’s deck is invalid"),
    NO_ACTIVE_DECK("%s has no active deck"),
    PLAYER_NOT_EXIST("there is no player with this username"),
    SAME_SECOND_PLAYER("please enter another username except yours"),
    SIDE_OPTION("side"),
    NUMBER_OF_ROUNDS_NOT_SUPPORTED("number of rounds is not supported"),
    NEW_OPTION("new"),
    PLAYER_FORMAT("%s : %s"),
    ZONE_PRINT_FORMAT("\t%s\t%s\t%s\t%s\t%s\t"),
    BOARD_STRUCTURE("\n%s\n\t%s\n%s\n%s\n%s\n%s\t\t\t\t\t\t%s\n\n--------------------------\n\n%s\t\t\t\t\t\t%s\n%s\n%s\n\t\t\t\t\t\t%s\n%s\n%s");


    public final String label;

    Strings(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
