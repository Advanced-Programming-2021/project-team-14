package graphic.component;

public enum MenuNames {
    DECK("deck"),
    SHOP("shop"),
    PROFILE("profile");

    public final String label;

    MenuNames(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
