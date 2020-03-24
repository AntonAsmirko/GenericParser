package GenericArithmetic;

import com.sun.jdi.Method;
import exceptions.checkers.Checkers;
import utils.BigArithmetic;

import java.math.BigInteger;
import java.util.function.BiFunction;

public class BigIntegerGenericArithmetic extends AbstractGenericArithmetic<BigInteger> {

    public BigIntegerGenericArithmetic(Number val) {
        super(new BigInteger(val.toString()));
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> add(AbstractGenericArithmetic<BigInteger> second) {
        return abstractBinaryOp(second, add, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> subtract(AbstractGenericArithmetic<BigInteger> second) {
        return abstractBinaryOp(second, subtract, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> divide(AbstractGenericArithmetic<BigInteger> second) {
        return abstractBinaryOp(second, divide, Checkers::bigIntegerDivisionByZero);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> multiply(AbstractGenericArithmetic<BigInteger> second) {
        return abstractBinaryOp(second, multiply, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> log2() {
        return abstractBinaryOp(null, log2, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> pow2() {
        return abstractBinaryOp(null, pow2, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> negate() {
        return abstractBinaryOp(null, negate, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> min(AbstractGenericArithmetic<BigInteger> second) {
        return abstractBinaryOp(second, min, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> max(AbstractGenericArithmetic<BigInteger> second) {
        return abstractBinaryOp(second, max, null);
    }

    @Override
    public AbstractGenericArithmetic<BigInteger> count() {
        return abstractBinaryOp(null, count, null);
    }

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> add = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue().add(second.getValue()));

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> subtract = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue().subtract(second.getValue()));

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> multiply = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue().multiply(second.getValue()));

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> divide = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue().divide(second.getValue()));

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> log2 = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue());

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> pow2 = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue());

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> negate = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue().negate());

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> max = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue().max(second.getValue()));

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> min = (
            first, second) -> new BigIntegerGenericArithmetic(first.getValue().min(second.getValue()));

    private BiFunction<AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>, AbstractGenericArithmetic<BigInteger>> count = (
            first,
            second) -> new BigIntegerGenericArithmetic(new BigInteger(String.valueOf(first.getValue().bitCount())));
}
