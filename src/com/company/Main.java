package com.company;

import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String str = readFromConsole();
        System.out.println(calc(str));
	// write your code here
    }

    private static final List<Character> ALLOWED_OPERATIONS = List.of('*', '/', '+', '-');
    private static final List<String> ALLOWED_ARABIC = List.of("1","2","3","4","5","6","7","8","9","10");
    private static final List<String> ALLOWED_ROMAN = List.of("I","II","III","IV","V","VI","VII","VIII","IX","X");

    public static String calc(String input) {
        char operation = getOperation(input);
        String regexp = operation == '+' || operation == '*' ? String.format("\\%s", operation) : String.valueOf(operation);
        String[] strDigits = input.split(regexp);

        if (strDigits.length != 2) {
            throw new IllegalArgumentException("неверное количество операций");
        }

        if (isArabic(strDigits)) {
            return String.valueOf(calculateArabic(strDigits, operation));
        } else if (isRoman(strDigits)) {
            return String.valueOf(calculateRoman(strDigits, operation));
        } else {
            throw new IllegalArgumentException("неподдерживаемые числа или неверный формат");
        }

    }

    private static int calculateArabic(String[] strDigits, char oper) {
        int first = parseArabic(strDigits[0]);
        int second = parseArabic(strDigits[1]);

        return switch (oper) {
            case '+' -> first + second;
            case '-' -> first - second;
            case '*' -> first * second;
            case '/' -> first / second;
            default -> throw new IllegalStateException("Неподдерживаемая операция");
        };

    }

    private static int parseArabic(String strDigit) {
        return switch (strDigit.trim()) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            case "7" -> 7;
            case "8" -> 8;
            case "9" -> 9;
            case "10" -> 10;
            default -> throw new IllegalArgumentException("неподдерживаемое число");
        };

    }
    private static String calculateRoman(String[] strDigits, char oper) {
        int first = parseRoman(strDigits[0]);
        int second = parseRoman(strDigits[1]);

        int result =  switch (oper) {
            case '+' -> first + second;
            case '-' -> first - second;
            case '*' -> first * second;
            case '/' -> first / second;
            default -> throw new IllegalStateException("Неподдерживаемая операция");
        };

        return toRoman(result);

    }

    private static int parseRoman(String strDigit) {
        return switch (strDigit.trim()) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> throw new IllegalArgumentException("неподдерживаемое число");
        };

    }

    private static boolean isArabic(String[] digits) {
        boolean fistOperand = ALLOWED_ARABIC.contains(digits[0].trim());
        boolean secondOperand = ALLOWED_ARABIC.contains(digits[0].trim());
        return fistOperand && secondOperand;
    }

    private static boolean isRoman(String[] digits) {
        boolean fistOperand = ALLOWED_ROMAN.contains(digits[0].trim());
        boolean secondOperand = ALLOWED_ROMAN.contains(digits[0].trim());
        return fistOperand && secondOperand;
    }

    private static String readFromConsole() {
        try (Scanner console = new Scanner(System.in)) {
            return console.nextLine();
        }
    }

    private static char getOperation(String rawString) {
        var operations = rawString.chars()
                .mapToObj(c -> (char) c)
                .filter(ALLOWED_OPERATIONS::contains)
                .collect(Collectors.toList());

        if (operations.size() != 1) {
            throw new IllegalArgumentException("неверное количество операций");
        } else {
            return operations.get(0);
        }
    }

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    private static String toRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("нет отрицательного римского числа");
        }
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }

}
