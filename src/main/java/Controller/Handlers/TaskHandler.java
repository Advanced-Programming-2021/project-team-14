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
import view.enums.CommandTags;

import java.util.Objects;

public class TaskHandler extends GameHandler {


    public String handle(JSONObject request, Duel duel) {
        set(request, duel);
        Response.success();
        switch (Objects.requireNonNull(CommandTags.fromValue(request.getString(Strings.COMMAND.getLabel())))) {
            case SELECT:
                return select();
            case SHOW_SELECTED_CARD:
                return showSelectedCard();
            case SET:
                return set();
            case NEXT_PHASE:
                return nextPhase();
            case SET_POSITION:
                return setPosition();
            case SHOW_GRAVEYARD:
                return showGraveyard();
            case DESELECT:
                return deselect();
            case SUMMON:
                return summon();
            case ATTACK:
                return attack();
            case DIRECT_ATTACK:
                return directAttack();
            case FLIP_SUMMON:
                return flipSummon();
            case ACTIVATE_EFFECT:
                return activateEffect();
            case INCREASE_LIFE_POINT:
                return increaseLifePoint();
        }
        return Strings.DONE.getLabel();
    }

    private String increaseLifePoint() {
        main.increaseLifePoint(request.getInt(Strings.AMOUNT.getLabel()));
        return Responses.INCREASE_LIFE_POINT.getLabel();
    }

    private String activateEffect() {

        selected.getCard().setState(State.OCCUPIED);

        if (selected.getCard().getProperty() == Property.EQUIP) {
            equipCard(selected.getCard(), (Monster) main.getMonsterZone().getCell(Integer.parseInt(request.getString("data"))).getCard());
            ((SpellTrap) selected.getCard()).setActivated(true);
        }
        if (selected.getCard().getProperty() == Property.FIELD) {
            addCardToFieldZone();
            ((SpellTrap) selected.getCard()).setActivated(true);
        } else {
            if (specialCaseEffects() == null) {
                applyNormalEffect(selected.getCard());
            }else{
                ((SpellTrap) selected.getCard()).setActivated(true);
            }

            if (selected.getCard() != null && selected.getCard().getPosition() != Position.SPELL_ZONE) {
                selected.getCard().setPosition(Position.SPELL_ZONE);
                removeFromHand(selected);
                main.getSpellZone().placeCard(selected.getCard());
            }
        }
        game.deselect();
        return Strings.ACTIVATE_SUCCESSFULLY.getLabel();
    }

    private void addCardToFieldZone() {
        selected.getCard().setPosition(Position.FIELD_ZONE);
        if (!main.getFieldZone().isEmpty()) {
            FieldController.deActive();
        }
        removeFromHand(selected);
        main.getFieldZone().setCard(selected.getCard());
    }

    private String specialCaseEffects() {
        final String SUCCESS = "done";
        String toDo = selected.getCard().getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel());
        if (toDo.equals("change owner")) {
            changeOwner();

            return SUCCESS;
        }

        if (toDo.equals("destroy card")) {
            destroyACard();
            return SUCCESS;
        }

        if (selected.getCard().getEffectValue(EffectsEnum.EFFECT_TIME.getLabel()).equals("entered name exist")) {
            discardIfIsAbsent();
            return SUCCESS;
        }

