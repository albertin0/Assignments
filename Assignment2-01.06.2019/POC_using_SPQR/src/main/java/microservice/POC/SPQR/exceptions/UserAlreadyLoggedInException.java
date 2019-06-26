package microservice.POC.SPQR.exceptions;

public class UserAlreadyLoggedInException extends Exception{
    private static final long serialVersionUID = 1L;

    public UserAlreadyLoggedInException() {
        super("User is already logged in");
    }

}
