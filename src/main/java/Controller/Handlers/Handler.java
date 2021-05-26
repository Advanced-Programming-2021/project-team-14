package Controller.Handlers;

import model.game.Duel;
import org.json.JSONObject;

public interface Handler {
    Handler linksWith(Handler next);

    String handle(JSONObject request, Duel duel);
}
