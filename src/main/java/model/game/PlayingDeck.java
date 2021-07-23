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

    private ArrayList<String> cardLoaders;
    private ArrayList<String> monsters = new ArrayList<>();
    private ArrayList<String> spellTraps = new ArrayList<>();

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
//        monsters = Monster.getMonsters();
//
//        spellTraps = SpellTrap.getSpellTraps();
    }

    public void shuffle() {
        Collections.shuffle(cardLoaders);
    }

    public Card drawCard() {
        if (cardLoaders.size() != 0) {
            Card card = Card.getCardByName(cardLoaders.get(0));
            cardLoaders.remove(0);
            return card;          // draw first card from deck
        }
        return null;              // no card to draw
    }

    public int getRemainingCardsSize() {
        return cardLoaders.size();
    }

    public void loadCard(Card card) {
        cardLoaders.add(card.getName());
    }


    public Card getACard(Property property) {
        for (String cardName : cardLoaders) {
            Card card = Card.getCardByName(cardName);
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
        ArrayList<String> removedCards = new ArrayList<>();
        cardLoaders.forEach(cardName -> {
            if (Card.getCardByName(cardName).getName().equals(name)) removedCards.add(cardName);
        });
        cardLoaders.removeAll(removedCards);
    }
}
