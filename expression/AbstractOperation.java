package expression;

import GenericArithmetic.AbstractGenericArithmetic;

import java.util.Objects;

public abstract class AbstractOperation<T extends Number> implements TripleExpression<T> {
    protected TripleExpression<T> firstArg;
    protected TripleExpression<T> secondArg;
    protected String opType;

    public AbstractOperation(TripleExpression<T> firstArg, TripleExpression<T> secondArg, String opType) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
        this.opType = opType;
    }

    @Override
    public String toString() {
        String res1, res2;
        res1 = firstArg.toString();
        res2 = secondArg.toString();
        return "("+res1+" "+opType+" "+res2+")";
    }

    @Override
    public boolean equals(Object o1) {
        if (o1 == this) {
            return true;
        }
        if (o1 == null || o1.getClass() != this.getClass()) {
            return false;
        }

        return this.firstArg.equals(((AbstractOperation) o1).firstArg)
                && this.secondArg.equals(((AbstractOperation) o1).secondArg);
    }

    @Override
    public int hashCode(){
        return Objects.hash(firstArg,secondArg,opType);
    }

    @Override
    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x,
                                          AbstractGenericArithmetic<T> y,
                                          AbstractGenericArithmetic<T> z){
        return eval(firstArg.evaluate(x, y, z), secondArg.evaluate(x, y, z));
    }

    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x){
        return eval(firstArg.evaluate(x), secondArg.evaluate(x));
    }

    public abstract AbstractGenericArithmetic<T> eval(AbstractGenericArithmetic<T> first,
                                                      AbstractGenericArithmetic<T> second);
}
