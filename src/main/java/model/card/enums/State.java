package model.card.enums;

public enum State {
    OCCUPIED("O"),
    EMPTY("E"),
    DEFENSIVE_OCCUPIED("DO"),
    DEFENSIVE_HIDDEN("DH"),
    OFFENSIVE_OCCUPIED("OO"),
    HIDDEN("H");
    public final String label;

    State(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
