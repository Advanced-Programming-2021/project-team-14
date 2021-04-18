package view.enums;

public enum CommandTags {
    LOGIN("login"),
    REGISTER("register");
    public final String label;

    CommandTags(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
