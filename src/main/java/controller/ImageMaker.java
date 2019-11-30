package controller;

import entities.Cell;
import entities.TypeOfCell;
import generator.Direction;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class ImageMaker {

    private Pair<Color, Cell>[][] field;
    private int entX;
    private int entY;
    private int exX;
    private int exY;

    public ImageMaker(String[] fieldString) {
        field = new Pair[fieldString.length][fieldString.length];
        for (int i = 0; i < fieldString.length; i++) {
            for (int j = 0; j < fieldString.length; j++) {
                Color color = Color.WHITE;
                Character c = fieldString[i].charAt(j);
                switch (fieldString[i].charAt(j)) {
                    case '.': {
                        color = Color.WHITE;
                        field[i][j] = new Pair<>(color, new Cell(i, j, TypeOfCell.HOLE));
                    }
                        break;
                    case '+': {
                        color = Color.BLACK;
                        field[i][j] = new Pair<>(color, new Cell(i, j, TypeOfCell.WALL));
                    }
                        break;
                    case '*': {
                        entX = j;
                        entY = i;
                        color = Color.CYAN;
                        field[i][j] = new Pair<>(color, new Cell(i, j, TypeOfCell.ENTRANCE));
                    }
                        break;
                    case 'X': {
                        exX = j;
                        exY = i;
                        color = Color.RED;
                        field[i][j] = new Pair<>(color, new Cell(i, j, TypeOfCell.EXIT));
                    }
                    break;
                }
            }
        }
    }

    public void start() {
        create();
        solveMaze();
    }

    public void create() {
        System.out.println("start");
        BufferedImage image = new BufferedImage(field.length * 3, field.length * 3, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                graphics2D.setColor(field[i][j].getKey());
                graphics2D.fillRect(i * 3, j * 3, 3, 3);
            }
        }
        System.out.println("ok");
        long i = System.currentTimeMillis();
        File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6) + i + "img.png");
        try {
            System.out.println("print");
            System.out.println(file);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "DONE");
    }

    public void solveMaze() {
        Random random = new Random();
        Cell currentCell = field[entY][entX].getValue();
        Cell neighbourCell;
        Stack<Cell> stack = new Stack<>();
        do {
            List<Cell> neighbours = getNeighbours(currentCell);
            if (neighbours.size() != 0) {
                field[currentCell.getX()][currentCell.getY()] = new Pair<>(new Color(150, 250, 150), currentCell);
                int randNum = random.nextInt(neighbours.size());
                neighbourCell = neighbours.get(randNum);
                stack.push(currentCell);
                currentCell = neighbourCell;
                currentCell.setVisited(true);
            } else if (stack.size() > 0) {
                field[currentCell.getX()][currentCell.getY()] = new Pair<>(new Color(250, 150, 150), currentCell);
                currentCell = stack.pop();
            } else {
                JOptionPane.showMessageDialog(null, "There is no exit in the maze");
                return;
            }
        }
        while (currentCell.getX() != exX || currentCell.getY() != exY);
        create();
    }

    private java.util.List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        for (Direction direction : Direction.values()) {//для каждого направдения
            if (cell.getX() + direction.getCoordsShift().getKey() > 0 &&
                    cell.getX() + direction.getCoordsShift().getKey() < field.length &&
                    cell.getY() + direction.getCoordsShift().getValue() > 0 &&
                    cell.getY() + direction.getCoordsShift().getValue() < field.length) {
                Cell mazeCellCurrent = field[cell.getX() + direction.getCoordsShift().getKey()][cell.getY() + direction.getCoordsShift().getValue()].getValue();
                if ((mazeCellCurrent.toString().equals("X") || mazeCellCurrent.toString().equals(".")) && !mazeCellCurrent.isVisited()) {
                    neighbours.add(mazeCellCurrent); //записать в массив;
                }
            }
        }
        return neighbours;
    }

}
