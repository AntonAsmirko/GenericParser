package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Subtract<T> extends AbstractOperation<T> {

    public Subtract(TripleExpression<T> firstArg, TripleExpression<T> secondArg) {
        super(firstArg, secondArg, "-");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first, AbstractGenericArithmetic<T> second) {
        return first.subtract(second);
    }
}
