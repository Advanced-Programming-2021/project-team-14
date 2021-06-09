package model.card;

import model.card.enums.Position;

public class SelectedCard {
    private Card card;

    private boolean isOpponent;


    public SelectedCard(Card card, boolean isOpponent) {
        this.card = card;
        this.isOpponent = isOpponent;
    }

    public Card getCard() {
        return card;
    }

    public boolean isOpponent() {
        return isOpponent;
    }
}
