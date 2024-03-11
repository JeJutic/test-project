package pan.artem.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pan.artem.test.dto.admin.RegisterForm;
import pan.artem.test.service.security.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Operation(
            summary = "Registers new user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "User with specified nickname already exists"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Role not found"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterForm form
    ) {
        userService.registerUser(form.username(), form.password(), form.role());
        return ResponseEntity.ok().build();
    }
}
