package model;

import java.util.HashMap;

public class Card {

    protected static HashMap<String, Card> allCards;

    static {
        allCards = new HashMap<>();
    }

    protected String name;
    protected String description;
    protected CardTypes type;
    protected boolean isSwitched;

//    public static void addCard(Card card){
//
//    }

    public static Card getCardByName(String cardName) {
        return allCards.get(cardName);
    }

    public static boolean doesCardExist(String cardName) {
        return allCards.containsKey(cardName);
    }

    public String getName() {
        return this.name;
    }

    // toString ...
}
