package Controller;

import model.User;
import org.json.JSONObject;

public class RegistrationController {


    public static String processCommand(JSONObject request) {


        String response = null;
        String commandTag = request.getString("command");

        if (commandTag.equals("login")) {
            response = login(request.getString("username"),
                    request.getString("password"));
        } else if (commandTag.equals("register")) {
            response = createNewUser(request.getString("username"), request.getString("nickname"),
                    request.getString("password"));
        }

        return response;
    }


    private static String login(String username, String password) {

        if (doesUsernameExists(username)) {
            if (isPasswordCorrects(username, password)) {
                return "user logged in successfully!";

            } else {
                return "Username and password didn’t match!";
            }
        } else {
            return "Username and password didn’t match!";
        }
    }


    private static String createNewUser(String username, String nickname, String password) {


        if (!doesUsernameExists(username)) {
            if (!nicknameExists(nickname)) {
                User user = new User(username, password, nickname);             // add new user
                return "user created successfully!";
            } else {
                return "user with nickname " + nickname + " already exists";
            }
        } else {
            return "user with username " + username + " already exists";
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