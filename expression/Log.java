package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Log<T extends Number> extends AbstractUnaryOperation<T>{

    public Log(TripleExpression<T> arg){
        super(arg, "log");
    }

    @Override
    public AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first) {
        return first.log2();
    }
}
