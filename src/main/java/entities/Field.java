package entities;

import javafx.util.Pair;

public class Field {

    Cell field[][];
    int width;
    int height;
    Pair<Integer, Integer> entrance;

    public Field(int height, int width) {
        this.width = width;
        this.height = height;
        field = new Cell[height][width];
    }

    public void init() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.initField(x, y);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result.append(field[i][j].toString());
            }
            result.append("\n");
        }
        return result.toString();
    }

    private void initField(int x, int y) {
        field[y][x] = new Cell(x, y, TypeOfCell.WALL);
    }

    public void setWall(int x, int y, Boolean isWall) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            if (isWall)
                field[y][x] = new Cell(x, y, TypeOfCell.WALL);
            else
                field[y][x] = new Cell(x, y, TypeOfCell.HOLE);
    }

    public Boolean setEndPoints(int x, int y, Boolean isExit) {
        if (!isExit) {
            if (x == 1 && y == 1) {
                field[y][x] = new Cell(x, y, TypeOfCell.ENTRANCE);
                entrance = new Pair<>(x, y);
                return true;
            }
        } else {
            if (x == width - 2 && y == height - 2) {
                field[y][x] = new Cell(x, y, TypeOfCell.EXIT);
                return true;
            }
        }
        return false;
    }

    public Cell getCell(int x, int y) {
        return field[y][x];
    }

    public Cell[][] getField() {
        return field;
    }

}
