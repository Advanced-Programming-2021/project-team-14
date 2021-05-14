package Controller.Handlers;

import model.Strings;
import model.card.enums.CardType;
import model.game.Game;
import org.json.JSONObject;

public class EmptyPlaceHandler extends GameHandler{
    public String handle(JSONObject request, Game game){
        String command = request.getString("command");
        if (game.getSelectedCard().getCardType() == CardType.MONSTER &&
            game.getBoard().getMainPlayer().getMonsterZone().isFull())
            return Strings.MONSTER_ZONE_FULL.getLabel();
        if (game.getSelectedCard().getCardType() == CardType.SPELL &&
            game.getBoard().getMainPlayer().getSpellZone().isFull())
            return Strings.SPELL_ZONE_FULL.getLabel();
        return super.handle(request, game);
    }
}
