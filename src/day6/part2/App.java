package day6.part2;

import java.util.ArrayList;
import java.util.LinkedList;

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
    private static LinkedList<int[]> blockingObstacles;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day6\\Input.txt");
        blockingObstacles = new LinkedList<>();
        populatemappedArea();
        guard = '^';
        currentGuardDirection = Directions.TOP;

        System.out.println(calculateNumberOfPositions());
        showMap();
        System.out.println(calculateNumberOfPossibleLoops());
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
        // showMap();
        return true;
    }

    private static boolean canMoveUp() {
        if (!guardIsInsdeOfMap(currentGuardLine - 1, currentGuardColumn)) {
            return false;
        }
        if (mappedArea[currentGuardLine - 1][currentGuardColumn] == '#') {
            addBlockingObstacle(currentGuardLine - 1, currentGuardColumn);
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
            addBlockingObstacle(currentGuardLine, currentGuardColumn + 1);
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
            addBlockingObstacle(currentGuardLine + 1, currentGuardColumn);
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
            addBlockingObstacle(currentGuardLine, currentGuardColumn - 1);
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

    private static void addBlockingObstacle(int line, int column) {
        int[] obstacleCoordinates = { line, column };
        blockingObstacles.add(obstacleCoordinates);
    }

    private static int calculateNumberOfPossibleLoops() {
        int sum = 0;
        while (blockingObstacles.size() >= 3) {
            if (addingObstacleCreatesLoop()) {
                sum++;
            }
        }
        return sum;
    }

    private static boolean addingObstacleCreatesLoop() {
        int[] firstCornerCoordinates = blockingObstacles.removeFirst();
        int[] secondCornerCoordinates = blockingObstacles.get(0);
        int[] thirdCornerCoordinates = blockingObstacles.get(1);
        Directions loopClosingDirection = determineLoopClosingDirection(firstCornerCoordinates,
                secondCornerCoordinates);

        return pathIsFreeToAddObstacle(firstCornerCoordinates, thirdCornerCoordinates, loopClosingDirection);
    }

    private static Directions determineLoopClosingDirection(int[] firstCornerCoordinates,
            int[] secondCornerCoordinates) {
        int firstCornerLine = firstCornerCoordinates[0];
        int firstCornerColumn = firstCornerCoordinates[1];
        int secondCornerLine = secondCornerCoordinates[0];
        int secondCornerColumn = secondCornerCoordinates[1];

        if (firstCornerLine < secondCornerLine && firstCornerColumn < secondCornerColumn) {
            return Directions.LEFT;
        }
        if (firstCornerLine < secondCornerLine && firstCornerColumn > secondCornerColumn) {
            return Directions.TOP;
        }
        if (firstCornerLine > secondCornerLine && firstCornerColumn > secondCornerColumn) {
            return Directions.RIGHT;
        }
        if (firstCornerLine > secondCornerLine && firstCornerColumn < secondCornerColumn) {
            return Directions.BOTTOM;
        }

        return null;
    }

    private static boolean pathIsFreeToAddObstacle(int[] firstCornerCoordinates, int[] thirdCornerCoordinates,
            Directions closingDirection) {
        switch (closingDirection) {
            case TOP:
                System.out.println();
                return pathIsFreeToTheTop(firstCornerCoordinates, thirdCornerCoordinates);
            case RIGHT:
                System.out.println();
                return pathIsFreeToTheRight(firstCornerCoordinates, thirdCornerCoordinates);
            case BOTTOM:
                System.out.println();
                return pathIsFreeToTheBottom(firstCornerCoordinates, thirdCornerCoordinates);
            case LEFT:
                System.out.println();
                return pathIsFreeToTheLeft(firstCornerCoordinates, thirdCornerCoordinates);
            default:
                return false;
        }
    }

    private static boolean pathIsFreeToTheTop(int[] firstCornerCoordinates, int[] thirdCornerCoordinates) {
        int firstCornerLine = firstCornerCoordinates[0];
        int firstCornerColumn = firstCornerCoordinates[1];
        int thirdCornerLine = thirdCornerCoordinates[0];
        int thirdCornerColumn = thirdCornerCoordinates[1];

        int finalLine = thirdCornerLine;
        for (int line = thirdCornerLine; line >= firstCornerLine - 1; line--) {
            finalLine = line;
            System.out.print(mappedArea[line][thirdCornerColumn + 1]);
            if (line < 0 || mappedArea[line][thirdCornerColumn + 1] == '#') {
                return false;
            }
        }
        System.out.println(": " + finalLine + ", " + (thirdCornerColumn + 1));
        return true;
    }

    private static boolean pathIsFreeToTheRight(int[] firstCornerCoordinates, int[] thirdCornerCoordinates) {
        int firstCornerLine = firstCornerCoordinates[0];
        int firstCornerColumn = firstCornerCoordinates[1];
        int thirdCornerLine = thirdCornerCoordinates[0];
        int thirdCornerColumn = thirdCornerCoordinates[1];

        int finalColumn = thirdCornerColumn;
        for (int column = thirdCornerColumn; column <= firstCornerColumn + 1; column++) {
            finalColumn = column;
            System.out.print(mappedArea[thirdCornerLine + 1][column]);
            if (column >= columnCount || mappedArea[thirdCornerLine + 1][column] == '#') {
                return false;
            }
        }
        System.out.println(": " + (thirdCornerLine + 1) + ", " + finalColumn);
        return true;
    }

    private static boolean pathIsFreeToTheBottom(int[] firstCornerCoordinates, int[] thirdCornerCoordinates) {
        int firstCornerLine = firstCornerCoordinates[0];
        int firstCornerColumn = firstCornerCoordinates[1];
        int thirdCornerLine = thirdCornerCoordinates[0];
        int thirdCornerColumn = thirdCornerCoordinates[1];

        int finalLine = thirdCornerLine;
        for (int line = thirdCornerLine; line <= firstCornerLine + 1; line++) {
            finalLine = line;
            System.out.print(mappedArea[line][thirdCornerColumn - 1]);
            if (line >= lineCount || mappedArea[line][thirdCornerColumn - 1] == '#') {
                return false;
            }
        }
        System.out.println(": " + finalLine + ", " + (thirdCornerColumn - 1));
        return true;
    }

    private static boolean pathIsFreeToTheLeft(int[] firstCornerCoordinates, int[] thirdCornerCoordinates) {
        int firstCornerLine = firstCornerCoordinates[0];
        int firstCornerColumn = firstCornerCoordinates[1];
        int thirdCornerLine = thirdCornerCoordinates[0];
        int thirdCornerColumn = thirdCornerCoordinates[1];

        int finalColumn = thirdCornerColumn;
        for (int column = thirdCornerColumn; column >= firstCornerColumn - 1; column--) {
            finalColumn = column;
            System.out.print(mappedArea[thirdCornerLine - 1][column]);
            if (column < 0 || mappedArea[thirdCornerLine - 1][column] == '#') {
                return false;
            }
        }
        System.out.println(": " + (thirdCornerLine - 1) + ", " + finalColumn);
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
