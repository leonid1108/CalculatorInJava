import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите операцию:");
        String input = scanner.nextLine();

        String result = null;
        result = calc(input);

        System.out.println('\n' + result);
    }

    static String calc(String input) throws Exception {

        //Выделение из строки цифр
        String[] numbers = input.split("[\\+\\-\\*\\/]");

        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        int[] arabicNumbers = new int[2];

        char operator = ' ';
        int counter = 0;
        for (char oper: input.toCharArray()) {
            if (oper == '+' || oper == '-' || oper == '*' || oper == '/') {
                operator = oper;
                counter += 1;
            }
            if (counter > 1 ) {
                //return "throws Exception";
                throw new Exception();
            }
        }

        int result = 0;
        int num1, num2;

        try {
            // Проверка на то, чтобы вводимые операнды были в одной С.С.
            boolean a = (Arrays.asList(romanNumbers).contains(numbers[1]));
            boolean b = (Arrays.asList(romanNumbers).contains(numbers[0]));

            if ((a && !b) || (!a && b)) {
                throw new Exception();
            }

            num1 = Integer.parseInt(numbers[0]);
            num2 = Integer.parseInt(numbers[1]);
            if (num1 > 10 || num2 > 10) {
                throw new Exception();
            }

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    //Обработка исключения деления на 0
                    if (!(num2 == 0)) {
                        result = num1 / num2;
                        break;
                    }
                    throw new Exception();
                default:
                    throw new Exception();

            }
            return String.valueOf(result);

        }   catch (NumberFormatException e) {

            //Приведение римских цифр в арабские для рассчета
            for (int i = 0; i < numbers.length; i++) {
                arabicNumbers[i] = romanToArabic(numbers[i], romanNumbers);
            }
            num1 = arabicNumbers[0];
            num2 = arabicNumbers[1];
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    if (result <= 0) {
                        throw new Exception();
                    }
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    if (result <= 0) {
                        throw new Exception();
                    }
                    break;
                default:
                    throw new Exception();
            }
            return arabicToRoman(result);
        }
    }

    //Метод, который переводит римские цифры в арабские
    static int romanToArabic(String roman, String[] romanNumbers) {
        for (int i = 0; i < romanNumbers.length; i++) {
            if (romanNumbers[i].equals(roman)) {
                return i + 1;
            }
        }
        return 0;
    }

    //Метод, который переводит арабские цифры в римские
    static String arabicToRoman(int number) {
        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] arabicValues = {1, 4, 5, 9, 10, 40, 50, 90, 100};

        StringBuilder roman = new StringBuilder();
        int i = arabicValues.length - 1;
        while (number > 0) {
            if (number >= arabicValues[i]) {
                roman.append(romanSymbols[i]);
                number -= arabicValues[i];
            } else {
                i--;
            }
        }
        return roman.toString();
    }
}

