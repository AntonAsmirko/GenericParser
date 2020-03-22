package exceptions.myExceptions;

public class WrongBracketSequenceException extends IllegalStateException {
    public WrongBracketSequenceException(String message) {
        super(message);
    }
}
