package exceptions.checkers;

import GenericArithmetic.AbstractGenericArithmetic;

import javax.print.DocFlavor;
import java.math.BigInteger;

public class Checkers {

    public enum ExceptionsTypes {
        NONE, BIG_INTEGER_DIVISION_BY_ZERO, INTEGER_ADD_OVERFLOW,
        INTEGER_SUBTRACTION_OVERFLOW, INTEGER_DIVISION_OVERFLOW, INTEGER_DIVISION_BY_ZERO,
        INTEGER_MULTIPLY_OVERFLOW, INTEGER_POWER_EXCEPTION, INTEGER_NEGATE_OVERFLOW,
        INTEGER_LOG_EXCEPTION
    }

    public static ExceptionsTypes bigIntegerDivisionByZero(AbstractGenericArithmetic<BigInteger> first,
                                                           AbstractGenericArithmetic<BigInteger> second) {
        if (second.getValue().equals(BigInteger.ZERO)) {
            return ExceptionsTypes.BIG_INTEGER_DIVISION_BY_ZERO;
        } else {
            return ExceptionsTypes.NONE;
        }
    }

    public static ExceptionsTypes integerAddOverflow(AbstractGenericArithmetic<Integer> first,
                                                     AbstractGenericArithmetic<Integer> second) {
        if (first.getValue() > 0 && second.getValue() > 0 && first.getValue() > Integer.MAX_VALUE - second.getValue())
            return ExceptionsTypes.INTEGER_ADD_OVERFLOW;
        if (first.getValue() < 0 && second.getValue() < 0 && first.getValue() < Integer.MIN_VALUE - second.getValue())
            return ExceptionsTypes.INTEGER_ADD_OVERFLOW;
        return ExceptionsTypes.NONE;
    }

    public static ExceptionsTypes integerSubtractionOverflow(AbstractGenericArithmetic<Integer> a,
                                                             AbstractGenericArithmetic<Integer> b) {
        if (b.getValue() > 0 && Integer.MIN_VALUE + b.getValue() > a.getValue())
            return ExceptionsTypes.INTEGER_SUBTRACTION_OVERFLOW;
        if (b.getValue() < 0 && Integer.MAX_VALUE + b.getValue() < a.getValue())
            return ExceptionsTypes.INTEGER_SUBTRACTION_OVERFLOW;
        return ExceptionsTypes.NONE;
    }

    public static ExceptionsTypes integerDivisionChecker(AbstractGenericArithmetic<Integer> first,
                                                         AbstractGenericArithmetic<Integer> second) {
        if (first.getValue() == Integer.MIN_VALUE && second.getValue() == -1) {
            return ExceptionsTypes.INTEGER_DIVISION_OVERFLOW;
        }
        if (second.getValue() == 0) {
            return ExceptionsTypes.INTEGER_DIVISION_BY_ZERO;
        }
        return ExceptionsTypes.NONE;
    }

    public static ExceptionsTypes integerMultiplyChecker(AbstractGenericArithmetic<Integer> first,
                                                         AbstractGenericArithmetic<Integer> second) {
        if (first.getValue() != 0 && second.getValue() != 0) {
            if ((first.getValue() > 0 && second.getValue() > 0 &&
                    first.getValue() > Integer.MAX_VALUE / second.getValue()) ||
                    (first.getValue() < 0 && second.getValue() < 0 &&
                            first.getValue() < Integer.MAX_VALUE / second.getValue()) ||
                    (first.getValue() < 0 && second.getValue() > 0 &&
                            first.getValue() < Integer.MIN_VALUE / second.getValue()) ||
                    (first.getValue() > 0 && second.getValue() < 0 &&
                            second.getValue() < Integer.MIN_VALUE / first.getValue())) {
                return ExceptionsTypes.INTEGER_MULTIPLY_OVERFLOW;
            }
        }
        return ExceptionsTypes.NONE;
    }

    public static ExceptionsTypes integerPowChecker(AbstractGenericArithmetic<Integer> first,
                                                    AbstractGenericArithmetic<Integer> second) {
        if (first.getValue() == 0 && second.getValue() == 0 || second.getValue() < 0 &&
                first.getValue() == 0 || second.getValue() < 0)
            return ExceptionsTypes.INTEGER_POWER_EXCEPTION;
        return ExceptionsTypes.NONE;
    }

    public static ExceptionsTypes integerNegateOverflow(AbstractGenericArithmetic<Integer> first,
                                                        AbstractGenericArithmetic<Integer> second) {
        if (first.getValue() == Integer.MIN_VALUE)
            return ExceptionsTypes.INTEGER_NEGATE_OVERFLOW;
        return ExceptionsTypes.NONE;
    }

    public static ExceptionsTypes integerLogChecker(AbstractGenericArithmetic<Integer> first,
                                                    AbstractGenericArithmetic<Integer> second) {
        if (second.getValue() == 1 || first.getValue() <= 0 || second.getValue() <= 0)
            return ExceptionsTypes.INTEGER_LOG_EXCEPTION;
        return ExceptionsTypes.NONE;
    }
}
