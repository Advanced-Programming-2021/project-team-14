package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.User;
import org.json.JSONObject;

public class ProfileController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.CHANGE_PASSWORD.getLabel()))
            changePass(request.getString("username"), request.getString("current"), request.getString("new"));
        else if (commandTag.equals(CommandTags.CHANGE_NICKNAME.getLabel()))
            changeNickname(request.getString("username"), request.getString("nickname"));

    }


    private static void changePass(String username, String currentPassword, String newPassword) {

        if (isPasswordValid(username, currentPassword)) {         // if current password valid
            if (!currentPassword.equals(newPassword)) {

                Response.success();
                Response.addMessage(Responses.CHANGE_PASSWORD_SUCCESSFUL.getLabel());
                User.getUserByName(username).changePassword(newPassword);

            } else {
                Response.error();
                Response.addMessage(Responses.NOT_NEW_PASSWORD.getLabel());
            }
        } else {
            Response.error();
            Response.addMessage(Responses.INVALID_CURRENT_PASSWORD.getLabel());
        }
    }


    private static void changeNickname(String username, String newNickname) {

        if (!doesNicknameExists(newNickname)) {

            Response.success();
            Response.addMessage(Responses.CHANGE_NICKNAME_SUCCESSFUL.getLabel());
            User.getUserByName(username).changeNickname(newNickname);

        } else {
            Response.error();
            Response.addMessage("user with nickname " + newNickname + " already exists");
        }
    }

    private static boolean isPasswordValid(String username, String password) {
        return User.isPasswordCorrect(username, password);
    }

    private static boolean doesNicknameExists(String nickname) {
        return User.doesNicknameExist(nickname);
    }
}
