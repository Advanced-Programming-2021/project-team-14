package server.controller.Handlers;

import model.card.SelectedCard;
import model.game.Duel;
import model.game.Game;
import model.game.Player;
import org.json.JSONObject;
import server.ServerResponse;

public class GameHandler implements Handler {
    private Handler nextHandler;
    protected String responseString;
    protected Player main, rival;
    protected JSONObject request;
    protected Game game;
    protected Duel duel;
    protected SelectedCard selected;

    protected void set(JSONObject requestJson, Duel duelClass){
        game = duelClass.getGame();
        duel = duelClass;
        main = game.getBoard().getMainPlayer();
        rival = game.getBoard().getRivalPlayer();
        request = requestJson;
        selected = game.getSelectedCard();
    }

    public Handler linksWith(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }

    public String handle(JSONObject request, Duel duel, ServerResponse response) {
        if (nextHandler != null) return nextHandler.handle(request, duel, response);
        return "end of handlers ...";
    }
}
