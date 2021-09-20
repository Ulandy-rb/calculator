import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    /*На вход поступает строка из целочисленных или вещественных чисел и операторов вычисления выражения
    * Отрицательные числа не поддерживаются
    * Скобки не поддерживаются*/
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String scanString = sc.nextLine();
        System.out.println(scanString);
        try {
            if(isValidString(scanString))
            System.out.println("Результат вычисления: " + Calculate(scanString));
        }
        catch (Exception e) {
            System.out.println("Введено некорректное значение!");
        }
    }
    /*Функция вычисления выражения
    * Строка преобразуется в 2 массива: операторов и операндов
    * При вычислении проверятся порядок действий (* / вычисляются первыми) */
    public static Double Calculate(String expression) {
        expression = expression.replaceAll("\\s+","");
        List<String> operators = new ArrayList<>(Arrays.asList(expression.split("([0-9]*[.])?[0-9]+")));
        List<String> operands = new ArrayList<>(Arrays.asList(expression.split("[+\\-*/]")));

        int indexOfOperator = 1;
        while (operators.contains("*")||operators.contains("/"))
        {
            if(operators.get(indexOfOperator).equals("*")) {
                double intermediateResult = Double.parseDouble(operands.get(indexOfOperator-1)) * Double.parseDouble(operands.get(indexOfOperator));
                operands.set(indexOfOperator, Double.toString(intermediateResult));
                operands.remove(indexOfOperator - 1);
                operators.remove(indexOfOperator);
                indexOfOperator--;
            }
            if(operators.get(indexOfOperator).equals("/")) {
                double intermediateResult = Double.parseDouble(operands.get(indexOfOperator-1)) / Double.parseDouble(operands.get(indexOfOperator));
                operands.set(indexOfOperator, Double.toString(intermediateResult));
                operands.remove(indexOfOperator - 1);
                operators.remove(indexOfOperator);
                indexOfOperator --;
            }
            indexOfOperator++;
        }
        double result = Double.parseDouble(operands.get(0));
        for (int i = 1; i < operands.size(); i++) {
            if (operators.get(i).equals("+"))
                result += Double.parseDouble(operands.get(i));
            if (operators.get(i).equals("-"))
                result -= Double.parseDouble(operands.get(i));

        }
        return result;
    }
    /*Функция валидации входной строки*/
    public static boolean isValidString(String expression) throws Exception {
        String divByZeroRegex = "/\\s*0"; // Проверяет деление на 0
        String validateRegex = "^-?\\d+\\s*[+-/*]\\s*(\\(-(\\d+|\\d+\\.\\d+)\\)|(\\d+|\\d+\\.\\d+))\\s*([+\\-*\\/]\\s*(\\(-(\\d+|\\d+\\.\\d+)\\)|(\\d+|\\d+\\.\\d+))+\\s*)*$";
        Pattern pattern = Pattern.compile(divByZeroRegex);
        Matcher matcher = pattern.matcher(expression);

        if (!Pattern.matches(validateRegex, expression)) {
            throw new Exception("Введено некорректное значение");
        } else if (matcher.find()) {
            throw new Exception("Попытка делить на 0");
        }
        return true;
    }
}
