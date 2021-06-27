package model.game;

import model.Strings;
import model.card.Card;
import model.card.enums.State;

public class FieldZone {

    private Card card;

    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isEmpty() {
        return card == null;
    }


    @Override
    public String toString() {
        return String.format(Strings.FIELD_ZONE_PRINT_FORMAT.getLabel(), isEmpty() ? State.EMPTY.getLabel() : State.OCCUPIED.getLabel());
    }
}
