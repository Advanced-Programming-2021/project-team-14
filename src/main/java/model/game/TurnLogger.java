package model.game;

import model.card.Card;
import model.card.SelectedCard;

import java.util.ArrayList;

public class TurnLogger {
    private ArrayList<Card> changedPositionCards;
    private boolean summoned;

    public boolean hasSummoned() {
        return summoned;
    }

    public TurnLogger(){
        this.changedPositionCards = new ArrayList<>();
        this.summoned = false;
    }

    public void cardPositionChanged(Card card){
        changedPositionCards.add(card);
    }

    public boolean doesPositionChanged(Card card) {
        return changedPositionCards.contains(card);
    }
}
