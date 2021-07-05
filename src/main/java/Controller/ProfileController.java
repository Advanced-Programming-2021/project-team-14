package Controller;

import Controller.enums.DatabaseResponses;
import Controller.enums.Responses;
import model.Database;
import model.User;
import org.json.JSONObject;
import view.enums.CommandTags;

public class ProfileController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.CHANGE_PASSWORD.getLabel()))
            Response.addMessage(changePass(request.getString("token"), request.getString("current"), request.getString("new")));
        else if (commandTag.equals(CommandTags.CHANGE_USERNAME.getLabel()))
            Response.addMessage(changeUsername(request.getString("token"), request.getString("username")));
        else if (commandTag.equals(CommandTags.CHANGE_NICKNAME.getLabel()))
            Response.addMessage(changeNickname(request.getString("token"), request.getString("nickname")));
        else if (commandTag.equals(CommandTags.SET_PROFILE_PHOTO.getLabel()))
            Response.addMessage(setProfile(request.getString("token"), request.getString("path")));
        else if (commandTag.equals(CommandTags.REMOVE_PROFILE_PHOTO.getLabel()))
            Response.addMessage(deleteProfile(request.getString("token")));

    }

    private static String deleteProfile(String username) {
        DatabaseResponses responses = Database.removeProfilePhoto(username);

        if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
            User.getUserByUsername(username).setHasProfilePhoto(false);
            Response.success();
            return "profile photo deleted successfully!";
        }
        Response.error();
        return "please try later";
    }

    private static String setProfile(String username, String path) {
        DatabaseResponses responses = Database.saveProfilePhoto(path, username);

        if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
            User.getUserByUsername(username).setHasProfilePhoto(true);
            Response.success();
            return "profile photo set successfully!";
        }
        Response.error();
        return "please try later";
    }


    private static String changePass(String username, String currentPassword, String newPassword) {
        if (isPasswordValid(username, currentPassword)) {         // if current password valid
            if (!currentPassword.equals(newPassword)) {

                Response.success();
                User user = User.getUserByUsername(username);
                user.changePassword(newPassword);
                user.updateDatabase();
                return Responses.CHANGE_PASSWORD_SUCCESSFUL.getLabel();
            }
            Response.error();
            return Responses.NOT_NEW_PASSWORD.getLabel();
        }
        Response.error();
        return Responses.INVALID_CURRENT_PASSWORD.getLabel();
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
        System.out.println(username + " | " + doesUsernameExists(username));
        if (!doesUsernameExists(newUsername)) {
            Response.success();
            User user = User.getUserByUsername(username);
            user.changeUsername(newUsername);
            User.changeUserUsername(username, user);
            Database.deleteFile(username);
            user.updateDatabase();
            Response.addToken(newUsername);
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
