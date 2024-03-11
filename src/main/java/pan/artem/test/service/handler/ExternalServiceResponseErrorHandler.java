package pan.artem.test.service.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import pan.artem.test.exception.ResourceNotFoundException;
import pan.artem.test.exception.externalservice.ExternalServiceClientErrorException;
import pan.artem.test.exception.externalservice.ExternalServiceInternalErrorException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExternalServiceResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        var statusCode = response.getStatusCode();

        if (statusCode.is5xxServerError()) {
            throw new ExternalServiceInternalErrorException(response.getStatusText());
        } else if (statusCode.is4xxClientError()) {
            var message = IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);

            if (statusCode == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException(message);
            } else {
                throw new ExternalServiceClientErrorException(
                        message, statusCode.value()
                );
            }
        }
    }
}
