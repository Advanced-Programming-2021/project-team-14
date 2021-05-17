package Controller.Handlers;

import model.Strings;
import model.card.enums.CardType;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class CardTypeHandler extends GameHandler {


    public String handle(JSONObject request, Game game) {
        Logger.log("card type handler", "checking ...");

        String command = request.getString("command");
        switch (Objects.requireNonNull(CommandTags.fromValue(command))){
            case SUMMON:
                if (game.getSelectedCard().getCard().getCardType() != CardType.MONSTER)
                    return Strings.CANNOT_SUMMON_THIS_CARD.getLabel();
                break;
            case ACTIVATE_EFFECT:
                if (game.getSelectedCard().getCard().getCardType() != CardType.SPELL ||
                    game.getSelectedCard().getCard().getCardType() != CardType.TRAP)
                    return Strings.ACTIVATION_IS_ONLY_FOR_SPELLS.getLabel();
                break;
        }




        return super.handle(request, game);
    }
}
