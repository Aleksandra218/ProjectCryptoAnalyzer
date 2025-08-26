import service.*;
import view.ConsoleMenu;

import java.io.IOException;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Создаем зависимость LOW-LEVEL (SecureRandom)
        SecureRandom secureRandom = new SecureRandom();

        CaesarCipherService caesarCipherService = new CaesarCipherService();
        BrutForceService brutForceService = new BrutForceService();
        FileService fileService = new FileService();

        // 3. Передаем сервис дальше по цепочке
        ApplicationService appService = new ApplicationService(secureRandom, caesarCipherService, brutForceService, fileService);

        ConsoleMenu consoleMenu = new ConsoleMenu(appService);
        consoleMenu.start();
    }
}