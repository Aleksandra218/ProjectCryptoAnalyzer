package service;

import api.IDecryptor;

/**
 * Сервис для шифрования/расшифровки методом Цезаря
 */
public class CaesarCipherService implements IDecryptor {


    /**
     * Дешифрует текст, обрабатывая каждый символ через {@link #decryptChar(int, char, String, String)}.
     *
     * <decrypt>Метод управляет процессом дешифрования: обрабатывает не кодируемые символы
     * и накапливает результаты. Непосредственное дешифрование делегируется вспомогательному методу.
     *
     * @param text зашифрованный текст для дешифрования (передается по одному символу getEncryptedChar)
     * @param key ключ дешифрования (должен совпадать с ключом шифрования, передается без изменений в getDecodingChar)
     * @param alphabet алфавит для дешифрования (передается без изменений в getDecodingChar)
     * @param symbol символы пунктуации для дешифрования (передается без изменений в getDecodingChar)
     *
     * @return дешифрованный текст с сохранением регистра и не кодируемых символов
     *
     * @see #decryptChar(int, char, String, String) метод, выполняющий фактическое дешифрование символов
     * @see #encrypt(String, int, String, String) соответствующий метод шифрования
     */
    public String decrypt(String text, int key, String alphabet, String symbol) {
        StringBuilder decryptedText = new StringBuilder();


        for (int i = 0; i < text.length(); i++) {
            char encryptedOrigChar = text.charAt(i);

            //расшифровываем символ
            char decryptedChar = decryptChar(key, encryptedOrigChar, alphabet, symbol);

            decryptedText.append(decryptedChar);
        }
        return decryptedText.toString();
    }


    /**
     * Выполняет обратное преобразование Цезаря для одного символа.
     * Сдвигает символ влево на указанное количество позиций в алфавите.
     *
     * @param key ключ шифрования (количество позиций для сдвига)
     * @param encryptedChar символ, который нужно расшифровать
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
    @Override
    public char decryptChar(int key, char encryptedChar, String alphabet, String symbol) {
        if (Character.isLetter(encryptedChar)) {
            boolean isUpperCase = Character.isUpperCase(encryptedChar);
            char searchChar = Character.toLowerCase(encryptedChar);

            int originPos = alphabet.indexOf(searchChar);
            if (originPos == -1) return encryptedChar;

            int newPos = (originPos - key) % alphabet.length();
            if (newPos < 0) newPos += alphabet.length();

            char result = alphabet.charAt(newPos);
            return isUpperCase ? Character.toUpperCase(result) : result;

        } else {
            int originPos = symbol.indexOf(encryptedChar);
            if (originPos == -1) return encryptedChar;

            int newPos = (originPos - key) % symbol.length();
            if (newPos < 0) newPos += symbol.length();

            return symbol.charAt(newPos);
        }
    }

    /**
     * Шифрует текст, обрабатывая каждый символ через {@link #getEncryptedChar(int, char, String, String)}.
     *
     * <encrypt>Метод управляет процессом шифрования: сохраняет регистр, обрабатывает некодируемые символы
     * и накапливает результаты. Непосредственное шифрование делегируется вспомогательному методу.
     *
     * @param text      текст для шифрования (передается по одному символу getEncryptedChar)
     * @param key       ключ шифрования (передается без изменений в getEncryptedChar)
     * @param alphabet  алфавит для шифрования (передается без изменений в getEncryptedChar)
     * @param symbol    символы пунктуации (передается без изменений в getEncryptedChar
     *
     * @return зашифрованный текст с сохранением регистра и некодируемых символов
     *
     * @see #getEncryptedChar(int, char, String, String) метод, выполняющий фактическое шифрование символов
     */
    public String encrypt(String text, int key, String alphabet, String symbol) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char origChar = text.charAt(i);

            char encryptedChar = getEncryptedChar(key, origChar, alphabet, symbol);
            encryptedText.append(encryptedChar);

        }
        return encryptedText.toString();
    }



    /**
     * Шифрует символ через шифр Цезаря, обрабатывая буквы и символы раздельно.
     * Сдвигает символ вправо на указанное количество позиций в алфавите
     *
     * @param key ключ шифрования
     * @param originChar символ для шифрования
     * @param alphabet алфавит для букв
     * @param symbol словарь для символов
     *
     * @return шифрованный символ или оригинал если не найден в словарях
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
     * - alphabet = "abc", key = 1, originСhar = 'a' → 0 + 1 = 1 → return 'b'
     * - alphabet = "abc", key = -1, originСhar = 'a' → 0 - 1 = -1 → -1 + 3 = 2 → return 'c'
     *
     * @implSpec Сохраняет регистр букв, использует модульную арифметику
     */
    private static char getEncryptedChar(int key, char originChar, String alphabet, String symbol) {
        if (Character.isLetter(originChar)) {
            boolean isUpperCase = Character.isUpperCase(originChar);
            char searchChar = Character.toLowerCase(originChar);

            int originPos = alphabet.indexOf(searchChar);
            if (originPos == -1) return originChar;

            int newPos = (originPos + key) % alphabet.length();
            if (newPos < 0) newPos += alphabet.length();

            char result = alphabet.charAt(newPos);
            return isUpperCase ? Character.toUpperCase(result) : result;

        } else {
            int originPos = symbol.indexOf(originChar);
            if (originPos == -1) return originChar;

            int newPos = (originPos + key) % symbol.length();
            if (newPos < 0) newPos += symbol.length();

            return symbol.charAt(newPos);
        }
    }
}