package pan.artem.test.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(@NotNull Integer id,
                      String name,
                      String username,
                      AddressDto address,
                      String phone,
                      String website,
                      CompanyDto company) {
}
