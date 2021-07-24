package server.controller.Handlers;

import model.Strings;
import model.card.enums.Position;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import server.ServerResponse;
import view.Logger;
import view.enums.CommandTags;

public class CardPositionHandler extends GameHandler {
    public String handle(JSONObject request, Duel duel, ServerResponse response) {
        Game game = duel.getGame();
        Logger.log("card position handler", "checking ...");

        String command = request.getString("command");

        Position expectedPosition = null;
        if (command.equals(CommandTags.SET.getLabel())) {
            expectedPosition = Position.HAND;
            responseString = Strings.CARD_NOT_EXIST_IN_HAND_SET.getLabel();
        } else if (command.equals(CommandTags.SUMMON.getLabel())) {
            expectedPosition = Position.HAND;
            responseString = Strings.CARD_NOT_EXIST_IN_HAND_SUMMON.getLabel();
        } else if (command.equals(CommandTags.SET_POSITION.getLabel()) || command.equals(CommandTags.FLIP_SUMMON.getLabel())) {
            expectedPosition = Position.MONSTER_ZONE;
            responseString = Strings.CARD_NOT_EXIST_IN_MONSTER_ZONE.getLabel();
        } else if (command.startsWith(CommandTags.ATTACK.getLabel())) {
            expectedPosition = Position.MONSTER_ZONE;
            responseString = Strings.CANNOT_ATTACK_WITH_THIS_CARD.getLabel();
        }


        if (!game.getSelectedCard().getCard().getPosition().equals(expectedPosition))
            return responseString;
        return super.handle(request, duel, response);
    }
}
