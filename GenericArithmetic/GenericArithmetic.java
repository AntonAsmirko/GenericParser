package GenericArithmetic;

interface GenericArithmetic<T extends Number> {

    AbstractGenericArithmetic<T> add(AbstractGenericArithmetic<T> second);
    AbstractGenericArithmetic<T> subtract(AbstractGenericArithmetic<T> second);
    AbstractGenericArithmetic<T> divide(AbstractGenericArithmetic<T> second);
    AbstractGenericArithmetic<T> multiply(AbstractGenericArithmetic<T> second);
    AbstractGenericArithmetic<T> log2();
    AbstractGenericArithmetic<T> pow2();
    AbstractGenericArithmetic<T> negate();
    AbstractGenericArithmetic<T> min(AbstractGenericArithmetic<T> second);
    AbstractGenericArithmetic<T> max(AbstractGenericArithmetic<T> second);
    AbstractGenericArithmetic<T> count();
}