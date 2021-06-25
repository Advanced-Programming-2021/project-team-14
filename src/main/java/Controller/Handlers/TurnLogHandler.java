package Controller.Handlers;

import model.Strings;
import model.card.SpellTrap;
import model.card.enums.CardType;
import model.game.Duel;
import model.game.Game;
import model.game.TurnLogger;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

public class TurnLogHandler extends GameHandler {


    public String handle(JSONObject request, Duel duel) {
        Game game = duel.getGame();

        Logger.log("turn log handler", "checking ...");
        String command = request.getString(Strings.COMMAND.getLabel());
        TurnLogger turnLogger = game.getBoard().getMainPlayer().getTurnLogger();
        if (command.equals(CommandTags.SET_POSITION.getLabel())) {
            if (turnLogger.doesPositionChanged(game.getSelectedCard().getCard()))
                return Strings.POSITION_ALREADY_CHANGED.getLabel();
        } else if (command.equals(CommandTags.SET.getLabel()) && game.getSelectedCard().getCard().getCardType() == CardType.MONSTER) {
            if (turnLogger.hasSummonedOrSetCards())
                return Strings.ALREADY_SUMMONED.getLabel();
        } else if (command.equals(CommandTags.SUMMON.getLabel())) {
            if (turnLogger.hasSummonedOrSetCards())
                return Strings.ALREADY_SUMMONED.getLabel();
        } else if (command.equals(CommandTags.FLIP_SUMMON.getLabel())) {
            if (turnLogger.hasAdded(game.getSelectedCard().getCard()))
                return Strings.CANNOT_FLIP_SUMMON_THIS_CARD.getLabel();
        } else if (command.equals(CommandTags.ATTACK.getLabel())) {
            if (turnLogger.hasAttacked(game.getSelectedCard().getCard()))
                return Strings.ALREADY_ATTACKER.getLabel();
        } else if (command.equals(CommandTags.DIRECT_ATTACK.getLabel())) {
            if (turnLogger.hasAttacked(game.getSelectedCard().getCard()))
                return Strings.ALREADY_ATTACKER.getLabel();
        } else if (command.equals(CommandTags.ACTIVATE_EFFECT.getLabel())) {
            if (game.getBoard().getMainPlayer().getTurnLogger().isTemporarilyChanged()){
                if (!game.getBoard().getMainPlayer().getTurnLogger().getCanBeActivatedCards().contains(game.getSelectedCard().getCard())){
                    return "you cannot activate this card. its preparations are not prepared. (Remember: turn has temporarily changed!)";
                }
            }
            if (((SpellTrap)duel.getGame().getSelectedCard().getCard()).isActivated()){
                return Strings.ALREADY_ACTIVATED.label;
            }
        }
        return super.handle(request, duel);
    }
}
