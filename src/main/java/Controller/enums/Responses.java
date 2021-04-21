package Controller.enums;

public enum Responses {
    LOGIN_SUCCESSFUL("user logged in successfully!"),
    WRONG_PASSWORD("Username and password did not match!"),
    REGISTER_SUCCESSFUL("user created successfully!"),
    CHANGE_NICKNAME_SUCCESSFUL("nickname changed successfully!"),
    CHANGE_PASSWORD_SUCCESSFUL("password changed successfully!"),
    INVALID_CURRENT_PASSWORD("current password is invalid"),
    NOT_NEW_PASSWORD("please enter a new password"),
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
