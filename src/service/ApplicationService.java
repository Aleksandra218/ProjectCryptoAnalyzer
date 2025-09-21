package service;

import util.InputValidator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;

public class ApplicationService {

    private FileService fileService;
    private CaesarCipherService caesarCipherService;
    private BrutForceService brutForceService;
    private SecureRandom secureRandom;

    private int randomKey;
    private String origText; //нужен методу брутфорс и encrypt

    private String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private String symbol = ".,:;?!()- ";

    public ApplicationService(SecureRandom secureRandom, CaesarCipherService caesarCipherService, BrutForceService brutForceService, FileService fileService) {
        this.randomKey = secureRandom.nextInt(1, 43);
        this.caesarCipherService = caesarCipherService;
        this.brutForceService = brutForceService;
        this.fileService = fileService;
    }

    public void encryptFile(String filePath) {
        try {
            origText = fileService.readLine(filePath);
            String encryptedText = caesarCipherService.encrypt(origText, randomKey, alphabet, symbol); //шифруем
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


    public void decryptFile(String filePath) {
        try {
            String contentDecoding = fileService.readLine(filePath);
            String decodingText = caesarCipherService.decrypt(contentDecoding, randomKey, alphabet, symbol); //расшифровываем
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


    public void bruteForceFile(String filePath) {
        try {
            String contentDecoding = fileService.readLine(filePath);
            String decodingBrutForce = brutForceService.brutForce(contentDecoding, origText, alphabet, symbol); //метод брутфорс
            System.out.println("Введите путь к файлу для записи. Пример: C:/files/text.txt");
            String path = InputValidator.filePath();
            fileService.writeLine(decodingBrutForce, path);
        }catch (FileNotFoundException e) {
            System.out.println("ОШИБКА: Файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ОШИБКА: Не удалось прочитать/записать файл - " + e.getMessage());
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("null")) {
                System.out.println("ОШИБКА: Проверьте, все ли файлы загружены");
            } else if (e.getMessage().contains("пустыми")) {
                System.out.println("ОШИБКА: Файл не должен быть пустым");
            }
        }
    }
}