package model.game;

import model.Deck;
import model.Strings;
import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
import model.card.enums.CardType;

import java.util.ArrayList;
import java.util.Collections;

public class PlayingDeck {

    private ArrayList<Card> cards;

    public PlayingDeck(Deck activeDeck) {
        cards = new ArrayList<>();
        loadCards(activeDeck);
    }

    private void loadCards(Deck activeDeck) {
        for (String cardName : activeDeck.getCards(true))   // load side cards
            loadCard(Card.getCardByName(cardName));
        for (String cardName : activeDeck.getCards(false))   // load main cards
            loadCard(Card.getCardByName(cardName));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {

        return cards.get(0);          // draw first card from deck
    }

    public int getRemainingCardsSize() {
        return cards.size();
    }

    public void loadCard(Card card) {
        if (card.getCardType() == CardType.MONSTER){
        Monster copiedCard = (Monster) card;
        cards.add(copiedCard);
        }else{
            SpellTrap copiedCard = (SpellTrap) card;
            cards.add(copiedCard);
        }
    }

    @Override
    public String toString() {
        return String.format(Strings.PLAYING_DECK_PRINT_FORMAT.getLabel(), getRemainingCardsSize());
    }
}
