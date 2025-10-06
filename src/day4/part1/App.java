package day4.part1;

import java.util.ArrayList;

import utils.Directions;
import utils.InputReader;

public class App {
    private static InputReader inputReader;
    private static char[][] inputMatrix;
    private static int columnCount;
    private static int lineCount;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day4\\Input.txt");

        populateInputMatrix();
        System.out.println(calculateValidWords());
    }

    private static void populateInputMatrix() {
        ArrayList<String> lines = new ArrayList<>();

        while (inputReader.hasNext()) {
            lines.add(inputReader.readLine());
        }

        lineCount = lines.size();
        columnCount = lines.getFirst().length();

        inputMatrix = new char[lineCount][columnCount];
        for (int l = 0; l < lineCount; l++) {
            char[] lineChars = lines.get(l).toCharArray();
            for (int c = 0; c < columnCount; c++) {
                inputMatrix[l][c] = lineChars[c];
            }
        }
    }

    private static int calculateValidWords() {
        int sum = 0;

        for (int l = 0; l < lineCount; l++) {
            for (int c = 0; c < columnCount; c++) {
                if (inputMatrix[l][c] == 'X') {
                    sum += countValidWordsFromCurrentX(l, c);
                }
            }
        }

        return sum;
    }

    private static int countValidWordsFromCurrentX(int line, int column) {
        int sum = 0;
        char currentLetter = 'X';

        if (topLeftIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line - 1, column - 1, nextLetter(currentLetter), Directions.TOP_LEFT)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.TOP_LEFT);
            }
        }
        if (topIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line - 1, column, nextLetter(currentLetter), Directions.TOP)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.TOP);
            }
        }

        if (topRightIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line - 1, column + 1, nextLetter(currentLetter), Directions.TOP_RIGHT)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.TOP_RIGHT);
            }
        }

        if (leftIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line, column - 1, nextLetter(currentLetter), Directions.LEFT)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.LEFT);
            }
        }

        if (rightIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line, column + 1, nextLetter(currentLetter), Directions.RIGHT)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.RIGHT);
            }
        }

        if (bottomLeftIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line + 1, column - 1, nextLetter(currentLetter), Directions.BOTTOM_LEFT)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.BOTTOM_LEFT);
            }
        }

        if (bottomIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line + 1, column, nextLetter(currentLetter), Directions.BOTTOM)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.BOTTOM);
            }
        }

        if (bottomRightIsValid(line, column, nextLetter(currentLetter))) {
            if (isValidWord(line + 1, column + 1, nextLetter(currentLetter), Directions.BOTTOM_RIGHT)) {
                sum++;
                // System.out.println(line + " " + column + ": " + Directions.BOTTOM_RIGHT);
            }
        }

        return sum;
    }

    private static boolean isValidWord(int line, int column, char currentLetter, Directions currentDirection) {
        if (currentLetter == 'S') {
            return true;
        }

        switch (currentDirection) {
            case TOP_LEFT:
                if (topLeftIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line - 1, column - 1, nextLetter(currentLetter), Directions.TOP_LEFT);
                }
                break;
            case TOP:
                if (topIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line - 1, column, nextLetter(currentLetter), Directions.TOP);
                }
                break;
            case TOP_RIGHT:
                if (topRightIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line - 1, column + 1, nextLetter(currentLetter), Directions.TOP_RIGHT);
                }
                break;
            case LEFT:
                if (leftIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line, column - 1, nextLetter(currentLetter), Directions.LEFT);
                }
                break;
            case RIGHT:
                if (rightIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line, column + 1, nextLetter(currentLetter), Directions.RIGHT);
                }
                break;
            case BOTTOM_LEFT:
                if (bottomLeftIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line + 1, column - 1, nextLetter(currentLetter), Directions.BOTTOM_LEFT);
                }
                break;
            case BOTTOM:
                if (bottomIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line + 1, column, nextLetter(currentLetter), Directions.BOTTOM);
                }
                break;
            case BOTTOM_RIGHT:
                if (bottomRightIsValid(line, column, nextLetter(currentLetter))) {
                    return isValidWord(line + 1, column + 1, nextLetter(currentLetter), Directions.BOTTOM_RIGHT);
                }
                break;
            default:
                break;
        }
        return false;
    }

    private static char nextLetter(char currentLetter) {
        char[] letterSequence = { 'X', 'M', 'A', 'S' };
        char nextLetter = 'X';

        for (int i = 0; i < letterSequence.length - 1; i++) {
            if (currentLetter == letterSequence[i]) {
                nextLetter = letterSequence[i + 1];
                break;
            }
        }
        return nextLetter;
    }

    private static boolean topLeftIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line - 1, column - 1)) {
            return false;
        }

        return isNextLetter(line - 1, column - 1, nextLetter);
    }

    private static boolean topIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line - 1, column)) {
            return false;
        }

        return isNextLetter(line - 1, column, nextLetter);
    }

    private static boolean topRightIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line - 1, column + 1)) {
            return false;
        }

        return isNextLetter(line - 1, column + 1, nextLetter);
    }

    private static boolean leftIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line, column - 1)) {
            return false;
        }
        return isNextLetter(line, column - 1, nextLetter);
    }

    private static boolean rightIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line, column + 1)) {
            return false;
        }

        return isNextLetter(line, column + 1, nextLetter);
    }

    private static boolean bottomLeftIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line + 1, column - 1)) {
            return false;
        }

        return isNextLetter(line + 1, column - 1, nextLetter);
    }

    private static boolean bottomIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line + 1, column)) {
            return false;
        }

        return isNextLetter(line + 1, column, nextLetter);
    }

    private static boolean bottomRightIsValid(int line, int column, char nextLetter) {
        if (isOutOfBounds(line + 1, column + 1)) {
            return false;
        }

        return isNextLetter(line + 1, column + 1, nextLetter);
    }

    private static boolean isOutOfBounds(int line, int column) {
        if (line < 0) {
            return true;
        }
        if (line >= lineCount) {
            return true;
        }
        if (column < 0) {
            return true;
        }
        if (column >= columnCount) {
            return true;
        }

        return false;
    }

    private static boolean isNextLetter(int line, int column, char letterToFind) {
        if (inputMatrix[line][column] == letterToFind) {
            return true;
        } else {
            return false;
        }
    }
}
