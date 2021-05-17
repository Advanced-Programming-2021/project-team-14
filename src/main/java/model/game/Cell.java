package model.game;

import model.card.Card;
import model.card.enums.State;

public class Cell {

    private Card card = null;


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
