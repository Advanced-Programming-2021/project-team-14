package model;

import java.util.ArrayList;

public class Wallet {

    private int cash;
    private ArrayList<String> cards;

    public Wallet(int test) {  // add some random cards to the wallet

        this.cards = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 50; i++) {
//            addCard(Card.getCard(random.nextInt(40)));
//        }
    }

    public void addCash(int amount) {
        setCash(getCash() + amount);
    }

    public void decreaseCash(int amount) {
        setCash(getCash() - amount);
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

    public void setCash(int cash) {
        this.cash = cash;
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
