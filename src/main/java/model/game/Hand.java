package model.game;

import model.card.Card;
import model.card.enums.Position;

import java.util.ArrayList;
import java.util.Random;

public class Hand {

    private ArrayList<String> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public boolean isFull() {
        return cards.size() == 6;
    }

    public void addCard(Card card) {
        card.setPosition(Position.HAND);
        card.setPositionIndex(cards.size() + 1);
        cards.add(card.getName());
    }

    public ArrayList<Card> getCards() {
        ArrayList<Card> toSend = new ArrayList<>();
        for (String cardName: cards) {
            toSend.add(Card.getCardByName(cardName));
        }
        return toSend;
    }

    public Card getCard(int position) {
        return Card.getCardByName(cards.get(position - 1));
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
        for (String name : cards) {
            if (name.equals(cardName)) return true;
        }
        return false;
    }
    public void remove(String cardName){
        ArrayList<String> toRemove = new ArrayList<>();
        for (String name: cards) {
            if (name.equals(cardName)) {
                toRemove.add(name);
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
