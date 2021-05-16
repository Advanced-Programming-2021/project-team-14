package Controller.Handlers;

import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;

public class MonsterCardTypeHandler extends GameHandler {


    public String handle(JSONObject request, Game game) {
        Logger.log("monster card type handler", "checking ...");

        String command = request.getString("command");

        if (game.getSelectedCard() != null) {
            if (game.getBoard().getMainPlayer().getPlayingDeck().
                    getMonsterByName(game.getSelectedCard().getCard().getName()).
                    getMonsterCardType().getLabel().equals("Ritual")) {

                response = Strings.CARD_NOT_EXIST_IN_HAND_SUMMON.getLabel();
                return response;
            }
        }

        return super.handle(request, game);
    }
}
