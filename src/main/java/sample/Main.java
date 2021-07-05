package sample;


import model.Database;
import model.card.Card;


public class Main{

    public static void main(String[] args) {
        Database.prepareDatabase();
        for (Card card : Card.getCards()) {
            System.out.println(card.getName());
        }


    }
}
