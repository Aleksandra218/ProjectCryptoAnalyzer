package view;

import service.*;

import java.io.IOException;

public class ConsoleMenu {

    private ApplicationService applicationService;

    public ConsoleMenu(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    public void start() throws IOException {
        System.out.println("Добро пожаловать в криптоанализатор!\n");

        while (true) {
            outputMenu();
            int choice = InputValidator.choiceMenu();
            if (choice == 1) {
                System.out.print("Введите путь к файлу для чтения c оригинальным текстом: "); //путь без кавычек с обеих сторон иначе валидатор не примет
                String filePath = InputValidator.filePath();
                applicationService.encryptFile(filePath);
            } else if (choice == 2) {
                System.out.print("Введите путь к файлу с зашифрованным текстом: "); //путь без кавычек с обеих сторон иначе валидатор не примет
                String filePath = InputValidator.filePath();
                applicationService.decryptFile(filePath);
            } else if (choice == 3) {
                System.out.print("Введите путь к файлу с зашифрованным текстом: "); //путь без кавычек с обеих сторон иначе валидатор не примет
                String filePath = InputValidator.filePath();
                applicationService.bruteForceFile(filePath);
            } else {
                System.out.println("Программа завершена! Всего доброго!");
                return;
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
