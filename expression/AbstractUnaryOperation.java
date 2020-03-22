package expression;

import GenericArithmetic.AbstractGenericArithmetic;

import java.util.Objects;

public abstract class AbstractUnaryOperation<T extends Number> implements TripleExpression<T> {
    protected TripleExpression<T> firstArg;
    protected String opType;

    public AbstractUnaryOperation(TripleExpression<T> firstArg, String opType) {
        this.firstArg = firstArg;
        this.opType = opType;
    }

    @Override
    public String toString() {
        String res1;
        res1 = firstArg.toString();
        return opType+"("+res1+")";
    }

    @Override
    public boolean equals(Object o1) {
        if (o1 == this) {
            return true;
        }
        if (o1 == null || o1.getClass() != this.getClass()) {
            return false;
        }

        return this.firstArg.equals(((AbstractOperation) o1).firstArg);
    }

    @Override
    public int hashCode(){
        return Objects.hash(firstArg,opType);
    }

    @Override
    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x,
                                                 AbstractGenericArithmetic<T> y,
                                                 AbstractGenericArithmetic<T> z){
        return eval(firstArg.evaluate(x, y, z));
    }

    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x){
        return eval(firstArg.evaluate(x));
    }

    public abstract AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first);
}
