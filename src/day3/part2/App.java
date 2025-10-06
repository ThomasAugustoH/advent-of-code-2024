package day3.part2;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.InputReader;

public class App {
    private static InputReader inputReader;
    private static boolean isMulEnabled;

    public static void main(String[] args) {
        inputReader = new InputReader("src\\day3\\Input.txt");
        isMulEnabled = true;

        int total = calculateTotalMultiplications();
        System.out.println(total);
    }

    private static int calculateTotalMultiplications() {
        int sum = 0;

        while (inputReader.hasNext()) {
            String line = inputReader.readLine();
            ArrayList<String> validExpressions = parseExpressions(line);

            for (String expression : validExpressions) {
                if (expression.startsWith("mul")) {
                    if (isMulEnabled) {
                        sum += multiply(expression);
                    }
                } else {
                    enablerHandler(expression);
                }
            }
        }

        return sum;
    }

    private static ArrayList parseExpressions(String line) {
        ArrayList<String> validExpressions = new ArrayList<>();
        String validExpressionRegex = "mul[(][0-9]+,[0-9]+[)]|do(n't)?[(][)]";

        Pattern pattern = Pattern.compile(validExpressionRegex);

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            validExpressions.add(matcher.group());
        }

        return validExpressions;
    }

    private static void enablerHandler(String enabler) {
        if (enabler.equals("don't()")) {
            isMulEnabled = false;
        } else if (enabler.equals("do()")) {
            isMulEnabled = true;
        }
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
