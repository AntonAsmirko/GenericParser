package GenericArithmetic;

import exceptions.checkers.Checkers;

import java.util.function.BiFunction;

public abstract class AbstractGenericArithmetic<T extends Number> implements GenericArithmetic<T> {

    protected T val;

    public T getValue() {
        return val;
    }

    protected AbstractGenericArithmetic(T val) {
        this.val = val;
    }

    protected AbstractGenericArithmetic<T> abstractBinaryOp(AbstractGenericArithmetic<T> second,
                                                            BiFunction<AbstractGenericArithmetic<T>,
                                                                    AbstractGenericArithmetic<T>,
                                                                    AbstractGenericArithmetic<T>> calc,
                                                            BiFunction<AbstractGenericArithmetic<T>,
                                                                    AbstractGenericArithmetic<T>, Checkers.ExceptionsTypes> checker) {
        if (checker != null) {
            Checkers.ExceptionsTypes resultCheck = checker.apply(this, second);
            if (resultCheck != Checkers.ExceptionsTypes.NONE)
                throw new IllegalStateException(resultCheck.toString());
        }
        return calc.apply(this, second);
    }
}
