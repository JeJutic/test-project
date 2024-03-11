package pan.artem.test.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pan.artem.test.dto.ErrorInfo;
import pan.artem.test.exception.MyAppException;
import pan.artem.test.exception.ResourceNotFoundException;
import pan.artem.test.exception.security.UserAlreadyExistsException;
import pan.artem.test.exception.externalservice.ExternalServiceInternalErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MyAppException.class)
    public ResponseEntity<ErrorInfo> handleMyAppException(HttpServletRequest request, MyAppException e) {
        logger.warn("Uncaught exception from request: {}", request.getRequestURL(), e);

        return ResponseEntity.internalServerError().body(
                new ErrorInfo(request.getRequestURL().toString(),
                        "Internal error occurred")
        );
    }

    public static String methodArgumentNotValidString(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        e.getFieldErrors()
                .forEach(fieldError ->
                        sb.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage())
                );
        return sb.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleMethodArgumentNotValid(
            HttpServletRequest request, MethodArgumentNotValidException e
    ) {
        return ResponseEntity.badRequest().body(
                new ErrorInfo(
                        request.getRequestURL().toString(),
                        "Data format is violated: " +
                                GlobalExceptionHandler.methodArgumentNotValidString(e)
                )
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFound(
            HttpServletRequest request, ResourceNotFoundException e
    ) {
        return ResponseEntity.status(404).body(
                new ErrorInfo(request.getRequestURL().toString(),
                        "Specified resource was not found: " + e.getMessage())
        );
    }

    @ExceptionHandler(ExternalServiceInternalErrorException.class)
    public ResponseEntity<ErrorInfo> handleInternalError(HttpServletRequest request) {
        return ResponseEntity.status(503).body(
                new ErrorInfo(
                        request.getRequestURL().toString(),
                        "Service is temporarily unavailable"
                )
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> handleUserAlreadyExists(
            HttpServletRequest request,
            AuthenticationException e
    ) {
        return ResponseEntity.status(401).body(
                new ErrorInfo(request.getRequestURL().toString(),
                        e.getMessage()
                )
        );
    }
}
