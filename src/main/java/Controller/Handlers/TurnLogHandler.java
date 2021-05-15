package Controller.Handlers;

import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;

public class TurnLogHandler extends GameHandler {
    public String handle(JSONObject request, Game game){
        String command = request.getString(Strings.COMMAND.getLabel());
        if (command.equals(CommandTags.SET_POSITION.getLabel())){
            if (game.getTurnLogger().doesPositionChanged(game.getSelectedCard().getCard()))
                return Strings.POSITION_ALREADY_CHANGED.getLabel();
        }
        return Strings.CARD_SELECTED.getLabel();
    }
}
