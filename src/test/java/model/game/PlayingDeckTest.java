package model.game;

import model.Database;
import model.Deck;
import model.card.Card;
import model.card.enums.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayingDeckTest {


    @Test
    public void playingDeckTest() {

        Database.prepareDatabase();

        Deck deck = new Deck("deck");
        deck.addCard("Haniwa", false);
        PlayingDeck playingDeck = new PlayingDeck(deck);
        Card card = playingDeck.getACard(Property.NORMAL);

        Assertions.assertEquals("Haniwa", card.getName());
    }


    @Test
    public void playingDeckTest2() {

        Database.prepareDatabase();

        Deck deck = new Deck("deck");
        deck.addCard("Haniwa", false);
        PlayingDeck playingDeck = new PlayingDeck(deck);

        playingDeck.remove("Haniwa");
        Card card = playingDeck.getACard(Property.NORMAL);

        Assertions.assertNull(card);
    }

}