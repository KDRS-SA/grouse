package no.kdrs.grouse.utils.exception;

/**
 * Created by tsodring on 28/03/18.
 * <p>
 * Just a simple way to identify various Exceptions the application can throw.
 * <p>
 * This one is used when creating a GrouseUser account, but you want to tell the
 * client the user already exists
 */
public class UserAlreadyExistsException
        extends RuntimeException {

    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
