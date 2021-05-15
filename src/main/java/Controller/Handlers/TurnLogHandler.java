package Controller.Handlers;

import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

public class TurnLogHandler extends GameHandler {


    public String handle(JSONObject request, Game game) {

        Logger.log("turn log handler", "checking ...");
        String command = request.getString(Strings.COMMAND.getLabel());
        if (command.equals(CommandTags.SET_POSITION.getLabel())) {
            if (game.getTurnLogger().doesPositionChanged(game.getSelectedCard().getCard()))
                return Strings.POSITION_ALREADY_CHANGED.getLabel();
        } else if (command.equals(CommandTags.SET.getLabel())) {
            if (game.getTurnLogger().hasSummoned())
                return Strings.ALREADY_SUMMONED.getLabel();
        }
        return super.handle(request, game);
    }
}
