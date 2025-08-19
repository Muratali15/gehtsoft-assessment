package org.gehtsoft.expressionEvaluatorApp;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.lang.Character.isDigit;

public class ExpressionEvaluatorApp {

    public static BigDecimal evaluate(String expression) {
        Parser p = new Parser(expression);
        BigDecimal result = p.parseExpression();
        p.expect(TokenType.EOE);// Ensure input is fully consumed
        return result.stripTrailingZeros();
    }

    private static final class Parser {
        private final String s;
        private int i = 0;

        private Token tok;
        // DECIMAL64 = 16 digits precision + HALF_EVEN rounding
        // ensures safe division (e.g. 10/3) without infinite decimals
        private static final MathContext MC = MathContext.DECIMAL64;


        private Parser(String input) {
            this.s = input.trim();
            next(); // Initialize first token
        }

        private ParseException err(String msg) {
            return new ParseException(msg);
        }


        /**
         * Parses an arithmetic expression following the grammar:</br>
         * Expression = Term { (+|-) Term }</br>
         * Handles addition and subtraction at the current precedence level.</br>
         * Calls parseTerm() for multiplication/division.
         *
         */
        BigDecimal parseExpression() {
            BigDecimal value = parseTerm();
            while (tok.type == TokenType.PLUS || tok.type == TokenType.MINUS) {
                TokenType plusOrMinus = tok.type;
                next();
                BigDecimal rightValue = parseTerm();
                value = (plusOrMinus == TokenType.PLUS)
                        ? value.add(rightValue, MC)
                        : value.subtract(rightValue, MC);
            }
            return value;
        }

        /**
         * Parses a term following the grammar:</br>
         * Term = Factor { (*|/) Factor }</br>
         * Handles multiplication and division operations.</br>
         * Checks for division by zero and throws ParseException.
         */
        BigDecimal parseTerm() {
            BigDecimal value = parseFactor();
            while (tok.type == TokenType.MUL || tok.type == TokenType.DIV) {
                TokenType mulOrDiv = tok.type;
                next();
                BigDecimal rightValue = parseFactor();
                if (mulOrDiv == TokenType.DIV){
                    // division by zero chek
                    if(rightValue.compareTo(BigDecimal.ZERO) == 0){
                        throw err("Division by zero");
                    }
                    value = value.divide(rightValue, MC);
                } else {
                    value = value.multiply(rightValue, MC);
                }
            }
            return value;
        }

        /**
         * Parses a factor:</br>
         * Factor = Number | (Expression) | +Factor | -Factor</br>
         * Handles:</br>
         * - Parentheses recursively</br>
         * - Unary plus and minus recursively</br>
         * - Ensures no two numbers are adjacent (invalid input)
         */
        BigDecimal parseFactor() {
            switch (tok.type) {
                case NUMBER -> {
                    BigDecimal v = tok.number;
                    next();
                    if (tok.type == TokenType.NUMBER){
                        throw err("two numbers in a row at position " + i);
                    }
                    return v;
                }
                case LPAREN -> {
                    next();
                    BigDecimal inside = parseExpression();
                    expect(TokenType.RPAREN);
                    next();
                    return inside;
                }
                case PLUS -> {
                    next();
                    return parseFactor();// unary +
                }
                case MINUS -> {
                    next();
                    return parseFactor().negate(MC);// unary -
                }
                default -> throw err("unexpected token: " + tok);
            }
        }

        /**
         * Determine the next token: operator, parentheses, or number</br>
         * Skip whitespace before parsing
         */
        private void next() {
            skipWs();
            if (i >= s.length()) {
                tok = new Token(TokenType.EOE);
                return;
            }

            char c = s.charAt(i);
            switch (c) {
                case '+' -> {
                    tok = new Token(TokenType.PLUS);
                    i++;
                }
                case '-' -> {
                    tok = new Token(TokenType.MINUS);
                    i++;
                }
                case '*' -> {
                    tok = new Token(TokenType.MUL);
                    i++;
                }
                case '/' -> {
                    tok = new Token(TokenType.DIV);
                    i++;
                }
                case '(' -> {
                    tok = new Token(TokenType.LPAREN);
                    i++;
                }
                case ')' -> {
                    tok = new Token(TokenType.RPAREN);
                    i++;
                }
            }
            if (isDigit(c) || c == '.') {
                tok = readNumber();
            }

        }

        private Token readNumber() {
            int start = i;
            boolean seenDot = false;
            while (i < s.length()) {
                char ch = s.charAt(i);
                if (isDigit(ch)) {
                    i++;
                } else if (ch == '.' && !seenDot) {
                    seenDot = true;
                    i++;
                } else if (ch == '.'){
                    throw err("2 dots in number");
                } else break;
            }
            String num = s.substring(start, i);
            if (num.equals(".") || num.isEmpty()) throw err("Incorrectly formed number in" + start);
            return new Token(TokenType.NUMBER, new BigDecimal(num));
        }

        private void skipWs() {
            while (i < s.length() && Character.isWhitespace(s.charAt(i))) i++;
        }

        private void expect(TokenType expected) {
            if (tok.type != expected) throw err("Expected: " + expected + ", got: " + tok);
        }

    }
}
