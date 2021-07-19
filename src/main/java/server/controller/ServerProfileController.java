package server.controller;

import model.Database;
import org.json.JSONObject;
import server.ServerResponse;


import Controller.enums.Responses;
import model.User;
import view.enums.CommandTags;


public class ServerProfileController {

    private static ServerResponse response;

    public static void processCommand(JSONObject request, ServerResponse response, User user) {
        ServerProfileController.response = response;
        String commandTag = request.getString("command");
        if (commandTag.equals(view.enums.CommandTags.CHANGE_PASSWORD.getLabel()))
            response.addMessage(changePass(user.getUsername(), request.getString("current"), request.getString("new")));
        else if (commandTag.equals(view.enums.CommandTags.CHANGE_USERNAME.getLabel()))
            response.addMessage(changeUsername(user.getUsername(), request.getString("username")));
        else if (commandTag.equals(view.enums.CommandTags.CHANGE_NICKNAME.getLabel()))
            response.addMessage(changeNickname(user.getUsername(), request.getString("new")));
        else if (commandTag.equals(view.enums.CommandTags.SET_PROFILE_PHOTO.getLabel()))
            response.addMessage(setProfilePhoto(user.getUsername(), request.getString("image")));
        else if (commandTag.equals(CommandTags.REMOVE_PROFILE_PHOTO.getLabel()))
            response.addMessage(deleteProfile(user.getUsername()));
        else if (commandTag.equals(CommandTags.SET_PROFILE_CIRCLE.getLabel()))
            response.addMessage(setCircle(user.getUsername(), request.getString("image")));
        else if (commandTag.equals(CommandTags.GET_PROFILE_PHOTO.getLabel()))
            response.addMessage(getProfileImage(user.getUsername()));

    }

    private static String setCircle(String username, String image) {
        User user = User.getUserByUsername(username);
        user.setImageString(image);
        User.getUserByUsername(username).setHasProfileCircle(true);
        response.success();
        return "profile circle set successfully!";
    }

    private static String deleteProfile(String username) {
        User user = User.getUserByUsername(username);
        user.setImageString(null);
        user.setImageString("");
        User.getUserByUsername(username).setHasProfilePhoto(false);
        response.success();
        return "profile photo deleted successfully!";

    }

    private static String setProfilePhoto(String username, String image) {
        User user = User.getUserByUsername(username);
        user.setImageString(image);
        User.getUserByUsername(username).setHasProfilePhoto(true);
        response.success();
        return "profile photo set successfully!";
    }


    private static String getProfileImage(String username) {
        String s = User.getUserByUsername(username).getImageString();
        if (s != null) {
            response.success();
            return s;
        } else {
            response.error();
            return "there is no profile photo to send";
        }
    }


    private static String changePass(String username, String currentPassword, String newPassword) {
        if (isPasswordValid(username, currentPassword)) {         // if current password valid
            if (!currentPassword.equals(newPassword)) {

                response.success();
                User user = User.getUserByUsername(username);
                user.changePassword(newPassword);
                user.updateDatabase();
                return Responses.CHANGE_PASSWORD_SUCCESSFUL.getLabel();
            }
            response.error();
            return Responses.NOT_NEW_PASSWORD.getLabel();
        }
        response.error();
        return Responses.INVALID_CURRENT_PASSWORD.getLabel();
    }

    private static String changeNickname(String username, String newNickname) {

        if (!doesNicknameExists(newNickname)) {
            response.success();
            User user = User.getUserByUsername(username);
            user.changeNickname(newNickname);

            return Responses.CHANGE_NICKNAME_SUCCESSFUL.getLabel();
        }
        response.error();
        return String.format(Responses.NICKNAME_ALREADY_EXIST.getLabel(), newNickname);
    }

    private static String changeUsername(String username, String newUsername) {
        System.out.println(username + " | " + doesUsernameExists(username));
        if (!doesUsernameExists(newUsername)) {
            response.success();
            User user = User.getUserByUsername(username);
            user.changeUsername(newUsername);
            User.changeUserUsername(username, user);
            Database.deleteFile(username);
            user.updateDatabase();
            response.addToken(newUsername);
            return Responses.CHANGE_USERNAME_SUCCESSFUL.getLabel();
        }
        response.error();
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
