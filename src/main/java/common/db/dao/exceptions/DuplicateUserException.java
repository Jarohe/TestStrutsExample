package common.db.dao.exceptions;

public class DuplicateUserException extends Exception {
    public DuplicateUserException() {
        super();
    }

    public DuplicateUserException(String s) {
        super(s);
    }
}
