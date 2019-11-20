package generator;

import entities.Cell;
import entities.Field;
import entities.Path;
import entities.TypeOfCell;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Generator {

    private int width;
    private int height;
    private long seed;
    private Random random;
    private Field field;
    private ArrayList<Path> listOfPaths;


    public Generator(int height, int width, Long seed) {
        dimensionsCheck(width, height);
        init(width, height, seed);
    }

    public Generator(int width, int height) {
        dimensionsCheck(width, height);
        init(width, height, null);
    }

    private void init(int width, int height, Long seed) {
        listOfPaths = new ArrayList<>();
        field = new Field(height, width);
        field.init();
        this.width = width;
        this.height = height;
        if (seed == null) {
            random = new Random();
        } else {
            random = new Random(seed);
        }
    }

    private void dimensionsCheck(int width, int height) {
        if (width < 15 || width > 202 || height < 15 || height > 202) {
            throw new IllegalArgumentException("Dimensions should be in 15..200");
        }
    }

    private void generateField() {
        field.init();
        for (int i = 1; i < field.getField().length; i++) {
            for (int j = 1; j < field.getField().length; j++) {
                if(i % 2 == 1 && j % 2 == 1 && i != field.getField().length - 1 && j != field.getField().length - 1) {
                    field.setWall(j, i, false);
                }
            }
        }
    }

    private void generateMaze() {
        Cell[][] cells = field.getField();
        Cell currentCell = cells[1][1];
        Cell neighbourCell;
        Stack<Cell> stack = new Stack<>();
        do {
            List<Cell> neighbours = getNeighbours(currentCell);
            if (neighbours.size() != 0) { //если у клетки есть непосещенные соседи
                int randNum = random.nextInt(neighbours.size());
                neighbourCell = neighbours.get(randNum); //выбираем случайного соседа
                stack.push(currentCell); //заносим текущую точку в стек
                removeWall(currentCell, neighbourCell); //убираем стену между текущей и сосендней точками
                currentCell = neighbourCell; //делаем соседнюю точку текущей и отмечаем ее посещенной
                currentCell.setVisited(true);
            } else if (stack.size() > 0) { //если нет соседей, возвращаемся на предыдущую точку
                currentCell = stack.pop();
            } else { //если нет соседей и точек в стеке, но не все точки посещены, выбираем случайную из непосещенных
                List<Cell> cellsUnvisited = getUnvisitedCells();
                int randNum = random.nextInt(cellsUnvisited.size() - 1);
                currentCell = cellsUnvisited.get(randNum);
            }
        }
            while (unvisitedCount() > 0);
    }

    private int unvisitedCount() {
        int ctr = 0;
        Cell[][] cells = field.getField();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if(cells[i][j].toString().equals(".") && !cells[i][j].isVisited()) {
                    ctr++;
                }
            }
        }
        return ctr;
    }

    private List<Cell> getUnvisitedCells() {
        Cell[][] cells = field.getField();
        List<Cell> cellArrayList = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if(cells[i][j].toString().equals(".") && !cells[i][j].isVisited()) {
                    cellArrayList.add(cells[i][j]);
                }
            }
        }
        return cellArrayList;
    }

    private List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        for(Direction direction : Direction.values()){//для каждого направдения
            if(cell.getX() + direction.getCoordsShift().getKey() * 2 > 0 &&
                    cell.getX() + direction.getCoordsShift().getKey() * 2 < width &&
                        cell.getY() + direction.getCoordsShift().getValue() * 2 > 0 &&
                            cell.getY() + direction.getCoordsShift().getValue() * 2 < height){
                Cell mazeCellCurrent = field.getCell(cell.getX() + direction.getCoordsShift().getKey() * 2, cell.getY() + direction.getCoordsShift().getValue() * 2);
                if(mazeCellCurrent.toString().equals(".") && !mazeCellCurrent.isVisited()){
                    neighbours.add(mazeCellCurrent); //записать в массив;
                }
            }
        }
        return neighbours;
    }

    private void removeWall(Cell currentCell, Cell neighbourCell) {
        int xDiff = neighbourCell.getX() - currentCell.getX();
        int yDiff = neighbourCell.getY() - currentCell.getY();
        int addX, addY;

        addX = (xDiff != 0) ? (xDiff / Math.abs(xDiff)) : 0;
        addY = (yDiff != 0) ? (yDiff / Math.abs(yDiff)) : 0;

        int newX = currentCell.getX() + addX; //координаты стенки
        int newY = currentCell.getY() + addY;

        field.setWall(newX, newY, false);
        field.getCell(newX, newY).setVisited(true);
    }


    public void generate() {
        generateField();
        generateMaze();
        field.setEndPoints(1, 1, false);
        field.setEndPoints(field.getField().length - 2, field.getField().length - 2, true);
    }

    public Field getField() {
        return field;
    }

}
