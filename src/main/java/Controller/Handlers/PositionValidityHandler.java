package Controller.Handlers;

import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;

public class PositionValidityHandler extends GameHandler{
    public String handle (JSONObject request, Game game){
        String command = request.getString(Strings.COMMAND.getLabel());
        if (command.equals(CommandTags.SELECT_CARD_MONSTER.getLabel()) &&
            isPositionValid(request.getInt(Strings.MONSTER_POSITION.getLabel()), 5)){
                return Strings.INVALID_SELECTION.getLabel();
        } else if (command.equals(CommandTags.SELECT_CARD_SPELL.getLabel()) &&
                   isPositionValid(request.getInt(Strings.SPELL_POSITION.getLabel()), 5)){
            return Strings.INVALID_SELECTION.getLabel();
        } else if (command.equals(CommandTags.SELECT_HAND.getLabel()) &&
                   isPositionValid(request.getInt(Strings.HAND_POSITION.getLabel()), game.getBoard().getMainPlayer().getHand().getSize())){
            return Strings.INVALID_SELECTION.getLabel();
        }

        return super.handle(request, game);
    }

    private boolean isPositionValid(int position, int limitation) {
        if (position < 1) return true;
        return position > limitation;
    }
}
