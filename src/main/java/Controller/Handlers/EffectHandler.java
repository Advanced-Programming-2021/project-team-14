package Controller.Handlers;

import Controller.enums.EffectsEnum;
import model.Strings;
import model.card.Card;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;

import java.util.ArrayList;

public class EffectHandler extends GameHandler {

    public String handle(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        String command = request.getString(Strings.COMMAND.getLabel());
        ArrayList<Card> canBeActivatedCards = new ArrayList<>();
        game.getBoard().getRivalPlayer().getSpellZone().getCells().forEach(cell -> {
            if (!cell.isEmpty()) {
                String effectTime = cell.getCard().getEffectValue(EffectsEnum.EFFECT_TIME.getLabel());
                if (effectTime.equals(command)){
                    if (cell.getCard().getEffectValue(EffectsEnum.FIRST_ATTACK_DEFENSE_LIFE_POINT.getLabel()).equals("lifePoint")){
                        game.getBoard().getRivalPlayer().increaseLifePoint(Integer.parseInt(cell.getCard().getEffectValue(EffectsEnum.FIRST_AMOUNT.getLabel())));
                    }else{
                        canBeActivatedCards.add(cell.getCard());
                    }
                }
            }
        });
        if (canBeActivatedCards.size() > 0){
            ChainHandler.add(game.getSelectedCard(), request);
            game.getBoard().getRivalPlayer().getTurnLogger().setAvailableCardsToApplyInRivalsTurn(canBeActivatedCards);
            game.getBoard().getRivalPlayer().getTurnLogger().setTemporarilyChanged(true);
            game.changeTurn();

            return "its now " + game.getBoard().getMainPlayer().getNickname() + "'s turn\nyou can activate some of your spells:\n" + canBeActivatedCards;
        }

        return super.handle(request, duel);
    }
}
