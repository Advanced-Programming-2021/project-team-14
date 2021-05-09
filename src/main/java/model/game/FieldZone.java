package model.game;

import model.Strings;
import model.card.Card;
import model.card.enums.State;

public class FieldZone {

    public boolean isEmpty;
    private Card card;

    public Card getCard() {
        setEmpty(true);
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        setEmpty(false);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean bool) {
        isEmpty = bool;
    }

    @Override
    public String toString() {
        return String.format(Strings.FIELD_ZONE_PRINT_FORMAT.getLabel(), isEmpty() ? State.EMPTY.getLabel() : State.OCCUPIED.getLabel());
    }
}
