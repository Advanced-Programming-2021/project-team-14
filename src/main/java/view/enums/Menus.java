package view.enums;

public enum Menus {
    REGISTER_MENU("Register Menu"),
    PROFILE_MENU("Profile Menu"),
    MAIN_MENU("Main Menu"),
    SCOREBOARD_MENU("Scoreboard Menu");
    public final String label;

    Menus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
