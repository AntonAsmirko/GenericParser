package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Divide<T extends Number> extends AbstractOperation<T> {

    public Divide(TripleExpression firstArg, TripleExpression secondArg) {
        super(firstArg, secondArg, "/");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first, AbstractGenericArithmetic<T> second) {
        return first.divide(second);
    }
}
