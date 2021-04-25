package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.User;
import org.json.JSONObject;

public class ProfileController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.CHANGE_PASSWORD.getLabel()))
            Response.addMessage(changePass(request.getString("token"), request.getString("current"), request.getString("new")));
        else if (commandTag.equals(CommandTags.CHANGE_NICKNAME.getLabel()))
            Response.addMessage(changeNickname(request.getString("token"), request.getString("nickname")));

    }


    private static String changePass(String username, String currentPassword, String newPassword) {

        if (isPasswordValid(username, currentPassword)) {         // if current password valid
            if (!currentPassword.equals(newPassword)) {

                Response.success();
                User.getUserByName(username).changePassword(newPassword);
                return Responses.CHANGE_PASSWORD_SUCCESSFUL.getLabel();

            } else {
                Response.error();
                return Responses.NOT_NEW_PASSWORD.getLabel();
            }
        }
        Response.error();
        return Responses.INVALID_CURRENT_PASSWORD.getLabel();
    }


    private static String changeNickname(String username, String newNickname) {

        if (!doesNicknameExists(newNickname)) {
            Response.success();
            User.getUserByName(username).changeNickname(newNickname);
            return Responses.CHANGE_NICKNAME_SUCCESSFUL.getLabel();
        }
        Response.error();
        return String.format(Responses.NICKNAME_ALREADY_EXIST.getLabel(), newNickname);
    }

    private static boolean isPasswordValid(String username, String password) {
        return User.isPasswordCorrect(username, password);
    }

    private static boolean doesNicknameExists(String nickname) {
        return User.doesNicknameExist(nickname);
    }
}
