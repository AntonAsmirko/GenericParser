package tabulator;

import GenericArithmetic.*;
import exceptions.myExceptions.GenericParserException;
import parser.ExpressionParser;

import java.math.BigInteger;
import java.util.function.Function;

public class GenericTabulator implements Tabulator {

    private static final String BI = "bi";
    private static final String I = "i";
    private static final String D = "d";
    private static final String S = "s";
    private static final String L = "l";
    private static int realAxeX, realAxeY, realAxeZ;

    public <T extends Number> Object[][][] tabulate(String expression, Object[][][] result, int x1, int y1, int z1,
            Function<Number, AbstractGenericArithmetic<T>> constructorFnc, Function<String, Number> mediator) {
        for (int i = 0; i < realAxeX; i++) {
            for (int j = 0; j < realAxeY; j++) {
                for (int k = 0; k < realAxeZ; k++) {
                    try {
                        result[i][j][k] = new ExpressionParser<T>(constructorFnc, mediator).parse(expression)
                                .evaluate(constructorFnc.apply(x1 + i), constructorFnc.apply(y1 + j),
                                        constructorFnc.apply(z1 + k))
                                .getValue();
                    } catch (Exception e) {
                        result[i][j][k] = null;
                    }
                }

            }
        }
        return result;

    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws GenericParserException {
        realAxeX = x2 - x1 + 1;
        realAxeY = y2 - y1 + 1;
        realAxeZ = z2 - z1 + 1;
        Object[][][] result = new Object[realAxeX][realAxeY][realAxeZ];
        switch (mode) {
            case I:
                return tabulate(expression, result, x1, y1, z1, IntegerGenericArithmetic::new, Integer::parseInt);
            case D:
                return tabulate(expression, result, x1, y1, z1, DoubleGenericArithmetic::new, Double::parseDouble);
            case BI:
                return tabulate(expression, result, x1, y1, z1, BigIntegerGenericArithmetic::new, BigInteger::new);
            case S:
                return tabulate(expression, result, x1, y1, z1, ShortGenericArithmetic::new, Short::parseShort);
            case L:
                return tabulate(expression, result, x1, y1, z1, LongGenericArithmetic::new, Long::parseLong);
            default:
                throw new GenericParserException("Strange mode: " + mode);
        }
    }

    public static void main(String[] args) throws GenericParserException {
        GenericTabulator tabulator = new GenericTabulator();
        System.out.println(tabulator.tabulate("s", "10", 2147483632, 2147483646, 2147483642, 2147483646, 2147483637,
                2147483646)[0][0][0]);
    }
}
