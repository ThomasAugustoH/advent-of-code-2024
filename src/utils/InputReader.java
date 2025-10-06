package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputReader {

    private Scanner fileReader;
    private File inputFile;

    public InputReader(String inputPath) {
        inputFile = new File(inputPath);
        openScanner();
    }

    private void openScanner() {
        try {
            fileReader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Path: " + inputFile.toPath());
        }
    }

    public String readLine() {
        if (fileReader.hasNext()) {
            return fileReader.nextLine();
        } else {
            return null;
        }
    }

    public void closeScanner() {
        fileReader.close();
    }

    public boolean hasNext() {
        return fileReader.hasNext();
    }
}
