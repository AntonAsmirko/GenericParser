package GenericArithmetic;

import exceptions.checkers.Checkers;

import java.util.function.BiFunction;
import java.util.logging.SocketHandler;

public class ShortGenericArithmetic extends AbstractGenericArithmetic<Short> {

    public ShortGenericArithmetic(Short value) {
        super(value);
    }

    @Override
    public AbstractGenericArithmetic<Short> add(AbstractGenericArithmetic<Short> second) {
        return abstractBinaryOp( second, add, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> subtract(AbstractGenericArithmetic<Short> second) {
        return abstractBinaryOp(second, subtract, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> divide(AbstractGenericArithmetic<Short> second) {
        return abstractBinaryOp(second, divide, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> multiply(AbstractGenericArithmetic<Short> second) {
        return abstractBinaryOp(second, multiply, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> log2() {
        return abstractBinaryOp(null, log2, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> pow2() {
        return abstractBinaryOp( null, pow2, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> negate() {
        return abstractBinaryOp( null, negate, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> max(AbstractGenericArithmetic<Short> second) {
        return abstractBinaryOp(second, max, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> count() {
        return abstractBinaryOp( null, count, null);
    }

    @Override
    public AbstractGenericArithmetic<Short> min(AbstractGenericArithmetic<Short> second) {
        return abstractBinaryOp(second, min, null);
    }

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> add = (first, second) ->
            new ShortGenericArithmetic((short)(first.getValue() + second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> subtract = (first, second) ->
            new ShortGenericArithmetic((short)(first.getValue() - second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> multiply = (first, second) ->
            new ShortGenericArithmetic((short)(first.getValue() * second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> divide = (first, second) ->
            new ShortGenericArithmetic((short)(first.getValue() / second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> log2 = (first, second) ->
            new ShortGenericArithmetic((short)(Math.log(first.getValue()) / Math.log(2)));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> pow2 = (first, second) ->
            new ShortGenericArithmetic((short)(Math.pow(2, first.getValue())));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> negate = (first, second) ->
            new ShortGenericArithmetic((short)(-first.getValue()));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> max = (first, second) ->
            new ShortGenericArithmetic((short)(Math.max(first.getValue(), second.getValue())));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> min = (first, second) ->
            new ShortGenericArithmetic((short)(Math.min(first.getValue(), second.getValue())));

    private BiFunction<AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>,
            AbstractGenericArithmetic<Short>> count = (first, second) ->
            null;
}
