package Controller.enums;

public enum EffectsEnum {
    NUMBER_OF_AFFECTED_CARDS("number of affected cards"),
    EFFECT_TIME("effect time"),
    CHANGE_DESTROY_ADD_CARD("change/destroy/add card"),
    FROM("from"),
    TO("to"),
    SPECIAL_SUMMON("special summon"),
    FIRST_ATTACK_DEFENSE_LIFE_POINT("first attack/defense/lifePoint"),
    FIRST_TARGET("first target"),
    FIRST_AMOUNT("first amount"),
    SECOND_ATTACK_DEFENSE_LIFE_POINT("second attack/defense/lifePoint"),
    SECOND_TARGET("second target"),
    SECOND_AMOUNT("second amount"),
    SPECIAL_COMMAND("special command"),
    EFFECT_DURATION("effect duration"),
    ATTACK_DEFENSE("attack/defense"),
    STATUS("status"),
    ATTACKED_CARDS("attacked cards"),
    ATTACK_LIFE_POINT_DEFENSE("attack /lifePoint/defense"),
    CHANGE_AMOUNT("change amount"),
    TRIBUTING_AMOUNT("tributing amount"),
    EFFECT("Effect"),
    NO_EFFECTS("nothing");


    public final String label;


    EffectsEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
