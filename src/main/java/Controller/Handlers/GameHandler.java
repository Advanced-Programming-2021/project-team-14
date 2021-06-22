package Controller.Handlers;

import model.card.SelectedCard;
import model.game.Duel;
import model.game.Game;
import model.game.Player;
import org.json.JSONObject;

public class GameHandler implements Handler {
    private Handler nextHandler;
    protected String response;
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

    public String handle(JSONObject request, Duel duel) {
        if (nextHandler != null) return nextHandler.handle(request, duel);
        return "end of handlers ...";
    }
}
