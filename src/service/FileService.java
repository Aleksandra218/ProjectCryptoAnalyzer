package service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Сервис для работы с файлами,
 * обеспечивает чтение и запись текстовых файлов
 */
public class FileService {

    /**
     * Читает всё содержимое файла как строку в кодировке UTF-8.
     *
     * @param path путь к файлу для чтения
     * @return содержимое файла как строку
     * @throws FileNotFoundException если файл не существует
     * @throws IOException при ошибках чтения файла
     *
     * @implSpec Явно использует кодировку UTF-8 и проверяет существование файла
     *
     * @see Files #readString(Path, Charset) базовый метод чтения
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
     * Записывает текст в файл в кодировке UTF-8.
     *
     * @param text текст для записи
     * @param path путь к файлу (будет создан или перезаписан)
     * @throws IOException при ошибках записи или если путь недоступен
     *
     * @implSpec Использует UTF-8 кодировку и создает директории при необходимости
     * @see Files #writeString(Path, CharSequence, Charset)
     */
    public void writeLine(String text, String path) throws IOException {

        Path writeFile = Path.of(path);
        Files.writeString(writeFile, text, StandardCharsets.UTF_8);

    }
}
