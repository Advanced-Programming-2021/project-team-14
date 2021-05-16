package Controller.Handlers;

import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

public class CardTypeHandler extends GameHandler {


    public String handle(JSONObject request, Game game) {
        Logger.log("card type handler", "checking ...");

        String command = request.getString("command");

        if (command.equals(CommandTags.SUMMON.getLabel())) {

            if (game.getSelectedCard() != null) {
                if (!game.getSelectedCard().getCard().getCardType().getLabel().equals("Monster")) {

                    response = Strings.CARD_NOT_EXIST_IN_HAND_SUMMON.getLabel();
                    return response;
                }
            }
        }

        return super.handle(request, game);
    }
}
