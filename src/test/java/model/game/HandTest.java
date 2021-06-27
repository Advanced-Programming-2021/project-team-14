package model.game;

import model.Database;
import model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HandTest {


    @Test
    public void playingDeckTest() {

        Database.prepareDatabase();

        Hand hand = new Hand();

        hand.addCard(Card.getCardByName("Haniwa"));

        Assertions.assertTrue(hand.doesHaveCard("Haniwa"));
    }


    @Test
    public void playingDeckTest2() {

        Database.prepareDatabase();

        Hand hand = new Hand();

        Assertions.assertFalse(hand.doesHaveCard("Haniwa"));
    }


    @Test
    public void playingDeckTest3() {

        Database.prepareDatabase();

        Hand hand = new Hand();

        hand.addCard(Card.getCardByName("Haniwa"));

        hand.remove("Haniwa");

        Assertions.assertFalse(hand.doesHaveCard("Haniwa"));
    }

}