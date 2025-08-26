package service;

import java.io.IOException;
import java.security.SecureRandom;

public class ApplicationService {
    // Вот он — мост между действиями. Его задача — координировать
    // работу всех инструментов для выполнения большой задачи (например, «зашифровать файл»).

    private FileService fileService;
    private CaesarCipherService caesarCipherService;
    private BrutForceService brutForceService;
    private SecureRandom secureRandom;

    private int randomKey;
    private String origText; //нужен методу брутфорс и encrypt

    private String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,:;?!() ";

    public ApplicationService(SecureRandom secureRandom, CaesarCipherService caesarCipherService, BrutForceService brutForceService, FileService fileService) {
    /*
    В его конструктор передаются ВСЕ необходимые инструменты:
FileService, CaesarCipherService, BruteForceService, InputValidator.
     */
        this.randomKey = secureRandom.nextInt(1, 43);
        this.caesarCipherService = caesarCipherService;
        this.brutForceService = brutForceService;
        this.fileService = fileService;
    }

    /*
Как он работает? Пример метода encryptFile:
Вызывает FileService.readFile(filePath), чтобы получить содержимое файла.
Вызывает CaesarCipherService.encrypt(content, key), чтобы зашифровать текст.
Вызывает FileService.writeFile(newFilePath, encryptedContent), чтобы записать результат.
 */
    public void encryptFile(String filePath) throws IOException {
        origText = fileService.readLine(filePath);
        String encryptedText = caesarCipherService.encrypt(origText, randomKey, alphabet); //шифруем
        System.out.println("Файл успешно зашифрован, введите путь к файлу для записи. Пример: C:/files/text.txt");
        String pathWriteEncryptText = InputValidator.filePath();
        fileService.writeLine(encryptedText, pathWriteEncryptText);
        System.out.println("Файл успешно записан!");
    }

    public void decryptFile(String filePath) throws IOException {
        String contentDecoding = fileService.readLine(filePath);
        String decodingText = caesarCipherService.decrypt(contentDecoding, randomKey, alphabet); //расшифровываем
        System.out.println("Файл успешно расшифрован, введите путь к файлу для записи. Пример: C:/files/text.txt");
        String pathWriteDecodText = InputValidator.filePath(); //путь к файлу для записи зашифрованного файла
        fileService.writeLine(decodingText, pathWriteDecodText);
        System.out.println("Файл успешно записан!");
    }

    public void bruteForceFile(String filePath) throws IOException {
        String contentDecoding = fileService.readLine(filePath);
        String decodingBrutForce = brutForceService.brutForce(contentDecoding, origText, alphabet); //метод брутфорс
        System.out.println("Введите путь к файлу для записи. Пример: C:/files/text.txt");
        String path = InputValidator.filePath();
        fileService.writeLine(decodingBrutForce, path);
    }
}