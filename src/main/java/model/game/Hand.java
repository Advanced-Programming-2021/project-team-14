package model.game;

import model.card.Card;
import model.card.enums.Position;

import java.util.ArrayList;
import java.util.Random;

public class Hand {

    private ArrayList<Card> cardLoaders;

    public Hand() {
        cardLoaders = new ArrayList<>();
    }

    public boolean isFull() {
        return cardLoaders.size() == 6;
    }

    public void addCard(Card card) {
        card.setPosition(Position.HAND);
        card.setPositionIndex(cardLoaders.size() + 1);
        cardLoaders.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int position) {
        return cardLoaders.get(position - 1);
    }
public int getSize(){
    return cardLoaders.size();
}
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cardLoaders.size(); i++) {
            result.append("c\t");
        }
        return result.toString();
    }

    public void remove(Card card) {
        cardLoaders.remove(card);
    }

    public boolean doesHaveCard(String cardName) {
        for (Card card : cardLoaders) {
            if (card.getName().equals(cardName)) return true;
        }
        return false;
    }
    public void remove(String cardName){
        ArrayList<Card> toRemove = new ArrayList<>();
        for (Card card : cardLoaders) {
            if (card.getName().equals(cardName)) {
                toRemove.add(card);
            }
        }
        cardLoaders.removeAll(toRemove);
    }

    public int getRemainedPlaces(){
        return 6 - getSize();
    }
    public void removeRandomly() {
        cardLoaders.remove(new Random().nextInt(cardLoaders.size() - 1));
    }
}
