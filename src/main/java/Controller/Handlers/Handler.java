package Controller.Handlers;

import model.game.Game;
import org.json.JSONObject;

public interface Handler {
    Handler linksWith(Handler next);
    String handle(JSONObject request, Game game);
}
