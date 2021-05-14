package model.game;


import model.Strings;
import model.card.Card;

import java.util.HashMap;
import java.util.Map;

public class Zone {
    private static final int ZONE_SIZE = 5;
    
    private HashMap<Integer, Cell> cells;

    public Zone (){
        cells = new HashMap<>();
        cells.put(5, new Cell());
        cells.put(3, new Cell());
        cells.put(1, new Cell());
        cells.put(2, new Cell());
        cells.put(4, new Cell());
    }

    public Cell getCell(int position) {
        return cells.get(position);
    }
    public void placeCard(Card card){
        int position = firstEmptyPlace();
        card.setPositionIndex(position);
        cells.get(position).setCard(card);
    }
    private int firstEmptyPlace(){
        for(Map.Entry<Integer, Cell> cell : cells.entrySet())
            if (cell.getValue().isEmpty()) return cell.getKey();
            return 0;
    }
    public boolean isFull(){
        return getSize() == ZONE_SIZE;
    }
    private int getSize(){
        int size = 0;
        for(Map.Entry<Integer, Cell> cell : cells.entrySet())
            if (!cell.getValue().isEmpty()) size++;
            return size;
    }

    public String toString(boolean isRotated) {
        if (isRotated) return String.format(Strings.ZONE_PRINT_FORMAT.getLabel(), cells.get(5), cells.get(3), cells.get(1), cells.get(2), cells.get(4));
        return String.format(Strings.ZONE_PRINT_FORMAT.getLabel(), cells.get(2), cells.get(2), cells.get(1), cells.get(3), cells.get(5));
    }
}
