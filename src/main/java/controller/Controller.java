package controller;

import entities.MazeView;
import generator.Generator;

import java.io.IOException;

public class Controller {

    Generator generator;
    int sizeX;
    int sizeY;
    long seed;
    String fileIn;
    String fileOut;

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
                if(s != null) {
                    return new MazeView(reader.getHeight(), reader.getWidth(), s);
                }
            }
            generator = new Generator(sizeX, sizeY);
            generator.generate();
            if (fileOut.isEmpty()) {
                fileOut = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6) + "txt.txt";
                setFileOut(fileOut);
            }
            Writer writer = new Writer(fileOut);
            System.out.println(fileOut);
            writer.write(generator.getField().toString());
            writer.close();

            reader = new Reader(fileOut);
            String[] s123 = reader.read();
            for (String s2 : s123) {
                System.out.println(s2);
            }
            reader.close();
            return new MazeView(reader.getHeight(), reader.getWidth(), s123);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
