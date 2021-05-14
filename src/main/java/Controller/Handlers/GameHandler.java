package Controller.Handlers;

import model.game.Game;
import org.json.JSONObject;

public class GameHandler implements Handler{
    private Handler nextHandler;
    protected String response;
    public Handler linksWith(Handler handler){
        this.nextHandler = handler;
        return handler;
    }

    public String handle(JSONObject request, Game game){
        if (nextHandler != null) return nextHandler.handle(request, game);
        return "end of handlers ...";
    }
}
