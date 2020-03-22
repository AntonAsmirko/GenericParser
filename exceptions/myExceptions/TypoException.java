package exceptions.myExceptions;

public class TypoException extends IllegalStateException {
    public TypoException(String message){
        super(message);
    }
}
