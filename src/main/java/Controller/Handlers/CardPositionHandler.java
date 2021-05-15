package Controller.Handlers;

import model.Strings;
import model.card.enums.Position;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;

public class CardPositionHandler extends GameHandler{
    public String handle(JSONObject request, Game game){

        String command = request.getString("command");

        Position expectedPosition = null;
        if (command.equals(CommandTags.SUMMON.getLabel()) || command.equals(CommandTags.SET.getLabel())){
            expectedPosition = Position.HAND;
            response = Strings.CARD_NOT_EXIST_IN_HAND.getLabel();
        }
        if (command.equals(CommandTags.SET_POSITION.getLabel()) || command.equals(CommandTags.FLIP_SUMMON.getLabel())){
            expectedPosition = Position.MONSTER_ZONE;
            response = Strings.CARD_NOT_EXIST_IN_MONSTER_ZONE.getLabel();
        }
        if (command.startsWith(CommandTags.ATTACK.getLabel())){
            expectedPosition = Position.MONSTER_ZONE;
            response = Strings.CANNOT_ATTACK_WITH_THIS_CARD.getLabel();
        }

        if (!game.getSelectedCard().getPosition().equals(expectedPosition))
            return response;
        return super.handle(request, game);
    }
}
