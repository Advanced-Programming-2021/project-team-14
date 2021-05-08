package model.game;

import model.Deck;
import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;

public class PlayingDeck {

    private ArrayList<Card> cards;

    public PlayingDeck(Deck activeDeck) {

        loadCards(activeDeck);
    }

    private void loadCards(Deck activeDeck) {


    }

    public void shuffle() {

        Collections.shuffle(cards);
    }

    public Card drawCard() {

        return cards.get(0);          // draw first card from deck
    }

    public String getSize() {
        return String.format("%d", cards.size());
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
