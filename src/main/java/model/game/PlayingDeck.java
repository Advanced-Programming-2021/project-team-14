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
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<SpellTrap> spellTraps = new ArrayList<>();

    public PlayingDeck(Deck activeDeck) {
        cards = new ArrayList<>();
        loadCards(activeDeck);
        shuffle();
    }

    private void loadCards(Deck activeDeck) {
        for (String cardName : activeDeck.getCards(true))   // load side cards
            loadCard(Card.getCardByName(cardName));
        for (String cardName : activeDeck.getCards(false))   // load main cards
            loadCard(Card.getCardByName(cardName));
        monsters = Monster.getMonsters();
        spellTraps = SpellTrap.getSpellTraps();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.size() != 0) {
            Card card = cards.get(0);
            cards.remove(0);
            return card;          // draw first card from deck
        }
        return null;              // no card to draw
    }

    public int getRemainingCardsSize() {
        return cards.size();
    }

    public void loadCard(Card card) {
        if (card.getCardType() == CardType.MONSTER)
            cards.add(new Monster((Monster) card));
        else
            cards.add(new SpellTrap((SpellTrap) card));
    }


    public Monster getMonsterByName(String monsterName) {

        for (Monster monster : monsters) {
            if (monster.getName().equals(monsterName)) {
                return monster;
            }
        }
        return null;
    }

    public SpellTrap getSpellTrapByName(String spellTrapName) {

        for (SpellTrap spellTrap : spellTraps) {
            if (spellTrap.getName().equals(spellTrapName)) {
                return spellTrap;
            }
        }
        return null;
    }

    public Card getCardByName(String cardName) {

        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return String.format(Strings.PLAYING_DECK_PRINT_FORMAT.getLabel(), getRemainingCardsSize());
    }
}
