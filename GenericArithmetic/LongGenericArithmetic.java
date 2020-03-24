package GenericArithmetic;

import java.util.function.BiFunction;

public class LongGenericArithmetic extends AbstractGenericArithmetic<Long> {

    public LongGenericArithmetic(Number val) {
        super(val.longValue());
    }

    @Override
    public AbstractGenericArithmetic<Long> add(AbstractGenericArithmetic<Long> second) {
        return abstractBinaryOp(second, add, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> subtract(AbstractGenericArithmetic<Long> second) {
        return abstractBinaryOp(second, subtract, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> divide(AbstractGenericArithmetic<Long> second) {
        return abstractBinaryOp(second, divide, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> multiply(AbstractGenericArithmetic<Long> second) {
        return abstractBinaryOp(second, multiply, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> log2() {
        return abstractBinaryOp(null, log2, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> pow2() {
        return abstractBinaryOp(null, pow2, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> negate() {
        return abstractBinaryOp(null, negate, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> max(AbstractGenericArithmetic<Long> second) {
        return abstractBinaryOp(second, max, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> count() {
        return abstractBinaryOp(null, count, null);
    }

    @Override
    public AbstractGenericArithmetic<Long> min(AbstractGenericArithmetic<Long> second) {
        return abstractBinaryOp(second, min, null);
    }

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> add = (
            first, second) -> new LongGenericArithmetic(first.getValue() + second.getValue());

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> subtract = (
            first, second) -> new LongGenericArithmetic(first.getValue() - second.getValue());

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> multiply = (
            first, second) -> new LongGenericArithmetic(first.getValue() * second.getValue());

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> divide = (
            first, second) -> new LongGenericArithmetic(first.getValue() / second.getValue());

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> log2 = (
            first, second) -> new LongGenericArithmetic((long) (Math.log(first.getValue()) / Math.log(2)));

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> pow2 = (
            first, second) -> new LongGenericArithmetic((long) Math.pow(2, first.getValue()));

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> negate = (
            first, second) -> new LongGenericArithmetic(-first.getValue());

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> max = (
            first, second) -> new LongGenericArithmetic(Math.max(first.getValue(), second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> min = (
            first, second) -> new LongGenericArithmetic(Math.min(first.getValue(), second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>, AbstractGenericArithmetic<Long>> count = (
            first, second) -> new LongGenericArithmetic((long) Long.bitCount(first.getValue()));
}
