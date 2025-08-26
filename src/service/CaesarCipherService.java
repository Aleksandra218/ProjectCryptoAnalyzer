package service;

public class CaesarCipherService {
    /**
     * Сервис для шифрования/расшифровки методом Цезаря
     * @param text исходный текст
     * @param key ключ
     * @param alphabet алфавит
     * @return зашифрованный текст
     */

    /**
     * Расшифровывает переданный текст с помощью шифра Цезаря.
     * Алгоритм сдвигает каждый символ текста на указанное количество позиций (key) влево по алфавиту.
     * Регистр символов (заглавный/строчный) сохраняется. Символы, отсутствующие в заданном алфавите,
     * остаются неизменными.
     *
     * @param text      Исходный текст для расшифровки.
     * @param key       Ключ шифрования - количество позиций для сдвига символов.
     *                  Для дешифровки используется отрицательный ключ равный (размер_алфавита - key).
     * @param alphabet  Строка, определяющая алфавит для шифрования. Порядок символов в строке важен.
     *                  Сдвиг происходит в порядке символов в этой строке.
     *
     * @return Возвращает строку, содержащую расшифрованный текст.
     */
    public String decrypt(String text, int key, String alphabet) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char encryptedChar = text.charAt(i);

            // Сохраняем информацию о регистре исходного символа ДО шифрования
            boolean wasUpper = Character.isUpperCase(encryptedChar);
            char lowerChar = Character.toLowerCase(encryptedChar);

            //расшифровываем символ
            char decryptedChar = getDecodingChar(key, lowerChar, alphabet);

            // Восстанавливаем регистр, если исходный символ был заглавным
            if (wasUpper && decryptedChar != '\0') {
                decryptedChar = Character.toUpperCase(decryptedChar);
            }

            decryptedText.append(decryptedChar != '\0' ? decryptedChar : encryptedChar);
        }
        return decryptedText.toString();
    }

    /**
     * Выполняет обратное преобразование Цезаря для одного символа.
     * Сдвигает символ влево на указанное количество позиций в алфавите.
     *
     * @param key ключ шифрования (количество позиций для сдвига)
     * @param originChar символ, который нужно расшифровать
     * @param alphabet алфавит, используемый для шифрования
     * @return расшифрованный символ или '\0' если символ не принадлежит алфавиту
     *
     * @implSpec Алгоритм:
     * 1. Находит позицию символа в алфавите
     * 2. Вычитает ключ из позиции (сдвиг влево)
     * 3. Обрабатывает отрицательный результат через modulo arithmetic
     * 4. Возвращает символ из новой позиции
     *
     * Пример: alphabet = "abc", key = 1, originChar = 'b'
     * → position = 1 → (1 - 1) % 3 = 0 → return 'a'
     */
    private static char getDecodingChar(int key, char originChar, String alphabet) {
        int charEncryptPos = alphabet.indexOf(originChar);

        if (charEncryptPos == -1) {
            return '\0'; // Не буква алфавита
        }

        int origCharDecodingPos = (charEncryptPos - key) % alphabet.length();
        if (origCharDecodingPos < 0) {
            origCharDecodingPos += alphabet.length();
        }
        return alphabet.charAt(origCharDecodingPos);
    }

    /**
     * Шифрует переданный текст с помощью шифра Цезаря.
     * Алгоритм сдвигает каждый символ текста на указанное количество позиций (key) вправо по алфавиту.
     * Регистр символов (заглавный/строчный) сохраняется. Символы, отсутствующие в заданном алфавите,
     * остаются неизменными.
     *
     * @param text      Исходный текст для шифрования.
     * @param key       Ключ шифрования - количество позиций для сдвига символов.
     *                 Должен быть неотрицательным. Для дешифровки используется отрицательный ключ
     *                 или ключ, равный (размер_алфавита - key).
     * @param alphabet  Строка, определяющая алфавит для шифрования. Порядок символов в строке важен.
     *                 Сдвиг происходит в порядке символов в этой строке. Не должен быть null или пустой.
     *                 Рекомендуется использовать строки без повторяющихся символов.
     *
     * @return Возвращает строку, содержащую зашифрованный текст.
     */
    public String encrypt(String text, int key, String alphabet) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char origChar = text.charAt(i);
            boolean isUpper = Character.isUpperCase(origChar);
            char lowerChar = Character.toLowerCase(origChar);

            // Шифруем символ (работаем только со строчными)
            char encryptedChar = getEncryptedChar(key, lowerChar, alphabet);

            // Если символ был заглавным - возвращаем регистр
            if (isUpper && encryptedChar != '\0') {
                encryptedChar = Character.toUpperCase(encryptedChar);
            }

            // Добавляем результат (если не '\0')
            if (encryptedChar != '\0') {
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(origChar); // Оставляем оригинальный символ
            }
        }
        return encryptedText.toString();
    }


    /**
     * Выполняет прямое преобразование Цезаря для одного символа.
     * Сдвигает символ вправо на указанное количество позиций в алфавите.
     *
     * @param key ключ шифрования (количество позиций для сдвига, может быть отрицательным)
     * @param originChar исходный символ для шифрования
     * @param alphabet алфавит, используемый для шифрования
     * @return зашифрованный символ или '\0' если символ не принадлежит алфавиту
     *
     * @implSpec Алгоритм:
     * 1. Находит позицию символа в алфавите
     * 2. Прибавляет ключ к позиции (сдвиг вправо для положительного key)
     * 3. Обрабатывает выход за границы алфавита через modulo arithmetic
     * 4. Возвращает символ из новой позиции
     *
     * @implNote Особенности:
     * - Возвращает '\0' для символов, отсутствующих в алфавите (пробелы, цифры, пунктуация)
     * - Корректно обрабатывает отрицательные ключи (обратный сдвиг)
     * - Обеспечивает "заворачивание" алфавита (z + 1 → a)
     *
     * Примеры:
     * - alphabet = "abc", key = 1, originChar = 'a' → 0 + 1 = 1 → return 'b'
     * - alphabet = "abc", key = -1, originChar = 'a' → 0 - 1 = -1 → -1 + 3 = 2 → return 'c'
     * - alphabet = "abc", originChar = '!' → return '\0' (не шифруется)
     */
    private static char getEncryptedChar(int key, char originChar, String alphabet) {
        int originPos = alphabet.indexOf(originChar);

        if (originPos == -1) {
            return '\0'; // Специальное значение для "не шифровать"
        }

        int newPos = (originPos + key) % alphabet.length();
        // Обработка отрицательных позиций (если key может быть отрицательным)
        if (newPos < 0) {
            newPos += alphabet.length();
        }
        return alphabet.charAt(newPos);
    }
}
