
package model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private static final int SIDE_MAX_SIZE = 15;
    private static final int MAIN_MAX_SIZE = 60;
    private static final int MAIN_MIN_SIZE = 40;
    private static final int MAX_CARD_NUMBER = 3;

    private ArrayList<String> availableCards;
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

    public boolean isCardAvailableInDeck(String cardName, String option) {
        return (option.equals(Strings.SIDE.getLabel()) ? sideCards : mainCards).contains(cardName);
    }

//    public boolean isFrequencyValid(Card card){}
//    public String showDeck(){}
//    public boolean isFull(){}
//    public void shuffle(){}
//    public Card drawOneCard(){}

    public boolean isDeckFull(String option) {
        return option.equals(Strings.SIDE.getLabel()) ? sideCards.size() > SIDE_MAX_SIZE : mainCards.size() > MAIN_MAX_SIZE;
    }


    public int getCardFrequency(String cardName) {
        return Collections.frequency(sideCards, cardName) + Collections.frequency(sideCards, cardName);
    }


    public ArrayList<String> getCardNames() {
        return availableCards;
    }

    public String getName() {
        return name;
    }


    public ArrayList<String> getCards(String option) {
        return option.equals(Strings.SIDE.getLabel()) ? sideCards : mainCards;
    }

    public boolean isValid() {
        return mainCards.size() >= MAIN_MIN_SIZE;
    }

    public void setActiveDeck(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void addCard(String card, String option) {
        availableCards.remove(card);
        (option.equals(Strings.SIDE.getLabel()) ? sideCards : mainCards).add(card);
    }

    public void removeCard(String card, String option) {
        availableCards.add(card);
        (option.equals(Strings.SIDE.getLabel()) ? sideCards : mainCards).remove(card);
    }


    public boolean canAddCard(String cardName) {
        return getCardFrequency(cardName) < MAX_CARD_NUMBER;
    }

}