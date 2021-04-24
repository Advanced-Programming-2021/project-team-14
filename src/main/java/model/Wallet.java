package model;

import java.util.ArrayList;

public class Wallet {

    private int cash;
    private ArrayList<Card> cards = new ArrayList<>();

    //    public void addCash(int amount){}
//    public void decreaseCash(int amount){}
//    public boolean isCashEnough(int amount){}
//
    public void addCard(Card card) {

        cards.add(card);
    }

    public void removeCard(Card card) {

        cards.remove(card);
    }

    public boolean doesCardExist(Card card) {
        return cards.contains(card);
    }
}
