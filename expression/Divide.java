package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Divide<T> extends AbstractOperation<T> {

    public Divide(TripleExpression<T> firstArg, TripleExpression<T> secondArg) {
        super(firstArg, secondArg, "/");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first, AbstractGenericArithmetic<T> second) {
        return first.divide(second);
    }
}
