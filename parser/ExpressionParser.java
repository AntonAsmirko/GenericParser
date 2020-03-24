package parser;

import GenericArithmetic.*;
import exceptions.myExceptions.*;
import expression.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExpressionParser<T> implements Parser<T> {

    private char[] expression;

    public Function<List<String>, IntegerGenericArithmetic> strToIntGen = x -> x.stream().map(Integer::parseInt)
            .map(IntegerGenericArithmetic::new).collect(Collectors.toList()).get(0);
    public Function<List<String>, LongGenericArithmetic> strToLongGen = x -> x.stream().map(Long::parseLong)
            .map(LongGenericArithmetic::new).collect(Collectors.toList()).get(0);
    public Function<List<String>, ShortGenericArithmetic> strToShortGen = x -> x.stream().map(Short::parseShort)
            .map(ShortGenericArithmetic::new).collect(Collectors.toList()).get(0);
    public Function<List<String>, DoubleGenericArithmetic> strToDoubleGen = x -> x.stream().map(Double::parseDouble)
            .map(DoubleGenericArithmetic::new).collect(Collectors.toList()).get(0);
    public Function<List<String>, BigIntegerGenericArithmetic> strToBigIntGen = x -> x.stream().map(BigInteger::new)
            .map(BigIntegerGenericArithmetic::new).collect(Collectors.toList()).get(0);

    private Map<Character, Operators> charToOperatorMap = Map.of('<', Operators.LEFT_SHIFT, '>', Operators.RIGHT_SHIFT,
            '+', Operators.ADD, '-', Operators.SUBTRACT, '*', Operators.MULTIPLY, '/', Operators.DIVIDE, 'i',
            Operators.MIN, 'a', Operators.MAX);

    private Map<Character, ParseLevel> charToParseLevel = Map.of('<', ParseLevel.LEFT_RIGHT, '>', ParseLevel.LEFT_RIGHT,
            '+', ParseLevel.ADD_SUB, '-', ParseLevel.ADD_SUB, '*', ParseLevel.MULTIPLY_DIVIDE, '/',
            ParseLevel.MULTIPLY_DIVIDE, 'i', ParseLevel.MIN_MAX, 'a', ParseLevel.MIN_MAX);

    private Map<ParseLevel, ParseLevel> levelDownPath = Map.of(ParseLevel.MIN_MAX, ParseLevel.LEFT_RIGHT,
            ParseLevel.LEFT_RIGHT, ParseLevel.ADD_SUB, ParseLevel.ADD_SUB, ParseLevel.MULTIPLY_DIVIDE,
            ParseLevel.MULTIPLY_DIVIDE, ParseLevel.TERMINAL);

    private Map<Operators, BiFunction<TripleExpression<T>, TripleExpression<T>, TripleExpression<T>>> getConstructor = Map
            .of(Operators.ADD, Add::new, Operators.SUBTRACT, Subtract::new, Operators.MULTIPLY, Multiply::new,
                    Operators.DIVIDE, Divide::new, Operators.MIN, Min::new, Operators.MAX, Max::new);

    private enum Operators {
        ADD, SUBTRACT, DIVIDE, MULTIPLY, LEFT_SHIFT, RIGHT_SHIFT, MIN, MAX
    }

    public enum ParseLevel {
        MULTIPLY_DIVIDE, ADD_SUB, LEFT_RIGHT, TERMINAL, MIN_MAX
    }

    private Set<Character> operatorsCh = Set.of('+', '-', '/', '*', '<', '>', 'i', 'a', 'm');

    private TripleExpression<T> parseExpression(ParseLevel level, int leftBorder, int rightBorder) {
        Optional<TripleExpression<T>> prevResult = Optional.empty();
        while (hasNextSameOperator(level, leftBorder, rightBorder)) {
            MyPair myPair = nextSameOperator(level, leftBorder, rightBorder);
            checkWrongOperatorFlow(leftBorder, myPair.position, rightBorder);
            if (prevResult.isEmpty()) {
                prevResult = Optional.of(getConstructor.get(myPair.opType).apply(
                        levelDecider(levelDownPath.get(level), leftBorder, myPair.position),
                        levelDecider(levelDownPath.get(level), myPair.position + 1,
                                nextOpPosition(myPair.position + 1, level, rightBorder))));

            } else {
                prevResult = Optional.of(
                        getConstructor.get(myPair.opType).apply(prevResult.get(), levelDecider(levelDownPath.get(level),
                                myPair.position + 1, nextOpPosition(myPair.position + 1, level, rightBorder))));
            }
            leftBorder = myPair.position + 1;
        }
        if (prevResult.isEmpty()) {
            prevResult = Optional.of(levelDecider(levelDownPath.get(level), leftBorder, rightBorder));
        }
        return prevResult.orElseThrow();
    }

    @Override
    public TripleExpression<T> parse(String expression) {
        this.expression = expression.toCharArray();
        skipWhiteSpacesAndPrepare();
        return parseExpression(ParseLevel.MIN_MAX, 0, this.expression.length);
    }

    private void skipWhiteSpacesAndPrepare() {
        int whitespaceCount = 0;
        int trashSymbols = 0;
        int openCloseBracketsDiff = 0;
        int openCloseBracketsDiffZeroPos = -1;
        boolean wasPrevSign = true;
        for (int i = 0; i < expression.length; i++) {
            Function<Character, Boolean> varChecker = (Character x) -> (x == 'x' || x == 'y' || x == 'z');
            if (Character.isLetter(expression[i]) && !varChecker.apply(expression[i])) {
                StringBuilder sb = new StringBuilder();
                for (int j = i; j < expression.length && !Character.isWhitespace(expression[j]) && expression[j] != '-'
                        && expression[j] != '('; j++) {
                    sb.append(expression[j]);
                }
                String result = sb.toString();
                if (!result.equals("log2") && !result.equals("pow2") && !result.equals("min") && !result.equals("max")
                        && !result.equals("count")) {
                    throw new TypoException("Typo at " + i + " you have written " + result);
                }
                if (result.equals("min") || result.equals("max")) {
                    i += 2;
                    trashSymbols += 2;
                    wasPrevSign = true;
                } else if (result.equals("count")) {
                    i += 4;
                    trashSymbols += 4;
                } else {
                    i += 3;
                    trashSymbols += 3;
                }
                if (operatorsCh.contains(expression[i]) && expression[i] != '-') {
                    throw new WrongArgFlowException("No first argument at position" + i + ": " + getVicinity(i));
                }
                if (!Character.isWhitespace(expression[i + 1]) && expression[i + 1] != '-'
                        && expression[i + 1] != '(') {
                    throw new TypoException("Wrong Basement of log or power at position " + i + ": " + getVicinity(i));
                }
                continue;
            }
            if (Character.isDigit(expression[i]) && !wasPrevSign) {
                throw new WrongOpFlowException("Two operands without operator: " + getVicinity(i));
            }

            if (operatorsCh.contains(expression[i]) && i != expression.length - 1
                    && !(operatorsCh.contains(expression[i + 1]) && expression[i + 1] != 'i'
                            && expression[i + 1] != 'a')) {
                wasPrevSign = true;
            }
            if (Character.isDigit(expression[i]) && i != expression.length - 1 && !Character.isDigit(expression[i + 1])
                    && expression[i + 1] != '.') {
                wasPrevSign = false;
            }
            if (Character.isWhitespace(expression[i])) {
                whitespaceCount++;
            }
            if (i != 0 && (expression[i] == '<' && expression[i - 1] == '<'
                    || expression[i] == '>' && expression[i - 1] == '>')) {
                trashSymbols++;
            }
            if (expression[i] == '(') {
                openCloseBracketsDiff++;
                if (openCloseBracketsDiffZeroPos == -1) {
                    openCloseBracketsDiffZeroPos = i;
                }
            } else if (expression[i] == ')') {
                openCloseBracketsDiff--;
                if (openCloseBracketsDiff == 0) {
                    openCloseBracketsDiffZeroPos = -1;
                }
            }
            if (openCloseBracketsDiff < 0) {
                throw new WrongBracketSequenceException(
                        "Wrong bracket sequence, more close brackets, than open brackets: " + getVicinity(i));
            }
            if (expression[i] != ')' && expression[i] != '(' && expression[i] != '.'
                    && !Character.isLetter(expression[i]) && !Character.isDigit(expression[i])
                    && !Character.isWhitespace(expression[i]) && !operatorsCh.contains(expression[i])) {
                throw new TypoException("Unresolved symbol \'" + expression[i] + "\' at position " + i);
            }
        }
        if (openCloseBracketsDiff != 0) {
            StringBuilder sb = new StringBuilder();
            for (int u = openCloseBracketsDiffZeroPos; u < expression.length; u++) {
                sb.append(expression[u]);
            }
            throw new WrongBracketSequenceException(
                    "Wrong bracket sequence, close brackets was missed somewhere: " + sb.toString());
        }
        char[] newExpression = new char[expression.length - whitespaceCount - trashSymbols];
        int j = 0;
        for (int i = 0; i < expression.length; i++) {
            if (expression[i] == 'p' || expression[i] == 'l' || expression[i] == 'm' || expression[i] == 'c') {
                if (expression[i] == 'm') {
                    if (expression[i + 1] == 'a') {
                        newExpression[j] = 'a';
                    }
                    if (expression[i + 1] == 'i') {
                        newExpression[j] = 'i';
                    }
                    i += 2;
                    j++;
                } else if (expression[i] == 'c') {
                    newExpression[j] = 'c';
                    i += 4;
                    j++;
                } else {
                    newExpression[j] = expression[i];
                    i += 3;
                    j++;
                }
                continue;
            }
            if (i != 0 && (expression[i] == '<' && expression[i - 1] == '<'
                    || expression[i] == '>' && expression[i - 1] == '>')) {
                continue;
            }
            if (!Character.isWhitespace(expression[i])) {
                newExpression[j] = expression[i];
                j++;
            }
        }
        expression = newExpression;
        someProblemsWithVarOrVal();
    }

    private void someProblemsWithVarOrVal() {
        for (int i = 0; i < expression.length; i++) {
            if (i != expression.length - 1
                    && (operatorsCh.contains(expression[i]) || !Character.isLetter(expression[i])
                            && !Character.isDigit(expression[i]) && expression[i] != ')')
                    && expression[i + 1] == ')'
                    || i == expression.length - 1
                            && (operatorsCh.contains(expression[i]) || !Character.isLetter(expression[i])
                                    && !Character.isDigit(expression[i]) && expression[i] != ')')) {
                throw new WrongOpFlowException("Missed Operand at position " + i + ": " + getVicinity(i));
            }
        }
    }

    private TripleExpression<T> parseVal(int leftBorder, int rightBorder, boolean hasNegotiation) {
        StringBuilder sb = new StringBuilder();
        if (hasNegotiation) {
            sb.append(expression[leftBorder - 1]);
        }
        for (; leftBorder < rightBorder; leftBorder++) {
            if (!(Character.isDigit(expression[leftBorder]) || expression[leftBorder] == '.'))
                break;
            sb.append(expression[leftBorder]);
        }
        if (rightBorder < expression.length && Character.isDigit(expression[rightBorder])) {
            sb.append(expression[rightBorder]);
        }
        return new Const<>(typeToInfer.apply(stringCutter(sb.toString())));
    }

    private String stringCutter(String str) {
        if (str.charAt(0) == '-') {
            return str.substring(1);
        } else
            return str;
    }

    private TripleExpression<T> parseVar(int leftBorder) {
        if (expression[leftBorder] != 'x' && expression[leftBorder] != 'y' && expression[leftBorder] != 'z') {
            throw new TypoException(
                    "Unacceptable variable \'" + expression[leftBorder] + "\' at position " + leftBorder);
        }
        return new Variable<>(String.valueOf(expression[leftBorder]));
    }

    private boolean hasNextParticularSymbol(int leftBorder, Function<Character, Boolean> checker) {
        return checker.apply(expression[leftBorder]);
    }

    private MyPair nextSameOperator(ParseLevel level, int leftBorder, int rightBorder) {
        for (; leftBorder < rightBorder; leftBorder++) {
            if (expression[leftBorder] == 'l' || expression[leftBorder] == 'p') {
                leftBorder = skipLogPow(leftBorder);
                if (leftBorder == rightBorder) {
                    throw new IllegalStateException("1");
                }
            }
            if (expression[leftBorder] == '(') {
                leftBorder = findCloseBracketPosition(leftBorder);
            }
            if (operatorsCh.contains(expression[leftBorder])) {
                if (charToParseLevel.get(expression[leftBorder]) == level) {
                    if (expression[leftBorder] == '-' && (leftBorder == 0
                            || operatorsCh.contains(expression[leftBorder - 1]) || expression[leftBorder - 1] == '(')) {
                        continue;
                    }
                    return new MyPair(leftBorder, charToOperatorMap
                            .get(expression[leftBorder] == 'm' ? expression[leftBorder + 1] : expression[leftBorder]));
                }
            }
        }
        throw new IllegalStateException("1");
    }

    private int skipLogPow(int since) {
        if (expression[since + 1] == '(') {
            return findCloseBracketPosition(since + 1);
        } else if (expression[since + 1] == '-') {
            return skipLogPow(since + 1);
        } else if (Character.isDigit(expression[since + 1])) {
            since++;
            while (since < expression.length && Character.isDigit(expression[since])) {
                since++;
            }
            return since;
        } else if (expression[since + 1] == 'l' || expression[since + 1] == 'p') {
            return skipLogPow(since + 1);
        } else if (Character.isLetter(expression[since + 1])) {
            if (since + 2 < expression.length) {
                return since + 2;
            } else {
                return since + 1;
            }
        }
        throw new IllegalStateException();
    }

    private boolean hasNextSameOperator(ParseLevel level, int leftBorder, int rightBorder) {
        for (; leftBorder < rightBorder; leftBorder++) {
            if (expression[leftBorder] == 'l' || expression[leftBorder] == 'p' || expression[leftBorder] == 'c') {
                leftBorder = skipLogPow(leftBorder);
                if (leftBorder == rightBorder) {
                    return false;
                }
            }
            if (expression[leftBorder] == '(') {
                leftBorder = findCloseBracketPosition(leftBorder);
            }
            if (level == ParseLevel.MULTIPLY_DIVIDE && (expression[leftBorder] == '*' || expression[leftBorder] == '/')
                    || level == ParseLevel.MIN_MAX && expression[leftBorder] == 'i'
                    || level == ParseLevel.MIN_MAX && expression[leftBorder] == 'a'
                    || level == ParseLevel.ADD_SUB && (expression[leftBorder] == '+' || expression[leftBorder] == '-')
                    || level == ParseLevel.LEFT_RIGHT
                            && (expression[leftBorder] == '<' || expression[leftBorder] == '>')) {
                if (expression[leftBorder] == '-' && (leftBorder == 0
                        || operatorsCh.contains(expression[leftBorder - 1]) || expression[leftBorder - 1] == '(')) {
                    continue;
                }
                return true;
            }
        }

        return false;
    }

    private int nextOpPosition(int since, ParseLevel level, int rightBorder) {
        for (int i = since; i < rightBorder; i++) {
            if (expression[i] == 'l' || expression[i] == 'p') {
                i = skipLogPow(i);
                if (i == rightBorder) {
                    return rightBorder;
                }
            }
            if (expression[i] == '(') {
                i = findCloseBracketPosition(i);
                if (i == rightBorder) {
                    return rightBorder;
                }
            }
            if (operatorsCh.contains(expression[i]) && charToParseLevel.get(expression[i]) == level
                    && !((expression[i] == '-' && (i == 0 || operatorsCh.contains(expression[i - 1]))))) {
                return i;
            }
        }
        return rightBorder;
    }

    private void checkWrongOperatorFlow(int from, int since, int to) {
        for (int i = since + 1; i < to; i++) {
            if (Character.isDigit(expression[i]) || Character.isLetter(expression[i]) || expression[i] == '('
                    || expression[i] == '-') {
                break;
            }
            if (operatorsCh.contains(expression[i]) || expression[i] == '%') {
                throw new WrongArgFlowException("No middle argument at: " + getVicinity(i));
            }
        }
        for (int i = since - 1; i >= from; i--) {
            if (Character.isDigit(expression[i]) || Character.isLetter(expression[i]) || expression[i] == ')'
                    || expression[i] == '-') {
                break;
            }
            if (operatorsCh.contains(expression[i]) || expression[i] == '%') {
                throw new WrongArgFlowException("No middle argument at: " + getVicinity(i));
            }
        }
    }

    private int findCloseBracketPosition(int since) {
        int bracketCount = 0;
        for (int i = since; i < expression.length; i++) {
            if (expression[i] == ')') {
                bracketCount--;
                if (bracketCount == 0) {
                    return i;
                }
            } else if (expression[i] == '(') {
                bracketCount++;
            }
        }
        throw new IllegalStateException();
    }

    private TripleExpression<T> unwrapNegationIfExistsAndParse(int leftBorder, int rightBorder,
            boolean hasNegotiation) {
        if (expression[leftBorder] == '-') {
            return new NegativeWrapper<>(unwrapNegationIfExistsAndParse(leftBorder + 1, rightBorder, true));
        } else if (expression[leftBorder] == 'l') {
            return new Log<>(unwrapNegationIfExistsAndParse(leftBorder + 1, rightBorder, false));
        } else if (expression[leftBorder] == 'p') {
            return new Pow<>(unwrapNegationIfExistsAndParse(leftBorder + 1, rightBorder, false));
        } else if (expression[leftBorder] == 'c') {
            return new Count<>(unwrapNegationIfExistsAndParse(leftBorder + 1, rightBorder, false));
        } else {
            if (hasNextParticularSymbol(leftBorder, Character::isDigit)) {
                return parseVal(leftBorder, rightBorder, hasNegotiation);
            } else if (hasNextParticularSymbol(leftBorder, Character::isLetter)) {
                if (hasNextParticularSymbol(leftBorder, (Character x) -> !(x == 'x' || x == 'y' || x == 'z'))) {
                    throw new TypoException(
                            "Unacceptable variable name \'" + expression[leftBorder] + "\' at position " + leftBorder);
                }
                return parseVar(leftBorder);
            } else if (hasNextParticularSymbol(leftBorder, (Character a) -> a.equals('('))) {
                int closeBracket = findCloseBracketPosition(leftBorder);
                return parseExpression(ParseLevel.LEFT_RIGHT, leftBorder + 1, closeBracket - 1);
            }
        }
        throw new WrongOpFlowException("Wrong Operand flow at vicinity: " + getVicinity(leftBorder));
    }

    private TripleExpression<T> levelDecider(ParseLevel level, int leftBorder, int rightBorder) {
        if (level == ParseLevel.TERMINAL) {
            return unwrapNegationIfExistsAndParse(leftBorder, rightBorder, false);
        }
        return parseExpression(level, leftBorder, rightBorder);
    }

    private String getVicinity(int center) {
        StringBuilder vicinity = new StringBuilder();
        String POINTER_FROM_RIGHT = "<--";
        String POINTER_FROM_LEFT = "-->";

        int i = center, j = 3;
        while (i > 0 && j > 0) {
            i--;
            j--;
        }
        for (; i < center; i++) {
            vicinity.append(expression[i]);
        }
        vicinity.append(POINTER_FROM_LEFT);
        vicinity.append(expression[i]);
        i++;
        vicinity.append(POINTER_FROM_RIGHT);
        j = 3;
        for (; i < expression.length && j >= 0; i++, j--) {
            vicinity.append(expression[i]);
        }
        return vicinity.toString();
    }

    private static class MyPair {
        private int position;
        private Operators opType;

        MyPair(int position, Operators opType) {
            this.position = position;
            this.opType = opType;
        }
    }
}
