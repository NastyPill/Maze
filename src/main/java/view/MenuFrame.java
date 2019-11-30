package view;

import controller.Controller;
import entities.Cell;
import entities.MazeView;
import entities.TypeOfCell;
import generator.Direction;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MenuFrame extends JFrame {

    private JPanel panel;
    private JTextArea sizeArea;
    private JTextArea seedArea;
    private JMenuBar bar;
    private JLabel label;
    private JButton startButton;
    private Controller controller;
    private JFileChooser fileChooser;
    private Boolean haveFile = false;


    private JPanel cells[][];
    private Pair<Integer, Integer> exit;
    private Pair<Integer, Integer> entrance;
    private boolean haveHole;

    private Cell[][] field;
    private String[] typeOfCell;


    public MenuFrame() {
        createMenuPanel();
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileToItem = new JMenuItem("Select file to write");
        JMenuItem fileFromItem = new JMenuItem("Select file to read");
        fileMenu.add(fileFromItem);
        fileMenu.add(fileToItem);
        fileFromItem.addActionListener(actionEvent -> {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.showOpenDialog(panel);
            if (fileChooser.getSelectedFile() != null && fileChooser.getSelectedFile().getName().contains(".txt")) {
                controller.setFileIn(fileChooser.getSelectedFile().getAbsolutePath());
                haveFile = true;
                try {
                    controller.start();
                } catch (IllegalArgumentException ex) {
                    haveFile = false;
                    controller.setFileIn("");
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                if (fileChooser.getSelectedFile() == null) {
                    JOptionPane.showMessageDialog(null, "File has not been chosen");
                } else {
                    JOptionPane.showMessageDialog(null, "It is not txt file: " + fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        fileToItem.addActionListener(actionEvent -> {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.showOpenDialog(panel);
            if (fileChooser.getSelectedFile() != null && fileChooser.getSelectedFile().getName().contains(".txt"))
                controller.setFileOut(fileChooser.getSelectedFile().getAbsolutePath());
        });

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem language = new JMenuItem("Language");
        JMenuItem colorScheme = new JMenuItem("Color scheme");
        settingsMenu.add(colorScheme);
        settingsMenu.add(language);
        //settingsMenu.add(solveMenuItem);

        JMenuItem helpMenuItem = new JMenu("Help");
        JMenu exitMenu = new JMenu("Exit");
        JMenuItem sure = new JMenuItem("Are you sure?");
        JMenuItem yes = new JMenuItem("YES");
        JMenuItem no = new JMenuItem("NO");
        exitMenu.add(sure);
        exitMenu.add(yes);
        exitMenu.add(no);
        sure.setEnabled(false);
        helpMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String res = getClass().getClassLoader().getResource("help.txt").toString().substring(6);
                    BufferedReader br = new BufferedReader(new FileReader(res));
                    String s = br.readLine();
                    StringBuilder sb = new StringBuilder();
                    while (s != null) {
                        sb.append(s);
                        sb.append("\n");
                        s = br.readLine();
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        yes.addActionListener(actionEvent -> {
            System.exit(0);
        });

        bar = new JMenuBar();
        bar.add(fileMenu);
        bar.add(settingsMenu);
        bar.add(helpMenuItem);
        bar.add(exitMenu);
        bar.setBackground(new Color(160, 160, 160));
        bar.setBorder(BorderFactory.createLineBorder(new Color(0), 0));

        this.setSize(300, 300);
        this.setContentPane(panel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(bar);
        this.setBackground(new Color(0));

        this.setVisible(true);

    }

    public void createMenuPanel() {
        controller = new Controller();
        startButton = new JButton("Start");
        label = new JLabel("MAZE");
        label.setFont(new Font("Arial", Font.BOLD, 74));
        panel = new JPanel();
        seedArea = new JTextArea("Enter seed here");
        seedArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seedArea.setText("");
            }
        });
        sizeArea = new JTextArea("Enter size (number in 15..200)");
        sizeArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sizeArea.setText("");
            }
        });

        panel.setLayout(null);
        panel.add(sizeArea);
        panel.add(startButton);
        panel.add(seedArea);
        panel.add(label);
        startButton.setBounds(50, 165, 180, 60);
        sizeArea.setBounds(50, 130, 180, 20);
        sizeArea.setBorder(new BevelBorder(1));
        seedArea.setBounds(50, 100, 180, 20);
        seedArea.setBorder(new BevelBorder(1));
        label.setBounds(35, 30, 250, 60);

        startButton.setBorder(new BevelBorder(0));
        startButton.setFont(new Font("Arial", Font.BOLD, 50));
        startButton.setForeground(new Color(205, 205, 205));
        startButton.setBackground(new Color(60, 60, 60));
        sizeArea.setBackground(new Color(60, 60, 60));
        sizeArea.setForeground(new Color(205, 205, 205));
        seedArea.setBackground(new Color(60, 60, 60));
        seedArea.setForeground(new Color(205, 205, 205));
        label.setForeground(new Color(205, 205, 205));
        panel.setBackground(new Color(60, 60, 60));
        JButton picButton = new JButton("Pic");
        picButton.setBorder(new BevelBorder(0));
        picButton.setFont(new Font("Arial", Font.BOLD, 20));
        picButton.setForeground(new Color(205, 205, 205));
        picButton.setBackground(new Color(60, 60, 60));
        picButton.setBounds(0, 165, 40, 50);

        panel.add(picButton);

        picButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String s = sizeArea.getText();
                int dim = -1;
                if (s.matches("\\d{2,6}")) {
                    dim = Integer.parseInt(s);
                    if (dim % 2 == 0) {
                        dim++;
                    }
                } else {
                    sizeArea.setText("Illegal format");
                    return;
                }
                controller.setSizeX(dim);
                controller.setSizeY(dim);
                if (seedArea.getText().equals("Illegal format!") || seedArea.getText().equals("Enter seed here")) {
                    controller.setSeed(System.currentTimeMillis());
                } else {
                    try {
                        controller.setSeed(Long.parseLong(s));
                    } catch (NumberFormatException ex) {
                        seedArea.setText("Illegal format!");
                    }
                }
                controller.imageMakerInvoke();
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (haveFile) {
                    try {
                        createMazePanel(controller.start());
                    } catch (IllegalArgumentException ex) {
                        controller.setFileIn("");
                        haveFile = false;
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                } else {
                    String s = sizeArea.getText();
                    int dim = -1;
                    if (s.matches("\\d{2,3}")) {
                        dim = Integer.parseInt(s);
                        if (dim > 202) {
                            sizeArea.setText("Size should be 15-200");
                        } else {
                            if (dim % 2 == 0) {
                                dim++;
                            }
                            if (dim < 15 || dim > 202) {
                                sizeArea.setText("Size should be 15-200");
                                return;
                            }
                        }
                    } else {
                        sizeArea.setText("Illegal format");
                        return;
                    }
                    controller.setSizeX(dim);
                    controller.setSizeY(dim);
                    if (seedArea.getText().equals("Illegal format!") || seedArea.getText().equals("Enter seed here")) {
                        controller.setSeed(System.currentTimeMillis());
                    } else {
                        try {
                            controller.setSeed(Long.parseLong(seedArea.getText()));
                        } catch (NumberFormatException ex) {
                            seedArea.setText("Illegal format!");
                        }
                    }
                    try {
                        createMazePanel(controller.start());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
        this.getContentPane().requestFocus();
    }

    public void createMazePanel(MazeView mazeView) {
        int rows = mazeView.getHeight();
        int cols = mazeView.getWidth();
        typeOfCell = mazeView.getField();
        entrance = null;
        exit = null;
        cells = new JPanel[rows][cols];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new JPanel();
                switch (typeOfCell[i].charAt(j)) {
                    case '+':
                        cells[i][j].setBackground(Color.BLACK);
                        break;

                    case '.':
                        cells[i][j].setBackground(Color.WHITE);
                        haveHole = true;
                        break;

                    case '0':
                        cells[i][j].setBackground(Color.GREEN);
                        break;

                    case 'X': {
                        cells[i][j].setBackground(Color.RED);
                        if (exit == null)
                            exit = new Pair<>(i, j);
                        else {
                            throw new IllegalArgumentException("There are two exits");
                        }
                    }
                    break;

                    case '*': {
                        cells[i][j].setBackground(Color.MAGENTA);
                        if (entrance == null)
                            entrance = new Pair<>(i, j);
                        else {
                            throw new IllegalArgumentException("There are two entrances");
                        }
                    }
                    break;
                }
                panel.add(cells[i][j]);
            }
        }
        if (entrance == null) {
            throw new IllegalArgumentException("ERROR: There is no entrance in the maze");
        }
        if (exit == null) {
            throw new IllegalArgumentException("ERROR: There is no exit in the maze");
        }
        if (!haveHole) {
            throw new IllegalArgumentException("ERROR: There is no holes in the maze");
        }
        int multiplier = 100;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        while (cols * multiplier > screenSize.getHeight() * 0.9) {
            multiplier--;
        }
        this.setSize(new Dimension(cols * multiplier + 16, rows * multiplier + 60));
        this.setBackground(new Color(0));
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int x = entrance.getValue();
                int y = entrance.getKey();
                int tempX = x;
                int tempY = y;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_S: {
                        field = new Cell[cells.length][cells.length];
                        for (int i = 0; i < cells.length; i++) {
                            for (int j = 0; j < cells.length; j++) {
                                switch (typeOfCell[i].charAt(j)) {
                                    case '+':
                                        field[i][j] = new Cell(i, j, TypeOfCell.WALL);
                                        break;

                                    case '.':
                                        field[i][j] = new Cell(i, j, TypeOfCell.HOLE);
                                        break;

                                    case '0':
                                        field[i][j] = new Cell(i, j, TypeOfCell.EMPTY_CELL);
                                        break;

                                    case 'X':
                                        field[i][j] = new Cell(i, j, TypeOfCell.EXIT);
                                        break;

                                    case '*':
                                        field[i][j] = new Cell(i, j, TypeOfCell.ENTRANCE);
                                        break;
                                }
                            }
                        }
                        solveMaze();
                        savePic();
                    }
                    break;

                    case KeyEvent.VK_UP: {
                        entrance = new Pair<>(y - 1, x);
                        y = entrance.getKey();
                    }
                    break;
                    case KeyEvent.VK_DOWN: {
                        entrance = new Pair<>(y + 1, x);
                        y = entrance.getKey();
                    }
                    break;
                    case KeyEvent.VK_LEFT: {
                        entrance = new Pair<>(y, x - 1);
                        x = entrance.getValue();
                    }
                    break;
                    case KeyEvent.VK_RIGHT: {
                        entrance = new Pair<>(y, x + 1);
                        x = entrance.getValue();
                    }
                    break;
                }
                if (y < rows && y >= 0 && x < cols && x >= 0 && !cells[y][x].getBackground().equals(Color.BLACK)) {
                    cells[tempY][tempX].setBackground(new Color(157, 157, 157));
                    cells[y][x].setBackground(Color.MAGENTA);
                } else {
                    y = tempY;
                    x = tempX;
                    entrance = new Pair<>(y, x);
                }
                if (exit.getKey() == entrance.getKey() && exit.getValue() == entrance.getValue()) {
                    int result = JOptionPane.showConfirmDialog(null, "You win!\n EXIT?", "YOU WIN!", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    } else {
                        String s = "PLEASE CLICK YES!";
                        int ctr = 0;
                        StringBuilder sb = new StringBuilder(s);
                        while (result != JOptionPane.YES_OPTION) {
                            ctr++;
                            result = JOptionPane.showConfirmDialog(null, sb.toString(), "YOU WIN!", JOptionPane.YES_NO_OPTION);
                            if (ctr % 5 == 0) {
                                sb.append("\n");
                            } else {
                                sb.append(" ");
                            }
                            sb.append(s);
                        }
                        System.exit(0);
                    }
                }
            }
        });
        panel.setBackground(new Color(0));
        this.setContentPane(panel);
        panel.requestFocus();
    }

    private void savePic() {
        System.out.println("start");
        BufferedImage image = new BufferedImage(cells.length * 3, cells.length * 3, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        for (int j = 0; j < cells.length; j++) {
            for (int i = 0; i < cells.length; i++) {
                graphics2D.setColor(cells[j][i].getBackground());
                graphics2D.fillRect(i * 3, j * 3, 3, 3);
            }
        }
        System.out.println("ok");
        File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6) + "solvedImg.png");
        try {
            System.out.println("print");
            System.out.println(file);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void solveMaze() {
        Random random = new Random();
        Cell currentCell = field[entrance.getKey()][entrance.getValue()];
        Cell neighbourCell;
        Stack<Cell> stack = new Stack<>();
        do {
            java.util.List<Cell> neighbours = getNeighbours(currentCell);
            if (neighbours.size() != 0) {
                cells[currentCell.getX()][currentCell.getY()].setBackground(new Color(150, 250, 150));
                int randNum = random.nextInt(neighbours.size());
                neighbourCell = neighbours.get(randNum);
                stack.push(currentCell);
                currentCell = neighbourCell;
                currentCell.setVisited(true);
            } else if (stack.size() > 0) {
                cells[currentCell.getX()][currentCell.getY()].setBackground(new Color(250, 150, 150));
                currentCell = stack.pop();
            } else {
                JOptionPane.showMessageDialog(null, "There is no exit in the maze");
                return;
            }
        }
        while (currentCell.getX() != exit.getKey() || currentCell.getY() != exit.getValue());
    }

    private List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        for (Direction direction : Direction.values()) {//для каждого направдения
            if (cell.getX() + direction.getCoordsShift().getKey() > 0 &&
                    cell.getX() + direction.getCoordsShift().getKey() < field.length &&
                    cell.getY() + direction.getCoordsShift().getValue() > 0 &&
                    cell.getY() + direction.getCoordsShift().getValue() < field.length) {
                Cell mazeCellCurrent = field[cell.getX() + direction.getCoordsShift().getKey()][cell.getY() + direction.getCoordsShift().getValue()];
                if ((mazeCellCurrent.toString().equals("X") || mazeCellCurrent.toString().equals(".")) && !mazeCellCurrent.isVisited()) {
                    neighbours.add(mazeCellCurrent); //записать в массив;
                }
            }
        }
        return neighbours;
    }
}
