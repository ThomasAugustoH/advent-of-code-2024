package day5.part1;

import java.util.HashMap;
import java.util.LinkedList;

import utils.InputReader;

public class App {
    private static InputReader inputReader;
    private static HashMap<Integer, LinkedList<Integer>> orderingRules;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day5\\Input.txt");
        orderingRules = new HashMap<>();

        populateOrderingRules();
        System.out.println(calculateMiddlePages());
    }

    public static void populateOrderingRules() {

        while (inputReader.hasNext()) {
            String nextPair = inputReader.readLine();
            if (nextPair.isBlank() || nextPair.isEmpty()) {
                break;
            }

            String[] numbers = nextPair.split("[|]");
            int before = Integer.parseInt(numbers[0]);
            int after = Integer.parseInt(numbers[1]);
            LinkedList<Integer> numbersAfter;

            if (!orderingRules.containsKey(before)) {
                numbersAfter = new LinkedList<>();
            } else {
                numbersAfter = orderingRules.get(before);
            }
            numbersAfter.add(after);

            orderingRules.put(before, numbersAfter);
        }
    }

    public static int calculateMiddlePages() {
        int sum = 0;

        while (inputReader.hasNext()) {
            String nextUpdate = inputReader.readLine();
            LinkedList<Integer> updateNumbers = createUpdateList(nextUpdate);

            if (updateObeysOrder(updateNumbers)) {
                sum += getMiddlePage(updateNumbers);
            }
        }

        return sum;
    }

    public static LinkedList createUpdateList(String update) {
        String[] parsedNumbers = update.split(",");
        LinkedList<Integer> numbers = new LinkedList<>();

        for (int i = 0; i < parsedNumbers.length; i++) {
            numbers.add(Integer.parseInt(parsedNumbers[i]));
        }

        return numbers;
    }

    public static boolean updateObeysOrder(LinkedList<Integer> update) {
        LinkedList<Integer> alreadyProcessedNumbers = new LinkedList<>();

        for (Integer processingNumber : update) {
            if (orderingRules.containsKey(processingNumber)) {
                LinkedList<Integer> numbersAfter = orderingRules.get(processingNumber);

                if (numberShouldHaveBeenProcessedLater(alreadyProcessedNumbers, numbersAfter)) {
                    return false;
                }

            }
            alreadyProcessedNumbers.add(processingNumber);
        }

        return true;
    }

    public static boolean numberShouldHaveBeenProcessedLater(LinkedList<Integer> processedNumbers,
            LinkedList<Integer> numbersToBeProcessedLater) {
        for (Integer processedNumber : processedNumbers) {
            for (Integer numberToBeProcessedLater : numbersToBeProcessedLater) {
                if (processedNumber == numberToBeProcessedLater) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int getMiddlePage(LinkedList<Integer> update) {
        int middlePage = 0;

        for (int i = 0; i <= update.size() / 2; i++) {
            middlePage = update.get(i);
        }

        return middlePage;
    }
}
