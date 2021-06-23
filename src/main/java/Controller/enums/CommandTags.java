package Controller.enums;

public enum CommandTags {

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
    CHANGE_USERNAME("change-username"),
    SIDE("side"),
    BUY_CARD("shop buy"),
    SHOP_SHOW_ALL("shop show --all"),
    INCREASE_MONEY("increase money"),
    IMPORT("import card"),
    EXPORT("export card");

    public final String label;

    CommandTags(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
