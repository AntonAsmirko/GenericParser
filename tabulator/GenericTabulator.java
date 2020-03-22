package tabulator;

import GenericArithmetic.*;
import parser.ExpressionParser;
import java.math.BigInteger;

public class GenericTabulator implements Tabulator {

    private static final String BI = "bi";
    private static final String I = "i";
    private static final String D = "d";
    private static final String S = "s";
    private static final String L = "l";

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        int realAxeX = x2 - x1 + 1, realAxeY = y2 - y1 + 1, realAxeZ = z2 - z1 + 1;
        Object[][][] result = new Object[realAxeX][realAxeY][realAxeZ];
        for (int i = 0; i < realAxeX; i++) {
            for (int j = 0; j < realAxeY; j++) {
                for (int k = 0; k < realAxeZ; k++) {
                    try {
                        switch (mode) {
                            case I:
                                result[i][j][k] = new ExpressionParser<>(ExpressionParser.STRING_TO_INTEGER
                                        .andThen(ExpressionParser.INTEGER_TO_GENERIC))
                                        .parse(expression)
                                        .evaluate(new IntegerGenericArithmetic(x1 + i),
                                                new IntegerGenericArithmetic(y1 + j),
                                                new IntegerGenericArithmetic(z1 + k))
                                        .getValue();
                                break;
                            case D:
                                result[i][j][k] = new ExpressionParser<>(ExpressionParser.STRING_TO_DOUBLE
                                        .andThen(ExpressionParser.DOUBLE_TO_GENERIC))
                                        .parse(expression)
                                        .evaluate(new DoubleGenericArithmetic((double) (x1 + i)),
                                                new DoubleGenericArithmetic((double) (y1 + j)),
                                                new DoubleGenericArithmetic((double) (z1 + k)))
                                        .getValue();
                                break;
                            case BI:
                                result[i][j][k] = new ExpressionParser<>(ExpressionParser.STRING_TO_BIG_INTEGER
                                        .andThen(ExpressionParser.BIG_INTEGER_TO_GENERIC))
                                        .parse(expression)
                                        .evaluate(new BigIntegerGenericArithmetic(new BigInteger(String.valueOf(x1 + i))),
                                                new BigIntegerGenericArithmetic(new BigInteger(String.valueOf(y1 + j))),
                                                new BigIntegerGenericArithmetic(new BigInteger(String.valueOf(z1 + k))))
                                        .getValue();
                                break;
                            case S:
                                result[i][j][k] = new ExpressionParser<>(ExpressionParser.STRING_TO_SHORT
                                        .andThen(ExpressionParser.SHORT_TO_GENERIC))
                                        .parse(expression)
                                        .evaluate(new ShortGenericArithmetic((short)(x1 + i)),
                                                new ShortGenericArithmetic((short)(y1 + j)),
                                                new ShortGenericArithmetic((short)(z1 + k)))
                                        .getValue();
                                break;
                            case L:
                                result[i][j][k] = new ExpressionParser<>(ExpressionParser.STRING_TO_LONG
                                        .andThen(ExpressionParser.LONG_TO_GENERIC))
                                        .parse(expression)
                                        .evaluate(new LongGenericArithmetic((long)x1 + i),
                                                new LongGenericArithmetic((long)y1 + j),
                                                new LongGenericArithmetic((long)z1 + k))
                                        .getValue();
                                break;
                        }
                    } catch (Exception e){
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        GenericTabulator tabulator = new GenericTabulator();
        System.out.println(tabulator.tabulate("i", "4 min 2 min 3",
                -5, 18, -15,14, -2, 7 )[0][0][0]);
    }
}
