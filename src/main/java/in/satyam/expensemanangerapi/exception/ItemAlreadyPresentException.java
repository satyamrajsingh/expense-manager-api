package in.satyam.expensemanangerapi.exception;

public class ItemAlreadyPresentException extends RuntimeException {
    private static final long serialVersionUID =1L;

    public ItemAlreadyPresentException(String message) {
        super(message);
    }
}
