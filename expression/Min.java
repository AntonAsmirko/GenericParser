package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Min<T extends Number> extends AbstractOperation<T> {

    public Min(TripleExpression<T> firstArg, TripleExpression<T> secondArg) {
        super(firstArg, secondArg, "min");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first, AbstractGenericArithmetic<T> second) {
        return first.min(second);
    }
}
