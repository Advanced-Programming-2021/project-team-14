package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.User;
import org.json.JSONObject;

public class RegistrationController {


    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.LOGIN.getLabel()))
            login(request.getString("username"), request.getString("password"));
        else if (commandTag.equals(CommandTags.REGISTER.getLabel()))
            register(request.getString("username"), request.getString("password"), request.getString("nickname"));

    }

    private static void login(String username, String password) {

        if (doesUsernameExists(username))
            if (isPasswordCorrects(username, password)) {
                Response.success();
                Response.addMessage(Responses.LOGIN_SUCCESSFUL.getLabel());
            } else {
                Response.error();
                Response.addMessage(Responses.WRONG_PASSWORD.getLabel());
            }
        else {
            Response.error();
            Response.addMessage(Responses.WRONG_PASSWORD.getLabel());
        }
    }


    private static void register(String username, String password, String nickname) {

        if (!doesUsernameExists(username)) {
            if (!nicknameExists(nickname)) {
                Response.success();
                new User(username, password, nickname); // add new user
                Response.addMessage(Responses.REGISTER_SUCCESSFUL.getLabel());
            } else {
                Response.error();
                Response.addMessage("user with nickname " + nickname + " already exists");
            }
        } else {
            Response.error();
            Response.addMessage("user with username " + username + " already exists");
        }
    }


    private static boolean isPasswordCorrects(String username, String password) {

        return User.isPasswordCorrect(username, password);
    }

    private static boolean doesUsernameExists(String username) {
        return User.doesUsernameExist(username);
    }

    private static boolean nicknameExists(String nickname) {
        return User.doesNicknameExist(nickname);
    }
}