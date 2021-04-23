package view.enums;

public enum Regexes {
    MENU_EXIT("menu exit"),
    MENU_CURRENT("show current menu"),
    MENU_ENTER("menu enter (.*)"),
    CREATE_USER("^user create (?=.*(--(username) ([a-zA-Z\\-]+)))(?=.*(--(password) ([a-zA-Z\\-]+)))(?=.*(--(nickname) ([a-zA-Z\\-]+))).*$"),
    LOGIN_USER("^user login (?=.*(--(username) ([a-zA-Z\\-]+)))(?=.*(--(password) ([a-zA-Z\\-]+))).*$"),
    CHANGE_PROFILE_NICKNAME("^profile change (?=.*(--(nickname) (\\w+))).*$"),
    CHANGE_PROFILE_PASSWORD("^profile change --password (?=.*(--(current) ([a-zA-Z\\-]+)))(?=.*(--(new) ([a-zA-Z\\-]+))).*$"),
    CREATE_DECK("deck create (.*)"),
    DELETE_DECK("deck delete (.*)"),
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

