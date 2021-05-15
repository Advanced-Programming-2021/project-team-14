package Controller.Handlers;

import model.game.Game;
import org.json.JSONObject;

public class SelectedCardHandler extends GameHandler {
    public String handle(JSONObject request, Game game){
        if (game.getSelectedCard() == null) return "no card is selected yet";
        return super.handle(request, game);
    }
}
