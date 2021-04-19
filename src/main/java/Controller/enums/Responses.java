package Controller.enums;

public enum Responses {
    LOGIN_SUCCESSFUL("user logged in successfully!"),
    WRONG_PASSWORD("Username and password did not match!"),
    REGISTER_SUCCESSFUL("user created successfully!"),
    SUCCESS("Successful"),
    ERROR("Error");


    public final String label;

    Responses(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
