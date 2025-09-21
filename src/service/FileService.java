package service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileService {

    public String readLine(String path) throws IOException {
        Path file = Path.of(path);
        if (Files.exists(file)) {
            // Явно указываем кодировку UTF-8
            return Files.readString(file, StandardCharsets.UTF_8);
        } else {
            throw new FileNotFoundException(path);
        }
    }

    public void writeLine(String text, String path) throws IOException {

        Path writeFile = Path.of(path);
        Files.writeString(writeFile, text, StandardCharsets.UTF_8);

    }
}
