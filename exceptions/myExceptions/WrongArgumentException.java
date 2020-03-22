package exceptions.myExceptions;

public class WrongArgumentException extends IllegalStateException {
    public WrongArgumentException(String message){
        super(message);
    }
}
