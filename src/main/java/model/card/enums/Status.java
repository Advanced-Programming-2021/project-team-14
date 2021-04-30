package model.card.enums;

public enum Status {
    LIMITED("Limited"),
    UNLIMITED("Unlimited");

    public final String label;

    Status(String label) {
        this.label = label;
    }

    public static Status fromValue(String givenName) {
        for (Status direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
