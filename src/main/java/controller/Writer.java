package controller;

import java.io.*;
import java.nio.file.Files;

public class Writer implements Closeable {

    BufferedWriter bw;

    public Writer(String file) throws IOException {
        File f = new File(file);
        if (!f.exists())
            f.createNewFile();
        bw = new BufferedWriter(new FileWriter(file));
    }

    public void write(String content) throws IOException {
        bw.write(content);
    }

    @Override
    public void close() throws IOException {
        bw.close();
        bw = null;
    }
}
