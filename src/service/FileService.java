package service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Сервис для работы с файлами
 * Обеспечивает чтение и запись текстовых файлов
 */
public class FileService {
    /**
     * читает текст из файла
     *
     * @param path путь к файлу
     * @return прочитанный текст из переданного пути
     * @throws IOException если файл не найден или не удалось прочитать
     */
    public String readLine(String path) throws IOException {
        Path file = Path.of(path);
        if (Files.exists(file)) {
            // Явно указываем кодировку UTF-8
            return Files.readString(file, StandardCharsets.UTF_8);
        } else {
            throw new FileNotFoundException(path);
        }
    }

    /**
     * пишет текст в файл
     *
     * @param text зашифрованный(расшифрованный) текст
     * @param path путь для записи файла
     * @throws IOException если файл не найден по указанному пути или не удалось прочитать файл
     */
    public void writeLine(String text, String path) throws IOException {

        Path writeFile = Path.of(path);
        Files.writeString(writeFile, text, StandardCharsets.UTF_8);

    }
}
