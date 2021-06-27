package model.game;

import model.Database;
import model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FieldZoneTest {


    @Test
    public void fieldZoneTest() {

        Database.prepareDatabase();
        FieldZone fieldZone = new FieldZone();

        fieldZone.setCard(Card.getCardByName("Haniwa"));


        Assertions.assertNotNull(fieldZone.getCard());
    }


    @Test
    public void fieldZoneTest2() {

        Database.prepareDatabase();
        FieldZone fieldZone = new FieldZone();

        Assertions.assertNull(fieldZone.getCard());
    }

}