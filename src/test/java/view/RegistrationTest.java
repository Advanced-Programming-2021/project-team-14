package Test;

import Controller.enums.Responses;
import model.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class RegistrationTest {

    public JSONObject sendRequest(String menu, CommandTags commandTag, String username, String password, String nickname) {
        Request.addData("view", menu);
        Request.setCommandTag(commandTag);
        Request.addData("username", username);
        Request.addData("password", password);
        Request.addData("nickname", nickname);
        Request.send();
        return new JSONObject(Request.getResponse());
    }

    @Test
    public void createUserSuccessfully() {
        JSONObject response = sendRequest(Menus.REGISTER_MENU.getLabel(), CommandTags.REGISTER,
                "username", "1234", "nickname");
        Assertions.assertEquals(Responses.REGISTER_SUCCESSFUL.getLabel(), response.getString("message"));
    }

    @BeforeEach
    public void createUserForTests() {
        sendRequest(Menus.REGISTER_MENU.getLabel(), CommandTags.REGISTER,
                "username", "Password1234", "nickname");
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
        JSONObject response = sendRequest(Menus.REGISTER_MENU.getLabel(), CommandTags.REGISTER,
                "username", "Password1234", "nickname");
        Assertions.assertEquals(Responses.USERNAME_ALREADY_EXIST.getLabel().replace("%s", "username"), response.getString("message"));
    }

    @Test
    public void createUserWithSameNickname() {
        JSONObject response = sendRequest(Menus.REGISTER_MENU.getLabel(), CommandTags.REGISTER,
                "username2", "Password1234", "nickname");
        Assertions.assertEquals(Responses.NICKNAME_ALREADY_EXIST.getLabel().replace("%s", "nickname"), response.getString("message"));
    }

    @Test
    public void loginSuccessful() {
        JSONObject response = sendRequest(Menus.REGISTER_MENU.getLabel(), CommandTags.LOGIN,
                "username", "Password1234", "nickname");
        Assertions.assertEquals(Responses.LOGIN_SUCCESSFUL.getLabel(), response.getString("message"));
    }

    @Test
    public void loginWithWrongPassword() {
        JSONObject response = sendRequest(Menus.REGISTER_MENU.getLabel(), CommandTags.LOGIN,
                "username", "Password123456", "nickname");
        Assertions.assertEquals(Responses.WRONG_PASSWORD.getLabel(), response.getString("message"));
    }

    @Test
    public void loginWithWrongUsername() {
        JSONObject response = sendRequest(Menus.REGISTER_MENU.getLabel(), CommandTags.LOGIN,
                "username2", "Password1234", "nickname");
        Assertions.assertEquals(Responses.WRONG_PASSWORD.getLabel(), response.getString("message"));
    }


}
