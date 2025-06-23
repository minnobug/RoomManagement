/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author LE VAN MINH
 */
public class Inputter {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public static String inputAndLoop(String msg, String pattern) {
        String input;
        do {
            input = getString(msg);
            if (!Acceptable.isValid(input, pattern)) {
                System.out.println("Invalid format. Try again.");
            }
        } while (!Acceptable.isValid(input, pattern));
        return input;
    }

    public static int getInt(String msg) {
        while (true) {
            try {
                return Integer.parseInt(getString(msg));
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }

    public static double getDouble(String msg) {
        while (true) {
            try {
                return Double.parseDouble(getString(msg));
            } catch (NumberFormatException e) {
                System.out.println("Invalid double. Try again.");
            }
        }
    }

    public static int getInt(String msg, int min, int max) {
        int n;
        do {
            n = getInt(msg);
            if (n < min || n > max) {
                System.out.printf("Must be between %d and %d.%n", min, max);
            }
        } while (n < min || n > max);
        return n;
    }

    public static LocalDate getLocalDate(String msg) {
        while (true) {
            try {
                String input = inputAndLoop(msg);
                LocalDate date = LocalDate.parse(input, DATE_FORMAT);
                if (date.isAfter(LocalDate.now())) {
                    return date;
                }
                System.out.println("Start date must be in the future.");
            } catch (Exception e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    public static LocalDate getValidBirthDate(String msg) {
        while (true) {
            try {
                String input = getString(msg);
                LocalDate date = LocalDate.parse(input, DATE_FORMAT);
                LocalDate now = LocalDate.now();
                if (date.isAfter(now)) {
                    System.out.println("Birthdate cannot be in the future.");
                } else if (date.isBefore(now.minusYears(120))) {
                    System.out.println("Unrealistic birthdate (over 120 years ago).");
                } else {
                    return date;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    public static String getOptionalString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public static String getOptionalString(String msg, Pattern pattern) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                return "";
            }
            if (pattern.matcher(input).matches()) {
                return input;
            }
            System.out.println("Invalid format.");
        }
    }

    public static String capitalizeWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String word : input.trim().toLowerCase().split("\\s+")) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    public String inputNationalId() {
        while (true) {
            String id = getString("Enter National ID to cancel reservation: ");
            if (Acceptable.isValid(id, Acceptable.NATIONAL_ID_VALID)) {
                return id;
            }
            System.out.println("National ID must be exactly 12 digits.");
        }
    }

    public static String inputAndLoop(String msg) {
        String input;
        do {
            System.out.print(msg);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

}
