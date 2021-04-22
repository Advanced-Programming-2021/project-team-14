package view.enums;

public enum CommandTags {
    LOGIN("login"),
    REGISTER("register"),
    CHANGE_PASSWORD("change-password"),
    CHANGE_NICKNAME("change-nickname"),
    SHOW_SCOREBOARD("scoreboard show");

    public final String label;

    CommandTags(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
