package model.game;

import model.Database;
import model.card.Card;
import model.card.Monster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ZoneTest {


    @Test
    public void zoneTest2() {

        Database.prepareDatabase();

        Zone zone = new Zone();

        Monster monster = new Monster((Monster) Card.getCardByName("Haniwa"));
        zone.placeCard(monster);

        ArrayList<Integer> list = zone.getCellsByType("Rock");
        Assertions.assertTrue(list.size() == 1);
    }


    @Test
    public void zoneTest3() {

        Zone zone = new Zone();

        ArrayList<Integer> list = zone.occupiedCells();

        Assertions.assertTrue(list.size() == 0);
    }


    @Test
    public void zoneTest4() {

        Database.prepareDatabase();

        Zone zone = new Zone();

        Monster monster = new Monster((Monster) Card.getCardByName("Haniwa"));
        zone.placeCard(monster);

        zone.remove("Haniwa");

        ArrayList<Integer> list = zone.occupiedCells();
        Assertions.assertTrue(list.size() == 0);
    }

}