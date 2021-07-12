package server.controller;

import Controller.Response;
import Controller.enums.CommandTags;
import Controller.enums.Responses;
import com.google.gson.Gson;
import model.User;
import org.json.JSONObject;
import server.Server;
import server.ServerResponse;
import server.TokenGenerator;

public class ServerRegistrationController {

    private static ServerResponse response;
    public static void processCommand(JSONObject request, ServerResponse response) {
        ServerRegistrationController.response = response;
        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.LOGIN.getLabel()))
            response.addMessage(login(request.getString("username"), request.getString("password")));
        else if (commandTag.equals(CommandTags.REGISTER.getLabel()))
            response.addMessage(register(request.getString("username"), request.getString("password"), request.getString("nickname")));

    }


    private static String login(String username, String password) {

        if (doesUsernameExists(username))
            if (isPasswordCorrects(username, password)) {
                response.success();
                String token = TokenGenerator.generateToken();
                Server.addUser(token, User.getUserByUsername(username));
                response.addToken(token);
                response.add("user", new Gson().toJson(User.getUserByUsername(username)));
                return Responses.LOGIN_SUCCESSFUL.getLabel();
            } else {
                response.error();
                return Responses.WRONG_PASSWORD.getLabel();
            }
        else {
            response.error();
            return Responses.WRONG_PASSWORD.getLabel();
        }
    }


    private static String register(String username, String password, String nickname) {

        if (!doesUsernameExists(username)) {
            if (!nicknameExists(nickname)) {
                response.success();
                new User(username, password, nickname); // add new user
                return Responses.REGISTER_SUCCESSFUL.getLabel();
            } else {
                response.error();
                return String.format(Responses.NICKNAME_ALREADY_EXIST.getLabel(), nickname);
            }
        } else {
            response.error();
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