package exceptions.myExceptions;

public class UnacceptableSymbolException extends IllegalStateException {
    public UnacceptableSymbolException(String message){
        super(message);
    }
}
