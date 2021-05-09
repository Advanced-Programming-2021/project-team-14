package model.game;

import model.card.Card;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
        //temporary for test
        for (int i = 0; i < 6; i++) {
            cards.add(Card.getCardByName("Suijin"));
        }
    }

    public boolean isFull() {
        return cards.size() == 6;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCard(String cardName) {

        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            result.append("c\t");
        }
        return result.toString();
    }
}
