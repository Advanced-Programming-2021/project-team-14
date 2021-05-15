package Controller.Handlers;

import model.Strings;
import model.card.enums.State;
import model.game.Game;
import org.json.JSONObject;

public class SetPositionHandler extends GameHandler {
    public String handle(JSONObject request, Game game) {
        game.getTurnLogger().cardPositionChanged(game.getSelectedCard().getCard());
        if (request.getString(Strings.POSITION.getLabel()).equals(Strings.ATTACK_OPTION.getLabel())) {
            game.getSelectedCard().getCard().setState(State.OFFENSIVE_OCCUPIED);
        } else
            game.getSelectedCard().getCard().setState(State.DEFENSIVE_OCCUPIED);


        return Strings.MONSTER_POSITION_CHANGED.getLabel();
    }
}
