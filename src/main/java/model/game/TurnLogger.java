package model.game;

import model.card.Card;
import model.card.SelectedCard;

import java.util.ArrayList;

public class TurnLogger {
    private ArrayList<Card> changedPositionCards;
    private ArrayList<Card> recentlyAddedCards;


    public boolean hasSummoned(Card card) {
        return recentlyAddedCards.contains(card);
    }

    public TurnLogger(){
        this.changedPositionCards = new ArrayList<>();
        this.recentlyAddedCards = new ArrayList<>();
    }

    public void cardPositionChanged(Card card){
        changedPositionCards.add(card);
    }

    public boolean doesPositionChanged(Card card) {
        return changedPositionCards.contains(card);
    }
}
