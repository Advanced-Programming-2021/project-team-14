package view.enums;

public enum CommandTags {

    SURRENDER("surrender"),
    CARD_NOT_FOUND("card not found!"),
    SET("set"),
    SELECT("select"),
    NEXT_PHASE("next phase"),
    SET_POSITION("set position"),
    SHOW_CARD("show card everywhere"),
    CARD_ADDED_SUCCESSFULLY("card added successfully"),
    SUMMON("summon"),
    ATTACK("attack"),
    DIRECT_ATTACK("attack direct"),
    FLIP_SUMMON("flip-summon"),
    RITUAL_SUMMON("ritual-summon"),
    SPECIAL_SUMMON("special-summon"),
    SHOW_GRAVEYARD("show graveyard"),
    SELECT_CARD_MONSTER("select monster"),
    SELECT_CARD_SPELL("select spell"),
    DESELECT("deselect"),
    SELECT_FIELD("select field"),
    SELECT_HAND("select hand"),
    ACTIVATE_EFFECT("activate effect"),
    SHOW_SELECTED_CARD("show card"),
    CANCEL_ACTIVATION("cancel activation"),
    BACK("back"),


    LOGIN("login"),
    LOGOUT("logout"),
    USER_DELETE("delete user"),
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
    CHANGE_USERNAME("change-username"),
    SHOW_SCOREBOARD("scoreboard show"),
    BUY_CARD("shop buy"),
    START_DUEL("start duel"),
    START_DUEL_AI("start duel ai"),
    SHOP_SHOW_ALL("shop show --all"),

    IMPORT("import card"),
    EXPORT("export card"),
    SET_PROFILE_PHOTO("set profile photo"),
    REMOVE_PROFILE_PHOTO("remove profile photo"),

    //cheat
    WIN_GAME("win game"),
    INCREASE_MONEY("increase money"),
    SELECT_FORCE("select force"),
    INCREASE_LIFE_POINT("increase --LP ([\\d]+)");


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
