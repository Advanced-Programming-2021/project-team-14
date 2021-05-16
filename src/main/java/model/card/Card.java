package model.card;

import model.Strings;
import model.card.enums.CardType;
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
    protected State state;
    public abstract String show();

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Card(String name, String description, CardType cardType, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cardType = cardType;
    }
    public Card(Card card){
        this.name = card.getName();
        this.description = card.getDescription();
        this.cardType = card.getCardType();
        this.price = card.getPrice();
        this.effects = card.getEffects();
    }
    public static ArrayList<Card> getCards() {
        return new ArrayList<>(cards.values());
    }

//    public static String getCard(int index) {
//        Object[] values = cards.values().toArray();
//        Card randomValue = (Card) values[index];
//        return randomValue.getName();
//    }

    public static void addCard(Card card) {
        cards.put(card.getName(), card);
    }

    public static Card getCardByName(String cardName) {
        return cards.get(cardName);
    }

    public static boolean doesCardExist(String cardName) {
        return cards.get(cardName) != null;
    }

    public String getDescription() {
        return description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public HashMap<String, String> getEffects() {
        return effects;
    }

    @Override
    public String toString() {
        return String.format(Strings.CARD_PRINT_FORMAT.getLabel(), name, description);
    }
}
