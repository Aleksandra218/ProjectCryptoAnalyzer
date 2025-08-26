package service;

public class BrutForceService {
    // Не знает о файлах!
    /**
     * Принимает зашифрованный текст, перебирает ключи
     * @param encryptedText
     * @param origText
     * @param alphabet
     * @return подобранный ключ и расшифрованный текст
     */
    public String brutForce(String encryptedText, String origText, String alphabet) {
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
            boolean isMatch = true;

            // Расшифровываем текст текущим ключом
            for (int i = 0; i < encryptedText.length(); i++) {
                char encryptedChar = encryptedText.charAt(i);
                boolean wasUpper = Character.isUpperCase(encryptedChar);
                char lowerChar = Character.toLowerCase(encryptedChar);

                char decryptedChar = getDecodingChar(key, lowerChar, alphabet);

                if (wasUpper && decryptedChar != '\0') {
                    decryptedChar = Character.toUpperCase(decryptedChar);
                }

                char finalChar = decryptedChar != '\0' ? decryptedChar : encryptedChar;
                decryptedText.append(finalChar);

                // Проверяем совпадение по мере расшифровки
                if (i < origText.length() &&
                        Character.toLowerCase(finalChar) != Character.toLowerCase(origText.charAt(i))) {
                    isMatch = false;
                    break;
                }
            }

            // Проверяем полное совпадение после расшифровки
            if (isMatch && decryptedText.length() == origText.length()) {
                String successMessage = String.format(
                        "\nКлюч успешно подобран!\n" +
                                "Ключ: %d\n", +
                                key
                );
                System.out.println(successMessage);
                return decryptedText.toString();
            }

            // Вывод текущей попытки (можно закомментировать)
            //       System.out.printf("Попытка с ключом %2d: %s%n", key, decryptedText);
        }

        System.out.println("\nНе удалось подобрать подходящий ключ.");
        return "Не удалось подобрать ключ";
    }

    private static char getDecodingChar(int key, char originChar, String alphabet) {
        int charEncryptPos = alphabet.indexOf(originChar);

        if (charEncryptPos == -1) {
            return '\0';
        }

        int origCharDecodingPos = (charEncryptPos - key) % alphabet.length();
        if (origCharDecodingPos < 0) {
            origCharDecodingPos += alphabet.length();
        }
        return alphabet.charAt(origCharDecodingPos);
    }
}

