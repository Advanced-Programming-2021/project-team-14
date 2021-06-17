package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.Database;
import model.User;
import org.json.JSONObject;

public class ProfileController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.CHANGE_PASSWORD.getLabel()))
            Response.addMessage(changePass(request.getString("token"), request.getString("current"), request.getString("new")));
        else if (commandTag.equals(CommandTags.CHANGE_USERNAME.getLabel()))
            Response.addMessage(changeUsername(request.getString("token"), request.getString("username")));
        else if (commandTag.equals(CommandTags.CHANGE_NICKNAME.getLabel()))
            Response.addMessage(changeNickname(request.getString("token"), request.getString("nickname")));

    }


    private static String changePass(String username, String currentPassword, String newPassword) {

        if (doesUsernameExists(username)) {
            if (isPasswordValid(username, currentPassword)) {         // if current password valid
                if (!currentPassword.equals(newPassword)) {

                    Response.success();
                    User user = User.getUserByUsername(username);
                    user.changePassword(newPassword);
                    user.updateDatabase();
                    return Responses.CHANGE_PASSWORD_SUCCESSFUL.getLabel();

                } else {
                    Response.error();
                    return Responses.NOT_NEW_PASSWORD.getLabel();
                }
            }
            Response.error();
            return Responses.INVALID_CURRENT_PASSWORD.getLabel();
        } else {
            Response.error();
            return Responses.USERNAME_NOT_EXISTS.getLabel();
        }
    }



    private static String changeNickname(String username, String newNickname) {

        if (!doesNicknameExists(newNickname)) {
            Response.success();
            User user = User.getUserByUsername(username);
            user.changeNickname(newNickname);
            user.updateDatabase();
            return Responses.CHANGE_NICKNAME_SUCCESSFUL.getLabel();
        }
        Response.error();
        return String.format(Responses.NICKNAME_ALREADY_EXIST.getLabel(), newNickname);
    }

    private static String changeUsername(String username, String newUsername) {

        if (!doesUsernameExists(newUsername)) {
            Response.success();
            User user = User.getUserByUsername(username);
            user.changeUsername(newUsername);
            Database.deleteFile(username);
            user.updateDatabase();
            return Responses.CHANGE_USERNAME_SUCCESSFUL.getLabel();
        }
        Response.error();
        return String.format(Responses.USERNAME_ALREADY_EXIST.getLabel(), newUsername);
    }

    private static boolean isPasswordValid(String username, String password) {
        return User.isPasswordCorrect(username, password);
    }

    private static boolean doesNicknameExists(String nickname) {
        return User.doesNicknameExist(nickname);
    }

    private static boolean doesUsernameExists(String username) {
        return User.doesUsernameExist(username);
    }
}
