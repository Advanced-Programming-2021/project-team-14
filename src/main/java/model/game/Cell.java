package model.game;

import model.card.Card;
import model.card.enums.State;

public class Cell {

    private Card card = null;
    private int position;
    public Cell(int position){
        this.position = position;
    }
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isEmpty() {
        return this.getCard() == null;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        if (this.card == null){
            return State.EMPTY.getLabel();
        }else{
            return card.getState().getLabel();
        }
    }

    public void removeCard(){
        card = null;
    }
}
