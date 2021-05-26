package Controller.Handlers;

import Controller.Response;
import Controller.enums.Responses;
import model.Strings;
import model.card.Card;
import model.card.Monster;
import model.card.SelectedCard;
import model.card.enums.CardType;
import model.card.enums.Position;
import model.card.enums.State;
import model.game.Cell;
import model.game.Duel;
import model.game.Game;
import model.game.Player;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class TaskHandler extends GameHandler {

    public String handle(JSONObject request, Duel duel) {
        Logger.log("task handler", "doing ...");
        switch (Objects.requireNonNull(CommandTags.fromValue(request.getString(Strings.COMMAND.getLabel())))) {
            case SET:
                return set(request, duel);
            case SELECT:
                return select(request, duel);
            case NEXT_PHASE:
                return nextPhase(duel);
            case SET_POSITION:
                return setPosition(request, duel);
            case SHOW_SELECTED_CARD:
                return showSelectedCard(duel);
            case SHOW_GRAVEYARD:
                return showGraveyard(duel);
            case DESELECT:
                return deselect(duel);
            case SUMMON:
                return summon(request, duel);
            case ATTACK:
                return attack(request, duel);
            case DIRECT_ATTACK:
                return directAttack(duel);
            case FLIP_SUMMON:
                return flipSummon(request, duel);
            case ACTIVATE_EFFECT:
                return activateEffect(duel);
            case INCREASE_LIFE_POINT:
                return increaseLifePoint(request, duel);
        }
        return "> .... <";
    }

    private String increaseLifePoint(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        game.getBoard().getMainPlayer().increaseLifePoint(request.getInt(Strings.AMOUNT.getLabel()));
        return Responses.INCREASE_LIFE_POINT.getLabel();
    }

    private String activateEffect(Duel duel) {
        Game game = duel.getGame();
        SelectedCard selectedCard = game.getSelectedCard();

        selectedCard.getCard().setState(State.OCCUPIED);
        selectedCard.setPosition(Position.SPELL_ZONE);
        game.getBoard().getMainPlayer().getSpellZone().placeCard(selectedCard);
        game.getBoard().getMainPlayer().addToActiveCards(selectedCard.getCard());
        game.deselect();
        return Strings.ACTIVATE_SUCCESSFULLY.getLabel();
    }

    private String directAttack(Duel duel) {
        Game game = duel.getGame();
        Monster card = (Monster) game.getSelectedCard().getCard();
        int damage = card.getAttack();
        game.getTurnLogger().cardAttack(card);
        damage(true, damage, duel);
        game.deselect();
        String firstResponse = String.format(Strings.DIRECT_ATTACK.getLabel(), damage);
        if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
        return String.format(Strings.DIRECT_ATTACK.getLabel(), damage);
    }

    private String deselect(Duel duel) {
        Game game = duel.getGame();
        game.deselect();
        return Strings.CARD_DESELECTED.getLabel();
    }

    private String attack(JSONObject request, Duel duel) {
        Game game = duel.getGame();

        Cell rivalCard = game.getBoard().getRivalPlayer().getMonsterZone().getCell(request.getInt(Strings.TO.getLabel()));
        Cell selectedCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(game.getSelectedCard().getPositionIndex());
        game.getTurnLogger().cardAttack(selectedCell.getCard());
        int damage;
        String opponentCardName = "";
        switch (rivalCard.getCard().getState()) {
            case DEFENSIVE_HIDDEN:
                opponentCardName = String.format(Strings.DH_ATTACK_EQUAL.getLabel(), rivalCard.getCard().getName());
            case DEFENSIVE_OCCUPIED:
                damage = ((Monster) rivalCard.getCard()).getDefence() -
                        ((Monster) selectedCell.getCard()).getAttack();
                if (damage > 0) {
                    damage(false, damage, duel);
                    game.getTurnLogger().cardAttack(selectedCell.getCard());
                    String firstResponse = opponentCardName + String.format(Strings.DO_ATTACK_MORE.getLabel(), damage);
                    if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
                    return opponentCardName + String.format(Strings.DO_ATTACK_MORE.getLabel(), damage);
                }
                if (damage < 0) {
                    game.getBoard().getRivalPlayer().getGraveYard().addCard(rivalCard.getCard());
                    rivalCard.removeCard();
                    return opponentCardName + String.format(Strings.DO_ATTACK_LESS.getLabel(), damage);
                }
                return opponentCardName + Strings.DO_ATTACK_EQUAL.getLabel();
            case OFFENSIVE_OCCUPIED:
                damage = ((Monster) selectedCell.getCard()).getAttack() -
                        ((Monster) rivalCard.getCard()).getAttack();
                if (damage > 0) {
                    damage(true, damage, duel);
                    game.getBoard().getRivalPlayer().getGraveYard().addCard(rivalCard.getCard());
                    rivalCard.removeCard();
                    String firstResponse = String.format(Strings.OO_ATTACK_MORE.getLabel(), damage);
                    if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
                    return String.format(Strings.OO_ATTACK_MORE.getLabel(), damage);
                }
                if (damage < 0) {
                    damage(false, damage, duel);
                    game.getBoard().getMainPlayer().getGraveYard().addCard(selectedCell.getCard());
                    selectedCell.removeCard();
                    String firstResponse = String.format(Strings.OO_ATTACK_LESS.getLabel(), damage);
                    if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
                    return String.format(Strings.OO_ATTACK_LESS.getLabel(), damage);
                }
                selectedCell.removeCard();
                rivalCard.removeCard();
                return String.format(Strings.OO_ATTACK_EQUAL.getLabel(), damage);

        }

        return null;
    }

    public void damage(boolean toOpponent, int damage, Duel duel) {
        Game game = duel.getGame();

        Player mainPlayer = game.getBoard().getMainPlayer();
        Player rivalPlayer = game.getBoard().getRivalPlayer();
        boolean isEnded = false;

        if ((toOpponent ? rivalPlayer : mainPlayer).getLifePoint() <= damage) {
            (toOpponent ? rivalPlayer : mainPlayer).setLifePoint(0);
            isEnded = true;
            game.endGame((toOpponent ? mainPlayer : rivalPlayer), (toOpponent ? rivalPlayer : mainPlayer));
            duel.startNewRound();
        }

        if (!isEnded) {
            (toOpponent ? rivalPlayer : mainPlayer).decreaseLifePoint(damage);
        }
    }


    private String nextPhase(Duel duel) {
        Game game = duel.getGame();
        return game.nextPhase();
    }

    private String set(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        SelectedCard selectedCard = game.getSelectedCard();
        if (selectedCard.getCard().getCardType() == CardType.MONSTER) {
            selectedCard.getCard().setState(State.DEFENSIVE_HIDDEN);
            selectedCard.setPosition(Position.MONSTER_ZONE);
            game.getBoard().getMainPlayer().getMonsterZone().placeCard(selectedCard);
        } else {
            selectedCard.getCard().setState(State.HIDDEN);
            selectedCard.setPosition(Position.SPELL_ZONE);
            game.getBoard().getMainPlayer().getSpellZone().placeCard(selectedCard);
        }

        removeFromHand(selectedCard, duel);

        game.getTurnLogger().cardAdded(selectedCard.getCard());
        game.deselect();
        return Strings.SET_SUCCESSFULLY.getLabel();
    }

    private void removeFromHand(SelectedCard selectedCard, Duel duel) {
        Game game = duel.getGame();
        game.getBoard().getMainPlayer().getHand().remove(selectedCard.getPositionIndex());
    }

    private String flipSummon(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        SelectedCard selectedCard = game.getSelectedCard();

        selectedCard.getCard().setState(State.OFFENSIVE_OCCUPIED);
        selectedCard.setPosition(Position.MONSTER_ZONE);

        game.deselect();

        return Strings.FLIP_SUMMON_SUCCESSFULLY.getLabel();
    }

    private String summon(JSONObject request, Duel duel) {
        Game game = duel.getGame();

        Monster monster = (Monster) game.getSelectedCard().getCard();

        int level = monster.getLevel();

        if (level == 5 || level == 6) {

            tribute(Integer.parseInt(request.getString("tributeCardAddress1")), 1000, duel);

        } else if (level == 7 || level == 8) {

            tribute(Integer.parseInt(request.getString("tributeCardAddress1")),
                    Integer.parseInt(request.getString("tributeCardAddress2")), duel);
        }

        removeFromHand(game.getSelectedCard(), duel);
        game.getSelectedCard().getCard().setState(State.OFFENSIVE_OCCUPIED);
        game.getSelectedCard().setPosition(Position.MONSTER_ZONE);
        game.getBoard().getMainPlayer().getMonsterZone().placeCard(game.getSelectedCard());
        game.getTurnLogger().cardAdded(game.getSelectedCard().getCard());
        game.deselect();
        Response.add("needTribute", "false");
        return Strings.SUMMON_SUCCESSFULLY.getLabel();

    }

    private void tribute(int tributeCardAddress1, int tributeCardAddress2, Duel duel) {
        Game game = duel.getGame();

        Cell tributeCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress1);
        tributeCell.removeCard();
        game.getBoard().getMainPlayer().getGraveYard().addCard(tributeCell.getCard());

        if (tributeCardAddress2 != 1000) {
            tributeCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress2);
            tributeCell.removeCard();
            game.getBoard().getMainPlayer().getGraveYard().addCard(tributeCell.getCard());
        }
    }

    private String select(JSONObject request, Duel duel) {
        Game game = duel.getGame();
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

    private String showSelectedCard(Duel duel) {
        Game game = duel.getGame();
        return game.getSelectedCard().getCard().show();
    }

    private String showGraveyard(Duel duel) {
        Game game = duel.getGame();
        return game.getBoard().getMainPlayer().getGraveYard().showCards();
    }

    private String setPosition(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        game.getTurnLogger().cardPositionChanged(game.getSelectedCard().getCard());
        if (request.getString(Strings.POSITION.getLabel()).equals(Strings.ATTACK_OPTION.getLabel())) {
            game.getSelectedCard().getCard().setState(State.OFFENSIVE_OCCUPIED);
        } else
            game.getSelectedCard().getCard().setState(State.DEFENSIVE_OCCUPIED);
        game.deselect();
        return Strings.MONSTER_POSITION_CHANGED.getLabel();
    }
}
