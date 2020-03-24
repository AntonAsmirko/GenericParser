package GenericArithmetic;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class DoubleGenericArithmetic extends AbstractGenericArithmetic<Double> {

    public DoubleGenericArithmetic(Number val) {
        super(val.doubleValue());
    }

    @Override
    public AbstractGenericArithmetic<Double> add(AbstractGenericArithmetic<Double> second) {
        return abstractBinaryOp(second, add, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> subtract(AbstractGenericArithmetic<Double> second) {
        return abstractBinaryOp(second, subtract, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> divide(AbstractGenericArithmetic<Double> second) {
        return abstractBinaryOp(second, divide, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> multiply(AbstractGenericArithmetic<Double> second) {
        return abstractBinaryOp(second, multiply, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> log2() {
        return abstractBinaryOp(null, log2, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> pow2() {
        return abstractBinaryOp(null, pow2, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> negate() {
        return abstractBinaryOp(null, negate, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> max(AbstractGenericArithmetic<Double> second) {
        return abstractBinaryOp(second, max, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> count() {
        return abstractBinaryOp(null, count, null);
    }

    @Override
    public AbstractGenericArithmetic<Double> min(AbstractGenericArithmetic<Double> second) {
        return abstractBinaryOp(second, min, null);
    }

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> add = (
            first, second) -> new DoubleGenericArithmetic(first.getValue() + second.getValue());

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> subtract = (
            first, second) -> new DoubleGenericArithmetic(first.getValue() - second.getValue());

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> multiply = (
            first, second) -> new DoubleGenericArithmetic(first.getValue() * second.getValue());

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> divide = (
            first, second) -> new DoubleGenericArithmetic(first.getValue() / second.getValue());

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> log2 = (
            first, second) -> new DoubleGenericArithmetic((Math.log(first.getValue()) / Math.log(2)));

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> pow2 = (
            first, second) -> new DoubleGenericArithmetic(Math.pow(2, first.getValue()));

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> negate = (
            first, second) -> new DoubleGenericArithmetic(-first.getValue());

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> max = (
            first, second) -> new DoubleGenericArithmetic(Math.max(first.getValue(), second.getValue()));

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> min = (
            first, second) -> new DoubleGenericArithmetic(Math.min(first.getValue(), second.getValue()));

    private Function<Double, Long> countOnes = val -> Long.toBinaryString(Double.doubleToRawLongBits(val)).chars()
            .filter(i -> i == '1').count();

    private BiFunction<AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>, AbstractGenericArithmetic<Double>> count = (
            first, second) -> new DoubleGenericArithmetic(Double.valueOf(countOnes.apply(first.getValue())));
}
