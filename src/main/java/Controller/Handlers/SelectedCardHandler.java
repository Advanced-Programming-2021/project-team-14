package Controller.Handlers;

import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;

public class SelectedCardHandler extends GameHandler {
    public String handle(JSONObject request, Duel duel) {
        Logger.log("selected card handler", "checking...");
        Game game = duel.getGame();
        if (game.getSelectedCard() == null) return "no card is selected yet";

        return super.handle(request, duel);
    }
}
