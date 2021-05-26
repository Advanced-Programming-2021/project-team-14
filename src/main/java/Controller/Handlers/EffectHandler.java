package Controller.Handlers;

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

        ArrayList<Card> activatedCards = game.getBoard().getRivalPlayer().getActivatedCards();
        for (Card card : activatedCards) {
//            if (card.getEffectValue(EffectsEnum.EFFECT_TIME.getLabel()).equals(CommandTags.ATTACK.getLabel())){
            System.out.println("here we do what the card is supposed to do (actually by calling another chain)");
//            }
        }
        return super.handle(request, duel);
    }
}
