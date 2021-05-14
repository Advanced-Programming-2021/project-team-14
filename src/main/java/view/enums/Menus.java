package view.enums;

public enum Menus {
    REGISTER_MENU("Register Menu"),
    PROFILE_MENU("Profile Menu"),
    MAIN_MENU("Main Menu"),
    DECK_MENU("Deck Menu"),
    SHOP_MENU("Shop Menu"),
    DUEL_MENU("Duel Menu"),
    GAMEPLAY_MENU("GamePlay Menu"),
    SCOREBOARD_MENU("Scoreboard Menu");
    public final String label;

    Menus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
