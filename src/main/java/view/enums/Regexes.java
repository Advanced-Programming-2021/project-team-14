package view.enums;

public enum Regexes {

    SELECT("^select (?=.*(--(spell|monster|hand) (\\d+))).*$"),
    SELECT_SPELL("^select (?=.*(--(spell) (\\d+))).*$"),
    DESELECT("select -d"),
    SELECT_CARD_FROM_BOARD("^select (?=.*(--(monster|spell) (\\d+))).*$"),
    SELECT_FIELD("^select (?=.*(--(field))).*$"),
    SELECT_HAND("select --hand \\d+"),
    SELECT_MONSTER("^select (?=.*(--(monster) (\\d+))).*$"),
    SELECT_AREA(".*--(monster|hand|spell) \\d+.*"),
    SELECT_POSITION(".*--(?:monster|hand|spell) (\\d+).*"),
    SHOW_SELECTED_CARD("card show --selected"),
    SHOW_GRAVEYARD("show graveyard"),
    SUMMON("summon"),
    FLIP_SUMMON("flip-summon"),
    SET("set"),
    NEXT_PHASE("next phase"),
    SET_POSITION("set --position (attack|defense)"),
    ATTACK_TO("attack (\\d)"),
    ACTIVATE_EFFECT("activate effect"),

    MENU_EXIT("menu exit"),
    MENU_CURRENT("show current menu"),
    MENU_ENTER("menu enter (.*)"),
    CREATE_USER("^user create (?=.*(--(username) (\\w+)))(?=.*(--(password) (\\w+)))(?=.*(--(nickname) (\\w+))).*$"),
    LOGIN_USER("^user login (?=.*(--(username) (\\w+)))(?=.*(--(password) (\\w+))).*$"),
    CHANGE_PROFILE_NICKNAME("^profile change (?=.*(--(nickname) (\\w+))).*$"),
    CHANGE_PROFILE_PASSWORD("^profile change (?=.*(--password))(?=.*(--(current) (\\w+)))(?=.*(--(new) (\\w+))).*$"),
    CREATE_DECK("deck create (\\w+)"),
    DELETE_DECK("deck delete (\\w+)"),
    ACTIVATE_DECK("deck set-activate (.*)"),
    ADD_CARD("^deck add-card (?=.*(--(card) ([\\w\\- ]+)))(?=.*(--(deck) ([\\w\\-]+))).*$"),
    REMOVE_CARD("^deck rm-card (?=.*(--(card) ([\\w\\-]+)))(?=.*(--(deck) ([\\w\\-]+))).*$"),
    SHOW_ALL_DECKS("deck show --all"),
    SHOW_DECK("^deck show (?=.*(--(deck-name) ([\\w\\-]+))).*$"),
    SHOW_CARDS("deck show --cards"),
    OPTION("--(\\w+)\\b(\\s+--([\\w\\-]+)|$)"),
    DATA("--([\\w-]+) ([\\w ]+)"),
    LOGOUT("logout"),
    SHOW_SCOREBOARD("show scoreboard"),
    SHOP_BUY("shop buy ([\\w\\s-]+)"),
    START_DUEL("^duel (?=.*(--(second-player) ([\\w\\-]+)))(?=.*(--(rounds) (\\d+))).*$"),
    SHOP_SHOW_ALL("shop show --all"),
    ATTACK_DIRECT("attack direct");


    public final String label;

    Regexes(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

