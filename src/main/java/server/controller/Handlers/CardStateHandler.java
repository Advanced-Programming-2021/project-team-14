package server.controller.Handlers;

import model.Strings;
import model.card.SelectedCard;
import model.card.enums.Position;
import model.card.enums.State;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import server.ServerResponse;
import view.enums.CommandTags;

public class CardStateHandler extends GameHandler {
    public String handle(JSONObject request, Duel duel, ServerResponse response) {
        Game game = duel.getGame();
        SelectedCard selectedCard = game.getSelectedCard();
        String command = request.getString(Strings.COMMAND.getLabel());
        if (command.equals(CommandTags.SHOW_SELECTED_CARD.getLabel())) {
            if (selectedCard.isOpponent() && selectedCard.getCard().getState() == State.DEFENSIVE_HIDDEN)
                return Strings.CARD_IS_NOT_VISIBLE.getLabel();
        } else if (command.equals((CommandTags.FLIP_SUMMON.getLabel()))) {

            if (selectedCard.getCard().getState() != State.DEFENSIVE_HIDDEN) {
                return Strings.CANNOT_FLIP_SUMMON_THIS_CARD.getLabel();
            }
        } else {
            if (selectedCard.getCard().getPosition() != Position.MONSTER_ZONE)
                return Strings.CANNOT_CHANGE_POSITION.getLabel();
            boolean isToAttack = request.getString("position").equals("attack");
            if (isToAttack && selectedCard.getCard().getState() != State.DEFENSIVE_OCCUPIED ||
                    (!isToAttack && selectedCard.getCard().getState() != State.OFFENSIVE_OCCUPIED))
                return Strings.ALREADY_POSITIONED.getLabel();
        }

        return super.handle(request, duel, response);
    }
}
