package model.game;

import model.card.Card;

import java.util.ArrayList;

public class TurnLogger {
    private ArrayList<Card> changedPositionCards;
    private ArrayList<Card> recentlyAddedCards;
    private ArrayList<Card> attackedCards;
    private boolean canDrawCard = true;

    public void setCanDrawCard(boolean canDrawCard) {
        this.canDrawCard = canDrawCard;
    }

    public boolean canDrawCard() {
        return canDrawCard;
    }

    public boolean hasSummonedOrSetCards() {

        return recentlyAddedCards.size() != 0;
    }

    public boolean hasAdded(Card card) {

        return recentlyAddedCards.contains(card);
    }

    public TurnLogger() {
        this.changedPositionCards = new ArrayList<>();
        this.recentlyAddedCards = new ArrayList<>();
        this.attackedCards = new ArrayList<>();
    }

    public void cardAdded(Card card) {
        recentlyAddedCards.add(card);
    }

    public void cardAttack(Card card) {
        attackedCards.add(card);
    }

    public boolean hasAttacked(Card card){
        return attackedCards.contains(card);
    }
    public void cardPositionChanged(Card card){
        changedPositionCards.add(card);
    }

    public boolean doesPositionChanged(Card card) {
        return changedPositionCards.contains(card);
    }

    public void reset() {
        changedPositionCards = new ArrayList<>();
        recentlyAddedCards = new ArrayList<>();
        attackedCards = new ArrayList<>();
    }
}
