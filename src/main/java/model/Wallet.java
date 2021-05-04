package model;

import java.util.ArrayList;

public class Wallet {

    private int cash;
    private ArrayList<String> cards;

    public Wallet() {
        this.cards = new ArrayList<>();
    }
//        public void addCash(int amount){}
//    public void decreaseCash(int amount){}
//    public boolean isCashEnough(int amount){}

    public void addCard(String card) {
        cards.add(card);
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void removeCard(String card) {
        cards.remove(card);
    }

    public boolean doesCardExist(String cardName) {
        return cards.contains(cardName);
    }
}
