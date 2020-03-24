package expression;

import GenericArithmetic.AbstractGenericArithmetic;

public class Const<T> implements TripleExpression<T> {

    private AbstractGenericArithmetic<T> value;

    public Const(AbstractGenericArithmetic<T> val) {
        value = val;
    }

    @Override
    public boolean equals(Object o1) {
        if (o1 == this) {
            return true;
        }
        if (o1 == null || o1.getClass() != this.getClass()) {
            return false;
        }

        Const exp = (Const) o1;
        return this.value.equals(exp.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    private boolean isValOverflow(String val) {
        char[] valInChars = val.toCharArray();
        String maxInt = String.valueOf(Integer.MAX_VALUE);
        boolean subZero = false;
        int valLength = val.length();
        if (valInChars[0] == '-') {
            subZero = true;
            valLength--;
        }
        if (valLength < maxInt.length()) {
            return false;
        } else if (valLength > maxInt.length()) {
            return true;
        } else {
            return isGreaterThanMax(val, subZero);
        }
    }

    private boolean isGreaterThanMax(String val, boolean isNegative) {
        String comparisonObj;
        if (isNegative) {
            comparisonObj = String.valueOf(Integer.MIN_VALUE).substring(1);
        } else {
            comparisonObj = String.valueOf(Integer.MAX_VALUE);
        }
        char[] comparisonObjChar = comparisonObj.toCharArray();
        char[] exampleToCheck = isNegative ? val.substring(1).toCharArray() : val.toCharArray();
        for (int i = 0; i < comparisonObjChar.length; i++) {
            if (exampleToCheck[i] < comparisonObjChar[i]) {
                return false;
            }
            if (exampleToCheck[i] > comparisonObjChar[i]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x, AbstractGenericArithmetic<T> y,
            AbstractGenericArithmetic<T> z) {
        return value;
    }

    @Override
    public AbstractGenericArithmetic<T> evaluate(AbstractGenericArithmetic<T> x) {
        return value;
    }
}
