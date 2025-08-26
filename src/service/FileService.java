package service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

public class FileService {
    // Только чтение и запись текста в файл. Никакого шифрования!

    public String readLine(String path) throws IOException {
        Path file = Path.of(path);
        if (Files.exists(file)) {
            return Files.readString(file);
        } else {
            throw new FileNotFoundException("Файл не найден: " + path);
        }
    }
    public void writeLine(String text, String path) throws IOException {
        Path writeFile = Path.of(path);
        Files.writeString(writeFile, text);
    }
}
