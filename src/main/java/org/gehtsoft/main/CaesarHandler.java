package org.gehtsoft.main;

import org.gehtsoft.caesarCipherApp.CaesarCipherApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CaesarHandler {
    private final Scanner scanner;
    public CaesarHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void handle(boolean encrypt) {
        System.out.print("Enter text to " + (encrypt ? "encrypt" : "decrypt") + ":");
        String text = scanner.nextLine();

        if (text.startsWith("file:")){
            String filePath = text.substring(5).trim();
            try {
                text = readFile(filePath);
            } catch (FileNotFoundException e){
                System.out.println("file not found, path: " + filePath);
                return;
            }
        }

        if (encrypt){
            System.out.print("Enter shift value (integer): ");
            int shift;
            try {
                shift = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid shift value, using 0");
                shift = 0;
            }
            System.out.println("Result: " + CaesarCipherApp.caesarEncrypt(text, shift));
        } else {
            System.out.print("Enter shift value (press Enter to try all): ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                CaesarCipherApp.caesarDecrypt(text);
            } else {
                int shift;
                try {
                    shift = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid shift value, using 0");
                    shift = 0;
                };
                System.out.println("Result: " + CaesarCipherApp.caesarDecrypt(text, shift));
            }
        }
    }

    private static String readFile(String filePath) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filePath));
        StringBuilder sb = new StringBuilder();
        while (fileScanner.hasNextLine()){
            sb.append(fileScanner.nextLine());
            sb.append("\n");
        }
        fileScanner.close();
        return sb.toString().trim();
    }
}
