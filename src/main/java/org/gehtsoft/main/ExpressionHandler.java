package org.gehtsoft.main;

import org.gehtsoft.expressionEvaluatorApp.ExpressionEvaluatorApp;
import org.gehtsoft.expressionEvaluatorApp.ParseException;

import java.math.BigDecimal;
import java.util.Scanner;

public class ExpressionHandler {
    private final Scanner scanner;

    public ExpressionHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void handle() {
        System.out.print("Enter arithmetic expression: ");
        String expr = scanner.nextLine();
        try{
            BigDecimal result = ExpressionEvaluatorApp.evaluate(expr);
            System.out.println("Result = " + result);
        } catch (ParseException err) {
            System.out.println("Error: " + err.getMessage());
        }

    }
}
