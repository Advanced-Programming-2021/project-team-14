package Controller.Handlers;

import Controller.enums.EffectsEnum;
import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
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
        if (!main.isEmpty()){
            applyEffect(main.getCard(), true);
        }
        if (!rival.isEmpty()){
            applyEffect(rival.getCard(), true);
        }
    }

    private static void applyEffect(Card card, boolean activate) {
        for (Cell cell : game.getBoard().getMainPlayer().getMonsterZone().getCells()) {
            Monster monster = ((Monster) cell.getCard());
            if (monster != null && !monster.isAffectedBy(card)){
                apply(card, monster, activate);
            }
        }
    }

    private static void apply(Card card, Monster monster, boolean active) {
        monster.addAffectedCard(card);
        int amount;
        if (monster.getMonsterType().getLabel().matches(card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel()))){
            amount = getAmount(card.getEffectValue(EffectsEnum.FIRST_AMOUNT.getLabel()));
            changeBoosters(card.getEffectValue(EffectsEnum.FIRST_ATTACK_DEFENSE_LIFE_POINT.getLabel()), monster, active ? amount : -amount);
        }
        if (monster.getMonsterType().getLabel().matches(card.getEffectValue(EffectsEnum.SECOND_TARGET.getLabel()))){
            amount = getAmount(card.getEffectValue(EffectsEnum.SECOND_AMOUNT.getLabel()));
            changeBoosters(card.getEffectValue(EffectsEnum.SECOND_ATTACK_DEFENSE_LIFE_POINT.getLabel()), monster, active ? amount : -amount);
        }
    }

    private static void changeBoosters(String whichOne, Monster monster, int amount) {
        switch (whichOne){
            case "attack|defense":
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
        if (effectValue.equals("graveyard monster each+100")) return 100; //TODO: graveYard * 100 and many more ...
        return Integer.parseInt(effectValue);
    }

    public static void deActive() {
        applyEffect(main.getCard(), true);
    }
}
