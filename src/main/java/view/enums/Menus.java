package view.enums;

public enum Menus {
    REGISTER_MENU("registerMenu");
    public final String label;

    Menus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
