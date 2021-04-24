package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {

    private static HashMap<String, Deck> decks;

    static {
        decks = new HashMap<>();
    }

    private String name;
    private ArrayList<Card> sideCards;
    private ArrayList<Card> mainCards;

    public Deck(String name) {

        this.name = name;
        decks.put(name, this);
    }

    public static boolean doesCardAvailableInDeck(String cardName, String deckName, String option) {

        if (option.equals("side")) {
            return Deck.getDeckByName(deckName).sideCards.contains(Card.getCardByName(cardName));
        } else {
            return Deck.getDeckByName(deckName).mainCards.contains(Card.getCardByName(cardName));
        }
    }

//    public void removeCard(Card card){}
//    public boolean isFrequencyValid(Card card){}
//    public String showDeck(){}
//    public boolean isValid(){}
//    public boolean isFull(){}
//    public void shuffle(){}
//    public Card drawOneCard(){}

    public static boolean isDeckFull(String deckName, String option) {
        if (option.equals("side")) {
            return Deck.getDeckByName(deckName).sideCards.size() > 15;
        } else {
            return Deck.getDeckByName(deckName).mainCards.size() > 60;
        }
    }

    public static void removeDeck(String deckName) {
        decks.remove(deckName);
    }

    public static int getNumberOfCardInDeck(String cardName, String deckName) {

        int counter = 0;
        for (Card card : Deck.getDeckByName(deckName).mainCards) {
            if (card.getName().equals(cardName)) {
                counter++;
            }
        }

        for (Card card : Deck.getDeckByName(deckName).sideCards) {
            if (card.getName().equals(cardName)) {
                counter++;
            }
        }
        return counter;
    }

    public static Deck getDeckByName(String deckName) {
        return decks.get(deckName);
    }

    public static boolean doesDeckExist(String deckName) {
        return decks.containsKey(deckName);
    }

    public void addCard(Card card, String option) {
        if (option.equals("side")) {
            sideCards.add(card);
        } else {
            mainCards.add(card);
        }
    }
}
