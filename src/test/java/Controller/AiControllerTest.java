package Controller;

import model.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class AiControllerTest {

    @Test
    public void startDuelAi() {
        login("main");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL_AI.getLabel());
        Request.addData("rounds", "1");
        Request.send();

        Assertions.assertEquals("duel started between main and AI", Request.getMessage());
    }


    private void login(String username) {
        Database.prepareDatabase();
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.LOGIN);
        Request.addData("username", username);
        Request.addData("password", "123");
        Request.send();

        if (Request.isSuccessful())
            Request.getToken();
    }
}
