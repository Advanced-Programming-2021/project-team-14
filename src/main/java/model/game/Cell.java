package model.game;

import model.card.Card;
import model.card.enums.State;

public class Cell {

    private Card card;


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isEmpty() {
        return this.getCard() == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) return State.EMPTY.getLabel();
        return card.getState().getLabel();
    }
}
