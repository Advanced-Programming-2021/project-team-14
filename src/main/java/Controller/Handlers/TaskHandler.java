package Controller.Handlers;

import model.Strings;
import model.card.Card;
import model.card.SelectedCard;
import model.card.enums.Position;
import model.card.enums.State;
import model.game.Game;
import model.game.Player;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class TaskHandler extends GameHandler {

    public String handle(JSONObject request, Game game) {
        Logger.log("task handler", "doing ...");
        switch (Objects.requireNonNull(CommandTags.fromValue(request.getString(Strings.COMMAND.getLabel())))){
            case SET:
                return set(request, game);
            case SELECT:
                return select(request, game);
            case NEXT_PHASE:
                return nextPhase(game);
            case SET_POSITION:
                return setPosition(request, game);
            case SUMMON:
                break;
            case ATTACK:
                break;
            case FLIP_SUMMON:
                break;
            case DESELECT:
                break;
            case ACTIVATE_EFFECT:
                break;
            case SHOW_SELECTED_CARD:
                return showSelectedCard(game);

        }
        return "> .... <";
    }

    private String nextPhase(Game game) {
        game.nextPhase();
        return null;
    }

    private String set(JSONObject request, Game game) {
        game.getSelectedCard().getCard().setState(State.DEFENSIVE_HIDDEN);
        game.getBoard().getMainPlayer().getMonsterZone().placeCard(game.getSelectedCard());
        game.deselect();
        return Strings.SET_SUCCESSFULLY.getLabel();
    }

    private String select(JSONObject request, Game game) {
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
                position = request.getInt(Strings.POSITION.getLabel());
                card = player.getSpellZone().getCell(position).getCard();
                break;
            case "field":
                card = player.getFieldZone().getCard();
                break;
            case "hand":
                position = request.getInt(Strings.POSITION.getLabel());
                card = player.getHand().getCard(position);
                break;
        }

        game.setSelectedCard(new SelectedCard(card, Position.fromValue(area), position, isOpponent));
        return Strings.CARD_SELECTED.getLabel();
    }

    private String showSelectedCard(Game game) {
        return game.getSelectedCard().getCard().show();
    }

    private String setPosition(JSONObject request, Game game) {
        game.getTurnLogger().cardPositionChanged(game.getSelectedCard().getCard());
        if (request.getString(Strings.POSITION.getLabel()).equals(Strings.ATTACK_OPTION.getLabel())) {
            game.getSelectedCard().getCard().setState(State.OFFENSIVE_OCCUPIED);
        } else
            game.getSelectedCard().getCard().setState(State.DEFENSIVE_OCCUPIED);
        game.deselect();
        return Strings.MONSTER_POSITION_CHANGED.getLabel();
    }


}
