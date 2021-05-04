
package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {

    private static HashMap<String, Deck> decks;
    private ArrayList<String> cardNames = new ArrayList<>();
    private boolean isActive;
    private boolean isValid;

    static {
        decks = new HashMap<>();
    }

    private String name;
    private ArrayList<Card> sideCards;
    private ArrayList<Card> mainCards;

    public Deck(String deckName) {

        this.name = deckName;
        isActive = false;
        isValid = false;
        decks.put(name, this);
    }

    public static boolean doesCardAvailableInDeck(String cardName, String deckName, String option) {

        if (option.equals("side")) {
            return Deck.getDeckByName(deckName).sideCards.contains(Card.getCardByName(cardName));
        } else {
            return Deck.getDeckByName(deckName).mainCards.contains(Card.getCardByName(cardName));
        }
    }

//    public boolean isFrequencyValid(Card card){}
//    public String showDeck(){}
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


    public ArrayList<String> getCardNames() {
        return cardNames;
    }

    public String getName() {
        return name;
    }


    public void removeDeck(String deckName) {
        decks.remove(deckName);
    }


    public ArrayList<Card> getCards(String option) {

        if (option.equals("side")) {
            return sideCards;
        }
        return mainCards;
    }

    public boolean isValid() {

        return this.getCards("main").size() >= 40;
    }

    public void setActiveDeck(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void addCard(Card card, String option) {
        cardNames.add(card.getName());
        if (option.equals("side")) {
            sideCards.add(card);
        } else {
            mainCards.add(card);
        }
    }

    public void removeCard(Card card, String option) {
        cardNames.remove(card.getName());
        if (option.equals("side")) {
            sideCards.add(card);
        } else {
            mainCards.add(card);
        }
    }
}