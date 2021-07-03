package graphic.component;

public enum CardSize {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");

    public final String label;

    CardSize(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
