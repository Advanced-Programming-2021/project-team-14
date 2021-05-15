package Controller.Handlers;

import model.Strings;
import model.card.Card;
import model.card.SelectedCard;
import model.card.enums.Position;
import model.card.enums.State;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;

public class CardStateHandler extends GameHandler{
    public String handle(JSONObject request, Game game){
        SelectedCard selectedCard = game.getSelectedCard();
//        String command = request.getString(Strings.COMMAND.getLabel());
//        if (command.equals(CommandTags.SHOW_SELECTED_CARD.getLabel())){
//            if (game.)
//        }

        if (selectedCard.getPosition() != Position.MONSTER_ZONE)
            return Strings.CANNOT_CHANGE_POSITION.getLabel();
        boolean isToAttack = request.getString("position").equals("attack");
        if (isToAttack && selectedCard.getCard().getState() != State.DEFENSIVE_OCCUPIED ||
            (!isToAttack && selectedCard.getCard().getState() == State.OFFENSIVE_OCCUPIED))
            return Strings.ALREADY_POSITIONED.getLabel();

        return super.handle(request, game);
    }
}
