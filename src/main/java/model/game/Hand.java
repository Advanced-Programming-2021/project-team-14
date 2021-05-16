package model.game;

import model.card.Card;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public boolean isFull() {
        return cards.size() == 6;
    }

    public void addCard(Card card) {
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
}
