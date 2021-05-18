package Controller.Handlers;

import model.Strings;
import model.card.enums.CardType;
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
        } else if (command.equals(CommandTags.SET.getLabel()) && game.getSelectedCard().getCard().getCardType() == CardType.MONSTER) {
            if (game.getTurnLogger().hasSummonedOrSetCards())
                return Strings.ALREADY_SUMMONED.getLabel();
        } else if (command.equals(CommandTags.SUMMON.getLabel())) {
            if (game.getTurnLogger().hasSummonedOrSetCards())
                return Strings.ALREADY_SUMMONED.getLabel();
        } else if (command.equals(CommandTags.FLIP_SUMMON.getLabel())) {
            if (game.getTurnLogger().hasAdded(game.getSelectedCard().getCard()))
                return Strings.CANNOT_FLIP_SUMMON_THIS_CARD.getLabel();
        } else if (command.equals(CommandTags.ATTACK.getLabel())) {
            if (game.getTurnLogger().hasAttacked(game.getSelectedCard().getCard()))
                return Strings.ALREADY_ATTACKER.getLabel();
        } else if (command.equals(CommandTags.DIRECT_ATTACK.getLabel())) {
            if (game.getTurnLogger().hasAttacked(game.getSelectedCard().getCard()))
                return Strings.ALREADY_ATTACKER.getLabel();
        } else if (command.equals(CommandTags.ACTIVATE_EFFECT.getLabel())) {
            if (game.getTurnLogger().hasAdded(game.getSelectedCard().getCard()))
                return Strings.ALREADY_ACTIVATED.getLabel();
        }
        return super.handle(request, game);
    }
}
