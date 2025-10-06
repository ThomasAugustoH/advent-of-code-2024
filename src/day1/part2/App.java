package day1.part2;

import java.util.ArrayList;

import utils.InputReader;

public class App {

    private static InputReader inputReader;
    private static ArrayList<Integer> leftList;
    private static ArrayList<Integer> rightList;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day1\\Input.txt");
        leftList = new ArrayList<>();
        rightList = new ArrayList<>();

        while (inputReader.hasNext()) {
            savePair(inputReader.readLine());
        }

        System.out.println(calculateTotalSimilarity());
    }

    private static void savePair(String line) {
        String[] numbers = line.split("   ");

        leftList.add(Integer.parseInt(numbers[0]));
        rightList.add(Integer.parseInt(numbers[1]));
    }

    private static int calculateTotalSimilarity() {
        int sum = 0;

        for (Integer leftNumber : leftList) {
            sum += calculateNumberSimilarity(leftNumber);
        }

        return sum;
    }

    private static int calculateNumberSimilarity(int leftNumber) {
        int timesOnRightList = 0;

        for (Integer rightNumber : rightList) {
            if (leftNumber == rightNumber) {
                timesOnRightList++;
            }
        }

        return leftNumber * timesOnRightList;
    }
}
