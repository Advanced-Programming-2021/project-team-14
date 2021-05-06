package model.card;

import model.card.enums.CardType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

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

    public static Card getCardByName(String cardName) {
        return cards.get(cardName);
    }

    public static ArrayList<String> getCardsNameAndDescription() {
        SortedSet<String> keys = new TreeSet<>(cards.keySet()); //sort keys
        return new ArrayList<String>(keys);
    }

    public static boolean doesCardExist(String cardName) {
        if (cards.get(cardName) == null){
            return false;
        }
        return true;
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

}
