package model.game;

import model.Strings;
import model.card.Card;

import java.util.ArrayList;

public class GraveYard {

    private ArrayList<Card> cardLoaders;

    public GraveYard() {
        cardLoaders = new ArrayList<>();
    }

    public int getSize() {
        return cardLoaders.size();
    }

    public void addCard(Card card) {
        cardLoaders.add(card);
    }

    public String showCards() {
        if (cardLoaders.isEmpty()) return Strings.GRAVEYARD_IS_EMPTY.getLabel();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cardLoaders.size(); i++) {

            result.append(i + 1).append(". ").append(cardLoaders.get(i).toString()).append("\n");
        }
        return result.toString();
    }


    @Override
    public String toString() {
        return String.format(Strings.GRAVEYARD_PRINT_FORMAT.getLabel(), getSize());
    }
}
