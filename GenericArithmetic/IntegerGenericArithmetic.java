package GenericArithmetic;

import exceptions.checkers.Checkers;

import java.util.function.BiFunction;

public class IntegerGenericArithmetic extends AbstractGenericArithmetic<Integer> {

    public IntegerGenericArithmetic(Integer val) {
        super(val);
    }

    @Override
    public AbstractGenericArithmetic<Integer> add(AbstractGenericArithmetic<Integer> second) {
        return abstractBinaryOp( second, add, Checkers::integerAddOverflow);
    }

    @Override
    public AbstractGenericArithmetic<Integer> subtract(AbstractGenericArithmetic<Integer> second) {
        return abstractBinaryOp(second, subtract, Checkers::integerSubtractionOverflow);
    }

    @Override
    public AbstractGenericArithmetic<Integer> divide(AbstractGenericArithmetic<Integer> second) {
        return abstractBinaryOp(second, divide, Checkers::integerDivisionChecker);
    }

    @Override
    public AbstractGenericArithmetic<Integer> multiply(AbstractGenericArithmetic<Integer> second) {
        return abstractBinaryOp(second, multiply, Checkers::integerMultiplyChecker);
    }

    @Override
    public AbstractGenericArithmetic<Integer> log2() {
        return abstractBinaryOp(null, log2, Checkers::integerLogChecker);
    }

    @Override
    public AbstractGenericArithmetic<Integer> pow2() {
        return abstractBinaryOp( null, pow2, Checkers::integerPowChecker);
    }

    @Override
    public AbstractGenericArithmetic<Integer> negate() {
        return abstractBinaryOp( null, negate, Checkers::integerNegateOverflow);
    }

    @Override
    public AbstractGenericArithmetic<Integer> max(AbstractGenericArithmetic<Integer> second) {
        return abstractBinaryOp(second, max, null);
    }

    @Override
    public AbstractGenericArithmetic<Integer> count() {
        return abstractBinaryOp( null, count, null);
    }

    @Override
    public AbstractGenericArithmetic<Integer> min(AbstractGenericArithmetic<Integer> second) {
        return abstractBinaryOp(second, min, null);
    }

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> add = (first, second) ->
            new IntegerGenericArithmetic(first.getValue() + second.getValue());

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> subtract = (first, second) ->
            new IntegerGenericArithmetic(first.getValue() - second.getValue());

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> multiply = (first, second) ->
            new IntegerGenericArithmetic(first.getValue() * second.getValue());

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> divide = (first, second) ->
            new IntegerGenericArithmetic(first.getValue() / second.getValue());

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> log2 = (first, second) ->
            new IntegerGenericArithmetic((int) (Math.log(first.getValue()) / Math.log(2)));

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> pow2 = (first, second) ->
            new IntegerGenericArithmetic((int) Math.pow(2, first.getValue()));

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> negate = (first, second) ->
            new IntegerGenericArithmetic(-first.getValue());

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> max = (first, second) ->
            new IntegerGenericArithmetic(Math.max(first.getValue(), second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> min = (first, second) ->
            new IntegerGenericArithmetic(Math.min(first.getValue(), second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>,
            AbstractGenericArithmetic<Integer>> count = (first, second) ->
            new IntegerGenericArithmetic(Integer.bitCount(first.getValue()));

}
