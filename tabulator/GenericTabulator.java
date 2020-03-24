package tabulator;

import GenericArithmetic.*;
import exceptions.myExceptions.GenericParserException;
import parser.ExpressionParser;
import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenericTabulator implements Tabulator {

    private static final String BI = "bi";
    private static final String I = "i";
    private static final String D = "d";
    private static final String S = "s";
    private static final String L = "l";
    private static int realAxeX, realAxeY, realAxeZ;

    public <T extends Number> Object[][][] tabulate(String expression, Object[][][] result, Integer x1, Integer y1,
            Integer z1, Function<Integer, AbstractGenericArithmetic<T>> constructor) {
        for (Integer i = 0; i < realAxeX; i++) {
            for (Integer j = 0; j < realAxeY; j++) {
                for (Integer k = 0; k < realAxeZ; k++) {
                    try {
                        result[i][j][k] = new ExpressionParser<T>().parse(expression)
                                .evaluate(constructor.apply((x1 + i)), constructor.apply(y1 + j),
                                        constructor.apply(z1 + k))
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
                return tabulate(expression, result, x1, y1, z1, IntegerGenericArithmetic::new);
            case D:
                return tabulate(expression, result, x1, y1, z1, DoubleGenericArithmetic::new);
            case BI:
                return tabulate(expression, result, x1, y1, z1, BigIntegerGenericArithmetic::new);
            case S:
                return tabulate(expression, result, x1, y1, z1, ShortGenericArithmetic::new);
            case L:
                return tabulate(expression, result, x1, y1, z1, IntegerGenericArithmetic::new);
            default:
                throw new GenericParserException("Strange mode: " + mode);
        }
    }

    public static void main(String[] args) throws Exception {
        GenericTabulator tabulator = new GenericTabulator();
        System.out.println(tabulator.tabulate("i", "4 min 2 min 3", -5, 18, -15, 14, -2, 7)[0][0][0]);
    }
}
