package Controller.Handlers;

import model.game.Duel;
import org.json.JSONObject;

public class GameHandler implements Handler {
    private Handler nextHandler;
    protected String response;

    public Handler linksWith(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }

    public String handle(JSONObject request, Duel duel) {
        if (nextHandler != null) return nextHandler.handle(request, duel);
        return "end of handlers ...";
    }
}
