package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Count<T extends Number> extends AbstractUnaryOperation<T> {

    public Count(TripleExpression<T> firstArg) {
        super(firstArg, "count");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first) {
        return first.count();
    }
}
