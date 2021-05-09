package model.game;

import model.card.Card;
import model.card.enums.Status;

public class Cell {

    private Card card;

    private Status state;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public boolean isEmpty() {
        return this.getCard() == null;
    }
}
