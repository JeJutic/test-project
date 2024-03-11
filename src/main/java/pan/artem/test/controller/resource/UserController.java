package pan.artem.test.controller.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pan.artem.test.dto.user.UserCreateDto;
import pan.artem.test.dto.user.UserDto;
import pan.artem.test.service.resource.UserServiceWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController extends ResourceController<UserDto, UserCreateDto> {
    public UserController(UserServiceWrapper externalService) {
        super(externalService);
    }

    @Operation(summary = "Retrieves all users")
    @GetMapping
    @Override
    public ResponseEntity<List<UserDto>> getAll() {
        return super.getAll();
    }

    @Operation(
            summary = "Creates new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "New user created"
                    )
            }
    )
    @PostMapping
    @Override
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateDto userCreateDto) {
        return super.create(userCreateDto);
    }

    @Operation(
            summary = "Retrieves user with specified id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserDto> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Operation(summary = "Updates user with specified id")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<UserDto> put(@PathVariable("id") @NotNull Integer id,
                                        @Valid @RequestBody UserCreateDto userCreateDto) {
        return super.put(id, userCreateDto);
    }

    @Operation(summary = "Deletes user with specified id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") @NotNull Integer id) {
        return super.delete(id);
    }
}
