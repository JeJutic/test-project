package pan.artem.test.exception;

public class ResourceNotFoundException extends MyAppException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
