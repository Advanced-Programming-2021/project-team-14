package model;

import Controller.enums.Responses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

class UserTest {

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
    public void some() {
        Request.setCommandTag(CommandTags.CHANGE_NICKNAME);
        Request.extractData("profile change --nickname newNickname");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.CHANGE_NICKNAME_SUCCESSFUL.getLabel());
    }
}