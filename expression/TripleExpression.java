package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public interface TripleExpression<T> {
    AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x, AbstractGenericArithmetic<T> y,
            AbstractGenericArithmetic<T> z);

    AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x);
}
