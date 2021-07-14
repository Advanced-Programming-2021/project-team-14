package Controller.enums;

import model.card.enums.Attribute;

import java.util.Locale;

public enum CommandTags {

    LOGIN("login"),
    SHOW_CARD("show card everywhere"),
    REGISTER("register"),
    UPDATE_CHAT("update chat"),
    SEND_MESSAGE("send message"),
    EDIT_MESSAGE("edit message"),
    REMOVE_MESSAGE("remove message"),
    USER_PROFILE_SHOW("show user profile"),

    CREATE_DECK("create-deck"),
    CARD_NOT_FOUND("card not found!"),
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
    EXPORT("export card"), DELETE_USER("delete user");

    public final String label;

    CommandTags(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static CommandTags fromValue(String givenName) {
        for (CommandTags tag : values()){
            if (tag.label.toLowerCase(Locale.ROOT).equals(givenName.toLowerCase(Locale.ROOT)))
                return tag;
        }
        return null;
    }
}
