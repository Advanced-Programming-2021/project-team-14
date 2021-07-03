package model.game;

import model.Deck;
import model.Strings;
import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
import model.card.enums.CardType;
import model.card.enums.Property;

import java.util.ArrayList;
import java.util.Collections;

public class PlayingDeck {

    private ArrayList<Card> cardLoaders;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<SpellTrap> spellTraps = new ArrayList<>();

    public PlayingDeck(Deck activeDeck) {
        cardLoaders = new ArrayList<>();
        loadCards(activeDeck);
        shuffle();
    }

    private void loadCards(Deck activeDeck) {
        for (String cardName : activeDeck.getCards(true))   // load side cardLoaders
            loadCard(Card.getCardByName(cardName));
        for (String cardName : activeDeck.getCards(false))   // load main cardLoaders
            loadCard(Card.getCardByName(cardName));
        monsters = Monster.getMonsters();
        spellTraps = SpellTrap.getSpellTraps();
    }

    public void shuffle() {
        Collections.shuffle(cardLoaders);
    }

    public Card drawCard() {
        if (cardLoaders.size() != 0) {
            Card card = cardLoaders.get(0);
            cardLoaders.remove(0);
            return card;          // draw first card from deck
        }
        return null;              // no card to draw
    }

    public int getRemainingCardsSize() {
        return cardLoaders.size();
    }

    public void loadCard(Card card) {
        if (card.getCardType() == CardType.MONSTER)
            cardLoaders.add(new Monster((Monster) card));
        else
            cardLoaders.add(new SpellTrap((SpellTrap) card));
    }



    public Card getACard(Property property) {
        for (Card card : cardLoaders) {
            if (card.getProperty() == property) {
                return card;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return String.format(Strings.PLAYING_DECK_PRINT_FORMAT.getLabel(), getRemainingCardsSize());
    }

    public void remove(String name) {
        ArrayList<Card> removedCards = new ArrayList<>();
        cardLoaders.forEach(card -> {
            if (card.getName().equals(name)) removedCards.add(card);
        });
        cardLoaders.removeAll(removedCards);
    }
}
