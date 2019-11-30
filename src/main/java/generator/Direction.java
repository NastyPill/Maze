package generator;

import javafx.util.Pair;

import java.util.ArrayList;

public enum Direction {
    NORTH(0, -1), WEST(-1, 0), EAST(1, 0), SOUTH(0, 1);

    private int x;
    private int y;
    //shifts in x,y for cells i should check
    private ArrayList<Pair<Integer, Integer>> cellsAround;

    Direction(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public Pair<Integer, Integer> getCoordsShift() {
        return new Pair<>(x, y);
    }

}
