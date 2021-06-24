package model;

import java.util.ArrayList;

public class Wallet {

    private int cash;
    private ArrayList<String> cards;

    public Wallet() {  // add some random cards to the wallet
        this.cards = new ArrayList<>();
        this.cash = 100000;
    }


    public void decreaseCash(int amount) {
        this.cash -= amount;
    }

    public void increaseCash(int amount) {
        this.cash += amount;

    }

    public boolean isCashEnough(int amount) {
        return getCash() >= amount;
    }

    public void addCard(String card) {

        cards.add(card);
    }

    public ArrayList<String> getCards() {
        return cards;
    }


    public int getCash() {
        return this.cash;
    }

    public void removeCard(String card) {
        cards.remove(card);
    }

    public boolean doesCardExist(String cardName) {
        return cards.contains(cardName);
    }

}
