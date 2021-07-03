package model;

import java.util.ArrayList;

public class Wallet {

    private int cash;
    private ArrayList<String> cardLoaders;

    public Wallet() {  // add some random cardLoaders to the wallet
        this.cardLoaders = new ArrayList<>();
        this.cash = 100000;
    }

    public ArrayList<String> getCards() {
        return cardLoaders;
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

        cardLoaders.add(card);
    }

    public int getCash() {
        return this.cash;
    }

    public void removeCard(String card) {
        cardLoaders.remove(card);
    }

    public boolean doesCardExist(String cardName) {
        return cardLoaders.contains(cardName);
    }

}
