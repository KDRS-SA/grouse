package no.kdrs.grouse.utils.exception;

public class ConcurrencyException
        extends RuntimeException {

    public ConcurrencyException(String message) {
        super(message);
    }
}
