package util;

import java.util.Scanner;

/**
 * Класс для проверки входных данных с консоли от пользователя
 */
public final class InputValidator {

    private static Scanner scanner = new Scanner(System.in);

    private InputValidator() {
    }


    /**
     * метод проверяет что строка не пустая и символы в одной строке
     *
     * @return корректный путь
     */
    public static String filePath() {
        while (true) {
            String filepath = scanner.nextLine().trim(); // Читаем ввод ОДИН раз

            if (filepath.contains("\n") || filepath.contains("\r")) {
                scanner = new Scanner(System.in); // Сброс сканера
                System.out.println("Ошибка: путь должен быть в одной строке!");
                continue;
            }
            if (filepath.isEmpty()) {
                System.out.println("Ошибка: путь не может быть пустым!");
                continue;
            }

            if (!isFilePath(filepath)) {
                System.out.println("Ошибка: некорректный формат пути. Пример: C:/files/text.txt ");
                continue;
            }

            return filepath; // Возвращаем только валидный путь
        }
    }

    /**
     * метод проверяет что путь от пользователя соответствует правилам, не
     * принимает путь скопированный из компьютера в кавычках с обеих сторон
     * @param str принимает строку
     * @return корректный путь к файлу
     */
    public static boolean isFilePath(String str) {
        // 1. Не пустая и не слишком короткая
        if (str == null || str.length() < 3) return false;

        // 2. Содержит допустимые символы для пути (для Windows/Linux)
        if (!str.matches("^[a-zA-Z0-9_\\-./:\\\\\\\\]+$")) return false;

        // 3. Имеет структуру пути (есть хотя бы один разделитель)
        if (!str.contains("/") && !str.contains("\\") && !str.contains(":")) return false;

        return true;
    }

    /**
     * проверяет диапазон выбора меню от пользователя и что строка соответствует цифре меню
     * @return корректное число
     */
    public static int choiceMenu() {
        while (true) {
            System.out.print("Введите действие: ");
            if (!scanner.hasNextInt()) {
                System.out.print("Ошибка: введите, пожалуйста, целое число от 0 до 4: ");
                scanner.nextLine();
                continue;
            }
            int number = scanner.nextInt();
            scanner.nextLine();
            if (number >= 0 && number <= 4) {
                return number;
            } else {
                System.out.print("Ошибка: число вне диапазона. Введите, пожалуйста, целое число от 0 до 4: ");
                scanner.nextLine();
                continue;
            }
        }
    }
}
