package server.controller.Handlers;

import model.Strings;
import model.card.enums.CardType;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import server.Server;
import server.ServerResponse;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class CardTypeHandler extends GameHandler {


    public String handle(JSONObject request, Duel duel, ServerResponse response) {
        Game game = duel.getGame();
        Logger.log("card type handler", "checking ...");

        String command = request.getString("command");
        switch (Objects.requireNonNull(CommandTags.fromValue(command))) {
            case SUMMON:
                if (game.getSelectedCard().getCard().getCardType() != CardType.MONSTER)
                    return Strings.CANNOT_SUMMON_THIS_CARD.getLabel();
                break;
            case ACTIVATE_EFFECT:
                if (game.getSelectedCard().getCard().getCardType() == CardType.MONSTER) {
                    return Strings.ACTIVATION_IS_ONLY_FOR_SPELLS.getLabel();
                }
                break;
        }


        return super.handle(request, duel, response);
    }
}
