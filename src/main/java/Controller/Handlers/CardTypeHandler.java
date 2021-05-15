package Controller.Handlers;

import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;

public class CardTypeHandler extends GameHandler {

    private String type;

    public CardTypeHandler(String type, JSONObject request, Game game) {

        this.type = type;
        handle(request, game);
    }

    public String handle(JSONObject request, Game game) {
        Logger.log("card type handler", "checking ...");

        String command = request.getString("command");

        if (!game.getSelectedCard().getCard().getCardType().getLabel().equals(type)) {

            response = Strings.CARD_NOT_EXIST_IN_HAND_SUMMON.getLabel();
            return response;
        }

        return super.handle(request, game);
    }
}
