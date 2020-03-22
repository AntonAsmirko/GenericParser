package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Add<T extends Number> extends AbstractOperation<T> {

    public Add(TripleExpression<T> firstArg, TripleExpression<T> secondArg) {
        super(firstArg, secondArg, "+");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first, AbstractGenericArithmetic<T> second) {
        return first.add(second);
    }

}
