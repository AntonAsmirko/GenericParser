package exceptions.myExceptions;

public class WrongOpFlowException extends IllegalStateException {
    public WrongOpFlowException(String message){
        super(message);
    }
}
