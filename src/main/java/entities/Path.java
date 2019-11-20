package entities;

import generator.Direction;

import java.util.Stack;

public class Path {
    private int endX;
    private int endY;
    private int beginX;
    private int beginY;
    private Stack<Direction> path;
    private int length;

    public Path(int currentX, int currentY) {
        endX = currentX;
        endY = currentY;
        beginX = currentX;
        beginY = currentY;
        path = new Stack<>();
        length = 0;
    }

    public void add(Direction direction) {
        path.add(direction);
        endX += direction.getCoordsShift().getKey();
        endY += direction.getCoordsShift().getValue();
        length++;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Direction peek() {
        return path.peek();
    }

    public Direction pop() {
        Direction direction = path.pop();
        endX -= direction.getCoordsShift().getKey();
        endY -= direction.getCoordsShift().getValue();
        length--;
        return direction;
    }

    public void reverse() {
        Stack<Direction> result = new Stack<>();
        while (!path.isEmpty()) {
            result.add(path.pop());
        }
        path = result;
        int tmp = endX;
        endX = beginX;
        beginX = tmp;
        tmp = endY;
        endY = beginY;
        beginY = tmp;
    }

    public int getLength() {
        return length;
    }
}
