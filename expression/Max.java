package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Max<T> extends AbstractOperation<T> {

    public Max(TripleExpression<T> firstArg, TripleExpression<T> secondArg) {
        super(firstArg, secondArg, "max");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first, AbstractGenericArithmetic<T> second) {
        return first.max(second);
    }
}
