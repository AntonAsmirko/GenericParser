package parser;

import GenericArithmetic.AbstractGenericArithmetic;
import GenericArithmetic.BigIntegerGenericArithmetic;
import GenericArithmetic.DoubleGenericArithmetic;
import GenericArithmetic.IntegerGenericArithmetic;
import expression.TripleExpression;

import java.awt.*;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        String expression = "count -5";
        ExpressionParser<Integer> parser = new ExpressionParser<>(ExpressionParser.STRING_TO_INTEGER
                .andThen(ExpressionParser.INTEGER_TO_GENERIC));
        AbstractGenericArithmetic t = parser.parse(expression).evaluate(new IntegerGenericArithmetic(-5),
                new IntegerGenericArithmetic(-15),
                new IntegerGenericArithmetic(-2));
        System.out.println(t.getValue());
    }
}
