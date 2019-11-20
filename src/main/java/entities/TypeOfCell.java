package entities;

public enum TypeOfCell {
    WALL("+"), EXIT("X"), ENTRANCE("*"), EMPTY_CELL("0"), HOLE(".");

    String string;

    TypeOfCell (String name) {
        this.string = name;
    }

    @Override
    public String toString() {
        return string;
    }

}
