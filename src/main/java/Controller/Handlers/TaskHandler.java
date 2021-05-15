package Controller.Handlers;

import Controller.Response;
import model.Strings;
import model.card.Card;
import model.card.Monster;
import model.card.SelectedCard;
import model.card.enums.Position;
import model.card.enums.State;
import model.game.Cell;
import model.game.Game;
import model.game.Player;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class TaskHandler extends GameHandler {

    public String handle(JSONObject request, Game game) {
        Logger.log("task handler", "doing ...");
        switch (Objects.requireNonNull(CommandTags.fromValue(request.getString(Strings.COMMAND.getLabel())))) {
            case SET:
                return set(request, game);
            case SELECT:
                return select(request, game);
            case NEXT_PHASE:
                return nextPhase(game);
            case SET_POSITION:
                return setPosition(request, game);
            case SHOW_SELECTED_CARD:
                return showSelectedCard(game);
            case DESELECT:
                return deselect(game);
            case SUMMON:
                return summon(request, game);
            case ATTACK:
                return attack(request, game);
            case DIRECT_ATTACK:
                return directAttack(game);
            case FLIP_SUMMON:
//                return flipSummon(request, game);
            case ACTIVATE_EFFECT:
                break;

        }
        return "> .... <";
    }

    private String directAttack(Game game) {
        Cell selectedCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(game.getSelectedCard().getPositionIndex());
        int damage = ((Monster) game.getSelectedCard().getCard()).getAttack();
        Player rivalPlayer = game.getBoard().getRivalPlayer();
        rivalPlayer.setLifePoint(rivalPlayer.getLifePoint()-damage);
        selectedCell.removeCard();
        return String.format(Strings.DIRECT_ATTACK.getLabel(), damage);
    }

    private String deselect(Game game) {
        game.deselect();
        return Strings.CARD_DESELECTED.getLabel();
    }

    private String attack(JSONObject request, Game game) {

        Cell rivalCard = game.getBoard().getRivalPlayer().getMonsterZone().getCell(request.getInt(Strings.TO.getLabel()));
        Cell selectedCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(game.getSelectedCard().getPositionIndex());
        int damage;
        String opponentCardName = "";
        switch (rivalCard.getCard().getState()) {
            case DEFENSIVE_HIDDEN:
                opponentCardName = String.format(Strings.DH_ATTACK_EQUAL.getLabel(), rivalCard.getCard().getName());
            case DEFENSIVE_OCCUPIED:
                damage = ((Monster) rivalCard.getCard()).getDefence() -
                        ((Monster) selectedCell.getCard()).getAttack();
                if (damage > 0) {
                    damage(false, damage, game);
                    return opponentCardName + String.format(Strings.DO_ATTACK_MORE.getLabel(), damage);
                }
                if (damage < 0) {
                    game.getBoard().getGraveYard().addCard(rivalCard.getCard());
                    rivalCard.removeCard();
                    return opponentCardName + String.format(Strings.DO_ATTACK_LESS.getLabel(), damage);
                }
                return opponentCardName + Strings.DO_ATTACK_EQUAL.getLabel();
            case OFFENSIVE_OCCUPIED:
                damage = ((Monster) selectedCell.getCard()).getAttack() -
                        ((Monster) rivalCard.getCard()).getAttack();
                if (damage > 0) {
                    damage(true, damage, game);
                    game.getBoard().getGraveYard().addCard(rivalCard.getCard());
                    rivalCard.removeCard();
                    return String.format(Strings.OO_ATTACK_MORE.getLabel(), damage);
                }
                if (damage < 0) {
                    damage(true, damage, game);
                    game.getBoard().getGraveYard().addCard(selectedCell.getCard());
                    selectedCell.removeCard();
                    return String.format(Strings.OO_ATTACK_LESS.getLabel(), damage);
                }
                selectedCell.removeCard();
                rivalCard.removeCard();
                return String.format(Strings.OO_ATTACK_EQUAL.getLabel(), damage);

        }

        return null;
    }

    private void damage(boolean toOpponent, int damage, Game game) {
        (toOpponent ? game.getBoard().getRivalPlayer() : game.getBoard().getMainPlayer()).decreaseLP(damage);
    }

    private String nextPhase(Game game) {
        game.nextPhase();
        return null;
    }

    private String set(JSONObject request, Game game) {
        game.getSelectedCard().getCard().setState(State.DEFENSIVE_HIDDEN);
        game.getSelectedCard().setPosition(Position.MONSTER_ZONE);
        game.getBoard().getMainPlayer().getMonsterZone().placeCard(game.getSelectedCard());
        game.getTurnLogger().cardAdded(game.getSelectedCard().getCard());
        game.deselect();
        return Strings.SET_SUCCESSFULLY.getLabel();
    }


    private String summon(JSONObject request, Game game) {

        int level = game.getBoard().getMainPlayer().getPlayingDeck().
                getMonsterByName(game.getSelectedCard().getCard().getName()).getLevel();

        if (level == 5 || level == 6) {

            tribute(Integer.parseInt(request.getString("tributeCardAddress1")), 1000, game);

        } else if (level == 7 || level == 8) {

            tribute(Integer.parseInt(request.getString("tributeCardAddress1")),
                    Integer.parseInt(request.getString("tributeCardAddress1")), game);
        }

        game.getSelectedCard().getCard().setState(State.OFFENSIVE_OCCUPIED);
        game.getSelectedCard().setPosition(Position.MONSTER_ZONE);
        game.getBoard().getMainPlayer().getMonsterZone().placeCard(game.getSelectedCard());
        game.getTurnLogger().cardAdded(game.getSelectedCard().getCard());
        game.deselect();
        Response.add("needTribute", "false");
        return Strings.SUMMON_SUCCESSFULLY.getLabel();

    }


    private void tribute(int tributeCardAddress1, int tributeCardAddress2, Game game) {

        game.getBoard().getMainPlayer().getMonsterZone().emptyCell(tributeCardAddress1);

        if (tributeCardAddress2 != 1000) {
            game.getBoard().getMainPlayer().getMonsterZone().emptyCell(tributeCardAddress2);
        }
    }


    private String select(JSONObject request, Game game) {
        String area = request.getString(Strings.AREA.getLabel());
        boolean isOpponent = request.getBoolean(Strings.OPPONENT_OPTION.getLabel());
        Player player = isOpponent ? game.getBoard().getRivalPlayer() : game.getBoard().getMainPlayer();
        int position = 1;
        Card card = null;
        switch (area) {
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
