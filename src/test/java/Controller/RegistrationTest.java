package Controller;

import Controller.enums.Responses;
import model.User;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class RegistrationTest {


    @BeforeEach
    @Test
    public void createUserForTests() {
        sendRequest(CommandTags.REGISTER,
                "username", "Password1234", "nickname");
    }

    @Test
    public void createUserSuccessfully() {
        String response = sendRequest(CommandTags.REGISTER,
                "usernameCreate", "1234", "nicknameCreate");
        Assertions.assertEquals(Responses.REGISTER_SUCCESSFUL.getLabel(), response);
    }

    @Test
    public void checkCreatedUserExistenceByUsername() {
        Assertions.assertTrue(User.doesUsernameExist("username"));
    }

    @Test
    public void checkCreatedUserExistenceByNickname() {
        Assertions.assertTrue(User.doesNicknameExist("nickname"));
    }

    @Test
    public void checkCreatedUserPassword() {
        Assertions.assertTrue(User.isPasswordCorrect("username", "Password1234"));
    }

    @Test
    public void createUserForSecondTime() {
        String response = sendRequest(CommandTags.REGISTER,
                "username", "Password1234", "nickname");
        Assertions.assertEquals(Responses.USERNAME_ALREADY_EXIST.getLabel().replace("%s", "username"), response);
    }

    @Test
    public void createUserWithSameNickname() {
        String response = sendRequest(CommandTags.REGISTER,
                "username2", "Password1234", "nickname");
        Assertions.assertEquals(Responses.NICKNAME_ALREADY_EXIST.getLabel().replace("%s", "nickname"), response);
    }

    @Test
    public void loginSuccessful() {
        String response = sendRequest(CommandTags.LOGIN,
                "username", "Password1234", "nickname");
        Assertions.assertEquals(Responses.LOGIN_SUCCESSFUL.getLabel(), response);
    }

    @Test
    public void loginWithWrongPassword() {
        String response = sendRequest(CommandTags.LOGIN,
                "username", "Password123456", "nickname");
        Assertions.assertEquals(Responses.WRONG_PASSWORD.getLabel(), response);
    }

    @Test
    public void loginWithWrongUsername() {
        String response = sendRequest(CommandTags.LOGIN,
                "username2", "Password1234", "nickname");
        Assertions.assertEquals(Responses.WRONG_PASSWORD.getLabel(), response);
    }


    public static String sendRequest(CommandTags commandTag, String username, String password, String nickname) {
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(commandTag);
        Request.addData("username", username);
        Request.addData("password", password);
        Request.addData("nickname", nickname);
        Request.send();
        return Request.getMessage();
    }



}
