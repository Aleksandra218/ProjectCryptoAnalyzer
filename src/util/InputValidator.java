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
     * Запрашивает и валидирует путь к файлу с консоли с повторением при ошибках.
     *
     * <p>Удаляет обрамляющие кавычки, проверяет отсутствие переносов строк,
     * непустоту и корректность формата пути.
     *
     * @return валидный путь к файлу после успешной проверки
     *
     * @implSpec Циклически запрашивает ввод до получения корректного пути,
     *           предоставляя конкретные сообщения об ошибках.
     *
     * @see #isFilePath(String) метод проверки формата пути
     */
    public static String filePath() {
        while (true) {
            String filepath = scanner.nextLine().trim(); // Читаем ввод ОДИН раз
            filepath = filepath.replaceAll("^['\"]|['\"]$", ""); //удаляем кавычки если путь копируем с компьютера

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
     * Проверяет базовую валидность формата пути к файлу.
     *
     * <p>Выполняет проверку длины, допустимых символов и наличия разделителей пути.
     * Не проверяет существование файла в файловой системе.
     *
     * @param inputPath строка для проверки
     * @return true если строка соответствует формату пути к файлу
     *
     * @implNote Поддерживает форматы Windows и Linux/Unix
     * @see #filePath() метод ввода пути с валидацией
     */
    public static boolean isFilePath(String inputPath) {
        // 1. Не пустая и не слишком короткая
        if (inputPath == null || inputPath.length() < 3) return false;

        // 2. Содержит допустимые символы для пути (для Windows/Linux)
        if (!inputPath.matches("^[a-zA-Z0-9_\\-./:\\\\\\\\]+$")) return false;

        // 3. Имеет структуру пути (есть хотя бы один разделитель)
        if (!inputPath.contains("/") && !inputPath.contains("\\") && !inputPath.contains(":")) return false;

        return true;
    }

    /**
     * Проверяет диапазон выбора меню от пользователя и что строка соответствует цифре меню
     *
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
