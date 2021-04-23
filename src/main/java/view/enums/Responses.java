package view.enums;

public enum Responses {
    MENU_ENTER_NOT_ALLOWED("please login first"),
    INVALID_COMMAND("invalid command"),
    SUCCESS("Successful"),
    NO_MORE_MENU("no more menu available"),
    IMPOSSIBLE_MENU_NAVIGATION("menu navigation is not possible");

    public final String label;

    Responses(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
