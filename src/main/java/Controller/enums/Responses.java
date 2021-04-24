package Controller.enums;

public enum Responses {
    LOGIN_SUCCESSFUL("user logged in successfully!"),
    WRONG_PASSWORD("Username and password did not match!"),
    REGISTER_SUCCESSFUL("user created successfully!"),
    CREATE_DECK_SUCCESSFUL("deck created successfully!"),
    DELETE_DECK_SUCCESSFUL("deck deleted successfully!"),
    ACTIVATE_DECK_SUCCESSFUL("deck activated successfully!"),
    ADD_CARD_SUCCESSFUL("card added to deck successfully!"),
    REMOVE_CARD_SUCCESSFUL("card removed form deck successfully!"),
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
