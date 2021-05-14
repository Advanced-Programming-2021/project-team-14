package Controller.Handlers;

import model.Strings;
import model.card.Card;
import model.card.enums.Position;
import model.card.enums.State;
import model.game.Game;
import org.json.JSONObject;

public class CardStateHandler extends GameHandler{
    public String handle(JSONObject request, Game game){
        Card selectedCard = game.getSelectedCard();

        if (selectedCard.getPosition() != Position.MONSTER_ZONE)
            return Strings.CANNOT_CHANGE_POSITION.getLabel();
        boolean isToAttack = request.getString("position").equals("attack");
        if (isToAttack && selectedCard.getState() != State.DEFENSIVE_OCCUPIED ||
            (!isToAttack && selectedCard.getState() == State.OFFENSIVE_OCCUPIED))
            return Strings.ALREADY_POSITIONED.getLabel();

        return super.handle(request, game);
    }
}
