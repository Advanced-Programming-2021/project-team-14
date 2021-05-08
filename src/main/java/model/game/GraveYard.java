package model.game;

import model.Strings;
import model.card.Card;

import java.util.ArrayList;

public class GraveYard {

    private static ArrayList<Card> cards;

    public int getSize() {
        return cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

//    public static String showCards() {
//
//
//    }

    public Card getCard(String cardName) {

        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format(Strings.GRAVEYARD_PRINT_FORMAT.getLabel(), getSize());
    }
}
