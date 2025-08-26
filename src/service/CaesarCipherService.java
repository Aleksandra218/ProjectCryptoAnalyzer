package service;

public class CaesarCipherService {

    // Принимает текст и ключ, возвращает зашифрованный/расшифрованный текст. Не знает о файлах!
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

    //декодируем символ
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

    //шифруем текст
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


    //шифруем символ
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
