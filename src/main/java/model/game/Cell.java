package model.game;

import model.card.Card;
import model.card.enums.State;

public class Cell {

    private Card card;

    private State state;

    public Cell() {  //temporary for test
        this.state = State.DEFENSIVE_HIDDEN;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isEmpty() {
        return this.getCard() == null;
    }

    @Override
    public String toString() {
        return state.getLabel();
    }
}
