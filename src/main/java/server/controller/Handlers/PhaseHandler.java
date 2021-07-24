package server.controller.Handlers;

import model.Strings;
import model.game.Duel;
import model.game.Game;
import model.game.Phase;
import org.json.JSONObject;
import server.ServerResponse;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class PhaseHandler extends GameHandler {
    public String handle(JSONObject request, Duel duel, ServerResponse response) {
        Game game = duel.getGame();
        Logger.log("phase handler", "checking ...");

        Phase expectedPhase = Phase.MAIN_PHASE;
        String command = request.getString("command");
        responseString = Strings.CANNOT_TAKE_ACTION_IN_THIS_PHASE.getLabel();
        switch (Objects.requireNonNull(CommandTags.fromValue(command))) {

            case SUMMON:
                responseString = Strings.ACTION_NOT_ALlOWED_IN_THIS_PHASE.getLabel();
                break;
            case ATTACK:
            case DIRECT_ATTACK:
                expectedPhase = Phase.BATTLE_PHASE;
                break;

            case ACTIVATE_EFFECT:
                responseString = Strings.CANNOT_ACTIVATE_IN_THIS_TURN.getLabel();
                break;

        }


        if (!game.getPhase().toString().contains(expectedPhase.toString())){
            return responseString;
        }
        return super.handle(request, duel, response);
    }
}
