package Controller.Handlers;

import model.Strings;
import model.game.Game;
import model.game.Player;
import model.game.Zone;
import org.json.JSONObject;

public class ShowCardHandler extends GameHandler{
    public String handle(JSONObject request, Game game) {
        return game.getSelectedCard().getCard().show();
    }
}
