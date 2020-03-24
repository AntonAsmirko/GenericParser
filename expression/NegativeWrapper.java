package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class NegativeWrapper<T> extends AbstractUnaryOperation<T> {

    public NegativeWrapper(TripleExpression<T> arg) {
        super(arg, "-");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first) {
        return first.negate();
    }
}
