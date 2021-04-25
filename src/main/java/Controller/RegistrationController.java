package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.User;
import org.json.JSONObject;

public class RegistrationController {


    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.LOGIN.getLabel()))
            Response.addMessage(login(request.getString("username"), request.getString("password")));
        else if (commandTag.equals(CommandTags.REGISTER.getLabel()))
            Response.addMessage(register(request.getString("username"), request.getString("password"), request.getString("nickname")));

    }

    private static String login(String username, String password) {

        if (doesUsernameExists(username))
            if (isPasswordCorrects(username, password)) {
                Response.success();
                Response.addToken(username);
                return Responses.LOGIN_SUCCESSFUL.getLabel();
            } else {
                Response.error();
                return Responses.WRONG_PASSWORD.getLabel();
            }
        else {
            Response.error();
            return Responses.WRONG_PASSWORD.getLabel();
        }
    }


    private static String register(String username, String password, String nickname) {

        if (!doesUsernameExists(username)) {
            if (!nicknameExists(nickname)) {
                Response.success();
                new User(username, password, nickname); // add new user
                return Responses.REGISTER_SUCCESSFUL.getLabel();
            } else {
                Response.error();
                return String.format(Responses.NICKNAME_ALREADY_EXIST.getLabel(), nickname);
            }
        } else {
            Response.error();
            return String.format(Responses.USERNAME_ALREADY_EXIST.getLabel(), username);
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