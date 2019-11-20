package generator;

import javafx.util.Pair;

import java.util.ArrayList;

public enum Direction {
    NORTH(0, -1), WEST(-1, 0), EAST(1, 0), SOUTH(0, 1);

    private int x;
    private int y;
    //shifts in x,y for cells i should check
    private ArrayList<Pair<Integer, Integer>> cellsAround;

    Direction (int x, int y) {

        this.x = x;
        this.y = y;
    }

    public Pair<Integer, Integer> getCoordsShift() {
        return new Pair<>(x, y);
    }

    public ArrayList<Pair<Integer, Integer>> getCellsAround() {
        cellsAround = new ArrayList<>();
        int fromY = -1;
        int toY = 1;
        int fromX = -1;
        int toX = 1;

        switch (this) {
            case NORTH: toY = 0;
                break;

            case SOUTH: fromY = 0;
                break;

            case WEST: toX = 0;
                break;

            case EAST: fromX = 0;
                break;
        }

        for (int y1 = fromY; y1 <= toY; y1++) {
            for (int x1 = fromX; x1 <= toX; x1++) {
                if(y1 != 0 || x1 != 0) {
                    cellsAround.add(new Pair<>(x1, y1));
                }
            }
        }
        return cellsAround;
    }

}
