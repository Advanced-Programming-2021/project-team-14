package server.controller.Handlers;

import Controller.enums.EffectsEnum;
import model.card.Card;
import model.card.Monster;
import model.card.enums.State;
import model.game.Cell;
import model.game.FieldZone;
import model.game.Game;

public class FieldController {
    private static Game game;
    private static FieldZone main, rival;

    public static void handle(Game game) {
        FieldController.game = game;
        FieldController.main = game.getBoard().getMainPlayer().getFieldZone();
        FieldController.rival = game.getBoard().getRivalPlayer().getFieldZone();
        if (!main.isEmpty()) {
            applyEffect(main.getCard(), true);
        }
        if (!rival.isEmpty()) {
            applyEffect(rival.getCard(), true);
        }
        handleUpdatingCards();
    }
    private static void handleUpdatingCards(){
        int amount = game.getBoard().getRivalPlayer().getMonsterZone().getCellsByState(State.OFFENSIVE_OCCUPIED);
        for (Card card : game.getBoard().getRivalPlayer().getMonsterZone().getCards("Update"))
            update(card, amount);
        amount = game.getBoard().getMainPlayer().getMonsterZone().getCellsByState(State.OFFENSIVE_OCCUPIED);
        for (Card card : game.getBoard().getMainPlayer().getMonsterZone().getCards("Update"))
            update(card, amount);
    }

    private static void update(Card card, int amount) {
        ((Monster)card).setAttack(amount * Integer.parseInt(card.getEffectValue(EffectsEnum.CHANGE_AMOUNT.getLabel())));
    }

    private static void applyEffect(Card card, boolean activate) {
        switch (card.getEffectValue(EffectsEnum.FROM.getLabel())) {
            case "both":
                for (Cell cell : game.getBoard().getRivalPlayer().getMonsterZone().getCells()) {
                    Monster monster = ((Monster) cell.getCard());
                    if (monster != null && !monster.isAffectedBy(card)) {
                        apply(card, monster, activate);
                        monster.addAffectedCard(card);
                    }
                }
            case "player board":
                if (!main.isEmpty() && game.getBoard().getMainPlayer().getFieldZone().getCard().equals(card)) {
                    for (Cell cell : game.getBoard().getMainPlayer().getMonsterZone().getCells()) {
                        Monster monster = ((Monster) cell.getCard());
                        if (monster != null && (activate != monster.isAffectedBy(card))) {
                            apply(card, monster, activate);
                            monster.addAffectedCard(card);

                        }
                    }
                }
                break;
        }
    }

    private static void apply(Card card, Monster monster, boolean active) {

        int amount;
        if (monster.getMonsterType().getLabel().matches(card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel()))) {
            amount = getAmount(card.getEffectValue(EffectsEnum.FIRST_AMOUNT.getLabel()));
            changeBoosters(card.getEffectValue(EffectsEnum.FIRST_ATTACK_DEFENSE_LIFE_POINT.getLabel()), monster, active ? amount : -amount);
        }
        if (monster.getMonsterType().getLabel().matches(card.getEffectValue(EffectsEnum.SECOND_TARGET.getLabel()))) {
            amount = getAmount(card.getEffectValue(EffectsEnum.SECOND_AMOUNT.getLabel()));
            changeBoosters(card.getEffectValue(EffectsEnum.SECOND_ATTACK_DEFENSE_LIFE_POINT.getLabel()), monster, active ? amount : -amount);
        }
    }

    private static void changeBoosters(String whichOne, Monster monster, int amount) {
        System.out.println(amount);
        switch (whichOne) {
            case "attack/defense":
                monster.changeDefenseBooster(amount);
            case "attack":
                monster.changeAttackBooster(amount);
                break;
            case "defense":
                monster.changeDefenseBooster(amount);
                break;
        }
    }

    private static int getAmount(String effectValue) {
        if (effectValue.equals("graveyard monster each+100")) return game.getBoard().getMainPlayer().getGraveYard().getSize() * 100;
        return Integer.parseInt(effectValue);
    }

    public static void deActive() {
        applyEffect(main.getCard(), false);
    }
}