        switch (toDo) {
            case "Mirror Force":
                mirrorForceEffect();
                return SUCCESS;
            case "Magic Cylinder":
                magicCylinderEffect();
                return SUCCESS;
            case "Negate Attack":
                negateAttackEffect();
                return SUCCESS;
            case "Magic Jammer":
                magicJammerEffect();
                return SUCCESS;
        }
        return null;

    }

    private void discardIfIsAbsent() {
        if (rival.getHand().doesHaveCard(request.getString("data"))) {
            String name = request.getString("data");
            rival.getHand().remove(name);
            rival.getMonsterZone().remove(name);
            rival.getPlayingDeck().remove(name);
        } else {
            Hand hand = main.getHand();
            selected.getCard().setPosition(Position.SPELL_ZONE);
            hand.removeRandomly();
        }
    }

    private void destroyACard() {
        Card selectedToDestroy = rival.getSpellZone().getCell(request.getInt("data")).getCard();
        if (selectedToDestroy != null) {
            rival.getSpellZone().remove(selectedToDestroy.getName());
            rival.getGraveYard().addCard(selectedToDestroy);
        }
    }

    private void changeOwner() {
        Cell rivalCell = rival.getMonsterZone().getCell(request.getInt("data"));
        main.getMonsterZone().placeCard(rivalCell.getCard());
        main.getTurnLogger().addChangedOwnerCards(rivalCell.getCard());
        rivalCell.removeCard();
    }

    private void magicJammerEffect() {
        Card card = ChainHandler.getLastChain().getSelectedCard().getCard();
        Cell cell = rival.getSpellZone().getCell(card.getPositionIndex());
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
        rival.getMonsterZone().getCells().forEach(cell -> {
            if (!cell.isEmpty() && cell.getCard().getState() == State.OFFENSIVE_OCCUPIED) {
                sendToGraveYard(cell.getCard(), false);
                cell.removeCard();
            }
        });
    }

    private void applyNormalEffect(Card card) {
        switch (card.getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel())) {
            case "destroy":
                switch (card.getEffectValue(EffectsEnum.FROM.getLabel())) {
                    case "both":
                        switch (card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())) {
                            case "monsters":
                                destroyAllMonsters(main.getMonsterZone(), true);
                                break;
                            case "spells/traps":
                                destroyAllMonsters(main.getSpellZone(), true);
                                break;
                        }
                    case "rival board":
                        switch (card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())) {
                            case "monsters":
                                destroyAllMonsters(rival.getMonsterZone(), false);
                                break;
                            case "spells/traps":
                                destroyAllMonsters(rival.getSpellZone(), false);
                                break;
                        }
                }
                break;
            case "draw":
                drawNCard(Integer.parseInt(card.getEffectValue(EffectsEnum.NUMBER_OF_AFFECTED_CARDS.getLabel())));
                break;
            case "add": // add from deck to hand
                addFromDeckToHand(Integer.parseInt(card.getEffectValue(EffectsEnum.NUMBER_OF_AFFECTED_CARDS.getLabel())),
                        Property.fromValue(card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())));
                break;
            case "skip draw phase":
                rival.getTurnLogger().setCanDrawCard(false);
                break;
            default: return;
        }
        ((SpellTrap) selected.getCard()).setActivated(true);
    }

    private void addFromDeckToHand(int numberOfCardsToAdd, Property property) {
        for (int i = 0; i < numberOfCardsToAdd; i++) {
            Card cardToAdd = main.getPlayingDeck().getACard(property);
            if (cardToAdd != null)
                main.getHand().addCard(cardToAdd);
        }
    }

    private void drawNCard(int n) {
        for (int i = 0; i < n; i++) {
            main.drawCard();
        }
    }

    private void destroyAllMonsters(Zone zone, boolean isMain) {
        for (Cell cell : zone.getCells()) {
            if (!cell.isEmpty())
                sendToGraveYard(cell.getCard(), isMain);
        }
    }

    private void equipCard(Card spell, Monster monster) {
        monster.addAffectedCard(selected.getCard());
        int amount = calculateAmount(spell, monster, EffectsEnum.FIRST_AMOUNT.getLabel());
        changeBoostersToEquip(monster, amount, spell.getEffectValue(EffectsEnum.FIRST_ATTACK_DEFENSE_LIFE_POINT.getLabel()));
        if (!spell.getEffectValue(EffectsEnum.SECOND_TARGET.getLabel()).equals(Strings.NONE.getLabel())) {
            amount = calculateAmount(spell, monster, EffectsEnum.SECOND_AMOUNT.getLabel());
            changeBoostersToEquip(monster, amount, spell.getEffectValue(EffectsEnum.SECOND_ATTACK_DEFENSE_LIFE_POINT.getLabel()));
        }
    }


    private int calculateAmount(Card spell, Monster monster, String amountString) {
        switch (spell.getEffectValue(amountString)) {
            case "player face up monsters each +800":
                return 800 * main.getMonsterZone().getNumberOfFullCells();
            case "!=":
                if (monster.getState() == State.OFFENSIVE_OCCUPIED) return monster.getDefence();
                return monster.getAttack();
            default:
                return Integer.parseInt(spell.getEffectValue(amountString));
        }
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

    private String directAttack() {
        Monster card = (Monster) selected.getCard();
        int damage = card.getAttack();
        main.getTurnLogger().cardAttack(card);
        damage(true, damage);
        game.deselect();
        String firstResponse = String.format(Strings.DIRECT_ATTACK.getLabel(), damage);
        if (duel.endDuelChecker()) return duel.endDuel(firstResponse);
        return String.format(Strings.DIRECT_ATTACK.getLabel(), damage);
    }

    private String deselect() {
        game.deselect();
        return Strings.CARD_DESELECTED.getLabel();
    }

    private String attack() {
        Cell rivalCard = rival.getMonsterZone().getCell(request.getInt(Strings.TO.getLabel()));
        Cell selectedCell = main.getMonsterZone().getCell(selected.getCard().getPositionIndex());
        main.getTurnLogger().cardAttack(selectedCell.getCard());

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

    private void sendToGraveYard(Card card, boolean isMain) {
        Player player = isMain ? main : rival;
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

        boolean isEnded = false;

        if ((toOpponent ? rival : main).getLifePoint() <= damage) {
            (toOpponent ? rival : main).setLifePoint(0);
            isEnded = true;
            game.endGame((toOpponent ? main : rival), (toOpponent ? rival : main));
            duel.startNewRound();
        }

        if (!isEnded) {
            (toOpponent ? rival : main).decreaseLifePoint(Math.abs(damage));
        }
    }


    private String nextPhase() {
        return game.nextPhase();
    }

    private String set() {
        SelectedCard selectedCard = selected;
        if (selectedCard.getCard().getCardType() == CardType.MONSTER) {
            selectedCard.getCard().setState(State.DEFENSIVE_HIDDEN);
            selectedCard.getCard().setPosition(Position.MONSTER_ZONE);
            main.getMonsterZone().placeCard(selectedCard.getCard());
        } else {
            selectedCard.getCard().setState(State.HIDDEN);
            selectedCard.getCard().setPosition(Position.SPELL_ZONE);
            main.getSpellZone().placeCard(selectedCard.getCard());
        }

        removeFromHand(selectedCard);
        if (selectedCard.getCard().getCardType() == CardType.MONSTER)
            main.getTurnLogger().cardAdded(selectedCard.getCard());
        game.deselect();
        return Strings.SET_SUCCESSFULLY.getLabel();
    }

    private void removeFromHand(SelectedCard selectedCard) {
        main.getHand().remove(selectedCard.getCard());
    }

    private String flipSummon() {
        selected.getCard().setState(State.OFFENSIVE_OCCUPIED);
        selected.getCard().setPosition(Position.MONSTER_ZONE);
        game.deselect();
        return Strings.FLIP_SUMMON_SUCCESSFULLY.getLabel();
    }

    private String summon() {
        Monster monster = (Monster) selected.getCard();

        if (monster.getLevel() > 4) {
            for (String data : request.getString("data").split(" ")) {
                tribute(Integer.parseInt(data));
            }
        }

        removeFromHand(selected);
        selected.getCard().setState(State.OFFENSIVE_OCCUPIED);
        selected.getCard().setPosition(Position.MONSTER_ZONE);
        main.getMonsterZone().placeCard(selected.getCard());
        main.getTurnLogger().cardAdded(selected.getCard());
        game.deselect();
        return Strings.SUMMON_SUCCESSFULLY.getLabel();
    }

    private void tribute(int positionIndex) {
        Cell tributeCell = main.getMonsterZone().getCell(positionIndex);
        main.getGraveYard().addCard(tributeCell.getCard());
        tributeCell.removeCard();
    }

    private String select() {
        String area = request.getString(Strings.AREA.getLabel());
        boolean isOpponent = request.getBoolean(Strings.OPPONENT_OPTION.getLabel());
        Player player = isOpponent ? rival : main;
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

    private String showSelectedCard() {
        return selected.getCard().show();
    }

    private String showGraveyard() {
        return main.getGraveYard().showCards();
    }

    private String setPosition() {
        main.getTurnLogger().cardPositionChanged(selected.getCard());
        if (request.getString(Strings.POSITION.getLabel()).equals(Strings.ATTACK_OPTION.getLabel())) {
            selected.getCard().setState(State.OFFENSIVE_OCCUPIED);
        } else
            selected.getCard().setState(State.DEFENSIVE_OCCUPIED);
        game.deselect();
        return Strings.MONSTER_POSITION_CHANGED.getLabel();
    }
}
