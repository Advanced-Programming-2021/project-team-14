package view.enums;

public enum Responses {
    MENU_ENTER_NOT_ALLOWED("please login first");


    public final String label;

    Responses(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
