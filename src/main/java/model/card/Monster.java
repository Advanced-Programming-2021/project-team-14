package model.card;

import model.card.enums.Attribute;
import model.card.enums.CardType;
import model.card.enums.MonsterCardType;
import model.card.enums.MonsterType;

public class Monster extends Card {
    private int level;
    private int attack, defence;
    private Attribute attribute;
    private MonsterCardType monsterCardType;
    private MonsterType monsterType;

    public Monster(String name, int level, Attribute attribute, MonsterType monsterType, MonsterCardType monsterCardType, int attack, int defence, String description, int price) {
        super(name, description, CardType.fromValue("Monster"), price);
        this.attack = attack;
        this.defence = defence;
        this.level = level;
        this.monsterCardType = monsterCardType;
        this.monsterType = monsterType;
        addCard(this);
    }

    public MonsterCardType getMonsterCardType() {
        return monsterCardType;
    }

}
