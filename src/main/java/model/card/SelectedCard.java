package model.card;

import model.card.enums.Position;

public class SelectedCard {
    private Card card;
    private int positionIndex;
    private Position position;
    private boolean isOpponent;


    public SelectedCard(Card card, Position position, int positionIndex, boolean isOpponent) {
        this.positionIndex = positionIndex;
        this.position = position;
        this.card = card;
        this.isOpponent = isOpponent;
    }

    public Card getCard() {
        return card;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isOpponent() {
        return isOpponent;
    }
}
