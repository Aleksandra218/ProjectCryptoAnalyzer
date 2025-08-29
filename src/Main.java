import service.*;
import api.IDecryptor;
import view.ConsoleMenu;

import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        // 1. Создаем зависимость LOW-LEVEL (SecureRandom)
        SecureRandom secureRandom = new SecureRandom();

        // 2. Создаем инструменты
        CaesarCipherService caesarCipherService = new CaesarCipherService();

        IDecryptor decryptor = caesarCipherService;
        BrutForceService brutForceService = new BrutForceService(decryptor);

        FileService fileService = new FileService();

        // 3. Создаем оркестратора и отдаем ему все инструменты
        ApplicationService appService = new ApplicationService(secureRandom, caesarCipherService, brutForceService, fileService);

        // 4. Создаем меню и отдаем ему оркестратора
        ConsoleMenu consoleMenu = new ConsoleMenu(appService);

        // 5. Запускаем программу
        consoleMenu.start();
    }
}