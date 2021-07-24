package server.controller.Handlers;

import model.Strings;
import model.card.Monster;
import model.card.enums.Property;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import server.ServerResponse;
import view.Logger;

public class MonsterCardTypeHandler extends GameHandler {


    public String handle(JSONObject request, Duel duel, ServerResponse response) {
        Game game = duel.getGame();
        Logger.log("monster card type handler", "checking ...");

        String command = request.getString("command");

        if (game.getSelectedCard() != null) {
            Monster monster = (Monster) game.getSelectedCard().getCard();
            if (monster.getProperty() == Property.RITUAL) {

                responseString = Strings.CARD_NOT_EXIST_IN_HAND_SUMMON.getLabel();
                return responseString;
            }
        }

        return super.handle(request, duel, response);
    }
}
