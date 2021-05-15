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

    @Override
    public String show() {
        return showCard.getHorizontalLine() + showCard.getTypeLine(getCardType().getLabel()) +
                showCard.getMonsterTypeLine(getMonsterType().getLabel()) +
                showCard.getFreeLine() + showCard.getNameLine(getName()) +
                showCard.getPriceAndLevelLine(getPrice(), getLevel()) +
                showCard.getAttackAndDefenseLine(getAttack(), getDefence()) +
                showCard.getFreeLine() + showCard.getDescriptionLine(getDescription()) +
                showCard.getHorizontalLine();
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public int getAttack() {
        return attack;
    }

    public int getLevel() {
        return level;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getDefence() {
        return defence;
    }

    public Monster(Monster card) {
        super(card);
        this.attack = card.getAttack();
        this.defence = card.getDefence();
        this.attribute = card.getAttribute();
        this.level = card.getLevel();
        this.monsterCardType = card.getMonsterCardType();
        this.monsterType = card.getMonsterType();
    }

    public MonsterCardType getMonsterCardType() {
        return monsterCardType;
    }

}
