package model;

public enum Strings {

    SIDE("side"),
    MAIN_DECK("main deck"),
    DECK("deck"),
    CARD("card"),
    OPTION("option"),
    TOKEN("token"),
    SIDE_DECK("side"), VALID("valid"), INVALID("invalid");

    public final String label;

    Strings(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
