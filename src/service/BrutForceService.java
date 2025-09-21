package service;

import api.IDecryptor;

public class BrutForceService {
    private IDecryptor decryptor;

    public BrutForceService(IDecryptor decryptor) {
        this.decryptor = decryptor;
    }

    public String brutForce(String encryptedText, String origText, String alphabet, String symbol) {
        // Проверка входных данных
        if (encryptedText == null || origText == null || alphabet == null) {
            return "Ошибка: входные данные не могут быть null";
        }
        if (encryptedText.isEmpty() || origText.isEmpty() || alphabet.isEmpty()) {
            return "Ошибка: входные данные не могут быть пустыми";
        }

        // Перебираем все возможные ключи
        for (int key = 1; key <= alphabet.length(); key++) {
            StringBuilder decryptedText = new StringBuilder();

            // Расшифровываем текст текущим ключом
            for (int i = 0; i < encryptedText.length(); i++) {
                char encryptedChar = encryptedText.charAt(i);
                boolean wasUpper = Character.isUpperCase(encryptedChar);
                char lowerChar = Character.toLowerCase(encryptedChar);

                char decryptedChar = decryptor.decryptChar(key, lowerChar, alphabet, symbol);

                if (wasUpper && decryptedChar != '\0') {
                    decryptedChar = Character.toUpperCase(decryptedChar);
                }

                // Важно: если символ не из алфавита, оставляем оригинальный
                char finalChar = (decryptedChar != '\0') ? decryptedChar : encryptedChar;
                decryptedText.append(finalChar);
            }
            // ПРОВЕРЯЕМ ПОЛНОЕ СОВПАДЕНИЕ С ОРИГИНАЛОМ
            if (decryptedText.toString().equals(origText)) {
                String successMessage = String.format(
                        "\nКлюч успешно подобран! Ключ: %d\n",
                        key
                );
                System.out.println(successMessage);
                return decryptedText.toString();
            }

            // Для отладки: выводим попытки
            // System.out.printf("Попытка с ключом %2d: %s%n", key, decryptedText);
        }

        System.out.println("\nНе удалось подобрать подходящий ключ.");
        return "Не удалось подобрать ключ";
    }
}