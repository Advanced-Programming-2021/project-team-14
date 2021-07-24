package server.controller.Handlers;

import model.game.Duel;
import org.json.JSONObject;
import server.ServerResponse;

public interface Handler {
    Handler linksWith(Handler next);

    String handle(JSONObject request, Duel duel, ServerResponse response);
}
