package microservice.POC.SPQR.exceptions;

public class InvalidCredentialsException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}