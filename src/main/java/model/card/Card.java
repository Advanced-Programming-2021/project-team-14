package model.card;

import model.card.enums.CardType;

import java.util.HashMap;

public abstract class Card {
    private static HashMap<String, Card> cards;

    static {
        cards = new HashMap<>();
    }

    protected String name;
    protected CardType cardType;
    protected String description;
    protected int price;

    public Card(String name, String description, CardType cardType, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cardType = cardType;
    }

    public static void addCard(Card card) {
        cards.put(card.getName(), card);
    }

    public String getName() {
        return name;
    }
}
