package exceptions.myExceptions;

public class ToLargeMagnitudeException extends IllegalStateException {
    public ToLargeMagnitudeException(String message){
        super(message);
    }
}
