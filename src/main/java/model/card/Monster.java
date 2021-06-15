package model.card;

import model.card.enums.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Monster extends Card {
    private int level;
    private int attack, defence, attackBooster, defenseBooster;
    private Attribute attribute;
    private MonsterType monsterType;
    private ArrayList<Card> affectedCards;
    private static ArrayList<Monster> monsters;

    public void changeAttackBooster(int amount){
        attackBooster += amount;
    }
    public void changeDefenseBooster(int amount){
        defenseBooster+= amount;
    }

    static {
        monsters = new ArrayList<>();
    }

    public Monster(String name, int level, Attribute attribute, MonsterType monsterType, Property monsterCardType,
                   int attack, int defence, String description, int price, HashMap<String, String> effects) {
        super(name, description, CardType.fromValue("Monster"), price);
        this.attack = attack;
        this.defence = defence;
        this.level = level;
        this.property = monsterCardType;
        this.monsterType = monsterType;
        this.effects = effects;
        affectedCards = new ArrayList<>();
        addCard(this);
        monsters.add(this);
    }
    public void addAffectedCard(Card card){
        affectedCards.add(card);
    }
    public boolean isAffectedBy(Card card){
        return affectedCards.contains(card);
    }

    public ArrayList<Card> getAffectedCards() {
        return affectedCards;
    }

    public void destroy(){
        affectedCards.clear();
        defenseBooster = 0;
        attackBooster = 0;
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

    public static ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public int getAttack() {
        return attack + attackBooster;
    }

    public int getLevel() {
        return level;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getDefence() {
        return defence + defenseBooster;
    }


    public Monster(Monster card) {
        super(card);
        this.attack = card.getAttack();
        this.defence = card.getDefence();
        this.attribute = card.getAttribute();
        this.level = card.getLevel();
        this.property = card.getProperty();
        this.monsterType = card.getMonsterType();
        this.effects = card.getEffects();
    }


}
