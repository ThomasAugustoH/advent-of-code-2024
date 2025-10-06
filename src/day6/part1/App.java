package day6.part1;

import java.util.ArrayList;

import utils.Directions;
import utils.InputReader;

public class App {
    private static InputReader inputReader;
    private static char[][] mappedArea;
    private static int columnCount;
    private static int lineCount;
    private static int currentGuardLine;
    private static int currentGuardColumn;
    private static Directions currentGuardDirection;
    private static char guard;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day6\\Input.txt");
        populatemappedArea();
        guard = '^';
        currentGuardDirection = Directions.TOP;
        System.out.println(calculateNumberOfPositions());
        showMap();
    }

    private static void populatemappedArea() {
        ArrayList<String> lines = new ArrayList<>();

        while (inputReader.hasNext()) {
            lines.add(inputReader.readLine());
        }

        lineCount = lines.size();
        columnCount = lines.getFirst().length();

        mappedArea = new char[lineCount][columnCount];
        for (int l = 0; l < lineCount; l++) {
            char[] lineChars = lines.get(l).toCharArray();
            for (int c = 0; c < columnCount; c++) {
                mappedArea[l][c] = lineChars[c];
                if (mappedArea[l][c] != '#' && mappedArea[l][c] != '.') {
                    currentGuardLine = l;
                    currentGuardColumn = c;
                }
            }
        }
    }

    private static int calculateNumberOfPositions() {
        int sum = 0;
        while (canMoveGuard()) {

        }

        for (int line = 0; line < lineCount; line++) {
            for (int column = 0; column < columnCount; column++) {
                if (mappedArea[line][column] == 'X') {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static boolean canMoveGuard() {
        switch (currentGuardDirection) {
            case TOP:
                if (!canMoveUp()) {
                    markVisitedArea();
                    return false;
                }
                break;
            case RIGHT:
                if (!canMoveRight()) {
                    markVisitedArea();
                    return false;
                }
                break;
            case BOTTOM:
                if (!canMoveDown()) {
                    markVisitedArea();
                    return false;
                }
                break;
            case LEFT:
                if (!canMoveLeft()) {
                    markVisitedArea();
                    return false;
                }
                break;
            default:
                break;
        }
        //showMap();
        return true;
    }

    private static boolean canMoveUp() {
        if (!guardIsInsdeOfMap(currentGuardLine - 1, currentGuardColumn)) {
            return false;
        }
        if (mappedArea[currentGuardLine - 1][currentGuardColumn] == '#') {
            changeGuardDirection();
        } else {
            markVisitedArea();
            currentGuardLine--;
            updateGuardPosition();
        }

        return true;
    }

    private static boolean canMoveRight() {
        if (!guardIsInsdeOfMap(currentGuardLine, currentGuardColumn + 1)) {
            return false;
        }
        if (mappedArea[currentGuardLine][currentGuardColumn + 1] == '#') {
            changeGuardDirection();
        } else {
            markVisitedArea();
            currentGuardColumn++;
            updateGuardPosition();
        }

        return true;
    }

    private static boolean canMoveDown() {
        if (!guardIsInsdeOfMap(currentGuardLine + 1, currentGuardColumn)) {
            return false;
        }
        if (mappedArea[currentGuardLine + 1][currentGuardColumn] == '#') {
            changeGuardDirection();
        } else {
            markVisitedArea();
            currentGuardLine++;
            updateGuardPosition();
        }

        return true;
    }

    private static boolean canMoveLeft() {
        if (!guardIsInsdeOfMap(currentGuardLine, currentGuardColumn - 1)) {
            return false;
        }
        if (mappedArea[currentGuardLine][currentGuardColumn - 1] == '#') {
            changeGuardDirection();
        } else {
            markVisitedArea();
            currentGuardColumn--;
            updateGuardPosition();
        }

        return true;
    }

    private static void changeGuardDirection() {

        switch (currentGuardDirection) {
            case TOP:
                currentGuardDirection = Directions.RIGHT;
                guard = '>';
                break;
            case RIGHT:
                currentGuardDirection = Directions.BOTTOM;
                guard = 'V';
                break;
            case BOTTOM:
                currentGuardDirection = Directions.LEFT;
                guard = '<';
                break;
            case LEFT:
                currentGuardDirection = Directions.TOP;
                guard = '^';
                break;
        }
    }

    private static void updateGuardPosition() {
        mappedArea[currentGuardLine][currentGuardColumn] = guard;
    }

    private static void markVisitedArea() {
        mappedArea[currentGuardLine][currentGuardColumn] = 'X';
    }

    private static boolean guardIsInsdeOfMap(int line, int column) {
        if (line < 0) {
            return false;
        }
        if (line >= lineCount) {
            return false;
        }
        if (column < 0) {
            return false;
        }
        if (column >= columnCount) {
            return false;
        }

        return true;
    }

    private static void showMap() {
        for (int line = 0; line < lineCount; line++) {
            for (int column = 0; column < columnCount; column++) {

                System.out.print(mappedArea[line][column]);

            }
            System.out.println();
        }
        System.out.println();
    }
}
