package Controller.Handlers;

import Controller.Response;
import Controller.enums.EffectsEnum;
import Controller.enums.Responses;
import model.Strings;
import model.card.Card;
import model.card.Monster;
import model.card.SelectedCard;
import model.card.SpellTrap;
import model.card.enums.CardType;
import model.card.enums.Position;
import model.card.enums.Property;
import model.card.enums.State;
import model.game.*;
import org.json.JSONObject;
import view.Logger;
import view.enums.CommandTags;

import java.util.Objects;

public class TaskHandler extends GameHandler {
    private Game game;
    private Duel duel;

    public String handle(JSONObject request, Duel duel) {
        this.game = duel.getGame();
        this.duel = duel;
        Logger.log("task handler", "doing ...");
        switch (Objects.requireNonNull(CommandTags.fromValue(request.getString(Strings.COMMAND.getLabel())))) {
            case SELECT:
                return select(request, duel);
            case SHOW_SELECTED_CARD:
                return showSelectedCard(duel);
            case SET:
                return set(request, duel);
            case NEXT_PHASE:
                return nextPhase(duel);
            case SET_POSITION:
                return setPosition(request, duel);
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
                return activateEffect(request, duel);
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

    private String activateEffect(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        SelectedCard selectedCard = game.getSelectedCard();
        selectedCard.getCard().setState(State.OCCUPIED);
        if (selectedCard.getCard().getProperty() == Property.EQUIP) {
            int cardToEquip = Integer.parseInt(request.getString("data"));
            Monster card = (Monster) game.getBoard().getMainPlayer().getMonsterZone().getCell(cardToEquip).getCard();
            card.addAffectedCard(selectedCard.getCard());
            equipCard(selectedCard.getCard(), card);
            ((SpellTrap) selectedCard.getCard()).setActivated(true);
        }
        if (selectedCard.getCard().getProperty() == Property.FIELD) {
            selectedCard.getCard().setPosition(Position.FIELD_ZONE);
            if (!game.getBoard().getMainPlayer().getFieldZone().isEmpty()) {
                FieldController.deActive();
            }
            removeFromHand(selectedCard, duel);
            game.getBoard().getMainPlayer().getFieldZone().setCard(selectedCard.getCard());
        } else {

            if (specialCaseEffects(game, selectedCard, request) == null) {
                applyNormalEffect(selectedCard.getCard(), game, request);
            }
            if (selectedCard.getCard() != null) {

                selectedCard.getCard().setPosition(Position.SPELL_ZONE);
                removeFromHand(selectedCard, duel);
                game.getBoard().getMainPlayer().getSpellZone().placeCard(selectedCard.getCard());
            }
        }
//        game.getBoard().getMainPlayer().addToActiveCards(selectedCard.getCard());
        game.deselect();
        return Strings.ACTIVATE_SUCCESSFULLY.getLabel();
    }

    private String specialCaseEffects(Game game, SelectedCard card, JSONObject request) {
        String toDo = card.getCard().getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel());
        if (toDo.equals("change owner")) {
            Cell rival = game.getBoard().getRivalPlayer().getMonsterZone().getCell(request.getInt("data"));
            game.getBoard().getMainPlayer().getMonsterZone().placeCard(rival.getCard());
            game.getBoard().getMainPlayer().getTurnLogger().addChangedOwnerCards(rival.getCard());
            rival.removeCard();
            ((SpellTrap) card.getCard()).setActivated(true);
        }

        if (toDo.equals("destroy card")) {
            Card selectedToSpecialSummon = game.getBoard().getRivalPlayer().getSpellZone().getCell(request.getInt("data")).getCard();
            if (selectedToSpecialSummon != null) {
                game.getBoard().getRivalPlayer().getSpellZone().remove(selectedToSpecialSummon.getName());
                game.getBoard().getRivalPlayer().getGraveYard().addCard(selectedToSpecialSummon);
                ((SpellTrap) card.getCard()).setActivated(true);
            }
        }

        if (card.getCard().getEffectValue(EffectsEnum.EFFECT_TIME.getLabel()).equals("entered name exist")) {
            if (game.getBoard().getRivalPlayer().getHand().doesHaveCard(request.getString("data"))) {
                String name = request.getString("data");
                game.getBoard().getRivalPlayer().getHand().remove(name);
                game.getBoard().getRivalPlayer().getMonsterZone().remove(name);
                game.getBoard().getRivalPlayer().getPlayingDeck().remove(name);

            } else {
                Hand hand = game.getBoard().getMainPlayer().getHand();
                card.getCard().setPosition(Position.SPELL_ZONE);
                hand.removeRandomly();
            }
            ((SpellTrap) card.getCard()).setActivated(true);

        }
        switch (toDo) {
            case "Mirror Force":
                mirrorForceEffect();
                break;
            case "Magic Cylinder":
                magicCylinderEffect();
                break;
            case "Negate Attack":
                negateAttackEffect();
                break;
            case "Magic Jammer":
                magicJammerEffect();
                break;
        }
        return null;

    }

    private void magicJammerEffect() {
        Card card = ChainHandler.getLastChain().getSelectedCard().getCard();
        Cell cell = game.getBoard().getRivalPlayer().getSpellZone().getCell(card.getPositionIndex());
        sendToGraveYard(cell.getCard(), false);
        cell.removeCard();
    }

    private void magicCylinderEffect() {
        Chain previous = ChainHandler.getLastChain();
        if (previous.getSelectedCard().getCard() != null) {
            sendToGraveYard(previous.getSelectedCard().getCard(), false);
            damage(true, ((Monster) ChainHandler.getLastChain().getSelectedCard().getCard()).getAttack());
        }
    }

    private void negateAttackEffect() {
        Chain previous = ChainHandler.getLastChain();
        if (previous.getSelectedCard().getCard() != null) {
            sendToGraveYard(previous.getSelectedCard().getCard(), false);
        }
        game.nextPhase();
    }

    private void mirrorForceEffect() {
        game.getBoard().getRivalPlayer().getMonsterZone().getCells().forEach(cell -> {
            if (!cell.isEmpty() && cell.getCard().getState() == State.OFFENSIVE_OCCUPIED) {
                sendToGraveYard(cell.getCard(), false);
                cell.removeCard();
            }
        });
    }

    private void applyNormalEffect(Card card, Game game, JSONObject request) {
        switch (card.getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel())) {
            case "destroy":
                switch (card.getEffectValue(EffectsEnum.FROM.getLabel())) {
                    case "both":
                        switch (card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())) {
                            case "monsters":
                                for (Cell cell : game.getBoard().getMainPlayer().getMonsterZone().getCells()) {
                                    if (!cell.isEmpty())
                                        sendToGraveYard(cell.getCard(), true);
                                }
                                break;
                            case "spells/traps":
                                for (Cell cell : game.getBoard().getMainPlayer().getSpellZone().getCells()) {
                                    if (!cell.isEmpty())
                                        sendToGraveYard(cell.getCard(), true);
                                }
                                break;
                        }
                    case "rival board":
                        switch (card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())) {
                            case "monsters":
                                for (Cell cell : game.getBoard().getRivalPlayer().getMonsterZone().getCells()) {
                                    if (!cell.isEmpty())
                                        sendToGraveYard(cell.getCard(), false);
                                }
                                break;
                            case "spells/traps":
                                for (Cell cell : game.getBoard().getRivalPlayer().getSpellZone().getCells()) {
                                    if (!cell.isEmpty())
                                        sendToGraveYard(cell.getCard(), false);
                                }
                                break;
                        }
                }
                break;
            case "draw":
                for (int i = 0; i < Integer.parseInt(card.getEffectValue(EffectsEnum.NUMBER_OF_AFFECTED_CARDS.getLabel())); i++) {
                    game.getBoard().getMainPlayer().drawCard();
                }
                break;
            case "add": // add from deck to hand
                for (int i = 0; i < Integer.parseInt(card.getEffectValue(EffectsEnum.NUMBER_OF_AFFECTED_CARDS.getLabel())); i++) {
                    Card cardToAdd = game.getBoard().getMainPlayer().getPlayingDeck().
                            getACard(Property.fromValue(card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())));
                    if (cardToAdd != null)
                        game.getBoard().getMainPlayer().getHand().addCard(cardToAdd);
                }
                break;
            case "skip draw phase":
                game.getBoard().getRivalPlayer().getTurnLogger().setCanDrawCard(false);
                break;

        }

    }

    private void equipCard(Card spell, Monster monster) {
        System.out.println((EffectsEnum.SECOND_ATTACK_DEFENSE_LIFE_POINT.getLabel()));
        int amount = calculateAmount(spell, monster, EffectsEnum.FIRST_AMOUNT.getLabel());
        changeBoostersToEquip(monster, amount, spell.getEffectValue(EffectsEnum.FIRST_ATTACK_DEFENSE_LIFE_POINT.getLabel()));
        if (!spell.getEffectValue(EffectsEnum.SECOND_TARGET.getLabel()).equals(Strings.NONE.getLabel())) {
            amount = calculateAmount(spell, monster, EffectsEnum.SECOND_AMOUNT.getLabel());
            changeBoostersToEquip(monster, amount, spell.getEffectValue(EffectsEnum.SECOND_ATTACK_DEFENSE_LIFE_POINT.getLabel()));
        }
    }


    private int calculateAmount(Card spell, Monster monster, String amountString) {
        int amount;
        System.out.println(amountString);
        System.out.println(spell.getEffectValue(amountString));
        switch (spell.getEffectValue(amountString)) {
            case "player face up monsters each +800":
                amount = 800 * game.getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells();
                break;
            case "!=":
                if (monster.getState() == State.OFFENSIVE_OCCUPIED) amount = monster.getDefence();
                else amount = monster.getAttack();
                break;
            default:
                amount = Integer.parseInt(spell.getEffectValue(amountString));
        }
        return amount;
    }

    private void changeBoostersToEquip(Monster monster, int amount, String whichFeature) {
        switch (whichFeature) {
            case "attack/defense":
                monster.changeDefenseBooster(amount);
            case "attack":
                monster.changeAttackBooster(amount);
                break;
            case "relative":
                if (monster.getState() == State.OFFENSIVE_OCCUPIED) {
                    monster.changeAttackBooster(amount);
                    break;
                }
            case "defense":
                monster.changeDefenseBooster(amount);
                break;
        }
    }

    private String directAttack(Duel duel) {
        Game game = duel.getGame();
        Monster card = (Monster) game.getSelectedCard().getCard();
        int damage = card.getAttack();
        game.getBoard().getMainPlayer().getTurnLogger().cardAttack(card);
        damage(true, damage);
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
        Cell selectedCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(game.getSelectedCard().getCard().getPositionIndex());
        game.getBoard().getMainPlayer().getTurnLogger().cardAttack(selectedCell.getCard());
        int damage;
        String opponentCardName = "";
        switch (rivalCard.getCard().getState()) {
            case DEFENSIVE_HIDDEN:
                opponentCardName = String.format(Strings.DH_ATTACK_EQUAL.getLabel(), rivalCard.getCard().getName());
            case DEFENSIVE_OCCUPIED:
                damage = ((Monster) rivalCard.getCard()).getDefence() -
                         ((Monster) selectedCell.getCard()).getAttack();
                if (damage > 0) {
                    damage(false, damage);
                    game.getBoard().getMainPlayer().getTurnLogger().cardAttack(selectedCell.getCard());
                    String firstResponse = opponentCardName + String.format(Strings.DO_ATTACK_MORE.getLabel(), damage);
                    if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
                    return opponentCardName + String.format(Strings.DO_ATTACK_MORE.getLabel(), damage);
                }
                if (damage < 0) {
                    sendToGraveYard(rivalCard.getCard(), false);
                    rivalCard.removeCard();
                    return opponentCardName + String.format(Strings.DO_ATTACK_LESS.getLabel(), damage);
                }
                sendToGraveYard(rivalCard.getCard(), false);
                sendToGraveYard(selectedCell.getCard(), true);
                rivalCard.removeCard();
                selectedCell.removeCard();
                return opponentCardName + Strings.DO_ATTACK_EQUAL.getLabel();
            case OFFENSIVE_OCCUPIED:
                damage = ((Monster) selectedCell.getCard()).getAttack() -
                         ((Monster) rivalCard.getCard()).getAttack();
                if (damage > 0) {
                    damage(true, damage);
                    sendToGraveYard(rivalCard.getCard(), false);
                    rivalCard.removeCard();
                    String firstResponse = String.format(Strings.OO_ATTACK_MORE.getLabel(), damage);
                    if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
                    return String.format(Strings.OO_ATTACK_MORE.getLabel(), damage);
                }
                if (damage < 0) {
                    damage(false, damage);
                    sendToGraveYard(selectedCell.getCard(), true);
                    selectedCell.removeCard();
                    String firstResponse = String.format(Strings.OO_ATTACK_LESS.getLabel(), damage);
                    if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
                    return String.format(Strings.OO_ATTACK_LESS.getLabel(), damage);
                }
                sendToGraveYard(selectedCell.getCard(), true);
                sendToGraveYard(rivalCard.getCard(), false);
                selectedCell.removeCard();
                rivalCard.removeCard();
                return String.format(Strings.OO_ATTACK_EQUAL.getLabel(), damage);

        }

        return null;
    }

    private void sendToGraveYard(Card card, boolean main) {
        Player player = main ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        if (card.getCardType() == CardType.MONSTER) {
            for (Card spell : ((Monster) card).getAffectedCards()) {
                if (spell.getProperty() == Property.EQUIP || "connected".matches(spell.getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel()))) {
                    player.getSpellZone().getCell(spell.getPositionIndex()).removeCard();
                    player.getGraveYard().addCard(spell);
                }
            }
            ((Monster) card).destroy();
            player.getGraveYard().addCard(card);
            player.getMonsterZone().getCell(card.getPositionIndex()).removeCard();
        } else if (card.getProperty() == Property.EQUIP) {
            player.getMonsterZone().getCells().forEach(cell -> {
                if (!cell.isEmpty()) {
                    Monster monster = (Monster) cell.getCard();
                    if (monster.getAffectedCards().contains(card)) {
                        ((Monster) cell.getCard()).destroy();
                        player.getGraveYard().addCard(cell.getCard());
                        cell.removeCard();
                    }
                }
            });
            player.getGraveYard().addCard(card);
            player.getSpellZone().getCell(card.getPositionIndex()).removeCard();
        } else if (card.getCardType() == CardType.SPELL) {
            player.getGraveYard().addCard(card);
            player.getSpellZone().getCell(card.getPositionIndex()).removeCard();
        }

    }

    public void damage(boolean toOpponent, int damage) {
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
            (toOpponent ? rivalPlayer : mainPlayer).decreaseLifePoint(Math.abs(damage));
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
            selectedCard.getCard().setPosition(Position.MONSTER_ZONE);
            game.getBoard().getMainPlayer().getMonsterZone().placeCard(selectedCard.getCard());
        } else {
            selectedCard.getCard().setState(State.HIDDEN);
            selectedCard.getCard().setPosition(Position.SPELL_ZONE);
            game.getBoard().getMainPlayer().getSpellZone().placeCard(selectedCard.getCard());
        }

        removeFromHand(selectedCard, duel);
        if (selectedCard.getCard().getCardType() == CardType.MONSTER)
            game.getBoard().getMainPlayer().getTurnLogger().cardAdded(selectedCard.getCard());
        game.deselect();
        return Strings.SET_SUCCESSFULLY.getLabel();
    }

    private void removeFromHand(SelectedCard selectedCard, Duel duel) {
        Game game = duel.getGame();
        game.getBoard().getMainPlayer().getHand().remove(selectedCard.getCard());
    }

    private String flipSummon(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        SelectedCard selectedCard = game.getSelectedCard();

        selectedCard.getCard().setState(State.OFFENSIVE_OCCUPIED);
        selectedCard.getCard().setPosition(Position.MONSTER_ZONE);

        game.deselect();

        return Strings.FLIP_SUMMON_SUCCESSFULLY.getLabel();
    }

    private String summon(JSONObject request, Duel duel) {
        Game game = duel.getGame();

        Monster monster = (Monster) game.getSelectedCard().getCard();

        int level = monster.getLevel();

        if (level == 5 || level == 6) {
            tribute(Integer.parseInt(request.getString("data")), 0, duel);
        } else if (level == 7 || level == 8) {
            tribute(Integer.parseInt(request.getString("data").split(" ")[0]),
                    Integer.parseInt(request.getString("data").split(" ")[1]), duel);
        }

        removeFromHand(game.getSelectedCard(), duel);
        game.getSelectedCard().getCard().setState(State.OFFENSIVE_OCCUPIED);
        game.getSelectedCard().getCard().setPosition(Position.MONSTER_ZONE);
        game.getBoard().getMainPlayer().getMonsterZone().placeCard(game.getSelectedCard().getCard());
        game.getBoard().getMainPlayer().getTurnLogger().cardAdded(game.getSelectedCard().getCard());
        game.deselect();
        return Strings.SUMMON_SUCCESSFULLY.getLabel();

    }

    private void tribute(int first, int second, Duel duel) {
        Game game = duel.getGame();

        Cell tributeCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(first);
        tributeCell.removeCard();
        game.getBoard().getMainPlayer().getGraveYard().addCard(tributeCell.getCard());

        if (second != 0) {
            tributeCell = game.getBoard().getMainPlayer().getMonsterZone().getCell(second);
            tributeCell.removeCard();
            game.getBoard().getMainPlayer().getGraveYard().addCard(tributeCell.getCard());
        }
    }

    private String select(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        String area = request.getString(Strings.AREA.getLabel());
        boolean isOpponent = request.getBoolean(Strings.OPPONENT_OPTION.getLabel());
        Player player = isOpponent ? game.getBoard().getRivalPlayer() : game.getBoard().getMainPlayer();
        int position;
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

        game.setSelectedCard(new SelectedCard(card, isOpponent));
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
        game.getBoard().getMainPlayer().getTurnLogger().cardPositionChanged(game.getSelectedCard().getCard());
        if (request.getString(Strings.POSITION.getLabel()).equals(Strings.ATTACK_OPTION.getLabel())) {
            game.getSelectedCard().getCard().setState(State.OFFENSIVE_OCCUPIED);
        } else
            game.getSelectedCard().getCard().setState(State.DEFENSIVE_OCCUPIED);
        game.deselect();
        return Strings.MONSTER_POSITION_CHANGED.getLabel();
    }
}
