package model.game;

import model.card.Card;
import model.card.enums.Position;

import java.util.ArrayList;
import java.util.Random;

public class Hand {

    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public boolean isFull() {
        return cards.size() == 6;
    }

    public void addCard(Card card) {
        card.setPosition(Position.HAND);
        card.setPositionIndex(cards.size() + 1);
        cards.add(card);
    }



    public Card getCard(int position) {
        return cards.get(position - 1);
    }
public int getSize(){
        return cards.size();
}
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            result.append("c\t");
        }
        return result.toString();
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public boolean doesHaveCard(String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName)) return true;
        }
        return false;
    }
    public void remove(String cardName){
        ArrayList<Card> toRemove = new ArrayList<>();
        for (Card card : cards) {
            if (card.getName().equals(cardName)){
                toRemove.add(card);
            }
        }
        cards.removeAll(toRemove);
    }

    public int getRemainedPlaces(){
        return 6 - getSize();
    }
    public void removeRandomly() {
        cards.remove(new Random().nextInt(cards.size() - 1));
    }
}
