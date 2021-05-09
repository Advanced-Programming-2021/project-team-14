package model.game;

import model.card.Card;

import java.util.HashMap;

public class Zone {
    private static final int ZONE_SIZE = 5;
    private static final HashMap<Integer, Integer> positions = new HashMap<Integer, Integer>() {{
        put(1, 5);
        put(2, 3);
        put(3, 1);
        put(4, 2);
        put(5, 4);
    }};
    private Cell[] cells = new Cell[ZONE_SIZE + 1];

    public Zone() {
        for (int i = 1; i <= ZONE_SIZE; i++) {
            cells[i] = new Cell();
        }
    }


    public Cell getCell(int position) {
        return this.cells[adapter(position)];
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


    public boolean isFull() {
        for (int i = 0; i < 5; i++) {
            if (cells[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            if (cells[i].isEmpty()) {
                counter++;
            }
        }
        return counter == 5;
    }

    public String toString(boolean isRotated) {
        if (isRotated)
            return String.format("\t%s\t%s\t%s\t%s\t%s\t", cells[adapter(5)], cells[adapter(4)], cells[adapter(3)], cells[adapter(2)], cells[adapter(1)]);
        return String.format("\t%s\t%s\t%s\t%s\t%s\t", cells[adapter(1)], cells[adapter(2)], cells[adapter(3)], cells[adapter(4)], cells[adapter(5)]);
    }
}
