package service;

import api.IDecryptor;

public class CaesarCipherService implements IDecryptor {


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


    public String encrypt(String text, int key, String alphabet, String symbol) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char origChar = text.charAt(i);

            char encryptedChar = getEncryptedChar(key, origChar, alphabet, symbol);
            encryptedText.append(encryptedChar);

        }
        return encryptedText.toString();
    }

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