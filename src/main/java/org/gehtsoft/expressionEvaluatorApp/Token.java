package org.gehtsoft.expressionEvaluatorApp;

import java.math.BigDecimal;

public class Token {

    final TokenType type;
    final BigDecimal number;

    Token (TokenType type, BigDecimal number){
        this.type = type;
        this.number = number;
    }

    Token(TokenType type) {
        this(type, null);
    }
}

