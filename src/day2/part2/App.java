package day2.part2;

import day2.ReportStatus;
import utils.InputReader;

public class App {

    private static InputReader inputReader;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day2\\Input.txt");

        int safeReports = calculateSafeReports();
        System.out.println(safeReports);
    }

    private static int calculateSafeReports() {
        int safeReports = 0;

        while (inputReader.hasNext()) {
            String report = inputReader.readLine();
            int[] levels = saveLine(report);
            if (validateReport(levels) == ReportStatus.SAFE) {
                safeReports++;
            } else if (validateWithProblemDampener(levels) == ReportStatus.SAFE) {
                safeReports++;
            }
        }

        return safeReports;
    }

    private static int[] saveLine(String report) {
        String[] parsedInputs = report.split(" ");
        int[] levels = new int[parsedInputs.length];
        for (int i = 0; i < parsedInputs.length; i++) {
            levels[i] = Integer.parseInt(parsedInputs[i]);
        }

        return levels;
    }

    private static ReportStatus validateReport(int[] levels) {
        ReportStatus changeDirection = determineChangeDirection(levels);
        ReportStatus reportSafety = ReportStatus.UNSAFE;

        if (changeDirection == ReportStatus.UNSAFE) {
            reportSafety = ReportStatus.UNSAFE;
        }

        if (changeDirection == ReportStatus.DECREASING) {
            reportSafety = validateDecrease(levels);
        }

        if (changeDirection == ReportStatus.INCREASING) {
            reportSafety = validateIncrease(levels);
        }

        return reportSafety;
    }

    private static ReportStatus determineChangeDirection(int[] levels) {
        if (levels[0] > levels[1]) {
            return ReportStatus.DECREASING;
        } else if (levels[0] < levels[1]) {
            return ReportStatus.INCREASING;
        }

        return ReportStatus.UNSAFE;
    }

    private static ReportStatus validateDecrease(int[] levels) {
        for (int i = 0; i < levels.length - 1; i++) {
            if (levels[i] - levels[i + 1] > 3 || levels[i] - levels[i + 1] < 1) {
                return ReportStatus.UNSAFE;
            }
        }

        return ReportStatus.SAFE;
    }

    private static ReportStatus validateIncrease(int[] levels) {
        for (int i = 0; i < levels.length - 1; i++) {
            if (levels[i] - levels[i + 1] < -3 || levels[i] - levels[i + 1] > -1) {
                return ReportStatus.UNSAFE;
            }
        }

        return ReportStatus.SAFE;
    }

    private static ReportStatus validateWithProblemDampener(int[] levels) {
        for (int i = 0; i < levels.length; i++) {
            ReportStatus report = createSubReport(levels, i);
            if (report == ReportStatus.SAFE) {
                return ReportStatus.SAFE;
            }
        }

        return ReportStatus.UNSAFE;
    }

    private static ReportStatus createSubReport(int[] levels, int levelToSkip) {
        int[] levelsWithDampener = new int[levels.length - 1];
        int indexOriginalReport = 0;

        for (int j = 0; j < levelsWithDampener.length; j++, indexOriginalReport++) {
            if (j == levelToSkip) {
                indexOriginalReport++;
            }

            levelsWithDampener[j] = levels[indexOriginalReport];
        }

        ReportStatus reportWithDampener = validateReport(levelsWithDampener);
        if (reportWithDampener == ReportStatus.SAFE) {
            return ReportStatus.SAFE;
        } else {
            return ReportStatus.UNSAFE;
        }
    }
}
