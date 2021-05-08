package model.game;

import model.card.Card;

import java.util.HashMap;

public class Zone {

    private Cell[] cells = new Cell[5];

    private HashMap<Integer, Integer> positions = new HashMap<Integer, Integer>() {{
        put(1, 5);
        put(2, 3);
        put(3, 1);
        put(4, 2);
        put(5, 4);
    }};


    public Cell getCell(int position) {
        return this.cells[position];
    }


    public void placeCard(Card card) {

        for (int i = 1; i <= 5; i++) {
            if (cells[adapter(i)].getCard() != null) {
                cells[adapter(i)].setCard(card);
            }
        }
    }

    public int adapter(int position) {

        for (int index : positions.keySet()) {
            if (position == positions.get(index)) {
                return index;
            }
        }
        return 0;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            result.append("\t").append(cells[positions.get(i)].getState());
        }
        return result.toString();
    }
}
