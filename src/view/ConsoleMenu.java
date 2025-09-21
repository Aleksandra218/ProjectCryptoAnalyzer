package view;

import service.*;
import util.InputValidator;

/**
 * Показать пункты меню, принять выбор пользователя,
 * запросить нужные данные (путь к файлу) и вызвать один из методов
 */
public class ConsoleMenu {

    private ApplicationService applicationService;

    public ConsoleMenu(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    public void start() {
        System.out.println("Добро пожаловать в криптоанализатор!\n");
        System.out.print("\n⚠\uFE0F  Важно:\n" +
                "• Расшифровка работает только после шифрования\n" +
                "• Для файлов из других сессий используйте brute force\n");

        while (true) {
            outputMenu();
            int choice = InputValidator.choiceMenu();
            switch (choice) {
                case 1 -> {
                    System.out.print("Введите путь к файлу для чтения c оригинальным текстом: ");
                    String filePath = InputValidator.filePath();
                    applicationService.encryptFile(filePath);
                }
                case 2 -> {
                    System.out.print("Введите путь к файлу с зашифрованным текстом: ");
                    String filePath = InputValidator.filePath();
                    applicationService.decryptFile(filePath);
                }
                case 3 -> {
                    System.out.print("Введите путь к файлу с зашифрованным текстом: ");
                    String filePath = InputValidator.filePath();
                    applicationService.bruteForceFile(filePath);
                }
                default -> {
                    System.out.println("Программа завершена! Всего доброго!");
                    return;
                }
            }
        }
    }

    private static void outputMenu() {
        System.out.print("Меню\n" +
                "1. Шифрование\n" +
                "2. Расшифровка ключом\n" +
                "3. Brute force\n" +
                "0. Выход\n");
    }
}
