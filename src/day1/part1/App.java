package day1.part1;

import java.util.ArrayList;
import java.util.Iterator;

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

        leftList.sort(null);
        rightList.sort(null);
        System.out.println(calculateDistance());
    }

    private static void savePair(String line) {
        String[] numbers = line.split("   ");
        
        leftList.add(Integer.parseInt(numbers[0]));
        rightList.add(Integer.parseInt(numbers[1]));
    }

    private static int calculateDistance() {
        Iterator leftListIterator = leftList.iterator();
        Iterator rightListIterator = rightList.iterator();
        int sum = 0;

        while (leftListIterator.hasNext()) {
            int leftNumber = (int) leftListIterator.next();
            int rightNumber =  (int) rightListIterator.next();

            if (leftNumber > rightNumber) {
                sum += leftNumber - rightNumber;
            } else {
                sum += rightNumber - leftNumber;
            }
        }

        return sum;
    }
}
