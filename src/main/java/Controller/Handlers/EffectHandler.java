package Controller.Handlers;

import Controller.enums.EffectsEnum;
import model.Strings;
import model.card.Card;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;

import java.util.ArrayList;

public class EffectHandler extends GameHandler {

    public String handle(JSONObject request, Game game){
        String command = request.getString(Strings.COMMAND.getLabel());

        ArrayList<Card> activatedCards = game.getBoard().getRivalPlayer().getActivatedCards();
        for (Card card: activatedCards) {
//            if (card.getEffectValue(EffectsEnum.EFFECT_TIME.getLabel()).equals(CommandTags.ATTACK.getLabel())){
                System.out.println("here we do what the card is supposed to do (actually by calling another chain)");
//            }
        }
        return super.handle(request, game);
    }
}
