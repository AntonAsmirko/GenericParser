package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Multiply<T extends Number> extends AbstractOperation<T> {

    public Multiply(TripleExpression<T> firstArg, TripleExpression<T> secondArg) {
        super(firstArg, secondArg, "*");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first, AbstractGenericArithmetic<T> second) {
        return first.multiply(second);
    }
}
