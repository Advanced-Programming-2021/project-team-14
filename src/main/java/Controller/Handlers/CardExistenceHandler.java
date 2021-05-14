package Controller.Handlers;

import model.Strings;
import model.card.Card;
import model.game.Cell;
import model.game.Game;
import model.game.Player;
import org.json.JSONObject;
import view.enums.CommandTags;

public class CardExistenceHandler extends GameHandler{
    public String handle (JSONObject request, Game game){
        String command = request.getString(Strings.COMMAND.getLabel());


        if (command.equals(CommandTags.SELECT_CARD_SPELL.getLabel())){
            Cell cell = (request.getBoolean(Strings.OPPONENT_OPTION.getLabel()) ? game.getBoard().getRivalPlayer() :
                    game.getBoard().getMainPlayer()).getSpellZone().getCell(request.getInt(Strings.SPELL_POSITION.getLabel()));
            if (cell.isEmpty()) return Strings.NO_CARD_FOUND.getLabel();
        } else if (command.equals(CommandTags.SELECT_CARD_MONSTER.getLabel())){
            Cell cell = (request.getBoolean(Strings.OPPONENT_OPTION.getLabel()) ? game.getBoard().getRivalPlayer() :
                    game.getBoard().getMainPlayer()).getMonsterZone().getCell(request.getInt(Strings.SPELL_POSITION.getLabel()));
            if (cell.isEmpty()) return Strings.NO_CARD_FOUND.getLabel();
        }
        else if (command.equals(CommandTags.SELECT_FIELD.getLabel())){
            Player player =  request.getBoolean(Strings.OPPONENT_OPTION.getLabel()) ? game.getBoard().getRivalPlayer() :
                    game.getBoard().getMainPlayer();
            if (player.getFieldZone().isEmpty()) return Strings.NO_CARD_FOUND.getLabel();
        }

        return super.handle(request, game);
    }

}
