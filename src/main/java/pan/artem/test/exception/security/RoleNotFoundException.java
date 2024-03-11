package pan.artem.test.exception.security;

import pan.artem.test.exception.ResourceNotFoundException;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
