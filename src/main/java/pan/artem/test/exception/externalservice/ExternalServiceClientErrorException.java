package pan.artem.test.exception.externalservice;

import lombok.Getter;

@Getter
public class ExternalServiceClientErrorException extends ExternalServiceException {

    private final int statusCode;

    public ExternalServiceClientErrorException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
