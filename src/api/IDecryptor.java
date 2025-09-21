package api;

import service.BrutForceService;
import service.CaesarCipherService;


public interface IDecryptor {


    char decryptChar(int key, char encryptedChar, String alphabet, String symbol);
}