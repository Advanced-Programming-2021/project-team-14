package Controller.Handlers;

import model.Strings;
import model.card.SelectedCard;
import model.card.SpellTrap;
import model.card.enums.CardType;
import model.card.enums.Position;
import model.card.enums.Property;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class EmptyPlaceHandler extends GameHandler {

    public String handle(JSONObject request, Duel duel) {
        Game game = duel.getGame();

        Logger.log("empty place handler", "checking ...");

        SelectedCard selectedCard = game.getSelectedCard();
        String command = request.getString("command");
        switch (Objects.requireNonNull(CommandTags.fromValue(command))) {
            case ACTIVATE_EFFECT:
                if (game.getBoard().getMainPlayer().getSpellZone().isFull() && selectedCard.getCard().getPosition() == Position.HAND && selectedCard.getCard().getProperty() != Property.FIELD)
                    return Strings.SPELL_ZONE_FULL.getLabel();
            case SUMMON:
                if (game.getBoard().getMainPlayer().getMonsterZone().isFull())
                    return Strings.MONSTER_ZONE_FULL.getLabel();
            case SET:
                if (selectedCard.getCard().getCardType() == CardType.MONSTER &&
                    game.getBoard().getMainPlayer().getMonsterZone().isFull())
                    return Strings.MONSTER_ZONE_FULL.getLabel();
                if ((selectedCard.getCard().getCardType() == CardType.SPELL || selectedCard.getCard().getCardType() == CardType.TRAP) &&
                    game.getBoard().getMainPlayer().getMonsterZone().isFull())
                    return Strings.MONSTER_ZONE_FULL.getLabel();
        }


        return super.handle(request, duel);
    }
}
