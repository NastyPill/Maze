package entities;

public class MazeView {
    private int height;
    private int width;
    private String[] field;

    public MazeView(int height, int width, String[] field) {
        this.height = height;
        this.width = width;
        this.field = field;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String[] getField() {
        return field;
    }
}
