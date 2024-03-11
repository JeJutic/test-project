package pan.artem.test.dto.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterForm(@NotEmpty String username,
                           @NotEmpty String password,
                           @NotNull String role) {
}
