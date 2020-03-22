package exceptions.myExceptions;

public class WrongArgFlowException extends IllegalStateException {
    public WrongArgFlowException(String message){
        super(message);
    }
}
