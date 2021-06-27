package model.game;

import model.Strings;
import model.card.Card;

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


    @Override
    public String toString() {
        return String.format(Strings.GRAVEYARD_PRINT_FORMAT.getLabel(), getSize());
    }
}
