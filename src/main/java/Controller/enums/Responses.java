package Controller.enums;

public enum Responses {
    LOGIN_SUCCESSFUL("user logged in successfully!"),
    USER_DELETE_SUCCESSFUL("user deleted successfully!"),
    WRONG_PASSWORD("Username and password did not match!"),
    REGISTER_SUCCESSFUL("user created successfully!"),
    CREATE_DECK_SUCCESSFUL("deck created successfully!"),
    DELETE_DECK_SUCCESSFUL("deck deleted successfully!"),
    ACTIVATE_DECK_SUCCESSFUL("deck activated successfully!"),
    ADD_CARD_SUCCESSFUL("card added to deck successfully!"),
    REMOVE_CARD_SUCCESSFUL("card removed form deck successfully!"),
    CHANGE_NICKNAME_SUCCESSFUL("nickname changed successfully!"),
    CHANGE_USERNAME_SUCCESSFUL("username changed successfully!"),
    CHANGE_PASSWORD_SUCCESSFUL("password changed successfully!"),
    INVALID_CURRENT_PASSWORD("current password is invalid"),
    NOT_NEW_PASSWORD("please enter a new password"),
    SUCCESS("Successful"),
    ERROR("Error"),
    CHOICE("Choice"),
    DECK_NOT_EXIST("deck with name %s does not exist"),
    DECK_ALREADY_EXIST("deck with name %s already exists"),
    CARD_NOT_EXIST("card with name %s does not exist"),
    CARD_NOT_AVAILABLE("This card is not available"),
    CARD_NOT_EXIST_IN_DECK("card with name %s does not exist in %s deck"),
    CARD_ALREADY_EXIST("card with name %s already exists"),
    ENOUGH_CARDS("there are already three cards with name %s in deck %s"),
    NICKNAME_ALREADY_EXIST("user with nickname %s already exists"),
    USERNAME_ALREADY_EXIST("user with username %s already exists"),
    USERNAME_NOT_EXISTS("username not exists"),
    SIDE_DECK_IS_FULL("side deck is full"),
    MAIN_DECK_IS_FULL("main deck is full"),
    THERE_IS_NO_CARD_WITH_THIS_NAME("there is no card with this name"),
    NOT_ENOUGH_MONEY("not enough money"),
    CARD_BOUGHT_SUCCESSFULLY("card added to your wallet successfully!"),
    INCREASE_MONEY("cash increased successfully!"),
    INCREASE_LIFE_POINT("life point increased successfully!");


    public final String label;

    Responses(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
