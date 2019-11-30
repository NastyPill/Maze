package controller;

import entities.Field;
import entities.MazeView;
import generator.Generator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {

    private Generator generator;
    private int sizeX;
    private int sizeY;
    private Long seed;
    private String fileIn;
    private String fileOut;

    public Controller() {
        fileOut = "";
        fileIn = "";
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setFileIn(String fileIn) {
        this.fileIn = fileIn;
    }

    public void setFileOut(String fileOut) {
        this.fileOut = fileOut;
    }

    private String[] check(Reader reader) throws IllegalArgumentException {
        try {
            String[] s = reader.read();
            for (String str : s) {
                if (!str.matches("[\\.|\\+|\\*|0|X]*")) {
                    throw new IllegalArgumentException("File is not properly formatted");
                }
            }
            return s;
        } catch (IOException ex) {
            return null;
        }
    }

    public MazeView start() throws IllegalArgumentException {
        try {
            Reader reader;
            String[] s;
            if (!fileIn.isEmpty()) {
                reader = new Reader(fileIn);
                s = check(reader);
                if (s != null) {
                    return new MazeView(reader.getHeight(), reader.getWidth(), s);
                }
            }
            if (seed != null) {
                System.out.println(seed);
                generator = new Generator(sizeX, sizeY, seed);
            } else {
                generator = new Generator(sizeX, sizeY);
            }
            generator.generate();
            if (fileOut.isEmpty()) {
                fileOut = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6) + "txt.txt";
                setFileOut(fileOut);
            }
            Writer writer = new Writer(fileOut);
            writer.write(generator.getField().toString());
            writer.close();
            reader = new Reader(fileOut);
            String[] s123 = reader.read();
            reader.close();
            savePic();
            return new MazeView(reader.getHeight(), reader.getWidth(), s123);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void imageMakerInvoke() {
        generator = new Generator(sizeX, sizeY, seed);
        generator.generate();
        ImageMaker imageMaker = new ImageMaker(generator.getField().toString().split("\n"));
        imageMaker.start();
    }

    private void savePic() {
        System.out.println("start");
        BufferedImage image = new BufferedImage(sizeY * 3, sizeX * 3, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        Field field = generator.getField();
        String[] fieldString = field.toString().split("\n");
        for (int j = 0; j < fieldString.length; j++) {
            for (int i = 0; i < fieldString[j].length(); i++) {
                switch (fieldString[j].charAt(i)) {
                    case '.':
                        graphics2D.setColor(Color.WHITE);
                        break;
                    case '+':
                        graphics2D.setColor(Color.BLACK);
                        break;
                    case '*':
                        graphics2D.setColor(Color.CYAN);
                        break;
                    case 'X':
                        graphics2D.setColor(Color.RED);
                        break;
                }
                graphics2D.fillRect(i * 3, j * 3, 3, 3);
            }
        }
        System.out.println("ok");
        File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6) + "img.png");
        try {
            System.out.println("print");
            System.out.println(file);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
