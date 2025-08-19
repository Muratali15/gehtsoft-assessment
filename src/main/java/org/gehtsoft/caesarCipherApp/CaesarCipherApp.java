package org.gehtsoft.caesarCipherApp;

public class CaesarCipherApp {
    private static final String EN_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String EN_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String RU_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String RU_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    /**
     * Brings the shift value into the valid range [0, n).
     * Handles both positive and negative shift values.
     */
    private static int normalizeShift(int shift, int n){
        int res = shift % n;
        return res >= 0 ? res : res + n;
    }


    private static char shiftInAlphabet(char ch, int shift, String alphabet){
        int index = alphabet.indexOf(ch);
        int normShift = normalizeShift(shift, alphabet.length());
        int newIndex = (index + normShift) % alphabet.length();

        return alphabet.charAt(newIndex);
    }

    public static String caesarEncrypt(String plaintext, int shift){
        StringBuilder ciphertext = new StringBuilder(plaintext.length());
        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);
            if (EN_UPPER.indexOf(ch) >= 0){
                ciphertext.append(shiftInAlphabet(ch, shift, EN_UPPER));
            } else if (EN_LOWER.indexOf(ch) >= 0) {
                ciphertext.append(shiftInAlphabet(ch, shift, EN_LOWER));
            } else if (RU_UPPER.indexOf(ch) >= 0) {
                ciphertext.append(shiftInAlphabet(ch, shift, RU_UPPER));
            } else if (RU_LOWER.indexOf(ch) >= 0) {
                ciphertext.append(shiftInAlphabet(ch, shift, RU_LOWER));
            } else ciphertext.append(ch);
        }
        return ciphertext.toString();
    }

    public static String caesarDecrypt(String ciphertext, int shift){
        return caesarEncrypt(ciphertext, -shift);
    }

/**
 * Attempts to decrypt the given ciphertext by trying all possible shifts
 * for the Russian alphabet. Useful when the original shift is unknown.
 * <p>
 * Note: For English text, some results may repeat due to shorter alphabet length.
 */
    public static void caesarDecrypt(String ciphertext){
        System.out.println("All possible variables:");
        for (int shift = 1; shift < RU_UPPER.length() ; shift++) {
            System.out.printf("For shift = %2d, plaintext:%s%n", shift, caesarEncrypt(ciphertext, -shift));
        }
    }
}
