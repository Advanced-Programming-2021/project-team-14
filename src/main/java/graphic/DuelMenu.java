package graphic;

import javafx.scene.input.MouseEvent;
import sample.MainGraphic;
import view.Console;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class DuelMenu extends Menu {
    public void startGame(MouseEvent mouseEvent) {
        setView(Menus.DUEL_MENU);
        Request.setCommandTag(CommandTags.START_DUEL_AI);
        Request.addData("rounds", "1");
        Request.send();
        if (Request.isSuccessful()) {
            Console.print(Request.getMessage());
            MainGraphic.setRoot("TossCoin");
        } else
            Console.print(Request.getMessage());
    }
}