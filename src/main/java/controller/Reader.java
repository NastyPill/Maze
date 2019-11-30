package controller;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements Closeable{

    private BufferedReader br;
    private int height;
    private int width;
    private String file;


    public Reader(String file) throws IOException {
        this.file = file;
        br = new BufferedReader(new FileReader(file));
        String tempString = br.readLine();
        width = tempString.length();

        while (tempString != null) {
            height++;
            tempString = br.readLine();
        }
    }

    public String[] read() throws IOException, IllegalArgumentException {
        String typeOfCell[] = new String[height];
        br = new BufferedReader(new FileReader(file));

        String tempString = br.readLine();
        String prevString = tempString;
        int i = 0;
        while (tempString != null) {
            if(prevString.length() != tempString.length()) {
                throw new IllegalArgumentException("File not properly formatted");
            }
            typeOfCell[i] = tempString;
            prevString = tempString;
            tempString = br.readLine();
            i++;
        }
        return typeOfCell;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void close() throws IOException {
        br.close();
        br = null;
    }
}
