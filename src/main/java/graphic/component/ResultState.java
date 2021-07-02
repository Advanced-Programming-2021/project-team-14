package graphic.component;

public enum ResultState {
    ERROR("error-snackbar"),
    SUCCESS("successful-snackbar");
    private String styleClass;

    ResultState(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
