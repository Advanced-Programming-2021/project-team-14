package view.enums;

public enum Regexes {
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
    ADD_CARD("^deck add-card (?=.*(--(card) ([a-zA-Z\\-]+)))(?=.*(--(deck) ([a-zA-Z\\-]+)))(?=.*(--(side))).*$"),
    REMOVE_CARD("^deck rm-card (?=.*(--(card) ([a-zA-Z\\-]+)))(?=.*(--(deck) ([a-zA-Z\\-]+)))(?=.*(--(side))).*$"),
    SHOW_ALL_DECKS("deck show --all"),
    SHOW_DECK("^deck show (?=.*(--(deck-name) ([a-zA-Z\\-]+)))(?=.*(--(side))).*$"),
    SHOW_CARDS("deck show --cards"),
    OPTION("--([a-zA-Z\\-]+)$"),
    DATA("--([a-zA-Z\\-]+) (\\w+)"),
    LOGOUT("logout"),
    SHOW_SCOREBOARD("show scoreboard");

    public final String label;

    Regexes(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

