package service;

import util.InputValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;

/**
 *  задача класса координировать работу всех инструментов для выполнения
 *  большой задачи (например, «зашифровать файл»).
 */
public class ApplicationService {

    private FileService fileService;
    private CaesarCipherService caesarCipherService;
    private BrutForceService brutForceService;
    private SecureRandom secureRandom;

    private int randomKey;
    private String origText; //нужен методу брутфорс и encrypt

    private String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,:;?!() ";

    /**
     * Конструктор сервиса-оркестратора. Принимает все необходимые зависимости.
     * @param secureRandom генератор случайных чисел для создания ключа шифрования
     * @param caesarCipherService сервис для шифрования/расшифровки методом Цезаря
     * @param brutForceService сервис для криптоанализа методом грубой силы
     * @param fileService сервис для работы с файловой системой (чтение/запись)
     */
    public ApplicationService(SecureRandom secureRandom, CaesarCipherService caesarCipherService, BrutForceService brutForceService, FileService fileService) {
        this.randomKey = secureRandom.nextInt(1, 43);
        this.caesarCipherService = caesarCipherService;
        this.brutForceService = brutForceService;
        this.fileService = fileService;
    }

    /**
     * метод координирует действия при шифровании файла
     * @param filePath путь к файлу с оригинальным текстом
     */
    public void encryptFile(String filePath) {
        try {
            origText = fileService.readLine(filePath);
            String encryptedText = caesarCipherService.encrypt(origText, randomKey, alphabet); //шифруем
            System.out.println("Файл успешно зашифрован, введите путь к файлу для записи. Пример: C:/files/text.txt");
            String pathWriteEncryptText = InputValidator.filePath();
            fileService.writeLine(encryptedText, pathWriteEncryptText);
            System.out.println("Файл успешно записан!");
        } catch (FileNotFoundException e) {
            System.out.println("ОШИБКА: Файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ОШИБКА: Не удалось прочитать/записать файл - " + e.getMessage());
        }
    }

    /**
     * координирует действия при расшифровке файла
     * @param filePath путь к файлу с зашифрованным текстом
     */
    public void decryptFile(String filePath) {
        try {
            String contentDecoding = fileService.readLine(filePath);
            String decodingText = caesarCipherService.decrypt(contentDecoding, randomKey, alphabet); //расшифровываем
            System.out.println("Файл успешно расшифрован, введите путь к файлу для записи. Пример: C:/files/text.txt");
            String pathWriteDecodText = InputValidator.filePath(); //путь к файлу для записи зашифрованного файла
            fileService.writeLine(decodingText, pathWriteDecodText);
            System.out.println("Файл успешно записан!");
        } catch (FileNotFoundException e) {
            System.out.println("ОШИБКА: Файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ОШИБКА: Не удалось прочитать/записать файл - " + e.getMessage());
        }
    }

    /**
     * метод координирует действия при расшифровке методом brutForce
     * @param filePath к файлу с зашифрованным текстом
     */
    public void bruteForceFile(String filePath) {
        try {
            String contentDecoding = fileService.readLine(filePath);
            String decodingBrutForce = brutForceService.brutForce(contentDecoding, origText, alphabet); //метод брутфорс
            System.out.println("Введите путь к файлу для записи. Пример: C:/files/text.txt");
            String path = InputValidator.filePath();
            fileService.writeLine(decodingBrutForce, path);
        }catch (FileNotFoundException e) {
            System.out.println("ОШИБКА: Файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ОШИБКА: Не удалось прочитать/записать файл - " + e.getMessage());
        }
    }
}