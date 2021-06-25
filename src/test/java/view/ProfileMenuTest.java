package view;

import Controller.Response;
import Controller.enums.Responses;
import model.Database;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.enums.CommandTags;
import view.enums.Menus;

import javax.xml.crypto.Data;


class ProfileMenuTest {

    private String PROFILE_USERNAME = "profileUsername";
    private String PROFILE_NICKNAME = "profileNickname";
    private String PROFILE_PASSWORD = "profilePassword";
    private String PASSWORD_FIELD = "password";
    @BeforeEach
    public void createUserForTests() {
        Request.addData("username", PROFILE_USERNAME);
        Request.addData(PASSWORD_FIELD, PROFILE_PASSWORD);
        Request.addData("nickname", PROFILE_NICKNAME);
        sendRequest(CommandTags.REGISTER, Menus.REGISTER_MENU);
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.addData("username", PROFILE_USERNAME);
        Request.addData(PASSWORD_FIELD, PROFILE_PASSWORD);
        sendRequest(CommandTags.LOGIN, Menus.REGISTER_MENU);
        Request.getToken();
    }

    private String sendRequest(CommandTags commandTag, Menus menu) {
        Request.addData("view", menu.getLabel());
        Request.setCommandTag(commandTag);
        Request.send();
        return Request.getMessage();
    }

    @Test
    public void checkChangedPassword() {
        Request.addData("current", PROFILE_PASSWORD);
        Request.addData("new", "123456");
        sendRequest(CommandTags.CHANGE_PASSWORD, Menus.PROFILE_MENU);
        Assertions.assertTrue(User.isPasswordCorrect(PROFILE_USERNAME, "123456"));
        Assertions.assertFalse(User.isPasswordCorrect(PROFILE_USERNAME, PROFILE_PASSWORD)); // set the password back
        Request.addData("current", "123456");
        Request.addData("new", PROFILE_PASSWORD);
        sendRequest(CommandTags.CHANGE_PASSWORD, Menus.PROFILE_MENU);
    }
    @Test
    public void changePasswordToTheCurrentOne() {
        Request.addData("current", PROFILE_PASSWORD);
        Request.addData("new", PROFILE_PASSWORD);
        String response = sendRequest(CommandTags.CHANGE_PASSWORD, Menus.PROFILE_MENU);
        Assertions.assertEquals(Responses.NOT_NEW_PASSWORD.getLabel(), response);
    }
    @Test
    public void changePasswordWhileTheCurrentIsWrong() {
        Request.addData("current", PROFILE_PASSWORD + ":)");
        Request.addData("new", PROFILE_PASSWORD);
        String response = sendRequest(CommandTags.CHANGE_PASSWORD, Menus.PROFILE_MENU);
        Assertions.assertEquals(Responses.INVALID_CURRENT_PASSWORD.getLabel(), response);
    }
    
    @Test
    public void changeNicknameToAnExistingOne() {
        Request.addData("nickname", PROFILE_NICKNAME);
        String response = sendRequest(CommandTags.CHANGE_NICKNAME, Menus.PROFILE_MENU);
        Assertions.assertEquals(String.format(Responses.NICKNAME_ALREADY_EXIST.getLabel(), PROFILE_NICKNAME), response);
    }
    @Test
    public void changeNicknameToNotExistingOne() {
        Request.addData("nickname", "newNickname");
        String response = sendRequest(CommandTags.CHANGE_NICKNAME, Menus.PROFILE_MENU);
        Assertions.assertEquals(Responses.CHANGE_NICKNAME_SUCCESSFUL.getLabel(), response);
        Assertions.assertTrue(User.doesNicknameExist("newNickname"));
        Assertions.assertFalse(User.doesNicknameExist("nickname"));
        Request.addData("nickname", PROFILE_NICKNAME);
        sendRequest(CommandTags.CHANGE_NICKNAME, Menus.PROFILE_MENU);
    }
    @Test
    public void changeUsernameToAnExistingOne() {
        Request.addData("username", PROFILE_USERNAME);
        String response = sendRequest(CommandTags.CHANGE_USERNAME, Menus.PROFILE_MENU);
        Assertions.assertEquals(String.format(Responses.USERNAME_ALREADY_EXIST.getLabel(), PROFILE_USERNAME), response);
    }
    @Test
    public void changeUsernameToNotExistingOne() {
        Request.addData("username", "usernameNewOne");
        String response = sendRequest(CommandTags.CHANGE_USERNAME, Menus.PROFILE_MENU);
        Assertions.assertEquals(Responses.CHANGE_USERNAME_SUCCESSFUL.getLabel(), response);
        Request.addData("username", PROFILE_USERNAME);
        Request.getToken();
        System.out.println(sendRequest(CommandTags.CHANGE_USERNAME, Menus.PROFILE_MENU));
    }
}