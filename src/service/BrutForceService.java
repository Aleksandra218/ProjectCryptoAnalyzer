package service;

/**
 * Сервис для криптоанализа методом brutForce
 */
public class BrutForceService {
    // Не знает о файлах!
    /**
     * Подбирает ключ шифра Цезаря методом полного перебора.
     *
     * <p>Перебирает все возможные ключи, сравнивая результат дешифрования с известным исходным текстом.
     * Дешифрование символов делегируется {@link #getDecodingChar(int, char, String)}.
     *
     * @param encryptedText зашифрованный текст
     * @param origText исходный текст для сравнения
     * @param alphabet алфавит шифрования
     *
     * @return дешифрованный текст при успехе или сообщение об ошибке
     *
     * @see #getDecodingChar(int, char, String) метод дешифрования символов
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

    /**
     * Дешифрует один символ сдвигом на key позиций влево по алфавиту.
     *
     * @param key ключ дешифрования (количество позиций для обратного сдвига)
     * @param originChar символ для дешифрования (должен быть в нижнем регистре)
     * @param alphabet алфавит для дешифрования
     *
     * @return дешифрованный символ или '\0' если символ не из алфавита
     */
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

