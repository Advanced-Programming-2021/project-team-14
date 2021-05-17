package view.enums;

import model.card.enums.Position;

public enum CommandTags {


    SET("set"),
    SELECT("select"),
    NEXT_PHASE("next phase"),
    SET_POSITION("set position"),
    SUMMON("summon"),
    ATTACK("attack"),
    DIRECT_ATTACK("attack direct"),
    FLIP_SUMMON("flip-summon"),
    SELECT_CARD_MONSTER("select monster"),
    SELECT_CARD_SPELL("select spell"),
    DESELECT("deselect"),
    SELECT_FIELD("select field"),
    SELECT_HAND("select hand"),
    ACTIVATE_EFFECT("activate effect"),
    SHOW_SELECTED_CARD("show card"),
    ATTACK_DIRECT("attack direct"),


    LOGIN("login"),
    REGISTER("register"),
    CREATE_DECK("create-deck"),
    DELETE_DECK("delete-deck"),
    ACTIVATE_DECK("activate-deck"),
    ADD_CARD("add-card"),
    REMOVE_CARD("remove-card"),
    SHOW_ALL_DECKS("show-all-decks"),
    SHOW_DECK("show-deck"),
    SHOW_CARDS("show-cards"),
    CHANGE_PASSWORD("change-password"),
    CHANGE_NICKNAME("change-nickname"),
    SHOW_SCOREBOARD("scoreboard show"),
    BUY_CARD("shop buy"),
    START_DUEL("start duel"),
    SHOP_SHOW_ALL("shop show --all");

    public final String label;

    CommandTags(String label) {
        this.label = label;
    }

    public static CommandTags fromValue(String givenName) {
        for (CommandTags direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
