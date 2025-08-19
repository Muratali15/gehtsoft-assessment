package org.gehtsoft.main;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final CaesarHandler caesarHandler = new CaesarHandler(scanner);
    private final ExpressionHandler expressionHandler = new ExpressionHandler(scanner);


    public void run(){

    boolean running = true;

        System.out.println("Welcome to Gehtsoft Technical Assessment");
        while (running) {
        System.out.print("""
                    Please choose an option:
                    1. Caesar Cipher Encryption
                    2. Caesar Cipher Decryption
                    3. Arithmetic Expression Evaluation
                    4. Exit
                    """);

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> caesarHandler.handle(true);
            case "2" -> caesarHandler.handle(false);
            case "3" -> expressionHandler.handle();
            case "4" -> {
                running = false;
                System.out.println("Goodbye!");
            }
            default -> System.out.println("Invalid choice. Please enter 1-4.");
        }

        if (running) {
            System.out.print("Continue? (y/n): ");
            String cont = scanner.nextLine().trim();
            if (!cont.equalsIgnoreCase("y")) {
                running = false;
                System.out.println("Goodbye!");
            }
        }
    }
        scanner.close();
}
}
