package model.game;

import model.Strings;
import model.card.Card;
import model.card.enums.CardType;

import java.util.ArrayList;

public class GraveYard {

    private ArrayList<Card> cards;

    public GraveYard() {
        cards = new ArrayList<>();
    }

    public int getSize() {
        return cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public String showCards() {
        if (cards.isEmpty()) return Strings.GRAVEYARD_IS_EMPTY.getLabel();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {

            result.append(i + 1).append(". ").append(cards.get(i).toString()).append("\n");
        }
        return result.toString();
    }


    public boolean isEmpty() {
        return cards.size() == 0;
    }


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

    public void remove(Card card) {
        cards.remove(card);
    }

    public boolean isEmpty(CardType type) {
        if (isEmpty()) return true;
        for (Card card : cards) {
            if (card.getCardType() == type) return false;
        }
        return true;
    }

    public ArrayList<Card> getCards(CardType type) {
        ArrayList<Card> suitableCards = new ArrayList<>();
        cards.forEach(card -> {
            if (card.getCardType() == type) suitableCards.add(card);
        });
        return suitableCards;
    }
}
