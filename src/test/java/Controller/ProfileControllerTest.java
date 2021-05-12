package Controller;

import Controller.enums.Responses;
import model.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

class ProfileControllerTest {

    @BeforeEach
    public void initialize() {

        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.REGISTER);
        Request.extractData("user create --username username --password password --nickname nickname");
        Request.send();
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.LOGIN);
        Request.extractData("user login --username username --password password");
        Request.send();
        if (Request.isSuccessful()) {
            Request.getToken();
        }
        Request.addData("view", Menus.PROFILE_MENU.getLabel());
        Database.prepareDatabase();
    }

    @Test
    public void changeNicknameTest() {
        Request.setCommandTag(CommandTags.CHANGE_NICKNAME);
        Request.extractData("profile change --nickname newNickname");
        Request.send();

        Request.addData("view", Menus.PROFILE_MENU.getLabel());

        Request.setCommandTag(CommandTags.CHANGE_NICKNAME);
        Request.extractData("profile change --nickname nickname");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.CHANGE_NICKNAME_SUCCESSFUL.getLabel());
    }


    @Test
    public void changeNicknameWithExistingOneTest() {
        Request.setCommandTag(CommandTags.CHANGE_NICKNAME);
        Request.extractData("profile change --nickname nickname");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.NICKNAME_ALREADY_EXIST.getLabel(), "nickname"));
    }


    @Test
    public void changePasswordTest() {
        Request.setCommandTag(CommandTags.CHANGE_PASSWORD);
        Request.extractData("profile change --password --current password --new newPassword");
        Request.send();

        Request.addData("view", Menus.PROFILE_MENU.getLabel());
        Request.setCommandTag(CommandTags.CHANGE_PASSWORD);
        Request.extractData("profile change --password --current newPassword --new password");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.CHANGE_PASSWORD_SUCCESSFUL.getLabel());
    }


    @Test
    public void changePasswordWithExistingOneTest() {
        Request.setCommandTag(CommandTags.CHANGE_PASSWORD);
        Request.extractData("profile change --password --current password --new password");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.NOT_NEW_PASSWORD.getLabel());
    }

    @Test
    public void changePasswordWithIncorrectCurrentOneTest() {
        Request.setCommandTag(CommandTags.CHANGE_PASSWORD);
        Request.extractData("profile change --password --current incorrectPassword --new newPassword");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.INVALID_CURRENT_PASSWORD.getLabel());
    }

}