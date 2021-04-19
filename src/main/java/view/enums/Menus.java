package view.enums;

public enum Menus {
    REGISTER_MENU("Register Menu"),
    PROFILE_MENU("Register Menu"),
    MAIN_MENU("Main menu");
    public final String label;

    Menus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
