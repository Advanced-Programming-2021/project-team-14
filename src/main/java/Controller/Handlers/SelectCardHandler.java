package Controller.Handlers;

import model.Strings;
import model.card.Card;
import model.card.SelectedCard;
import model.card.enums.Position;
import model.game.*;
import org.json.JSONObject;

public class SelectCardHandler extends GameHandler{

    public String handle(JSONObject request, Game game){

        String area = request.getString(Strings.AREA.getLabel());
        boolean isOpponent = request.getBoolean(Strings.OPPONENT_OPTION.getLabel());
        Player player = isOpponent ? game.getBoard().getRivalPlayer() : game.getBoard().getMainPlayer();
        int position = 1;
        Card card = null;
        switch (area){
            case "monster":
                position = request.getInt(Strings.POSITION.getLabel());
                card = player.getMonsterZone().getCell(position).getCard();
                break;
            case "spell":
                card = player.getSpellZone().getCell(position).getCard();
                break;
            case "field":
                card = player.getFieldZone().getCard();
                break;
            case "hand":
                card = player.getHand().getCard(position);
                break;
        }

        game.setSelectedCard(new SelectedCard(card, Position.fromValue(area), position, isOpponent));
        return Strings.CARD_SELECTED.getLabel();
    }

}
