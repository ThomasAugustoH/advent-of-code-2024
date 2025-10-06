package day3.part1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.InputReader;

public class App {
    private static InputReader inputReader;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day3\\Input.txt");

        int total = calculateTotalMultiplications();
        System.out.println(total);
    }

    private static int calculateTotalMultiplications() {
        int sum = 0;

        while (inputReader.hasNext()) {
            String line = inputReader.readLine();
            ArrayList<String> validMulExpressions = parseMul(line);

            for (String mulExpression : validMulExpressions) {
                sum += multiply(mulExpression);
            }
        }

        return sum;
    }

    private static ArrayList parseMul(String line) {
        ArrayList<String> validMulExpressions = new ArrayList<>();
        String validMulRegex = "mul[(][0-9]+,[0-9]+[)]";

        Pattern pattern = Pattern.compile(validMulRegex);

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            validMulExpressions.add(matcher.group());
        }

        return validMulExpressions;
    }

    private static int multiply(String mulExpression) {
        int openParanthesesIndex = mulExpression.indexOf("(");
        int commaIndex = mulExpression.indexOf(",");
        int closeParanthesesIndex = mulExpression.indexOf(")");

        String firstNumber = mulExpression.substring(openParanthesesIndex + 1, commaIndex);
        String secondNumber = mulExpression.substring(commaIndex + 1, closeParanthesesIndex);

        int firstNumberInt = Integer.parseInt(firstNumber);
        int secondNumberInt = Integer.parseInt(secondNumber);

        return firstNumberInt * secondNumberInt;
    }
}
