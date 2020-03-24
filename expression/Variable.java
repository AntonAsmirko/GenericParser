package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Variable<T> implements TripleExpression<T> {

    protected String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    @Override
    public boolean equals(Object o1) {
        if (o1 == this) {
            return true;
        }
        if (o1 == null || o1.getClass() != this.getClass()) {
            return false;
        }

        Variable<T> exp = (Variable) o1;
        return this.varName.equals(exp.varName);
    }

    @Override
    public int hashCode() {
        return varName.hashCode();
    }

    @Override
    public String toString() {
        return varName;
    }

    @Override
    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x, AbstractGenericArithmetic<T> y,
            AbstractGenericArithmetic<T> z) {
        switch (varName) {
            case "x":
                return x;
            case "y":
                return y;
            default:
                return z;
        }
    }

    @Override
    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x) {
        return x;
    }
}
