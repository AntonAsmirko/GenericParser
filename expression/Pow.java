package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Pow<T> extends AbstractUnaryOperation<T> {

    public Pow(TripleExpression<T> arg) {
        super(arg, "pow2");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first) {
        return first.pow2();
    }
}
