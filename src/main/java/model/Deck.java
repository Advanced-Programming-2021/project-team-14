
package model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private static final int SIDE_MAX_SIZE = 15;
    private static final int MAIN_MAX_SIZE = 60;
    private static final int MAIN_MIN_SIZE = 40;
    private static final int MAX_CARD_NUMBER = 3;

    private boolean isActive;

    private String name;
    private ArrayList<String> sideCards;
    private ArrayList<String> mainCards;

    public Deck(String deckName) {
        sideCards = new ArrayList<>();
        mainCards = new ArrayList<>();
        this.name = deckName;
        isActive = false;
    }

    public boolean isCardAvailableInDeck(String cardName, boolean isSideDeck) {
        return (isSideDeck ? sideCards : mainCards).contains(cardName);
    }

    public boolean isDeckFull(Boolean isSide) {
        return isSide ? sideCards.size() > SIDE_MAX_SIZE : mainCards.size() > MAIN_MAX_SIZE;
    }


    public int getCardFrequency(String cardName) {
        int result = Collections.frequency(mainCards, cardName) + Collections.frequency(sideCards, cardName);
        return result;
    }

    public String getName() {
        return name;
    }


    public ArrayList<String> getCards(boolean option) {
        return option ? sideCards : mainCards;
    }

    public boolean isValid() {
        return mainCards.size() >= MAIN_MIN_SIZE;
    }

    public boolean isActive() {
        return isActive;
    }

    public void addCard(String card, Boolean isSideDeck) {
        (isSideDeck ? sideCards : mainCards).add(card);
    }

    public void removeCard(String card, boolean isSideDeck) {
        (isSideDeck ? sideCards : mainCards).remove(card);
    }


    public boolean canAddCard(String cardName) {
        return getCardFrequency(cardName) < MAX_CARD_NUMBER;
    }

    @Override
    public String toString() {
        return String.format(Strings.DECK_PRINT_FORMAT.getLabel(), name, mainCards.size(),
                sideCards.size(), isValid() ? Strings.VALID.getLabel() : Strings.INVALID.getLabel());
    }
}