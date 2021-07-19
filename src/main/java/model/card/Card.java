package model.card;

import model.Strings;
import model.card.enums.CardType;
import model.card.enums.Position;
import model.card.enums.Property;
import model.card.enums.State;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Card {
    private static final HashMap<String, Card> cards;

    static {
        cards = new HashMap<>();
    }

    protected HashMap<String, String> effects;
    protected String name;
    protected CardType cardType;
    protected String description;
    protected int price;
    protected int number;
    protected State state;
    protected Property property;
    protected int positionIndex;
    protected Position position;

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Card(String name, String description, CardType cardType, int price) {
        this.number = 3;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cardType = cardType;
        this.position = Position.DECK;
        this.positionIndex = -1;
    }

    public static ArrayList<Card> getCards() {
        return new ArrayList<>(cards.values());
    }

    public static void addCard(Card card) {
        cards.put(card.getName(), card);
    }

    public abstract String show();

    public abstract String getDescriptionGraphic();

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public static Card getCardByName(String cardName) {
        return cards.get(cardName);
    }

    public Card(Card card) {
        this.name = card.getName();
        this.description = card.getDescription();
        this.cardType = card.getCardType();
        this.price = card.getPrice();
        this.effects = card.getEffects();
        this.position = Position.DECK;
        this.positionIndex = -1;
    }

    public static boolean doesCardExist(String cardName) {
        return cards.get(cardName) != null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void changeNumber(int amount) {

        this.number += amount;
    }

    public String getDescription() {
        return description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Property getProperty() {
        return property;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getEffects() {
        return effects;
    }

    public String getEffectValue(String key){
        return getEffects().get(key);
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format(Strings.CARD_PRINT_FORMAT.getLabel(), name, description);
    }
}